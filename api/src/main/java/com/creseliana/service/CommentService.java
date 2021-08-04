package com.creseliana.service;

import com.creseliana.dto.CommentShowResponse;

import java.util.List;

/**
 * Service for {@link com.creseliana.model.Comment}
 */
public interface CommentService {

    /**
     * Creates new comment to the ad
     *
     * @param username the name of the user that leaves comment
     * @param id       the id of the ad that is commented
     * @param comment  the string text of the comment
     */
    void create(String username, Long id, String comment);

    /**
     * Gets limited amount of ad's comments
     *
     * @param id     the id of the ad
     * @param page   the number of the page that contains certain amount of comments
     * @param amount the number of comments on page
     * @return List of DTOs of the {@link com.creseliana.model.Comment}
     */
    List<CommentShowResponse> getComments(Long id, int page, int amount);
}
