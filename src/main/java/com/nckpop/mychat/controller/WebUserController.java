package com.nckpop.mychat.controller;

import com.nckpop.mychat.service.WebUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/users")
@RestController
public class WebUserController {

    @Autowired
    private WebUserService webUserService;

    @GetMapping
    public ResponseEntity getUsers() {
        var users = webUserService.getUsers();
        return ResponseEntity.ok(users);
    }

}
