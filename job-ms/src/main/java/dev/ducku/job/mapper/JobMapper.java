package dev.ducku.job.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.ducku.job.Job;
import dev.ducku.job.external.Company;
import dev.ducku.job.external.Review;
import dev.ducku.job.response.JobResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class JobMapper {
    private final ObjectMapper objectMapper;

    public JobResponse toJobResponse(Job job, Company company, List<Review> reviews) {
        JobResponse jobResponse = objectMapper.convertValue(job, JobResponse.class);
        jobResponse.setCompany(company);
        jobResponse.setReviews( objectMapper.convertValue(reviews, List.class));
        return jobResponse;
    }

}
