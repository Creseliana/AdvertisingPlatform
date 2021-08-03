package com.creseliana.service;

/**
 * Service for {@link com.creseliana.model.Rating}
 */
public interface RatingService {

    /**
     * Rates the user with rate from the authorized user
     *
     * @param raterUsername the name of the user that sets the rate
     * @param username      the name of the user that is rated
     * @param rate          the numerical evaluation of the user
     */
    void rate(String raterUsername, String username, int rate);
}
