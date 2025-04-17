package dev.ducku.review.impl;

import dev.ducku.review.Review;
import dev.ducku.review.ReviewRepository;
import dev.ducku.review.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final int PAGE_SIZE = 10;

    @Override
    public Review findById(Long reviewId) {
        return reviewRepository.findById(reviewId).orElse(null);
    }

    @Override
    public Review createReview(Review review, Long companyId) {
        try {
            review.setId(null);
            review.setCompanyId(companyId);
            return reviewRepository.save(review);
        } catch (RuntimeException e) {
            return null;
        }
    }

    @Override
    public List<Review> findAllReviewByCompany(Long companyId) {
        return companyId != null ?
                reviewRepository.findByCompanyId(companyId)
                : reviewRepository.findAll(PageRequest.of(0, PAGE_SIZE)).getContent();
    }

    @Override
    public Review updateReview(Review review, Long reviewId) {
        try {
            if (reviewRepository.existsById(reviewId)) {
                review.setId(reviewId);
                return reviewRepository.save(review);
            }
        } catch (RuntimeException e) {
            return null;
        }
        return null;
    }

    @Override
    public void deleteReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId).orElse(null);
        if (review != null) {
            review.setDeleted(true);
        }
    }
}
