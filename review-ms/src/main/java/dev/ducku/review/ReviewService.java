package dev.ducku.review;

import java.util.List;

public interface ReviewService {
    List<Review> findAllReviewByCompany(Long companyId);

    Review createReview(Review review, Long companyId);

    Review findById(Long reviewId);

    Review updateReview(Review review, Long reviewId);

    void deleteReview(Long reviewId);

    Double calculateAverageRating(Long companyId);
}
