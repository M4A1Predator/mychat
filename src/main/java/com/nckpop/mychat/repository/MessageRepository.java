package com.nckpop.mychat.repository;

import com.nckpop.mychat.entity.Message;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends MongoRepository<Message, String> {

    Page<Message> findByConversationId(ObjectId conversationId, Pageable pageable);

}
