package com.nckpop.mychat.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/hello")
@RestController
@CrossOrigin
public class HelloController {

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity hello() {
        return ResponseEntity.ok().build();
    }

}
