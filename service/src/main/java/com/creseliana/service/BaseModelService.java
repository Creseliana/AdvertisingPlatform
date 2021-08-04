package com.creseliana.service;

import com.creseliana.model.User;
import com.creseliana.repository.UserRepository;
import com.creseliana.service.exception.user.UserNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
public abstract class BaseModelService {
    private static final String MSG_USER_NOT_FOUND_BY_USERNAME = "There is no user with username '%s'";

    protected User getUserByUsername(String username, UserRepository userRepository) {
        return userRepository.findByUsername(username).orElseThrow(() -> {
            String msg = String.format(MSG_USER_NOT_FOUND_BY_USERNAME, username);
            log.info(msg);
            return new UserNotFoundException(msg);
        });
    }

    protected <A, B> List<B> mapList(ModelMapper mapper, List<A> list, Class<B> aClass) {
        return list.stream()
                .map(element -> mapper.map(element, aClass))
                .collect(Collectors.toList());
    }
}
