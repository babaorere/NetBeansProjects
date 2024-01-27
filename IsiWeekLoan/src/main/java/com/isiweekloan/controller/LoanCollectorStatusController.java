package com.isiweekloan.controller;

import com.isiweekloan.dto.LoanCollectorStatusDto;
import com.isiweekloan.entity.LoanCollectorStatusEntity;
import com.isiweekloan.exception.ResourceNotFoundException;
import com.isiweekloan.mapper.LoanCollectorStatusMapper;
import com.isiweekloan.service.LoanCollectorStatusService;
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

@RequestMapping("/loan-collector-status")
@RestController
@Slf4j
@Api("loan-collector-status")
public class LoanCollectorStatusController {
    private final LoanCollectorStatusService loanCollectorStatusService;

    public LoanCollectorStatusController(LoanCollectorStatusService loanCollectorStatusService) {
        this.loanCollectorStatusService = loanCollectorStatusService;
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody @Validated LoanCollectorStatusDto loanCollectorStatusDto) {
        loanCollectorStatusService.save(loanCollectorStatusDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoanCollectorStatusDto> findById(@PathVariable("id") Long id) {
        LoanCollectorStatusDto loanCollectorStatus = loanCollectorStatusService.findById(id);
        return ResponseEntity.ok(loanCollectorStatus);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        try {
            LoanCollectorStatusDto loanCollectorStatus = loanCollectorStatusService.findById(id);

            if (loanCollectorStatus == null) {
                log.error("Unable to delete non-existent data with ID {}", id);
                throw new ResourceNotFoundException("Unable to delete non-existent data with ID " + id);
            }

            loanCollectorStatusService.deleteById(id);
            log.info("Data with ID {} deleted successfully", id);

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("Error deleting data with ID {}: {}", id, e.getMessage());
            // Puedes lanzar una excepción diferente o manejarla de otra manera según tus requisitos.
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/page-query")
    public ResponseEntity<Page<LoanCollectorStatusDto>> pageQuery(LoanCollectorStatusDto loanCollectorStatusDto, @PageableDefault(sort = "createAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<LoanCollectorStatusDto> loanCollectorStatusPage = loanCollectorStatusService.findByCondition(loanCollectorStatusDto, pageable);
        return ResponseEntity.ok(loanCollectorStatusPage);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@RequestBody @Validated LoanCollectorStatusDto loanCollectorStatusDto, @PathVariable("id") Long id) {
        loanCollectorStatusService.update(loanCollectorStatusDto, id);
        return ResponseEntity.ok().build();
    }
}
