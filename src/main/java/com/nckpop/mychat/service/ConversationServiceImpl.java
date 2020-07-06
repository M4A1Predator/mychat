package com.nckpop.mychat.service;

import com.mongodb.MongoClient;
import com.nckpop.mychat.entity.Conversation;
import com.nckpop.mychat.entity.CustomUser;
import com.nckpop.mychat.entity.Message;
import com.nckpop.mychat.model.ConversationDto;
import com.nckpop.mychat.model.MessageDto;
import com.nckpop.mychat.model.SearchOldMessagesDto;
import com.nckpop.mychat.model.UserDto;
import com.nckpop.mychat.repository.ConversationRepository;
import com.nckpop.mychat.repository.MessageRepository;
import com.nckpop.mychat.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import javax.swing.text.html.Option;
import java.util.*;
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

    @Autowired
    private MongoClient mongoClient;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Conversation createConversation(String ownerId, Collection<String> userIds) {
        ObjectId ownerObjectId = new ObjectId(ownerId);
        List<ObjectId> chatUsers = new ArrayList<>();
        chatUsers.add(ownerObjectId);
        for(String userId : userIds) {
            chatUsers.add(new ObjectId(userId));
        }

        var con = new Conversation();
        con.setOwner(ownerObjectId);
        con.setUsers(chatUsers);
        con.setCreatedAt(new Date());
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
        boolean hasMatchedUserId = con.getUsers().stream().anyMatch(x -> x.toString().equals(messageDto.getFrom()));
        if (user == null || con.getUsers().isEmpty() || !hasMatchedUserId) {
            throw new Exception("Invalid user id");
        }

        // save message
        var message = new Message();
        message.setBody(messageDto.getBody());
        message.setConversationId(con.get_id());
        message.setCreatedAt(new Date());
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
        // var cons = conversationRepository.findUserConversations(userId);
        Query query = new Query();
        query.addCriteria(Criteria.where("users").is(new ObjectId(userId)));
        var cons = mongoTemplate.find(query, Conversation.class);
        return cons.stream().map(c -> {
            var conr = new ConversationDto();
            conr.setId(c.get_id().toString());
            conr.setUsers(c.getUsers().stream().map(u -> {
                Optional<CustomUser> customUser = userRepository.findById(u.toString());
                if (customUser.isEmpty()) {
                    return null;
                }
                var userDto = new UserDto();
                userDto.setUserId(customUser.get().get_id().toString());
                userDto.setUsername(customUser.get().getUsername());
                return userDto;
            }).collect(Collectors.toList()));
            return conr;
        }).collect(Collectors.toList());
    }

    public List<MessageDto> getOldMessages(SearchOldMessagesDto searchOldMessagesDto) {
        Pageable pageable = PageRequest.of(searchOldMessagesDto.getPage(), 100, Sort.by("createdAt").ascending());
        var pagedMessages = messageRepository
                .findByConversationId(new ObjectId(searchOldMessagesDto.getConversationId()), pageable);

        return pagedMessages.stream().map(m -> {
            var messageDto = new MessageDto();
            messageDto.setFrom(m.getOwner().get_id().toString());
            messageDto.setBody(m.getBody());
            messageDto.setConversationId(m.getConversationId().toHexString());
            messageDto.setFromName(m.getOwner().getUsername());
            messageDto.setId(m.get_id().toString());
            return messageDto;
        }).collect(Collectors.toList());
    }

    public List<UserDto> getConversationUsers(String conId) {
        Optional<Conversation> con = conversationRepository.findById(conId);
        if (con.isEmpty()) {
            return new ArrayList<>();
        }

        return con.get().getUsers().stream().map(u -> {
            var user = userRepository.findById(u.toString());
            if (user.isEmpty()) {
                return null;
            }

            var userDto = new UserDto();
            userDto.setUserId(user.get().get_id().toString());
            userDto.setUsername(user.get().getUsername());
            return userDto;
        }).filter(Objects::nonNull).collect(Collectors.toList());
    }

    @Override
    public Conversation getConversationByUsers(String id1, String id2) {
        List<ObjectId> ids = Arrays.asList(new ObjectId(id1), new ObjectId(id2));

        Query query = new Query();
//        query.addCriteria(Criteria.where("users").size(2).and("users").all(ids));
        query.addCriteria(Criteria.where("users").size(2).all(ids));
        return mongoTemplate.findOne(query, Conversation.class);
    }
}
