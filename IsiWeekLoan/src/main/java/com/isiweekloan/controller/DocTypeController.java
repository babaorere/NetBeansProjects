package com.isiweekloan.controller;

import com.isiweekloan.entity.DocTypeEntity;
import com.isiweekloan.repository.DocTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/doc-types")
public class DocTypeController {

    private final DocTypeRepository docTypeRepository;

    @Autowired
    public DocTypeController(DocTypeRepository docTypeRepository) {
        this.docTypeRepository = docTypeRepository;
    }

    /**
     * Retrieves all DocType entities.
     *
     * @return List of DocTypeEntity
     */
    @GetMapping
    public ResponseEntity<List<DocTypeEntity>> getAllDocTypes() {
        List<DocTypeEntity> docTypes = docTypeRepository.findAll();
        return ResponseEntity.ok(docTypes);
    }

    /**
     * Retrieves a DocType entity by its ID.
     *
     * @param id DocType ID
     * @return ResponseEntity with DocTypeEntity if found, or 404 Not Found if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<DocTypeEntity> getDocTypeById(@PathVariable Long id) {
        Optional<DocTypeEntity> optionalDocType = docTypeRepository.findById(id);
        return optionalDocType.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Creates a new DocType entity.
     *
     * @param docTypeEntity DocTypeEntity to be created
     * @return ResponseEntity with created DocTypeEntity
     */
    @PostMapping
    public ResponseEntity<DocTypeEntity> createDocType(@RequestBody DocTypeEntity docTypeEntity) {
        Objects.requireNonNull(docTypeEntity.getName(), "Name cannot be null");
        Objects.requireNonNull(docTypeEntity.getDescription(), "Description cannot be null");

        // Additional validation if needed
        DocTypeEntity createdDocType = docTypeRepository.save(docTypeEntity);
        return ResponseEntity.ok(createdDocType);
    }

    /**
     * Updates an existing DocType entity by its ID.
     *
     * @param id DocType ID
     * @param docTypeEntity Updated DocTypeEntity
     * @return ResponseEntity with updated DocTypeEntity if found, or 404 Not Found if not found
     */
    @PutMapping("/{id}")
    public ResponseEntity<DocTypeEntity> updateDocType(@PathVariable Long id, @RequestBody DocTypeEntity docTypeEntity) {
        Optional<DocTypeEntity> optionalExistingDocType = docTypeRepository.findById(id);

        if (optionalExistingDocType.isPresent()) {
            DocTypeEntity existingDocType = optionalExistingDocType.get();

            // Update fields if needed
            existingDocType.setName(docTypeEntity.getName());
            existingDocType.setDescription(docTypeEntity.getDescription());

            DocTypeEntity updatedDocType = docTypeRepository.save(existingDocType);
            return ResponseEntity.ok(updatedDocType);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Deletes a DocType entity by its ID.
     *
     * @param id DocType ID
     * @return ResponseEntity with no content if deleted successfully, or 404 Not Found if not found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDocType(@PathVariable Long id) {
        if (docTypeRepository.existsById(id)) {
            docTypeRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Add @ExceptionHandler and additional methods as needed for error handling
}
