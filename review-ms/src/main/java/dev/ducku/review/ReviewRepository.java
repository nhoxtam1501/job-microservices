package dev.ducku.review;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    //@Query("SELECT r FROM Review r WHERE r.company.id = :companyId")
//    List<Review> findByCompanyId(@Param("companyId") Long companyId);
    List<Review> findByCompanyId(Long companyId);
}
