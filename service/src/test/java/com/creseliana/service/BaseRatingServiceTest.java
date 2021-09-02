package com.creseliana.service;

import com.creseliana.RatingLevel;
import com.creseliana.model.Rating;
import com.creseliana.model.User;
import com.creseliana.repository.RatingRepository;
import com.creseliana.repository.UserRepository;
import com.creseliana.service.exception.rating.RatingExistsException;
import com.creseliana.service.exception.rating.UserRateException;
import com.creseliana.service.exception.user.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class BaseRatingServiceTest {
    private final ArgumentCaptor<Rating> ratingCaptor = ArgumentCaptor.forClass(Rating.class);
    private final String userUsername = "user";
    private final String raterUsername = "rater";

    @InjectMocks
    private BaseRatingService ratingService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private RatingRepository ratingRepository;
    private User user;
    private User rater;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        rater = new User();
        user.setId(1L);
        user.setUsername(userUsername);
        rater.setId(2L);
        rater.setUsername(raterUsername);
    }

    @Test
    void rate() {
        Rating rate = new Rating();
        rate.setLevel(RatingLevel.EXCELLENT);
        List<Rating> ratings = new ArrayList<>();
        ratings.add(rate);
        when(userRepository.findByUsername(userUsername)).thenReturn(Optional.of(user));
        when(userRepository.findByUsername(raterUsername)).thenReturn(Optional.of(rater));
        when(ratingRepository.getRatingsByUserId(anyLong())).thenReturn(ratings);

        assertDoesNotThrow(() -> ratingService.rate(raterUsername, userUsername, 5));
        verify(userRepository, times(2)).findByUsername(anyString());
        verify(ratingRepository, times(1)).existsByUserIdAndRaterId(anyLong(), anyLong());
        verify(ratingRepository, times(1)).save(any(Rating.class));
        verify(ratingRepository, times(1)).getRatingsByUserId(anyLong());
        verify(userRepository, times(1)).update(any(User.class));

        verify(ratingRepository).save(ratingCaptor.capture());
        Rating rating = ratingCaptor.getValue();

        assertEquals(rating.getRater(), rater);
        assertEquals(rating.getUser(), user);
        assertNotNull(rating.getLevel());
        assertNotNull(rating.getDate());
    }

    @Test
    void rateThrowsExceptionOnUser() {
        when(userRepository.findByUsername(userUsername)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> ratingService.rate(raterUsername, userUsername, 5));
        verify(userRepository, times(1)).findByUsername(anyString());
        verify(ratingRepository, times(0)).save(any(Rating.class));
        verify(ratingRepository, times(0)).getRatingsByUserId(anyLong());
        verify(userRepository, times(0)).update(any(User.class));
    }

    @Test
    void rateThrowsExceptionOnRaterUser() {
        when(userRepository.findByUsername(userUsername)).thenReturn(Optional.of(user));
        when(userRepository.findByUsername(raterUsername)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> ratingService.rate(raterUsername, userUsername, 5));
        verify(userRepository, times(2)).findByUsername(anyString());
        verify(ratingRepository, times(0)).save(any(Rating.class));
        verify(ratingRepository, times(0)).getRatingsByUserId(anyLong());
        verify(userRepository, times(0)).update(any(User.class));
    }

    @Test
    void rateThrowsExceptionOnUserComparing() {
        when(userRepository.findByUsername(userUsername)).thenReturn(Optional.of(user));
        when(userRepository.findByUsername(raterUsername)).thenReturn(Optional.of(user));
        assertThrows(UserRateException.class, () -> ratingService.rate(raterUsername, userUsername, 5));
        verify(userRepository, times(2)).findByUsername(anyString());
        verify(ratingRepository, times(0)).save(any(Rating.class));
        verify(ratingRepository, times(0)).getRatingsByUserId(anyLong());
        verify(userRepository, times(0)).update(any(User.class));
    }

    @Test
    void rateThrowsExceptionIfRateExists() {
        when(userRepository.findByUsername(userUsername)).thenReturn(Optional.of(user));
        when(userRepository.findByUsername(raterUsername)).thenReturn(Optional.of(rater));
        when(ratingRepository.existsByUserIdAndRaterId(anyLong(), anyLong())).thenReturn(true);
        assertThrows(RatingExistsException.class, () -> ratingService.rate(raterUsername, userUsername, 5));
        verify(userRepository, times(2)).findByUsername(anyString());
        verify(ratingRepository, times(0)).save(any(Rating.class));
        verify(ratingRepository, times(0)).getRatingsByUserId(anyLong());
        verify(userRepository, times(0)).update(any(User.class));
    }
}