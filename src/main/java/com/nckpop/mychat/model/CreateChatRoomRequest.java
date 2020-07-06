package com.nckpop.mychat.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
public class CreateChatRoomRequest {

    @NotBlank
    private String userId;

}
