package com.isiweekloan.controller;

import com.isiweekloan.dto.LoanContractDto;
import com.isiweekloan.entity.LoanContractEntity;
import com.isiweekloan.exception.ResourceNotFoundException;
import com.isiweekloan.mapper.LoanContractMapper;
import com.isiweekloan.service.LoanContractService;
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

@RequestMapping("/loan-contract")
@RestController
@Slf4j
@Api("loan-contract")
public class LoanContractController {
    private final LoanContractService loanContractService;

    public LoanContractController(LoanContractService loanContractService) {
        this.loanContractService = loanContractService;
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody @Validated LoanContractDto loanContractDto) {
        loanContractService.save(loanContractDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoanContractDto> findById(@PathVariable("id") Long id) {
        LoanContractDto loanContract = loanContractService.findById(id);
        return ResponseEntity.ok(loanContract);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        try {
            LoanContractDto loanContractDto = Optional.ofNullable(loanContractService.findById(id))
                    .orElseThrow(() -> {
                        log.error("Unable to delete non-existent data with ID {}", id);
                        return new ResourceNotFoundException("Unable to delete non-existent data with ID " + id);
                    });

            loanContractService.deleteById(id);
            log.info("Data with ID {} deleted successfully", id);

            return ResponseEntity.ok().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            log.error("Error deleting data with ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }

    @GetMapping("/page-query")
    public ResponseEntity<Page<LoanContractDto>> pageQuery(LoanContractDto loanContractDto, @PageableDefault(sort = "createAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<LoanContractDto> loanContractPage = loanContractService.findByCondition(loanContractDto, pageable);
        return ResponseEntity.ok(loanContractPage);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@RequestBody @Validated LoanContractDto loanContractDto, @PathVariable("id") Long id) {
        loanContractService.update(loanContractDto, id);
        return ResponseEntity.ok().build();
    }
}
