package dev.ducku.job.external;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review {
    private Long id;
    private String title;
    private String description;
    private Double rating;
    private Long companyId;
    private boolean isDeleted = false;
}
