package dev.ducku.job.client;

import dev.ducku.job.external.Review;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "review-ms") //this name will work either with capital case or normal case letters
public interface ReviewClient {

    @GetMapping("/reviews")
    List<Review> getReviews(@RequestParam Long companyId);
}
