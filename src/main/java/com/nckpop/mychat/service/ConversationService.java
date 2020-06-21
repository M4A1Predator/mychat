package com.nckpop.mychat.service;

import com.nckpop.mychat.entity.Conversation;
import com.nckpop.mychat.entity.Message;
import com.nckpop.mychat.model.ConversationDto;
import com.nckpop.mychat.model.MessageDto;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public interface ConversationService {

    Conversation createConversation(Collection<String> userIds);

    Message saveMessage(MessageDto messageDto) throws Exception;

    List<String> getConversationUserIds(String conId);

    List<ConversationDto> getConversationsByUser(String userId);

}
