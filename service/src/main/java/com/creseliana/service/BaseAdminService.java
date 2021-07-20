package com.creseliana.service;

import com.creseliana.model.Category;
import com.creseliana.model.User;
import com.creseliana.repository.CategoryRepository;
import com.creseliana.repository.UserRepository;
import com.creseliana.service.exception.user.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@RequiredArgsConstructor
@Transactional
@Service
public class BaseAdminService implements AdminService {
    private static final String MSG_USER_NOT_FOUND_BY_USERNAME = "There is no user with username '%s'";

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    @Override
    public void addCategory(String name) {
        if (categoryRepository.existsByName(name)) {
            throw new RuntimeException(); //todo throw
        }

        categoryRepository.save(new Category(name));
    }

    @Override
    public void blockUser(String username) {
        User user = getUserByUsername(username);
        user.setActive(false);
        userRepository.update(user);
    }

    @Override
    public void activateAdminAccount(String username) {
        User user = getUserByUsername(username);
        user.setActive(true);
        userRepository.update(user);
    }

    private User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> {
            String msg = String.format(MSG_USER_NOT_FOUND_BY_USERNAME, username);
            log.info(msg);
            return new UserNotFoundException(msg);
        });
    }
}
