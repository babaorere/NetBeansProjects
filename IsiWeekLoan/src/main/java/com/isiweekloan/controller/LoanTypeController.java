package com.isiweekloan.controller;

import com.isiweekloan.dto.LoanTypeDto;
import com.isiweekloan.entity.LoanTypeEntity;
import com.isiweekloan.exception.ResourceNotFoundException;
import com.isiweekloan.mapper.LoanTypeMapper;
import com.isiweekloan.service.LoanTypeService;
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

@RequestMapping("/loan-type")
@RestController
@Slf4j
@Api("loan-type")
public class LoanTypeController {
    private final LoanTypeService loanTypeService;

    public LoanTypeController(LoanTypeService loanTypeService) {
        this.loanTypeService = loanTypeService;
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody @Validated LoanTypeDto loanTypeDto) {
        loanTypeService.save(loanTypeDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoanTypeDto> findById(@PathVariable("id") Long id) {
        LoanTypeDto loanType = loanTypeService.findById(id);
        return ResponseEntity.ok(loanType);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        try {
            LoanTypeDto loanTypeDto = Optional.ofNullable(loanTypeService.findById(id))
                    .orElseThrow(() -> {
                        log.error("Unable to delete non-existent data with ID {}", id);
                        return new ResourceNotFoundException("Unable to delete non-existent data with ID " + id);
                    });

            loanTypeService.deleteById(id);
            log.info("Data with ID {} deleted successfully", id);

            return ResponseEntity.ok().build();

        } catch (Exception e) {
            log.error("Error deleting data with ID {}: {}", id, e.getMessage());
            // Puedes lanzar una excepción diferente o manejarla de otra manera según tus requisitos.
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @GetMapping("/page-query")
    public ResponseEntity<Page<LoanTypeDto>> pageQuery(LoanTypeDto loanTypeDto, @PageableDefault(sort = "createAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<LoanTypeDto> loanTypePage = loanTypeService.findByCondition(loanTypeDto, pageable);
        return ResponseEntity.ok(loanTypePage);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@RequestBody @Validated LoanTypeDto loanTypeDto, @PathVariable("id") Long id) {
        loanTypeService.update(loanTypeDto, id);
        return ResponseEntity.ok().build();
    }
}
