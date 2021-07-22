package com.creseliana.repository;

import com.creseliana.model.Rating;

import java.util.List;

public interface RatingRepository extends ModelRepository<Rating, Long> {

    List<Rating> getRatingsByUserId(Long id);

    boolean existsByUserIdAndRaterId(Long userId, Long raterId);
}
