package dev.ducku.company.messaging;

import dev.ducku.company.CompanyService;
import dev.ducku.company.dto.ReviewMessage;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ReviewMessageConsumer {
    private final CompanyService companyService;

    @RabbitListener(queues = "companyRatingQueue")
    public void consumeMessage(ReviewMessage message) {
        companyService.updateCompanyRating(message.getCompanyId(),message);
    }
}
