package dev.ducku.company;

import dev.ducku.company.dto.ReviewMessage;

import java.util.List;

public interface CompanyService {
    List<Company> findAll(int page, int size);

    Company findById(long id);

    boolean updateCompany(Company company, Long id);

    Company createCompany(Company company);

    boolean deleteCompany(long id);

    void updateCompanyRating(Long companyId,ReviewMessage message);
}
