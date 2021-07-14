package com.creseliana.service;

import com.creseliana.dto.CommentCreateRequest;
import com.creseliana.model.Advertisement;
import com.creseliana.model.Comment;
import com.creseliana.model.User;
import com.creseliana.repository.AdvertisementRepository;
import com.creseliana.repository.CommentRepository;
import com.creseliana.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Log4j2
@RequiredArgsConstructor
@Transactional
@Service
public class BaseCommentService implements CommentService {
    private final CommentRepository commentRepository;
    private final AdvertisementRepository adRepository;
    private final UserRepository userRepository;
    private final ModelMapper mapper;

    @Override
    public void create(String username, Long id, CommentCreateRequest newComment) {
        Advertisement ad = adRepository.findById(id).orElseThrow(); //todo handle
        User user = userRepository.findByUsername(username).orElseThrow(); //todo handle
        Comment comment = mapper.map(newComment, Comment.class);
        comment.setAd(ad);
        comment.setAuthor(user);
        comment.setDate(LocalDateTime.now());
        commentRepository.save(comment);
    }
}
