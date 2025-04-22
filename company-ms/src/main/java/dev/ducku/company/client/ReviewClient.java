package dev.ducku.company.client;

import jakarta.ws.rs.QueryParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "review-ms")
public interface ReviewClient {

    @GetMapping("/reviews/ratings/average")
    Double getAverageRating(@RequestParam("companyId") Long companyId);
}
