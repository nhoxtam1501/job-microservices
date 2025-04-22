package dev.ducku.review.impl;

import dev.ducku.review.Review;
import dev.ducku.review.ReviewRepository;
import dev.ducku.review.ReviewService;
import dev.ducku.review.messaging.ReviewMessageProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final int PAGE_SIZE = 10;
    private final ReviewMessageProducer reviewMessageProducer;

    @Override
    public Review findById(Long reviewId) {
        return reviewRepository.findById(reviewId).orElse(null);
    }

    //    @Override
//    public Review createReview(Review review, Long companyId) {
//        try {
//            review.setId(null);
//            review.setCompanyId(companyId);
//            reviewMessageProducer.sendMessage(review);
//            return reviewRepository.save(review);
//        } catch (RuntimeException e) {
//            return null;
//        }
//    }
    @Override
    public Review createReview(Review review, Long companyId) {
        try {
            log.info("Creating review for company: {}", companyId);
            review.setId(null);
            review.setCompanyId(companyId);
            Review savedReview = reviewRepository.save(review);
            reviewMessageProducer.sendMessage(savedReview);
            log.info("Successfully created review: {}", savedReview);
            return savedReview;
        } catch (RuntimeException e) {
            log.error("Error creating review", e);
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

    @Override
    public Double calculateAverageRating(Long companyId) {
        return Math.round(reviewRepository.findByCompanyId(companyId).stream()
                .mapToDouble(Review::getRating)
                .average()
                .orElse(0.0) * 10.0) / 10.0;
    }
}