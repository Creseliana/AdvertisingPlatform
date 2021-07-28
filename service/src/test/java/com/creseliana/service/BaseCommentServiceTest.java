package com.creseliana.service;

import com.creseliana.model.Advertisement;
import com.creseliana.model.Comment;
import com.creseliana.model.User;
import com.creseliana.repository.AdvertisementRepository;
import com.creseliana.repository.CommentRepository;
import com.creseliana.repository.UserRepository;
import com.creseliana.service.exception.ad.AdvertisementNotFoundException;
import com.creseliana.service.exception.comment.CommentFormatException;
import com.creseliana.service.exception.user.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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

class BaseCommentServiceTest {
    private final ArgumentCaptor<Comment> commentCaptor = ArgumentCaptor.forClass(Comment.class);

    @InjectMocks
    private BaseCommentService commentService;

    @Mock
    private CommentRepository commentRepository;
    @Mock
    private AdvertisementRepository adRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ModelMapper mapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void create() {
        Advertisement ad = new Advertisement();
        User user = new User();
        String strComment = "comment";

        when(adRepository.findById(anyLong())).thenReturn(Optional.of(ad));
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
        assertDoesNotThrow(() -> commentService.create("user", anyLong(), strComment));

        verify(adRepository, times(1)).findById(anyLong());
        verify(userRepository, times(1)).findByUsername(anyString());
        verify(commentRepository, times(1)).save(any(Comment.class));

        verify(commentRepository).save(commentCaptor.capture());
        Comment comment = commentCaptor.getValue();
        assertEquals(comment.getAuthor(), user);
        assertEquals(comment.getAd(), ad);
        assertNotNull(comment.getDate());
        assertEquals(comment.getComment(), strComment);
    }

    @Test
    void createThrowsExceptionOnComment() {
        String comment1 = "";
        String comment2 = "      ";
        assertThrows(CommentFormatException.class, () -> commentService.create("user", 1L, comment1));
        assertThrows(CommentFormatException.class, () -> commentService.create("user", 1L, comment2));
        assertThrows(CommentFormatException.class, () -> commentService.create("user", 1L, null));
    }

    @Test
    void createThrowsExceptionOnAd() {
        when(adRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(AdvertisementNotFoundException.class, () -> commentService.create("user", anyLong(), "comment"));
        verify(adRepository, times(1)).findById(anyLong());
        verify(userRepository, times(0)).findByUsername(anyString());
        verify(commentRepository, times(0)).save(any(Comment.class));
    }

    @Test
    void createThrowsExceptionOnUser() {
        when(adRepository.findById(anyLong())).thenReturn(Optional.of(new Advertisement()));
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> commentService.create("user", anyLong(), "comment"));
        verify(adRepository, times(1)).findById(anyLong());
        verify(userRepository, times(1)).findByUsername(anyString());
        verify(commentRepository, times(0)).save(any(Comment.class));
    }

    @Test
    @Disabled
    void getAll() {
    }
}