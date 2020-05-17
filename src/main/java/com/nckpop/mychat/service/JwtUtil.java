package com.nckpop.mychat.service;

import com.nckpop.mychat.model.JwtResponse;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public interface JwtUtil {
    public String getIdFromToken(String token);

    public String getUserIdFromToken(String token);

    public String getUsernameFromToken(String token);

    public List getRolesFromToken(String token);

    public Date getCreatedDateFromToken(String token);

    public Date getExpirationDateFromToken(String token);

    public Claims getClaimsFromToken(String token);

    public Boolean isTokenExpired(String token);

    public Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset);

    public JwtResponse generateToken(String username);

    public JwtResponse refreshToken(String token);

    public Boolean validateToken(String token);
}
