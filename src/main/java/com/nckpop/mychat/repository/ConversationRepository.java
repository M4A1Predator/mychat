package com.nckpop.mychat.repository;

import com.nckpop.mychat.entity.Conversation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConversationRepository extends MongoRepository<Conversation, String> {
    @Query("{'users': ObjectId('$0')}")
    List<Conversation> findUserConversations(String userId);

}
