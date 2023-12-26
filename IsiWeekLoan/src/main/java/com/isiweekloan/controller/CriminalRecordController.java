package com.isiweekloan.controller;

import com.isiweekloan.dto.CriminalRecordDto;
import com.isiweekloan.entity.CriminalRecordEntity;
import com.isiweekloan.exception.ResourceNotFoundException;
import com.isiweekloan.mapper.CriminalRecordMapper;
import com.isiweekloan.service.CriminalRecordService;
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

@RequestMapping("/criminal-record")
@RestController
@Slf4j
@Api("criminal-record")
public class CriminalRecordController {
    private final CriminalRecordService criminalRecordService;

    public CriminalRecordController(CriminalRecordService criminalRecordService) {
        this.criminalRecordService = criminalRecordService;
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody @Validated CriminalRecordDto criminalRecordDto) {
        criminalRecordService.save(criminalRecordDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CriminalRecordDto> findById(@PathVariable("id") Long id) {
        CriminalRecordDto criminalRecord = criminalRecordService.findById(id);
        return ResponseEntity.ok(criminalRecord);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        try {
            CriminalRecordDto criminalRecordDto = Optional.ofNullable(criminalRecordService.findById(id))
                    .orElseThrow(() -> {
                        log.error("Unable to delete non-existent data with ID {}", id);
                        return new ResourceNotFoundException("Unable to delete non-existent data with ID " + id);
                    });

            criminalRecordService.deleteById(id);
            log.info("Data with ID {} deleted successfully", id);

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("Error deleting data with ID {}: {}", id, e.getMessage());
            // Puedes lanzar una excepción diferente o manejarla de otra manera según tus requisitos.
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/page-query")
    public ResponseEntity<Page<CriminalRecordDto>> pageQuery(CriminalRecordDto criminalRecordDto, @PageableDefault(sort = "createAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<CriminalRecordDto> criminalRecordPage = criminalRecordService.findByCondition(criminalRecordDto, pageable);
        return ResponseEntity.ok(criminalRecordPage);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@RequestBody @Validated CriminalRecordDto criminalRecordDto, @PathVariable("id") Long id) {
        criminalRecordService.update(criminalRecordDto, id);
        return ResponseEntity.ok().build();
    }
}
