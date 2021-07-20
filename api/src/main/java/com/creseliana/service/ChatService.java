package com.creseliana.service;

public interface ChatService {
    void sendMessageToChat(String username, Long id, String message);

    void sendMessageToUser(String senderUsername, String receiverUsername, String message);
}
