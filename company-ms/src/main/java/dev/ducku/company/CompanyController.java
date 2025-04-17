package dev.ducku.company;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/companies")
public class CompanyController {
    private final CompanyService companyService;
    private final int PAGE_SIZE = 10;

    @GetMapping
    public List<Company> findAll(@RequestParam(required = false) Integer page) {
        page = page == null ? 0 : page;
        return companyService.findAll(page, PAGE_SIZE);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable(name = "id") Long id, @RequestBody Company company) {
        boolean isUpdated = companyService.updateCompany(company, id);
        return isUpdated == true ? ResponseEntity.ok("Company updated successfully") : ResponseEntity.badRequest().build();
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody Company company) {
        Company savedCompany = companyService.createCompany(company);
        if (savedCompany != null) {
            return new ResponseEntity("Company created successfully", HttpStatus.CREATED);
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        boolean isDeleted = companyService.deleteCompany(id);
        return isDeleted == true ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable Long id) {
        Company company = companyService.findById(id);
        if (company != null) {
            return new ResponseEntity(company, HttpStatus.OK);
        } else {
            return new ResponseEntity("Company not found", HttpStatus.NOT_FOUND);
        }
    }
}
