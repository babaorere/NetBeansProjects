package com.isiweekloan.controller;

import com.isiweekloan.dto.DepartamentDto;
import com.isiweekloan.entity.DepartamentEntity;
import com.isiweekloan.exception.ResourceNotFoundException;
import com.isiweekloan.mapper.DepartamentMapper;
import com.isiweekloan.service.DepartamentService;
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

@RequestMapping("/departament")
@RestController
@Slf4j
@Api("departament")
public class DepartamentController {
    private final DepartamentService departamentService;

    public DepartamentController(DepartamentService departamentService) {
        this.departamentService = departamentService;
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody @Validated DepartamentDto departamentDto) {
        departamentService.save(departamentDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartamentDto> findById(@PathVariable("id") Long id) {
        DepartamentDto departament = departamentService.findById(id);
        return ResponseEntity.ok(departament);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        try {
            DepartamentDto departamentDto = Optional.ofNullable(departamentService.findById(id))
                    .orElseThrow(() -> {
                        log.error("Unable to delete non-existent data with ID {}", id);
                        return new ResourceNotFoundException("Unable to delete non-existent data with ID " + id);
                    });

            departamentService.deleteById(id);
            log.info("Data with ID {} deleted successfully", id);

            return ResponseEntity.ok().build();

        } catch (Exception e) {
            log.error("Error deleting data with ID {}: {}", id, e.getMessage());
            // Puedes lanzar una excepción diferente o manejarla de otra manera según tus requisitos.
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/page-query")
    public ResponseEntity<Page<DepartamentDto>> pageQuery(DepartamentDto departamentDto, @PageableDefault(sort = "createAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<DepartamentDto> departamentPage = departamentService.findByCondition(departamentDto, pageable);
        return ResponseEntity.ok(departamentPage);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@RequestBody @Validated DepartamentDto departamentDto, @PathVariable("id") Long id) {
        departamentService.update(departamentDto, id);
        return ResponseEntity.ok().build();
    }
}
