package dev.ducku.review.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.ducku.review.Review;
import dev.ducku.review.dto.ReviewMessage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ReviewMapper {
    private final ObjectMapper objectMapper;

    public ReviewMessage toReviewMessage(Review review) {
        return objectMapper.convertValue(review, ReviewMessage.class);
    }
}
