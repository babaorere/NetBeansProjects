package com.isiweekloan.controller;

import com.isiweekloan.dto.CompanyDto;
import com.isiweekloan.entity.CompanyEntity;
import com.isiweekloan.exception.ResourceNotFoundException;
import com.isiweekloan.mapper.CompanyMapper;
import com.isiweekloan.service.CompanyService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequestMapping("/company")
@RestController
@Slf4j
@Api("company")
public class CompanyController {
    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody @Validated CompanyDto companyDto) {
        companyService.save(companyDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyDto> findById(@PathVariable("id") Long id) {
        CompanyDto company = companyService.findById(id);
        return ResponseEntity.ok(company);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        try {
            CompanyDto companyDto = Optional.ofNullable(companyService.findById(id))
                    .orElseThrow(() -> {
                        log.error("Unable to delete non-existent data with ID {}", id);
                        return new ResourceNotFoundException("Unable to delete non-existent data with ID " + id);
                    });

            companyService.deleteById(id);
            log.info("Data with ID {} deleted successfully", id);

            return ResponseEntity.ok().build();

        } catch (Exception e) {
            log.error("Error deleting data with ID {}: {}", id, e.getMessage());
            // Puedes lanzar una excepción diferente o manejarla de otra manera según tus requisitos.
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/page-query")
    public ResponseEntity<Page<CompanyDto>> pageQuery(CompanyDto companyDto, @PageableDefault(sort = "createAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<CompanyDto> companyPage = companyService.findByCondition(companyDto, pageable);
        return ResponseEntity.ok(companyPage);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@RequestBody @Validated CompanyDto companyDto, @PathVariable("id") Long id) {
        companyService.update(companyDto, id);
        return ResponseEntity.ok().build();
    }
}
