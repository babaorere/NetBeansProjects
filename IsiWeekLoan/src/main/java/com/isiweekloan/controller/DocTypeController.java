package com.isiweekloan.controller;

import com.isiweekloan.dto.DocTypeDto;
import com.isiweekloan.entity.DocTypeEntity;
import com.isiweekloan.exception.ResourceNotFoundException;
import com.isiweekloan.mapper.DocTypeMapper;
import com.isiweekloan.service.DocTypeService;
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

@RequestMapping("/doc-type")
@RestController
@Slf4j
@Api("doc-type")
public class DocTypeController {
    private final DocTypeService docTypeService;

    public DocTypeController(DocTypeService docTypeService) {
        this.docTypeService = docTypeService;
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody @Validated DocTypeDto docTypeDto) {
        docTypeService.save(docTypeDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DocTypeDto> findById(@PathVariable("id") Long id) {
        DocTypeDto docType = docTypeService.findById(id);
        return ResponseEntity.ok(docType);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        try {
            DocTypeDto docTypeDto = Optional.ofNullable(docTypeService.findById(id))
                    .orElseThrow(() -> {
                        log.error("Unable to delete non-existent data with ID {}", id);
                        return new ResourceNotFoundException("Unable to delete non-existent data with ID " + id);
                    });

            docTypeService.deleteById(id);
            log.info("Data with ID {} deleted successfully", id);

            return ResponseEntity.ok().build();

        } catch (Exception e) {
            log.error("Error deleting data with ID {}: {}", id, e.getMessage());
            // Puedes lanzar una excepción diferente o manejarla de otra manera según tus requisitos.
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/page-query")
    public ResponseEntity<Page<DocTypeDto>> pageQuery(DocTypeDto docTypeDto, @PageableDefault(sort = "createAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<DocTypeDto> docTypePage = docTypeService.findByCondition(docTypeDto, pageable);
        return ResponseEntity.ok(docTypePage);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@RequestBody @Validated DocTypeDto docTypeDto, @PathVariable("id") Long id) {
        docTypeService.update(docTypeDto, id);
        return ResponseEntity.ok().build();
    }
}
