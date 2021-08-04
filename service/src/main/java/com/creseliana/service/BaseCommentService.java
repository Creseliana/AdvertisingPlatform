package com.creseliana.service;

import com.creseliana.dto.CommentShowResponse;
import com.creseliana.model.Advertisement;
import com.creseliana.model.Comment;
import com.creseliana.model.User;
import com.creseliana.repository.AdvertisementRepository;
import com.creseliana.repository.CommentRepository;
import com.creseliana.repository.UserRepository;
import com.creseliana.service.exception.ad.AdvertisementNotFoundException;
import com.creseliana.service.exception.comment.CommentFormatException;
import com.creseliana.service.util.StartCount;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Log4j2
@RequiredArgsConstructor
@Transactional
@Service
public class BaseCommentService extends BaseModelService implements CommentService {
    private static final String MSG_AD_NOT_FOUND_BY_ID = "There is no ad with id '%s'";
    private static final String MSG_COMMENT_IS_BLANK = "Comment is empty or contains only white spaces";

    private final CommentRepository commentRepository;
    private final AdvertisementRepository adRepository;
    private final UserRepository userRepository;
    private final ModelMapper mapper;

    @Override
    public void create(String username, Long id, String comment) {
        checkComment(comment);

        Advertisement ad = getAdById(id);
        User user = getUserByUsername(username, userRepository);
        Comment newComment = new Comment(ad, user, LocalDateTime.now(), comment);
        commentRepository.save(newComment);
    }

    @Override
    public List<CommentShowResponse> getComments(Long id, int page, int amount) {
        checkAdExistsById(id);

        int start = StartCount.count(page, amount);

        List<Comment> comments = commentRepository.getCommentsByAdId(id, start, amount);
        return mapList(mapper, comments, CommentShowResponse.class);
    }

    private Advertisement getAdById(Long id) {
        return adRepository.findById(id).orElseThrow(() -> {
            String msg = String.format(MSG_AD_NOT_FOUND_BY_ID, id);
            log.info(msg);
            return new AdvertisementNotFoundException(msg);
        });
    }

    private void checkComment(String comment) {
        if (comment == null || comment.isBlank()) {
            log.info(MSG_COMMENT_IS_BLANK);
            throw new CommentFormatException(MSG_COMMENT_IS_BLANK);
        }
    }

    private void checkAdExistsById(Long id) {
        if (!adRepository.existsById(id)) {
            String msg = String.format(MSG_AD_NOT_FOUND_BY_ID, id);
            log.info(msg);
            throw new AdvertisementNotFoundException(msg);
        }
    }
}
