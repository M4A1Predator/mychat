package com.nckpop.mychat.controller;

import com.nckpop.mychat.constant.JwtConstant;
import com.nckpop.mychat.entity.Conversation;
import com.nckpop.mychat.model.CreateChatRoomRequest;
import com.nckpop.mychat.model.CreateChatRoomResponse;
import com.nckpop.mychat.model.MessageDto;
import com.nckpop.mychat.model.SearchOldMessagesDto;
import com.nckpop.mychat.service.ConversationService;
import com.nckpop.mychat.service.JwtUtil;
import com.nckpop.mychat.service.MyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;

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
    public ResponseEntity create(@RequestHeader(value = JwtConstant.JWT_HEADER) String tokenHeader,
                                 @Valid @RequestBody CreateChatRoomRequest request) {
        String userId = jwtTokenUtil.getUserIdFromToken(tokenHeader.substring(6));
        var user = userService.getUser(userId);

        // check is room exist
        Conversation existConversation = conversationService.getConversationByUsers(user.get_id().toString(), request.getUserId());
        if (existConversation != null) {
            var response = new CreateChatRoomResponse();
            response.setResult("found");
            response.setConversationId(existConversation.get_id().toString());
            return ResponseEntity.ok(response);
        }

        var con = conversationService.createConversation(userId, Collections.singletonList(request.getUserId()));
        var response = new CreateChatRoomResponse();
        response.setResult("new");
        response.setConversationId(con.get_id().toString());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{conId}/messages")
    public ResponseEntity getOldMessages(@PathVariable("conId") String conId, @RequestParam int page){
        var searchOldMsgDto = new SearchOldMessagesDto();
        searchOldMsgDto.setConversationId(conId);
        searchOldMsgDto.setPage(page);
        List<MessageDto> messageDtoList = conversationService.getOldMessages(searchOldMsgDto);
        return ResponseEntity.ok(messageDtoList);
    }

    @GetMapping("/{conId}/users")
    public ResponseEntity getUsers(@PathVariable("conId")String conId) {
        var users = conversationService.getConversationUsers(conId);
        return ResponseEntity.ok(users);
    }

}
