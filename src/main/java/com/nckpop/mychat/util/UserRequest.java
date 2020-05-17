package com.nckpop.mychat.util;

import lombok.Data;

@Data
public class UserRequest {

    public UserRequest() {
    }

    public UserRequest(String userId) {
        this.userId = userId;
    }

    private String userId;

}
