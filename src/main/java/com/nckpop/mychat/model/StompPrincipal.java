package com.nckpop.mychat.model;

import lombok.Data;

import java.security.Principal;

@Data
public class StompPrincipal implements Principal {
    private String name;
    private String userId;

    public StompPrincipal(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

}
