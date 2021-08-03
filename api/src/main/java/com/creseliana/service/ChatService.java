package com.creseliana.service;

import com.creseliana.dto.ChatShortResponse;
import com.creseliana.dto.MessageResponse;

import java.util.List;

/**
 * Service for {@link com.creseliana.model.Chat}
 */
public interface ChatService {
    /**
     * Sends message to chat by its id from the user
     *
     * @param username the name of the sender
     * @param id       the chat id
     * @param message  the string text of the message
     */
    void sendMessageToChat(String username, Long id, String message);

    /**
     * Sends message to the user by its username from the authorized user
     *
     * @param senderUsername   the name of the sender
     * @param receiverUsername the name of the receiver
     * @param message          the string text of the message
     */
    void sendMessageToUser(String senderUsername, String receiverUsername, String message);

    /**
     * Gets limited amount of user's chats
     *
     * @param username the name of the user whose chats are
     * @param page     the number of the page that contains certain amount of chats
     * @param amount   the number of chats on page
     * @return List of DTOs of the {@link com.creseliana.model.Chat}
     */
    List<ChatShortResponse> getUserChats(String username, int page, int amount);

    /**
     * Gets limited amount of chat's messages
     *
     * @param username the name of the user whose chat is
     * @param id       the needed chat id
     * @param page     the number of the page that contains certain amount of messages
     * @param amount   the number of messages on page
     * @return List of DTOs of the {@link com.creseliana.model.Message}
     */
    List<MessageResponse> getChatMessages(String username, Long id, int page, int amount);
}
