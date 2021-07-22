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
import java.util.Optional;

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

        checkUserPresenceInChat(user, chat);

        Message newMessage = new Message(chat, user,
                LocalDateTime.now(), message, false);
        messageRepository.save(newMessage);
    }

    @Override
    public void sendMessageToUser(String senderUsername, String receiverUsername, String message) {
        checkMessage(message);

        Chat chat;
        User sender = getUserByUsername(senderUsername);
        User receiver = getUserByUsername(receiverUsername);
        Optional<Chat> optionalChat = chatRepository.getChatByFirstUserIdAndSecondUserId
                (sender.getId(), receiver.getId());

        if (optionalChat.isEmpty()) {
            chat = new Chat(sender, receiver);
            chatRepository.save(chat);
        } else {
            chat = optionalChat.get();
        }

        Message newMessage = new Message(chat, sender,
                LocalDateTime.now(), message, false);
        messageRepository.save(newMessage);
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

    private void checkUserPresenceInChat(User user, Chat chat) {
        if (!chat.getFirstUser().getId().equals(user.getId()) &&
                !chat.getSecondUser().getId().equals(user.getId())) {
            throw new AccessException(); //todo handle
        }
    }
}
