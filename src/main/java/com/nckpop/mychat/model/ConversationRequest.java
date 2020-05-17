package com.nckpop.mychat.model;

import lombok.Data;

import java.util.List;

@Data
public class ConversationRequest {

    private List<String> userIds;

}
