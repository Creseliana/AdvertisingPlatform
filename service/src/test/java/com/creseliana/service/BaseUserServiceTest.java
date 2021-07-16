package com.creseliana.service;

import com.creseliana.RoleType;
import com.creseliana.dto.UserCreateRequest;
import com.creseliana.dto.UserProfileResponse;
import com.creseliana.model.Role;
import com.creseliana.model.User;
import com.creseliana.repository.RoleRepository;
import com.creseliana.repository.UserRepository;
import com.creseliana.service.exception.user.UniqueValueException;
import com.creseliana.service.exception.user.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BaseUserServiceTest {

    @InjectMocks
    private BaseUserService userService;

    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private PasswordEncoder encoder;
    @Mock
    private ModelMapper mapper;

    private ModelMapper testMapper;
    private PasswordEncoder testEncoder;
    private UserCreateRequest userCreateRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        testMapper = new ModelMapper();
        testEncoder = new BCryptPasswordEncoder();
        userCreateRequest = new UserCreateRequest();
        userCreateRequest.setUsername("user");
        userCreateRequest.setPassword("user");
        userCreateRequest.setFirstName("user");
        userCreateRequest.setLastName("user");
        userCreateRequest.setEmail("user@user.com");
        userCreateRequest.setPhoneNumber("00000000");
    }

    @Test
    void create() {
        User createUser = this.testMapper.map(userCreateRequest, User.class);
        Role role = new Role(RoleType.USER);
        when(this.mapper.map(userCreateRequest, User.class)).thenReturn(createUser);
        when(this.encoder.encode(userCreateRequest.getPassword())).thenReturn(this.testEncoder.encode(createUser.getPassword()));
        when(roleRepository.getRoleByType(RoleType.USER)).thenReturn(Optional.of(role));
        userService.create(userCreateRequest);

        assertNotEquals(userCreateRequest.getPassword(), createUser.getPassword());
        assertTrue(createUser.isActive());
        assertNotNull(createUser.getRegistrationDate());
        assertEquals(createUser.getRating(), BigDecimal.valueOf(0));
        assertFalse(createUser.getRoles().isEmpty());
        assertTrue(createUser.getRoles().contains(role));

        verify(userRepository, times(1)).existsByUsername(any());
        verify(userRepository, times(1)).existsByEmail(any());
        verify(userRepository, times(1)).existsByPhoneNumber(any());
        verify(roleRepository, times(1)).getRoleByType(RoleType.USER);
        verify(userRepository, times(1)).save(createUser);
    }

    @Test
    void createThrowsExceptionOnUsername() {
        when(userRepository.existsByUsername(any())).thenReturn(true);
        assertThrows(UniqueValueException.class, () -> userService.create(userCreateRequest));
    }

    @Test
    void createThrowsExceptionOnEmail() {
        when(userRepository.existsByEmail(any())).thenReturn(true);
        assertThrows(UniqueValueException.class, () -> userService.create(userCreateRequest));
    }

    @Test
    void createThrowsExceptionOnPhoneNumber() {
        when(userRepository.existsByPhoneNumber(any())).thenReturn(true);
        assertThrows(UniqueValueException.class, () -> userService.create(userCreateRequest));
    }

//    public void edit(String username, UserEditRequest userChanges) {
//        User user = getUserByUsername(username);
//        String newUsername = userChanges.getUsername();
//        String newEmail = userChanges.getEmail();
//        String newPhoneNumber = userChanges.getPhoneNumber();
//
//        if (!newUsername.isBlank() && !newUsername.equals(user.getUsername())) {
//            checkUsername(newUsername);
//        }
//        if (!newEmail.isBlank() && !newEmail.equals(user.getEmail())) {
//            checkEmail(newEmail);
//        }
//        if (!newPhoneNumber.isBlank() && !newPhoneNumber.equals(user.getPhoneNumber())) {
//            checkPhoneNumber(newPhoneNumber);
//        }
//
//        mapper.map(userChanges, user);
//        userRepository.update(user);
//    }

    @Test
    void edit() {
    }

    @Test
    void show() {
        User user = new User();
        UserProfileResponse userResponse = new UserProfileResponse();
        when(userRepository.findByUsername(any())).thenReturn(Optional.of(user));
        when(mapper.map(user, UserProfileResponse.class)).thenReturn(userResponse);
        assertNotNull(userResponse);
    }

    @Test
    void showThrowsException() {
        when(userRepository.findByUsername(any())).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.getProfile("test"));
    }
}