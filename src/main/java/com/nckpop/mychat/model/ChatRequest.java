package com.nckpop.mychat.model;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ChatRequest {

    public String conversationId;
    @NotNull
    public String body;

}
