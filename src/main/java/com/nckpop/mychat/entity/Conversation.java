package com.nckpop.mychat.entity;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import java.util.Collection;
import java.util.Date;

@Data
public class Conversation {

    @Id
    private ObjectId _id;

    // private Collection<String> users;
    private Collection<ObjectId> users;
    private ObjectId owner;
    private Date createdAt;
    private Date lastActionAt;

}
