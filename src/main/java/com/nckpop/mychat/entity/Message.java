package com.nckpop.mychat.entity;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

@Data
public class Message {

    @Id
    private ObjectId _id;
    private String body;
    @DBRef
    private CustomUser owner;

    private String conversationId;

}
