package com.nckpop.mychat.controller;

import com.nckpop.mychat.constant.JwtConstant;
import com.nckpop.mychat.service.ConversationService;
import com.nckpop.mychat.service.JwtUtil;
import com.nckpop.mychat.service.MyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RequestMapping("/conversations")
@RestController
@CrossOrigin
public class ConversationController {

    @Autowired
    @Qualifier(value = "jwtUtilWithoutDbCheckImpl")
    private JwtUtil jwtTokenUtil;

    @Autowired
    private MyUserService userService;

    @Autowired
    private ConversationService conversationService;

    @GetMapping
    public ResponseEntity getList(@RequestHeader(value = JwtConstant.JWT_HEADER) String tokenHeader) {
        String userId = jwtTokenUtil.getUserIdFromToken(tokenHeader.substring(6));
        var user = userService.getUser(userId);

        var cons = conversationService.getConversationsByUser(userId);
        return ResponseEntity.ok(cons);
    }

    @PostMapping
    public ResponseEntity create(@RequestHeader(value = JwtConstant.JWT_HEADER) String tokenHeader) {
        String userId = jwtTokenUtil.getUserIdFromToken(tokenHeader.substring(6));
        var user = userService.getUser(userId);
        var con = conversationService.createConversation(Collections.singletonList(user.get_id().toString()));
        return ResponseEntity.ok(con);
    }

}
