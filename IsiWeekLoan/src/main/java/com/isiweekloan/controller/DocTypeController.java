package com.isiweekloan.controller;

import com.isiweekloan.entity.DocTypeEntity;
import com.isiweekloan.exception.BadRequestException;
import com.isiweekloan.exception.ResourceNotFoundException;
import com.isiweekloan.service.DocTypeService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/doc-types")
public class DocTypeController {

    @Autowired
    private DocTypeService docTypeService;

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<String> handleBadRequestException(BadRequestException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @GetMapping
    @Transactional
    public ResponseEntity<List<DocTypeEntity>> getDocTypes() {
        List<DocTypeEntity> docTypes = docTypeService.findAllDocTypes();
        return ResponseEntity.ok(docTypes);
    }

    @GetMapping("/{id}")
    @Transactional
    public ResponseEntity<DocTypeEntity> getDocTypeById(@PathVariable Long id) {
        Optional<DocTypeEntity> docType = docTypeService.findDocTypeById(id);
        return docType.isPresent() ? ResponseEntity.ok(docType.get()) : ResponseEntity.notFound().build();
    }

    @PostMapping
    @Transactional
    public ResponseEntity<DocTypeEntity> createDocType(@Valid @RequestBody DocTypeEntity docType) throws BadRequestException {
        validateRequiredFields(docType);
        DocTypeEntity createdDocType = docTypeService.createDocType(docType);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDocType);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DocTypeEntity> updateDocType(@PathVariable Long id, @Valid @RequestBody DocTypeEntity docType) throws BadRequestException, ResourceNotFoundException {
        if (docType.getId() != id) {
            throw new BadRequestException("ID in request body does not match ID in path variable.");
        }

        validateRequiredFields(docType);
        Optional<DocTypeEntity> existingDocType = docTypeService.findDocTypeById(id);
        if (!existingDocType.isPresent()) {
            throw new ResourceNotFoundException("DocType with ID " + id + " not found.");
        }

        existingDocType.get().setName(docType.getName());
        existingDocType.get().setDescription(docType.getDescription());
        return ResponseEntity.ok(docTypeService.updateDocType(id, existingDocType.get()));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deleteDocType(@PathVariable Long id) throws ResourceNotFoundException {
        docTypeService.deleteDocType(id);
        return ResponseEntity.noContent().build();
    }

    private void validateRequiredFields(DocTypeEntity docType) throws BadRequestException {
        if (docType.getName() == null || docType.getName().isEmpty()) {
            throw new BadRequestException("The name field is required.");
        }
        if (docType.getDescription() == null || docType.getDescription().isEmpty()) {
            throw new BadRequestException("The description field is required.");
        }
    }
}
