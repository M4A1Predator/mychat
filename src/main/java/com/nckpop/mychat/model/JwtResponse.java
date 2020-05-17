package com.nckpop.mychat.model;

import lombok.Data;

@Data
public class JwtResponse {

    private String jwtToken;
    private String refreshToken;

    public JwtResponse(String jwtToken, String refreshToken) {
        this.jwtToken = jwtToken;
        this.refreshToken = refreshToken;
    }
}
