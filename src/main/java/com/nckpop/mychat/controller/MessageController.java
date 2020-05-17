package com.nckpop.mychat.controller;

import com.nckpop.mychat.model.ChatRequest;
import com.nckpop.mychat.util.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RequestMapping("/chat")
@RestController
@CrossOrigin
public class MessageController {

    @Resource(name = "initUserRequest")
    private UserRequest userRequest;

    @PostMapping
    public ResponseEntity sendMeesage(@RequestBody ChatRequest request) {
        System.out.println(userRequest.getUserId());
        return ResponseEntity.ok().build();
    }

}
