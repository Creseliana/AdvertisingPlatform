package com.creseliana.service;

import com.creseliana.RatingLevel;
import com.creseliana.model.Rating;
import com.creseliana.model.User;
import com.creseliana.repository.RatingRepository;
import com.creseliana.repository.UserRepository;
import com.creseliana.service.exception.user.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

@Log4j2
@RequiredArgsConstructor
@Transactional
@Service
public class BaseRatingService implements RatingService {
    private static final String MSG_USER_NOT_FOUND_BY_USERNAME = "There is no user with username '%s'";

    private final UserRepository userRepository;
    private final RatingRepository ratingRepository;

    @Override
    public void rate(String raterUsername, String username, int rate) {
        RatingLevel level = RatingLevel.getRatingLevel(rate);
        User user = getUserByUsername(username);
        User rater = getUserByUsername(raterUsername);

        if (ratingRepository.existsByUserIdAndRaterId(user.getId(), rater.getId())) {
            throw new RuntimeException(); //todo handle
        }

        Rating rating = new Rating(level, rater, user, LocalDateTime.now());
        ratingRepository.save(rating);

        List<Rating> ratings = ratingRepository.getRatingsByUserId(user.getId());
        user.setRating(countRating(ratings));
        userRepository.update(user);
    }

    private User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> {
            String msg = String.format(MSG_USER_NOT_FOUND_BY_USERNAME, username);
            log.info(msg);
            return new UserNotFoundException(msg);
        });
    }

    private BigDecimal countRating(List<Rating> ratings) {
        return ratings.stream()
                .map(rating -> BigDecimal.valueOf(rating.getLevel().getRate()))
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .divide(BigDecimal.valueOf(ratings.size()), 2, RoundingMode.CEILING);
    }
}
