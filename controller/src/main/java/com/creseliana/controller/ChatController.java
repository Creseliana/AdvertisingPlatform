package com.creseliana.controller;

import com.creseliana.dto.ChatShortResponse;
import com.creseliana.dto.MessageResponse;
import com.creseliana.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping("/chats")
public class ChatController {

    private final ChatService chatService;

    @GetMapping
    public ResponseEntity<List<ChatShortResponse>> getChats(Authentication authentication,
                                                            @RequestParam int page,
                                                            @RequestParam int amount) {
        List<ChatShortResponse> chats = chatService.getUserChats(authentication.getName(), page, amount);
        return ResponseEntity.ok(chats);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<MessageResponse>> getMessages(Authentication authentication,
                                                             @PathVariable Long id,
                                                             @RequestParam int page,
                                                             @RequestParam int amount) {
        List<MessageResponse> messages = chatService.getChatMessages(authentication.getName(), id, page, amount);
        return ResponseEntity.ok(messages);
    }

    @PostMapping("/{id}")
    public ResponseEntity<Void> sendMessageToChat(Authentication authentication,
                                                  @PathVariable Long id,
                                                  @RequestBody String message) {
        chatService.sendMessageToChat(authentication.getName(), id, message);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{username}")
    public ResponseEntity<Void> sendMessageToUser(Authentication authentication,
                                                  @PathVariable String username,
                                                  @RequestBody String message) {
        chatService.sendMessageToUser(authentication.getName(), username, message);
        return ResponseEntity.noContent().build();
    }
}
