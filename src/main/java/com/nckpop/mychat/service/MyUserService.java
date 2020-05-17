package com.nckpop.mychat.service;

import com.nckpop.mychat.entity.CustomUser;
import com.nckpop.mychat.model.JwtRequest;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
public interface MyUserService {

    CustomUser createUser(JwtRequest request);

    CustomUser getUser(String id);

}
