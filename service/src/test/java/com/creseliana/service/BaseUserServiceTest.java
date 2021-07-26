package com.creseliana.service;

import com.creseliana.RoleType;
import com.creseliana.dto.UserCreateRequest;
import com.creseliana.dto.UserEditRequest;
import com.creseliana.dto.UserProfileResponse;
import com.creseliana.model.Role;
import com.creseliana.model.User;
import com.creseliana.repository.RoleRepository;
import com.creseliana.repository.UserRepository;
import com.creseliana.service.exception.user.UniqueValueException;
import com.creseliana.service.exception.user.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
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

    private final ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
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
        String type = "admin";
        Role role = new Role(RoleType.ADMIN);
        User createUser = this.testMapper.map(userCreateRequest, User.class);
        when(this.mapper.map(userCreateRequest, User.class)).thenReturn(createUser);
        when(this.encoder.encode(userCreateRequest.getPassword())).thenReturn(this.testEncoder.encode(createUser.getPassword()));
        when(roleRepository.getRoleByType(RoleType.ADMIN)).thenReturn(Optional.of(role));
        userService.create(userCreateRequest, type);

        assertNotEquals(userCreateRequest.getPassword(), createUser.getPassword());
        assertFalse(createUser.isActive());
        assertNotNull(createUser.getRegistrationDate());
        assertEquals(createUser.getRating(), BigDecimal.valueOf(0));
        assertFalse(createUser.getRoles().isEmpty());
        assertTrue(createUser.getRoles().contains(role));

        verify(userRepository, times(1)).existsByUsername(any());
        verify(userRepository, times(1)).existsByEmail(any());
        verify(userRepository, times(1)).existsByPhoneNumber(any());
        verify(roleRepository, times(1)).getRoleByType(RoleType.ADMIN);
        verify(userRepository, times(1)).save(createUser);
    }

    @Test
    void createUser() {
        User createUser = this.testMapper.map(userCreateRequest, User.class);
        Role role = new Role(RoleType.USER);
        when(this.mapper.map(userCreateRequest, User.class)).thenReturn(createUser);
        when(this.encoder.encode(userCreateRequest.getPassword())).thenReturn(this.testEncoder.encode(createUser.getPassword()));
        when(roleRepository.getRoleByType(RoleType.USER)).thenReturn(Optional.of(role));
        userService.create(userCreateRequest, anyString());

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
        assertThrows(UniqueValueException.class, () -> userService.create(userCreateRequest, anyString()));
    }

    @Test
    void createThrowsExceptionOnEmail() {
        when(userRepository.existsByEmail(any())).thenReturn(true);
        assertThrows(UniqueValueException.class, () -> userService.create(userCreateRequest, anyString()));
    }

    @Test
    void createThrowsExceptionOnPhoneNumber() {
        when(userRepository.existsByPhoneNumber(any())).thenReturn(true);
        assertThrows(UniqueValueException.class, () -> userService.create(userCreateRequest, anyString()));
    }

    @Test
    void edit() {
        User user = this.testMapper.map(userCreateRequest, User.class);
        UserEditRequest newUser = new UserEditRequest();
        newUser.setUsername("test");
        newUser.setFirstName("test");
        newUser.setLastName("test");
        newUser.setEmail("test@test.com");
        newUser.setPhoneNumber("12341234");
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
//        this.testMapper.map(newUser, user);
        assertDoesNotThrow(() -> userService.edit(anyString(), newUser));
        verify(userRepository, times(1)).update(user);
        verify(userRepository).update(userCaptor.capture());
        User capturedUser = userCaptor.getValue();
//        assertEquals(capturedUser.getUsername(), newUser.getUsername()); //todo continue here
    }

    @Test
    void getProfile() {
        User user = new User();
        UserProfileResponse userResponse = new UserProfileResponse();
        when(userRepository.findByUsername(any())).thenReturn(Optional.of(user));
        when(mapper.map(user, UserProfileResponse.class)).thenReturn(userResponse);
        assertNotNull(userResponse);
    }

    @Test
    void getProfileThrowsException() {
        when(userRepository.findByUsername(any())).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.getProfile("test"));
    }
}