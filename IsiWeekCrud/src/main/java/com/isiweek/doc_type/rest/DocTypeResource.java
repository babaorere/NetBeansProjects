package com.isiweek.doc_type.rest;

import com.isiweek.doc_type.model.DocTypeDTO;
import com.isiweek.doc_type.service.DocTypeService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/docTypes", produces = MediaType.APPLICATION_JSON_VALUE)
public class DocTypeResource {

    private final DocTypeService docTypeService;

    public DocTypeResource(final DocTypeService docTypeService) {
        this.docTypeService = docTypeService;
    }

    @GetMapping
    public ResponseEntity<List<DocTypeDTO>> getAllDocTypes() {
        return ResponseEntity.ok(docTypeService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DocTypeDTO> getDocType(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(docTypeService.get(id));
    }

    @PostMapping
    public ResponseEntity<Long> createDocType(@RequestBody @Valid final DocTypeDTO docTypeDTO) {
        final Long createdId = docTypeService.create(docTypeDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateDocType(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final DocTypeDTO docTypeDTO) {
        docTypeService.update(id, docTypeDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDocType(@PathVariable(name = "id") final Long id) {
        docTypeService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
