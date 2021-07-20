package com.creseliana.service;

import com.creseliana.model.Chat;
import com.creseliana.model.Message;
import com.creseliana.model.User;
import com.creseliana.repository.ChatRepository;
import com.creseliana.repository.MessageRepository;
import com.creseliana.repository.UserRepository;
import com.creseliana.service.exception.ad.AccessException;
import com.creseliana.service.exception.chat.ChatNotFoundException;
import com.creseliana.service.exception.user.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Log4j2
@RequiredArgsConstructor
@Transactional
@Service
public class BaseChatService implements ChatService {
    private static final String MSG_USER_NOT_FOUND_BY_USERNAME = "There is no user with username '%s'";
    private static final String MSG_CHAT_NOT_FOUND_BY_ID = "There is no chat with id '%s'";

    private final ChatRepository chatRepository;
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    @Override
    public void sendMessageToChat(String username, Long id, String message) {
        checkMessage(message);
        User user = getUserByUsername(username);
        Chat chat = getChatById(id);
        checkChat(user, chat);
        Message newMessage = new Message(chat, user,
                LocalDateTime.now(), message, false);
        messageRepository.save(newMessage);
    }

    @Override
    public void sendMessageToUser(String senderUsername, String receiverUsername, String message) {
        User sender = getUserByUsername(senderUsername);
        User receiver = getUserByUsername(receiverUsername);
        //todo check chat existence
        Chat chat = new Chat();
        Set<User> users = new HashSet<>();
        Collections.addAll(users, sender, receiver);
        chat.setUsers(users);
        //todo continue
    }

    private Chat getChatById(Long id) {
        return chatRepository.findById(id).orElseThrow(() -> {
            String msg = String.format(MSG_CHAT_NOT_FOUND_BY_ID, id);
            log.info(msg);
            return new ChatNotFoundException(msg);
        });
    }

    private User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> {
            String msg = String.format(MSG_USER_NOT_FOUND_BY_USERNAME, username);
            log.info(msg);
            return new UserNotFoundException(msg);
        });
    }

    private void checkMessage(String message) {
        if (message.isBlank()) {
            throw new RuntimeException(); //todo handle
        }
    }

    private void checkChat(User user, Chat chat) {
        if (!chat.getUsers().contains(user)) {
            throw new AccessException(); //todo handle
        }
    }

//    private void checkUser(String username, Advertisement ad) {
//        String authorUsername = ad.getAuthor().getUsername();
//        if (!username.equals(authorUsername)) {
//            log.info(MSG_ACCESS_DENIED_USER_MISMATCH);
//            log.debug(String.format(MSG_AD_DETAILS, ad.getId(), authorUsername, username));
//            throw new AccessException(MSG_ACCESS_DENIED_USER_MISMATCH);
//        }
//    }
}
