package com.isiweek.company;

import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/companies", produces = MediaType.APPLICATION_JSON_VALUE)
public class CompanyResource {

    private final CompanyService companyService;

    public CompanyResource(final CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping
    public ResponseEntity<List<CompanyDTO>> getAllCompanies() {
        return ResponseEntity.ok(companyService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyDTO> getCompany(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(companyService.get(id));
    }

    @PostMapping
    public ResponseEntity<Long> createCompany(@RequestBody @Valid final CompanyDTO companyDTO) {
        final Long createdId = companyService.create(companyDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateCompany(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final CompanyDTO companyDTO) {
        companyService.update(id, companyDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompany(@PathVariable(name = "id") final Long id) {
        companyService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
