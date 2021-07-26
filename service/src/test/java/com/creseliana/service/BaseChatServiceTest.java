package com.creseliana.service;

import com.creseliana.model.Chat;
import com.creseliana.model.Message;
import com.creseliana.model.User;
import com.creseliana.repository.ChatRepository;
import com.creseliana.repository.MessageRepository;
import com.creseliana.repository.UserRepository;
import com.creseliana.service.exception.AccessException;
import com.creseliana.service.exception.chat.ChatNotFoundException;
import com.creseliana.service.exception.message.MessageFormatException;
import com.creseliana.service.exception.user.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class BaseChatServiceTest {

    private final ArgumentCaptor<Message> messageCaptor = ArgumentCaptor.forClass(Message.class);
    private final ArgumentCaptor<Chat> chatCaptor = ArgumentCaptor.forClass(Chat.class);
    @InjectMocks
    private BaseChatService chatService;
    @Mock
    private ChatRepository chatRepository;
    @Mock
    private MessageRepository messageRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ModelMapper mapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void sendMessageToChat() {
        String msg = "mew message";
        User user = new User();
        Chat chat = new Chat();
        user.setId(1L);
        chat.setFirstUser(user);
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
        when(chatRepository.findById(anyLong())).thenReturn(Optional.of(chat));
        assertDoesNotThrow(() -> chatService.sendMessageToChat("user", anyLong(), msg));
        verify(messageRepository, times(1)).save(any(Message.class));
        verify(messageRepository).save(messageCaptor.capture());
        Message message = messageCaptor.getValue();
        assertNotNull(message.getChat());
        assertNotNull(message.getSender());
        assertNotNull(message.getDate());
        assertNotNull(message.getMessage());
        assertEquals(message.getMessage(), msg);
    }

    @Test
    void sendMessageToChatWrongMessageFormat() {
        String message1 = "   ";
        String message2 = "";
        assertThrows(MessageFormatException.class, () -> chatService.sendMessageToChat("user", anyLong(), message1));
        assertThrows(MessageFormatException.class, () -> chatService.sendMessageToChat("user", anyLong(), message2));
        assertThrows(MessageFormatException.class, () -> chatService.sendMessageToChat("user", anyLong(), null));
    }

    @Test
    void sendMessageToChatThrowsExceptionOnUser() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> chatService.sendMessageToChat("user", anyLong(), "message"));
        verify(chatRepository, times(0)).findById(anyLong());
        verify(messageRepository, times(0)).save(any(Message.class));
    }

    @Test
    void sendMessageToChatThrowsExceptionOnChat() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(new User()));
        when(chatRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(ChatNotFoundException.class, () -> chatService.sendMessageToChat("user", anyLong(), "message"));
        verify(messageRepository, times(0)).save(any(Message.class));
    }

    @Test
    void sendMessageToChatUserNotInChat() {
        User user = new User();
        User user1 = new User();
        User user2 = new User();
        Chat chat = new Chat();
        user.setId(1L);
        user1.setId(2L);
        user2.setId(3L);
        chat.setFirstUser(user1);
        chat.setSecondUser(user2);
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
        when(chatRepository.findById(anyLong())).thenReturn(Optional.of(chat));
        assertThrows(AccessException.class, () -> chatService.sendMessageToChat("user", anyLong(), "message"));
        verify(messageRepository, times(0)).save(any(Message.class));
    }

    @Test
    void sendMessageToUser() {
        String msg = "message";
        String name1 = "user1";
        String name2 = "user2";
        User user1 = new User();
        User user2 = new User();
        user1.setUsername(name1);
        user2.setUsername(name2);
        Chat chat = new Chat();
        chat.setId(1L);
        when(userRepository.findByUsername(name1)).thenReturn(Optional.of(user1));
        when(userRepository.findByUsername(name2)).thenReturn(Optional.of(user2));
        when(chatRepository.getChatByFirstUserIdAndSecondUserId(anyLong(), anyLong())).thenReturn(Optional.of(chat));
        assertDoesNotThrow(() -> chatService.sendMessageToUser(name1, name2, msg));
        verify(userRepository, times(2)).findByUsername(anyString());
        verify(messageRepository, times(1)).save(any(Message.class));
        verify(messageRepository).save(messageCaptor.capture());
        Message message = messageCaptor.getValue();
        assertNotNull(message.getChat());
        assertNotNull(message.getSender());
        assertNotNull(message.getDate());
        assertNotNull(message.getMessage());
        assertEquals(message.getMessage(), msg);
    }

    @Test
    void sendMessageToUserWrongMessageFormat() {
        String message1 = "   ";
        String message2 = "";
        assertThrows(MessageFormatException.class, () -> chatService.sendMessageToChat("user", anyLong(), message1));
        assertThrows(MessageFormatException.class, () -> chatService.sendMessageToChat("user", anyLong(), message2));
        assertThrows(MessageFormatException.class, () -> chatService.sendMessageToChat("user", anyLong(), null));
    }

    @Test
    void sendMessageToUserSameUser() {
        String username1 = "username";
        String username2 = "username";
        assertThrows(MessageFormatException.class, () -> chatService.sendMessageToUser(username1, username2, "message"));
        verify(userRepository, times(0)).findByUsername(anyString());
        verify(chatRepository, times(0)).getChatByFirstUserIdAndSecondUserId(anyLong(), anyLong());
        verify(chatRepository, times(0)).save(any(Chat.class));
        verify(messageRepository, times(0)).save(any(Message.class));
    }

    @Test
    void sendMessageToUserThrowsExceptionOnFirstUser() {
        String username1 = "username1";
        String username2 = "username2";
        when(userRepository.findByUsername(username1)).thenReturn(Optional.empty());
        when(userRepository.findByUsername(username2)).thenReturn(Optional.of(new User()));
        assertThrows(UserNotFoundException.class, () -> chatService.sendMessageToUser(username1, username2, "message"));
        verify(userRepository, times(1)).findByUsername(anyString());
    }

    @Test
    void sendMessageToUserThrowsExceptionOnSecondUser() {
        String username1 = "username1";
        String username2 = "username2";
        when(userRepository.findByUsername(username1)).thenReturn(Optional.of(new User()));
        when(userRepository.findByUsername(username2)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> chatService.sendMessageToUser(username1, username2, "message"));
        verify(userRepository, times(2)).findByUsername(anyString());
    }

    @Test
    void sendMessageToUserNoPresentChat() {
        String username1 = "username1";
        String username2 = "username2";
        User user1 = new User();
        User user2 = new User();
        user1.setUsername(username1);
        user2.setUsername(username2);
        when(userRepository.findByUsername(username1)).thenReturn(Optional.of(user1));
        when(userRepository.findByUsername(username2)).thenReturn(Optional.of(user2));
        when(chatRepository.getChatByFirstUserIdAndSecondUserId(anyLong(), anyLong())).thenReturn(Optional.empty());
        assertDoesNotThrow(() -> chatService.sendMessageToUser(username1, username2, "message"));
        verify(messageRepository, times(1)).save(any(Message.class));
        verify(chatRepository, times(1)).save(any(Chat.class));
        verify(chatRepository).save(chatCaptor.capture());
        Chat chat = chatCaptor.getValue();
        assertEquals(chat.getFirstUser(), user1);
        assertEquals(chat.getSecondUser(), user2);
    }

    @Test
        //todo write test
    void getUserChats() {
    }

    @Test
        //todo write test
    void getChatMessages() {
    }
}