package dev.ducku.company.impl;


import dev.ducku.company.Company;
import dev.ducku.company.CompanyRepository;
import dev.ducku.company.CompanyService;
import dev.ducku.company.client.ReviewClient;
import dev.ducku.company.dto.ReviewMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository companyRepository;
    private final ReviewClient reviewClient;

    @Override
    public List<Company> findAll(int page, int size) {
        return companyRepository.findAll(PageRequest.of(page, size)).getContent();
    }

    @Override
    public Company findById(long id) {
        return companyRepository.findById(id).orElse(null);
    }


    @Override
    public boolean updateCompany(Company company, Long id) {
        Optional<Company> existedCompany = companyRepository.findById(id);
        if (existedCompany.isPresent()) {
            company.setId(id);
            companyRepository.save(company);
            return true;
        }
        return false;
    }

    @Override
    public Company createCompany(Company company) {
        try {
            company.setId(null);
            return companyRepository.save(company);
        } catch (RuntimeException e) {
            return null;
        }
    }

    @Override
    public boolean deleteCompany(long id) {
        Optional<Company> company = companyRepository.findById(id);
        if (company.isPresent()) {
            company.get().setDeleted(true);
            companyRepository.save(company.get());
            return true;
        } else return false;
    }

    @Override
    public void updateCompanyRating(Long companyId, ReviewMessage message) {
        log.info("Received message: {}", message);
        Company company =  companyRepository.findById(companyId).orElse(null);
        if(company != null) {
            company.setRating(reviewClient.getAverageRating(companyId));
            companyRepository.save(company);
            log.info("Updated company rating: {}", company);
        }

    }
}
