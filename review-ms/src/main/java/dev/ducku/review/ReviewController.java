package dev.ducku.review;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping
    public ResponseEntity<List<Review>> findAllByCompanyId(@RequestParam(name = "companyId", required = false) Long companyId) {
        return ResponseEntity.ok(reviewService.findAllReviewByCompany(companyId));
    }

    @PostMapping
    public ResponseEntity<Review> create(@RequestParam(name = "companyId") Long companyId, @RequestBody Review review) {
        if (reviewService.createReview(review, companyId) != null) {
            return ResponseEntity.ok(review);
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<Review> findById(@PathVariable Long reviewId) {
        Review review = reviewService.findById(reviewId);
        if (review != null) {
            return ResponseEntity.ok(review);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<String> updateReview(@PathVariable Long reviewId, @RequestBody Review review) {
        Review updatedReview = reviewService.updateReview(review, reviewId);
        if (updatedReview != null) {
            return ResponseEntity.ok("Review updated successfully");
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<String> deleteById(@RequestParam Long reviewId, @PathVariable String companyId) {
        reviewService.deleteReview(reviewId);
        return ResponseEntity.ok("Review deleted successfully");
    }
}
