package dev.ducku.review.messaging;

import dev.ducku.review.Review;
import dev.ducku.review.dto.ReviewMessage;
import dev.ducku.review.mapper.ReviewMapper;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;  // Add this Lombok annotation

@AllArgsConstructor
@Service
@Slf4j  // Add this Lombok annotation
public class ReviewMessageProducer {
    private final RabbitTemplate rabbitTemplate;
    private final ReviewMapper reviewMapper;

    public void sendMessage(Review review) {
        try {
            log.info("Attempting to send review message for review: {}", review);
            ReviewMessage reviewMessage = reviewMapper.toReviewMessage(review);
            log.info("Mapped to review message: {}", reviewMessage);
            rabbitTemplate.convertAndSend("companyRatingQueue", reviewMessage);
            log.info("Successfully sent message to queue");
        } catch (Exception e) {
            log.error("Failed to send message to RabbitMQ", e);
            throw e;  // Re-throw to be caught by service layer
        }
    }
}