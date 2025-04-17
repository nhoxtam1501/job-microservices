package dev.ducku.job.response;

import dev.ducku.job.Job;
import dev.ducku.job.external.Company;
import dev.ducku.job.external.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobResponse {
    private Long id;
    private String title;
    private BigDecimal minSalary;
    private BigDecimal maxSalary;
    private String location;
    private String description;
    private boolean isDeleted = false;
    private Company company;
    private List<Review> reviews;
}
