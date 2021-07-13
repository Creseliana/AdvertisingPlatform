package com.creseliana.service;

import com.creseliana.RoleType;
import com.creseliana.dto.UserCreateRequest;
import com.creseliana.dto.UserEditRequest;
import com.creseliana.model.Role;
import com.creseliana.model.User;
import com.creseliana.repository.RoleRepository;
import com.creseliana.repository.UserRepository;
import com.creseliana.service.exception.PhoneNumberFormatException;
import com.creseliana.service.exception.UniqueValueException;
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
    public static final String MSG_NOT_UNIQUE_USERNAME = "User with such username already exists";
    public static final String MSG_NOT_UNIQUE_PHONE_NUMBER = "User with such phone number already exists";
    public static final String MSG_NOT_UNIQUE_EMAIL = "User with such email already exists";
    public static final String MSG_WRONG_PHONE_NUMBER_FORMAT = "Phone number doesn't match regex";

    private static final String PHONE_NUMBER_REGEX = "^[0-9]{7,15}$";

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;
    private final ModelMapper mapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(); //todo handle
    }

    @Override
    public void create(UserCreateRequest newUser) {
        checkUsername(newUser.getUsername());
        checkEmail(newUser.getEmail());
        checkPhoneNumber(newUser.getPhoneNumber());

        User user = mapper.map(newUser, User.class);
        user.setPassword(encoder.encode(newUser.getPassword()));
        user.setActive(true);
        user.setRegistrationDate(LocalDateTime.now());
        user.setRating(BigDecimal.valueOf(0));
        user.setRoles(Collections.singleton(getRoleByType(RoleType.USER)));
        userRepository.save(user);
    }

    @Override
    public void edit(String username, UserEditRequest userChanges) {
        User user = userRepository.findByUsername(username).orElseThrow(); //todo handle
        if (userChanges.getUsername() != null && !userChanges.getUsername().equals(user.getUsername())) {
            //todo continue
        }
        mapper.map(userChanges, user);
    }

    private void checkUsername(String username) {
        if (userRepository.existsByUsername(username)) {
            log.info(MSG_NOT_UNIQUE_USERNAME);
            throw new UniqueValueException(MSG_NOT_UNIQUE_USERNAME);
        }
    }

    private void checkEmail(String email) {
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
