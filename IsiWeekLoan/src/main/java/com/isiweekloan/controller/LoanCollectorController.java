package com.isiweekloan.controller;

import com.isiweekloan.dto.LoanCollectorDto;
import com.isiweekloan.entity.LoanCollectorEntity;
import com.isiweekloan.exception.ResourceNotFoundException;
import com.isiweekloan.mapper.LoanCollectorMapper;
import com.isiweekloan.service.LoanCollectorService;
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

@RequestMapping("/loan-collector")
@RestController
@Slf4j
@Api("loan-collector")
public class LoanCollectorController {
    private final LoanCollectorService loanCollectorService;

    public LoanCollectorController(LoanCollectorService loanCollectorService) {
        this.loanCollectorService = loanCollectorService;
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody @Validated LoanCollectorDto loanCollectorDto) {
        loanCollectorService.save(loanCollectorDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoanCollectorDto> findById(@PathVariable("id") Long id) {
        LoanCollectorDto loanCollector = loanCollectorService.findById(id);
        return ResponseEntity.ok(loanCollector);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        try {
            LoanCollectorDto loanCollectorDto = Optional.ofNullable(loanCollectorService.findById(id))
                    .orElseThrow(() -> {
                        log.error("Unable to delete non-existent data with ID {}", id);
                        return new ResourceNotFoundException("Unable to delete non-existent data with ID " + id);
                    });

            loanCollectorService.deleteById(id);
            log.info("Data with ID {} deleted successfully", id);

            return ResponseEntity.ok().build();

        } catch (Exception e) {
            log.error("Error deleting data with ID {}: {}", id, e.getMessage());
            // Puedes lanzar una excepción diferente o manejarla de otra manera según tus requisitos.
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @GetMapping("/page-query")
    public ResponseEntity<Page<LoanCollectorDto>> pageQuery(LoanCollectorDto loanCollectorDto, @PageableDefault(sort = "createAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<LoanCollectorDto> loanCollectorPage = loanCollectorService.findByCondition(loanCollectorDto, pageable);
        return ResponseEntity.ok(loanCollectorPage);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@RequestBody @Validated LoanCollectorDto loanCollectorDto, @PathVariable("id") Long id) {
        loanCollectorService.update(loanCollectorDto, id);
        return ResponseEntity.ok().build();
    }
}
