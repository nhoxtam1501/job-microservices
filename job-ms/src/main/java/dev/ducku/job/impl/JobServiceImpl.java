package dev.ducku.job.impl;

import dev.ducku.job.Job;
import dev.ducku.job.JobRepository;
import dev.ducku.job.JobService;
import dev.ducku.job.client.CompanyClient;
import dev.ducku.job.client.ReviewClient;
import dev.ducku.job.external.Company;
import dev.ducku.job.external.Review;
import dev.ducku.job.mapper.JobMapper;
import dev.ducku.job.response.JobResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.CommonsClientAutoConfiguration;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class JobServiceImpl implements JobService {
    private final JobRepository jobRepository;
    private final RestTemplate restTemplate;
    private final JobMapper jobMapper;
    private final CompanyClient companyClient;
    private final ReviewClient reviewClient;
    private final CommonsClientAutoConfiguration commonsClientAutoConfiguration;


    @Override
    @CircuitBreaker(name = "companyBreaker", fallbackMethod = "companyBreakerFallback")
//    @RateLimiter(name = "companyBreaker", fallbackMethod = "companyBreakerFallback")
//    @Retry(name = "companyBreaker", fallbackMethod = "companyBreakerFallback")
    public List<Job> findAll() {
        Company company = restTemplate.getForObject("http://company-ms/companies/1", Company.class);
        log.info("Company id: {}, company name: {}, company description: {}", company.getId(), company.getName(), company.getDescription());
        return jobRepository.findAll(PageRequest.of(0, 10)).getContent();
    }

    public List<String> companyBreakerFallback(Exception e) {
        return List.of("Company 1", "Company 2", "Company 3");
    }

    @Override
    public void createJob(Job job) {
        job.setId(null);
        jobRepository.save(job);
    }

    @Override
    public JobResponse findJob(Long id) {
        try {
            Job job = jobRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Can not found entity with id " + id));
//            Company company = restTemplate.getForObject("http://company-ms/companies/" + job.getCompanyId(), Company.class);
            Company company = companyClient.getCompanyById(job.getCompanyId());
//            List<Review> reviews = restTemplate.exchange(
//                    "http://review-ms/reviews?companyId={companyId}",
//                    HttpMethod.GET,
//                    null,
//                    new ParameterizedTypeReference<List<Review>>() {
//                    },
//                    company.getId()
//            ).getBody();
            List<Review> reviews = reviewClient.getReviews(job.getCompanyId());
            return jobMapper.toJobResponse(job, company, reviews);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Job deleteJobById(Long id) {
        try {
            Job job = jobRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Can not found entity with id " + id));
            job.setDeleted(false);
            return job;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Job updateJobById(Long id, Job job) {
        try {
            if (jobRepository.existsById(id)) {
                job.setId(id);
                jobRepository.flush();
                return job;
            }
            throw new EntityNotFoundException("Can not found entity with id " + id);
        } catch (Exception e) {
            return null;
        }
    }
}
