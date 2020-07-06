package com.nckpop.mychat.model;

import lombok.Data;

@Data
public class SearchOldMessagesDto {

    private String conversationId;
    private int page;

}
