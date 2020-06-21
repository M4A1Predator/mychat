package com.nckpop.mychat.service;

import com.nckpop.mychat.entity.Conversation;
import com.nckpop.mychat.entity.CustomUser;
import com.nckpop.mychat.entity.Message;
import com.nckpop.mychat.model.ConversationDto;
import com.nckpop.mychat.model.MessageDto;
import com.nckpop.mychat.model.UserDto;
import com.nckpop.mychat.repository.ConversationRepository;
import com.nckpop.mychat.repository.MessageRepository;
import com.nckpop.mychat.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ConversationServiceImpl implements ConversationService {

    @Autowired
    private ConversationRepository conversationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MyUserService userService;

    @Autowired
    private MessageRepository messageRepository;

    @Override
    public Conversation createConversation(Collection<String> userIds) {
        var con = new Conversation();
        List<ObjectId> users = userIds.stream().map(id -> new ObjectId(id.getBytes())).collect(Collectors.toList());
        con.setUsers(users);
        return conversationRepository.insert(con);
    }

    @Override
    public Message saveMessage(MessageDto messageDto) throws Exception {
        // get conversation
        var con = conversationRepository.findById(messageDto.getConversationId()).orElse(null);
        if (con == null) {
            throw new Exception("Invalid conversation id");
        }

        // check user
        var user = userService.getUser(messageDto.getFrom());
        if (user == null || con.getUsers().isEmpty() || !con.getUsers().contains(messageDto.getFrom())) {
            throw new Exception("Invalid user id");
        }

        // save message
        var message = new Message();
        message.setBody(messageDto.getBody());
        message.setConversationId(con.get_id().toString());
        message.setOwner(user);

        return messageRepository.save(message);
    }

    @Override
    public List<String> getConversationUserIds(String conId) {
        var con = conversationRepository.findById(conId).orElse(null);
        if (con == null) {
            return null;
        }

        return con.getUsers().stream().map(ObjectId::toString).collect(Collectors.toList());
    }

    @Override
    public List<ConversationDto> getConversationsByUser(String userId) {
        var cons = conversationRepository.findAll();
        List<ConversationDto> conrs = cons.stream().map(c -> {
            var conr = new ConversationDto();
            conr.setId(c.get_id().toString());
            conr.setUsers(c.getUsers().stream().map(u -> {
                Optional<CustomUser> customUser = userRepository.findById(u.toString());
                if (!customUser.isPresent()) {
                    return null;
                }
                var userDto = new UserDto();
                userDto.setUserId(customUser.get().get_id().toString());
                userDto.setUsername(customUser.get().getUsername());
                return userDto;
            }).collect(Collectors.toList()));
            return conr;
        }).collect(Collectors.toList());
        return conrs;
    }
}
