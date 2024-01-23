package com.isiweek.departament.rest;

import com.isiweek.departament.model.DepartamentDTO;
import com.isiweek.departament.service.DepartamentService;
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
@RequestMapping(value = "/api/departaments", produces = MediaType.APPLICATION_JSON_VALUE)
public class DepartamentResource {

    private final DepartamentService departamentService;

    public DepartamentResource(final DepartamentService departamentService) {
        this.departamentService = departamentService;
    }

    @GetMapping
    public ResponseEntity<List<DepartamentDTO>> getAllDepartaments() {
        return ResponseEntity.ok(departamentService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartamentDTO> getDepartament(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(departamentService.get(id));
    }

    @PostMapping
    public ResponseEntity<Long> createDepartament(
            @RequestBody @Valid final DepartamentDTO departamentDTO) {
        final Long createdId = departamentService.create(departamentDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateDepartament(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final DepartamentDTO departamentDTO) {
        departamentService.update(id, departamentDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartament(@PathVariable(name = "id") final Long id) {
        departamentService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
