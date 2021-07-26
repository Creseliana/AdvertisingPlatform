package com.creseliana.service;

import com.creseliana.RatingLevel;
import com.creseliana.model.Rating;
import com.creseliana.model.User;
import com.creseliana.repository.RatingRepository;
import com.creseliana.repository.UserRepository;
import com.creseliana.service.exception.rating.NoSuchRateException;
import com.creseliana.service.exception.rating.RatingExistsException;
import com.creseliana.service.exception.rating.UserRateException;
import com.creseliana.service.exception.user.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Log4j2
@RequiredArgsConstructor
@Transactional
@Service
public class BaseRatingService implements RatingService {
    private static final String MSG_USER_NOT_FOUND_BY_USERNAME = "There is no user with username '%s'";
    private static final String MSG_RATING_EXISTS = "User '%s' has already rated user '%s'";
    private static final String MSG_NO_RATING_LEVEL = "There is no such rating level with rate '%s'";
    private static final String MSG_USER_RATE_HIMSELF = "User cannot rate himself";

    private final UserRepository userRepository;
    private final RatingRepository ratingRepository;

    @Override
    public void rate(String raterUsername, String username, int rate) {
        User user = getUserByUsername(username);
        User rater = getUserByUsername(raterUsername);

        checkUserAndRater(user, rater);

        RatingLevel level = getRatingLevel(rate);
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

    private RatingLevel getRatingLevel(int rate) {
        Optional<RatingLevel> level = RatingLevel.getRatingLevel(rate);

        if (level.isEmpty()) {
            String msg = String.format(MSG_NO_RATING_LEVEL, rate);
            log.info(msg);
            throw new NoSuchRateException(msg);
        }
        return level.get();
    }

    private void checkUserAndRater(User user, User rater) {
        if (user.getId().equals(rater.getId())) {
            log.info(MSG_USER_RATE_HIMSELF);
            throw new UserRateException(MSG_USER_RATE_HIMSELF);
        }
        if (ratingRepository.existsByUserIdAndRaterId(user.getId(), rater.getId())) {
            String msg = String.format(MSG_RATING_EXISTS, rater.getUsername(), user.getUsername());
            log.info(msg);
            throw new RatingExistsException(msg);
        }
    }
}
