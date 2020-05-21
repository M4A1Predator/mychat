package com.nckpop.mychat.controller;

import com.nckpop.mychat.model.ChatRequest;
import com.nckpop.mychat.model.MessageDto;
import com.nckpop.mychat.model.OutputMessage;
import com.nckpop.mychat.service.ConversationService;
import com.nckpop.mychat.util.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.security.Principal;

@RequestMapping("/chat")
@RestController
@CrossOrigin
public class MessageController {

    @Resource(name = "initUserRequest")
    private UserRequest userRequest;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private ConversationService conversationService;

    @PostMapping
    public ResponseEntity sendMeesage(@RequestBody @Valid ChatRequest request) {
        System.out.println(userRequest.getUserId());
        return ResponseEntity.ok().build();
    }

    @MessageMapping("/send")
    @SendToUser("/topic/chat")
    public OutputMessage send(@RequestBody @Valid ChatRequest request, Principal principal, SimpMessageHeaderAccessor accessor) {
        var output = new OutputMessage("", request.getBody());
        var messageDto = new MessageDto();
        messageDto.setConversationId(principal.getName());
        messageDto.setBody(request.getBody());
        conversationService.saveMessage(messageDto);
        // messagingTemplate.convertAndSendToUser(principal.getName(), "/topic/chat", output);
        return output;
    }

}
