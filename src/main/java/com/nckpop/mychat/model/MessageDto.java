package com.nckpop.mychat.model;

import lombok.Data;

@Data
public class MessageDto {

    private String conversationId;
    private String body;
    private String from;

}
