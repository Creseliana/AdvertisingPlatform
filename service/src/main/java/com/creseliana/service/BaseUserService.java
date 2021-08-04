package com.creseliana.service;

import com.creseliana.RoleType;
import com.creseliana.dto.UserCreateRequest;
import com.creseliana.dto.UserEditRequest;
import com.creseliana.dto.UserProfileResponse;
import com.creseliana.model.Role;
import com.creseliana.model.User;
import com.creseliana.repository.RoleRepository;
import com.creseliana.repository.UserRepository;
import com.creseliana.service.exception.user.EmailFormatException;
import com.creseliana.service.exception.user.PhoneNumberFormatException;
import com.creseliana.service.exception.user.UniqueValueException;
import com.creseliana.service.exception.user.UserNotFoundException;
import com.creseliana.service.exception.user.UsernameFormatException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

@Log4j2
@RequiredArgsConstructor
@Transactional
@Service
public class BaseUserService implements UserService {
    private static final String MSG_NOT_UNIQUE_USERNAME = "User with such username already exists";
    private static final String MSG_NOT_UNIQUE_PHONE_NUMBER = "User with such phone number already exists";
    private static final String MSG_NOT_UNIQUE_EMAIL = "User with such email already exists";
    private static final String MSG_WRONG_PHONE_NUMBER_FORMAT = "Phone number doesn't match regex";
    private static final String MSG_WRONG_USERNAME_FORMAT = "Username doesn't match regex";
    private static final String MSG_WRONG_EMAIL_FORMAT = "Email doesn't match regex";
    private static final String MSG_USER_NOT_FOUND_BY_USERNAME = "There is no user with username '%s'";

    private static final String PHONE_NUMBER_REGEX = "^[0-9]{7,15}$";
    private static final String USERNAME_REGEX = "^[a-zA-Z0-9-_]*$";
    private static final String EMAIL_REGEX = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;
    private final ModelMapper mapper;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return getUserByUsername(username);
    }

    @Override
    public void create(UserCreateRequest newUser, String role) {
        checkUsername(newUser.getUsername());
        checkEmail(newUser.getEmail());
        checkPhoneNumber(newUser.getPhoneNumber());

        User user = mapper.map(newUser, User.class);

        if (role != null && role.equalsIgnoreCase(RoleType.ADMIN.toString())) {
            user.setActive(false);
            user.setRoles(Collections.singleton(getRoleByType(RoleType.ADMIN)));
        } else {
            user.setActive(true);
            user.setRoles(Collections.singleton(getRoleByType(RoleType.USER)));
        }

        user.setPassword(encoder.encode(newUser.getPassword()));
        user.setRegistrationDate(LocalDateTime.now());
        user.setRating(BigDecimal.valueOf(0));
        userRepository.save(user);
    }

    @Override
    public void edit(String username, UserEditRequest userChanges) {
        User user = getUserByUsername(username);
        String newUsername = userChanges.getUsername();
        String newEmail = userChanges.getEmail();
        String newPhoneNumber = userChanges.getPhoneNumber();

        if (!newUsername.equals(user.getUsername())) {
            checkUsername(newUsername);
        }
        if (!newEmail.equals(user.getEmail())) {
            checkEmail(newEmail);
        }
        if (!newPhoneNumber.equals(user.getPhoneNumber())) {
            checkPhoneNumber(newPhoneNumber);
        }

        mapper.map(userChanges, user);
        userRepository.update(user);
    }

    @Override
    public UserProfileResponse getByUsername(String username) {
        User user = getUserByUsername(username);
        return mapper.map(user, UserProfileResponse.class);
    }

    private User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> {
            String msg = String.format(MSG_USER_NOT_FOUND_BY_USERNAME, username);
            log.info(msg);
            return new UserNotFoundException(msg);
        });
    }

    private void checkUsername(String username) {
        if (!username.matches(USERNAME_REGEX)) {
            log.info(MSG_WRONG_USERNAME_FORMAT);
            throw new UsernameFormatException(MSG_WRONG_USERNAME_FORMAT);
        }
        if (userRepository.existsByUsername(username)) {
            log.info(MSG_NOT_UNIQUE_USERNAME);
            throw new UniqueValueException(MSG_NOT_UNIQUE_USERNAME);
        }
    }

    private void checkEmail(String email) {
        if (!email.matches(EMAIL_REGEX)) {
            log.info(MSG_WRONG_EMAIL_FORMAT);
            throw new EmailFormatException(MSG_WRONG_EMAIL_FORMAT);
        }
        if (userRepository.existsByEmail(email)) {
            log.info(MSG_NOT_UNIQUE_EMAIL);
            throw new UniqueValueException(MSG_NOT_UNIQUE_EMAIL);
        }
    }

    private void checkPhoneNumber(String phoneNumber) {
        if (!phoneNumber.matches(PHONE_NUMBER_REGEX)) {
            log.info(MSG_WRONG_PHONE_NUMBER_FORMAT);
            throw new PhoneNumberFormatException(MSG_WRONG_PHONE_NUMBER_FORMAT);
        }
        if (userRepository.existsByPhoneNumber(phoneNumber)) {
            log.info(MSG_NOT_UNIQUE_PHONE_NUMBER);
            throw new UniqueValueException(MSG_NOT_UNIQUE_PHONE_NUMBER);
        }
    }

    private Role getRoleByType(RoleType type) {
        Optional<Role> optional = roleRepository.getRoleByType(type);
        if (optional.isEmpty()) {
            return roleRepository.save(new Role(type));
        }
        return optional.get();
    }
}
