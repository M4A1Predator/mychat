package com.nckpop.mychat.service;

import com.nckpop.mychat.entity.Conversation;
import com.nckpop.mychat.entity.Message;
import com.nckpop.mychat.model.ConversationDto;
import com.nckpop.mychat.model.MessageDto;
import com.nckpop.mychat.model.SearchOldMessagesDto;
import com.nckpop.mychat.model.UserDto;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public interface ConversationService {

    Conversation createConversation(String ownerId, Collection<String> userIds);

    Message saveMessage(MessageDto messageDto) throws Exception;

    List<String> getConversationUserIds(String conId);

    List<ConversationDto> getConversationsByUser(String userId);

    List<MessageDto> getOldMessages(SearchOldMessagesDto searchOldMessagesDto);

    List<UserDto> getConversationUsers(String conId);

    Conversation getConversationByUsers(String id1, String id2);

}
