package com.creseliana.service;

import com.creseliana.dto.CommentShowResponse;
import com.creseliana.model.Advertisement;
import com.creseliana.model.Comment;
import com.creseliana.model.User;
import com.creseliana.repository.AdvertisementRepository;
import com.creseliana.repository.CommentRepository;
import com.creseliana.repository.UserRepository;
import com.creseliana.service.exception.ad.AdvertisementNotFoundException;
import com.creseliana.service.exception.user.UserNotFoundException;
import com.creseliana.service.util.StartCount;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@RequiredArgsConstructor
@Transactional
@Service
public class BaseCommentService implements CommentService {
    private static final String MSG_USER_NOT_FOUND_BY_USERNAME = "There is no user with username '%s'";
    private static final String MSG_AD_NOT_FOUND_BY_ID = "There is no ad with id '%s'";

    private final CommentRepository commentRepository;
    private final AdvertisementRepository adRepository;
    private final UserRepository userRepository;
    private final ModelMapper mapper;

    @Override
    public void create(String username, Long id, String comment) {
        Advertisement ad = getAdById(id);
        User user = getUserByUsername(username);
        Comment newComment = new Comment(ad, user, LocalDateTime.now(), comment);
        commentRepository.save(newComment);
    }

    @Override
    public List<CommentShowResponse> getAll(Long id, int page, int amount) {
        if (!adRepository.existsById(id)) {
            String msg = String.format(MSG_AD_NOT_FOUND_BY_ID, id);
            log.info(msg);
            throw new AdvertisementNotFoundException(msg);
        }

        int start = StartCount.count(page, amount);
        List<Comment> comments = commentRepository.getCommentsByAdId(id, start, amount);

        return comments.stream()
                .map(comment -> mapper.map(comment, CommentShowResponse.class))
                .collect(Collectors.toList());
    }

    private Advertisement getAdById(Long id) {
        return adRepository.findById(id).orElseThrow(() -> {
            String msg = String.format(MSG_AD_NOT_FOUND_BY_ID, id);
            log.info(msg);
            return new AdvertisementNotFoundException(msg);
        });
    }

    private User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> {
            String msg = String.format(MSG_USER_NOT_FOUND_BY_USERNAME, username);
            log.info(msg);
            return new UserNotFoundException(msg);
        });
    }
}
