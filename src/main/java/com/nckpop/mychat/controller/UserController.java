package com.nckpop.mychat.controller;

import com.nckpop.mychat.model.JwtRequest;
import com.nckpop.mychat.model.JwtResponse;
import com.nckpop.mychat.service.JwtUtil;
import com.nckpop.mychat.service.MyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.ServerResponse;

@RequestMapping("/user")
@RestController
@CrossOrigin
public class UserController {

    @Autowired
    private MyUserService myUserService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody JwtRequest request) {
        var u = myUserService.createUser(request);
        return ResponseEntity.ok(u);
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody JwtRequest request) {
        try{
            final Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }catch(AuthenticationException e){

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        final JwtResponse token = jwtTokenUtil.generateToken(request.getUsername());
        // Return the token
        return ResponseEntity.ok(token);
    }

}
