package com.isiweekloan.controller;

import com.isiweekloan.dto.EmployeeStatusDto;
import com.isiweekloan.entity.EmployeeStatusEntity;
import com.isiweekloan.exception.ResourceNotFoundException;
import com.isiweekloan.mapper.EmployeeStatusMapper;
import com.isiweekloan.service.EmployeeStatusService;
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

@RequestMapping("/employee-status")
@RestController
@Slf4j
@Api("employee-status")
public class EmployeeStatusController {
    private final EmployeeStatusService employeeStatusService;

    public EmployeeStatusController(EmployeeStatusService employeeStatusService) {
        this.employeeStatusService = employeeStatusService;
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody @Validated EmployeeStatusDto employeeStatusDto) {
        employeeStatusService.save(employeeStatusDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeStatusDto> findById(@PathVariable("id") Long id) {
        EmployeeStatusDto employeeStatus = employeeStatusService.findById(id);
        return ResponseEntity.ok(employeeStatus);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        try {
            EmployeeStatusDto employeeStatusDto = Optional.ofNullable(employeeStatusService.findById(id))
                    .orElseThrow(() -> {
                        log.error("Unable to delete non-existent data with ID {}", id);
                        return new ResourceNotFoundException("Unable to delete non-existent data with ID " + id);
                    });

            employeeStatusService.deleteById(id);
            log.info("Data with ID {} deleted successfully", id);

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("Error deleting data with ID {}: {}", id, e.getMessage());
            // Puedes lanzar una excepción diferente o manejarla de otra manera según tus requisitos.
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/page-query")
    public ResponseEntity<Page<EmployeeStatusDto>> pageQuery(EmployeeStatusDto employeeStatusDto, @PageableDefault(sort = "createAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<EmployeeStatusDto> employeeStatusPage = employeeStatusService.findByCondition(employeeStatusDto, pageable);
        return ResponseEntity.ok(employeeStatusPage);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@RequestBody @Validated EmployeeStatusDto employeeStatusDto, @PathVariable("id") Long id) {
        employeeStatusService.update(employeeStatusDto, id);
        return ResponseEntity.ok().build();
    }
}
