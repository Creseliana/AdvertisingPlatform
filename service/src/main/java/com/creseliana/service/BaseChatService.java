package com.creseliana.service;

import com.creseliana.dto.ChatShortResponse;
import com.creseliana.dto.MessageResponse;
import com.creseliana.model.Chat;
import com.creseliana.model.Message;
import com.creseliana.model.User;
import com.creseliana.repository.ChatRepository;
import com.creseliana.repository.MessageRepository;
import com.creseliana.repository.UserRepository;
import com.creseliana.service.exception.AccessException;
import com.creseliana.service.exception.chat.ChatNotFoundException;
import com.creseliana.service.exception.message.MessageFormatException;
import com.creseliana.service.util.StartCount;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log4j2
@RequiredArgsConstructor
@Transactional
@Service
public class BaseChatService extends BaseModelService implements ChatService {
    private static final String MSG_CHAT_NOT_FOUND_BY_ID = "There is no chat with id '%s'";
    private static final String MSG_MESSAGE_IS_BLANK = "Message is empty or contains only white spaces";
    private static final String MSG_USER_NOT_IN_CHAT = "User with id '%s' is not present in chat with id '%s'";
    private static final String MSG_USER_MESSAGE_TO_HIMSELF = "User cannot send message to himself";

    private final ChatRepository chatRepository;
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final ModelMapper mapper;

    @Override
    public void sendMessageToChat(String username, Long id, String message) {
        checkMessage(message);

        User user = getUserByUsername(username, userRepository);
        Chat chat = getChatById(id);

        checkUserPresenceInChat(user, chat);

        Message newMessage = new Message(chat, user,
                LocalDateTime.now(), message, false);
        messageRepository.save(newMessage);
    }

    @Override
    public void sendMessageToUser(String senderUsername, String receiverUsername, String message) {
        checkMessage(message);
        checkUsers(senderUsername, receiverUsername);

        Chat chat;
        User sender = getUserByUsername(senderUsername, userRepository);
        User receiver = getUserByUsername(receiverUsername, userRepository);
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

    @Override
    public List<ChatShortResponse> getUserChats(String username, int page, int amount) {
        User user = getUserByUsername(username, userRepository);
        int start = StartCount.count(page, amount);
        List<Chat> chats = chatRepository.getChatsByUserId(user.getId(), start, amount);
        List<ChatShortResponse> chatsShort = new ArrayList<>();
        String anotherUsername;
        for (Chat chat : chats) {
            anotherUsername = chat.getFirstUser().getUsername().equals(username) ?
                    chat.getSecondUser().getUsername() :
                    chat.getFirstUser().getUsername();
            chatsShort.add(new ChatShortResponse(chat.getId(), anotherUsername));
        }
        return chatsShort;
    }

    @Override
    public List<MessageResponse> getChatMessages(String username, Long id, int page, int amount) {
        User user = getUserByUsername(username, userRepository);
        Chat chat = getChatById(id);

        checkUserPresenceInChat(user, chat);

        int start = StartCount.count(page, amount);
        List<Message> messages = messageRepository.getMessagesByChatId(id, start, amount);
        return mapList(mapper, messages, MessageResponse.class);
    }

    private Chat getChatById(Long id) {
        return chatRepository.findById(id).orElseThrow(() -> {
            String msg = String.format(MSG_CHAT_NOT_FOUND_BY_ID, id);
            log.info(msg);
            return new ChatNotFoundException(msg);
        });
    }

    private void checkMessage(String message) {
        if (message == null || message.isBlank()) {
            log.info(MSG_MESSAGE_IS_BLANK);
            throw new MessageFormatException(MSG_MESSAGE_IS_BLANK);
        }
    }

    private void checkUsers(String senderUsername, String receiverUsername) {
        if (senderUsername.equals(receiverUsername)) {
            log.info(MSG_USER_MESSAGE_TO_HIMSELF);
            throw new MessageFormatException(MSG_USER_MESSAGE_TO_HIMSELF);
        }
    }

    private void checkUserPresenceInChat(User user, Chat chat) {
        Long userId = user.getId();

        if (!chat.getFirstUser().getId().equals(userId) &&
                !chat.getSecondUser().getId().equals(userId)) {
            String msg = String.format(MSG_USER_NOT_IN_CHAT, userId, chat.getId());
            log.info(msg);
            throw new AccessException(msg);
        }
    }
}
