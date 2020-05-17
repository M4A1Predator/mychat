package com.nckpop.mychat.service;

import com.nckpop.mychat.entity.CustomUser;
import com.nckpop.mychat.model.JwtRequest;
import com.nckpop.mychat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class MyUserServiceImpl implements MyUserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public CustomUser createUser(JwtRequest request) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        var pwd = passwordEncoder.encode(request.getPassword());
        var role = new SimpleGrantedAuthority("USER");
        var user = new CustomUser(request.getUsername(), pwd, Collections.singletonList(role));
        userRepository.insert(user);
        return user;
    }

    @Override
    public CustomUser getUser(String id) {
        return userRepository.findById(id).orElse(null);
    }
}
