package com.creseliana.service;

import com.creseliana.model.Category;
import com.creseliana.model.User;
import com.creseliana.repository.CategoryRepository;
import com.creseliana.repository.UserRepository;
import com.creseliana.service.exception.category.UniqueCategoryException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * {@inheritDoc}
 */
@Log4j2
@RequiredArgsConstructor
@Transactional
@Service
public class BaseAdminService extends BaseModelService implements AdminService {
    private static final String MSG_CATEGORY_EXISTS = "Category with name '%s' already exists";

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    @Override
    public void addCategory(String name) {
        checkCategoryNotExistsByName(name);
        categoryRepository.save(new Category(name));
    }

    @Override
    public void blockUser(String username) {
        User user = getUserByUsername(username, userRepository);
        user.setActive(false);
        userRepository.update(user);
    }

    @Override
    public void activateAccount(String username) {
        User user = getUserByUsername(username, userRepository);
        user.setActive(true);
        userRepository.update(user);
    }

    private void checkCategoryNotExistsByName(String name) {
        if (categoryRepository.existsByName(name)) {
            String msg = String.format(MSG_CATEGORY_EXISTS, name);
            log.info(msg);
            throw new UniqueCategoryException(msg);
        }
    }
}
