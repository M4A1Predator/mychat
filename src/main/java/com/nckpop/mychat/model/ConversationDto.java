package com.nckpop.mychat.model;

import lombok.Data;

import java.util.Collection;

@Data
public class ConversationDto {

    private String id;
    private Collection<UserDto> users;

}
