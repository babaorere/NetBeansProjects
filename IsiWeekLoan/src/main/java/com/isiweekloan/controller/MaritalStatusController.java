package com.isiweekloan.controller;

import com.isiweekloan.dto.MaritalStatusDto;
import com.isiweekloan.entity.MaritalStatusEntity;
import com.isiweekloan.exception.ResourceNotFoundException;
import com.isiweekloan.mapper.MaritalStatusMapper;
import com.isiweekloan.service.MaritalStatusService;
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

@RequestMapping("/marital-status")
@RestController
@Slf4j
@Api("marital-status")
public class MaritalStatusController {
    private final MaritalStatusService maritalStatusService;

    public MaritalStatusController(MaritalStatusService maritalStatusService) {
        this.maritalStatusService = maritalStatusService;
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody @Validated MaritalStatusDto maritalStatusDto) {
        maritalStatusService.save(maritalStatusDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MaritalStatusDto> findById(@PathVariable("id") Long id) {
        MaritalStatusDto maritalStatus = maritalStatusService.findById(id);
        return ResponseEntity.ok(maritalStatus);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        try {
            MaritalStatusDto maritalStatusDto = Optional.ofNullable(maritalStatusService.findById(id))
                    .orElseThrow(() -> {
                        log.error("Unable to delete non-existent data with ID {}", id);
                        return new ResourceNotFoundException("Unable to delete non-existent data with ID " + id);
                    });

            maritalStatusService.deleteById(id);
            log.info("Data with ID {} deleted successfully", id);

            return ResponseEntity.ok().build();

        } catch (Exception e) {
            log.error("Error deleting data with ID {}: {}", id, e.getMessage());
            // Puedes lanzar una excepción diferente o manejarla de otra manera según tus requisitos.
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @GetMapping("/page-query")
    public ResponseEntity<Page<MaritalStatusDto>> pageQuery(MaritalStatusDto maritalStatusDto, @PageableDefault(sort = "createAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<MaritalStatusDto> maritalStatusPage = maritalStatusService.findByCondition(maritalStatusDto, pageable);
        return ResponseEntity.ok(maritalStatusPage);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@RequestBody @Validated MaritalStatusDto maritalStatusDto, @PathVariable("id") Long id) {
        maritalStatusService.update(maritalStatusDto, id);
        return ResponseEntity.ok().build();
    }
}
