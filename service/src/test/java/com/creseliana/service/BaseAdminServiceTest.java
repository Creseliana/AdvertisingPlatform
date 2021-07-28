package com.creseliana.service;

import com.creseliana.model.Category;
import com.creseliana.model.User;
import com.creseliana.repository.CategoryRepository;
import com.creseliana.repository.UserRepository;
import com.creseliana.service.exception.category.UniqueCategoryException;
import com.creseliana.service.exception.user.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BaseAdminServiceTest {

    @InjectMocks
    private BaseAdminService adminService;

    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addCategory() {
        when(categoryRepository.existsByName(anyString())).thenReturn(false);
        assertDoesNotThrow(() -> adminService.addCategory(anyString()));
        verify(categoryRepository, times(1)).save(any(Category.class));
        verify(categoryRepository, times(1)).existsByName(anyString());
    }

    @Test
    void addCategoryThrowsException() {
        when(categoryRepository.existsByName(anyString())).thenReturn(true);
        assertThrows(UniqueCategoryException.class, () -> adminService.addCategory(anyString()));
        verify(categoryRepository, times(0)).save(any(Category.class));
        verify(categoryRepository, times(1)).existsByName(anyString());
    }

    @Test
    void blockUser() {
        User user = new User();
        when(userRepository.findByUsername(any())).thenReturn(Optional.of(user));
        adminService.blockUser(anyString());
        assertFalse(user.isActive());
        verify(userRepository, times(1)).update(user);
    }

    @Test
    void blockUserThrowsException() {
        when(userRepository.findByUsername(any())).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> adminService.blockUser(anyString()));
        verify(userRepository, times(0)).update(any(User.class));
    }

    @Test
    void activateAccount() {
        User user = new User();
        when(userRepository.findByUsername(any())).thenReturn(Optional.of(user));
        adminService.activateAccount(anyString());
        assertTrue(user.isActive());
        verify(userRepository, times(1)).update(user);
    }

    @Test
    void activateAccountThrowsException() {
        when(userRepository.findByUsername(any())).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> adminService.activateAccount(anyString()));
        verify(userRepository, times(0)).update(any(User.class));
    }
}