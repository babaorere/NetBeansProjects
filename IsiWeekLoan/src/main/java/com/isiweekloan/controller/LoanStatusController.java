package com.isiweekloan.controller;

import com.isiweekloan.dto.LoanStatusDto;
import com.isiweekloan.entity.LoanStatusEntity;
import com.isiweekloan.exception.ResourceNotFoundException;
import com.isiweekloan.mapper.LoanStatusMapper;
import com.isiweekloan.service.LoanStatusService;
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

@RequestMapping("/loan-status")
@RestController
@Slf4j
@Api("loan-status")
public class LoanStatusController {
    private final LoanStatusService loanStatusService;

    public LoanStatusController(LoanStatusService loanStatusService) {
        this.loanStatusService = loanStatusService;
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody @Validated LoanStatusDto loanStatusDto) {
        loanStatusService.save(loanStatusDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoanStatusDto> findById(@PathVariable("id") Long id) {
        LoanStatusDto loanStatus = loanStatusService.findById(id);
        return ResponseEntity.ok(loanStatus);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        try {
            LoanStatusDto loanStatusDto = Optional.ofNullable(loanStatusService.findById(id))
                    .orElseThrow(() -> {
                        log.error("Unable to delete non-existent data with ID {}", id);
                        return new ResourceNotFoundException("Unable to delete non-existent data with ID " + id);
                    });

            loanStatusService.deleteById(id);
            log.info("Data with ID {} deleted successfully", id);

            return ResponseEntity.ok().build();

        } catch (Exception e) {
            log.error("Error deleting data with ID {}: {}", id, e.getMessage());
            // Puedes lanzar una excepción diferente o manejarla de otra manera según tus requisitos.
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/page-query")
    public ResponseEntity<Page<LoanStatusDto>> pageQuery(LoanStatusDto loanStatusDto, @PageableDefault(sort = "createAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<LoanStatusDto> loanStatusPage = loanStatusService.findByCondition(loanStatusDto, pageable);
        return ResponseEntity.ok(loanStatusPage);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@RequestBody @Validated LoanStatusDto loanStatusDto, @PathVariable("id") Long id) {
        loanStatusService.update(loanStatusDto, id);
        return ResponseEntity.ok().build();
    }
}
