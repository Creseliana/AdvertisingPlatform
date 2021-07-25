package com.creseliana.service;

import com.creseliana.dto.ChatShortResponse;
import com.creseliana.dto.MessageResponse;

import java.util.List;

public interface ChatService {
    void sendMessageToChat(String username, Long id, String message);

    void sendMessageToUser(String senderUsername, String receiverUsername, String message);

    List<ChatShortResponse> getUserChats(String username, int page, int amount);

    List<MessageResponse> getChatMessages(String username, Long id, int page, int amount);
}
