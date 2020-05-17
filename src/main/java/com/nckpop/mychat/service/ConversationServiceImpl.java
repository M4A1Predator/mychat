package com.nckpop.mychat.service;

import com.nckpop.mychat.entity.Conversation;
import com.nckpop.mychat.repository.ConversationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class ConversationServiceImpl implements ConversationService {

    @Autowired
    private ConversationRepository conversationRepository;

    @Override
    public Conversation createConversation(Collection<String> userIds) {
        var con = new Conversation();
        con.setUsers(userIds);
        return conversationRepository.insert(con);
    }
}
