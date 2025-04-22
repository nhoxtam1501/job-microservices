package dev.ducku.review.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewMessage {
    private Long id;
    private String title;
    private String description;
    private Double rating;
    private Long companyId;
}
