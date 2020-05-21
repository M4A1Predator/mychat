package com.nckpop.mychat.service;

import com.nckpop.mychat.entity.Conversation;
import com.nckpop.mychat.model.MessageDto;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public interface ConversationService {

    Conversation createConversation(Collection<String> userIds);

    void saveMessage(MessageDto messageDto);

}
