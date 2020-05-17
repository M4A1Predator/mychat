package com.nckpop.mychat.entity;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import java.util.Collection;

@Data
public class Conversation {

    @Id
    private ObjectId _id;

    private Collection<String> users;

}
