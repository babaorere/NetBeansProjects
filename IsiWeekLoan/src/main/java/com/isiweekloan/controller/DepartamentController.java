package com.isiweekloan.controller;

import com.isiweekloan.entity.DepartamentEntity;
import com.isiweekloan.exception.BadRequestException;
import com.isiweekloan.exception.ResourceNotFoundException;
import com.isiweekloan.service.DepartamentService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/departments")
public class DepartamentController {

    @Autowired
    private DepartamentService departamentService;

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<String> handleBadRequestException(BadRequestException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @GetMapping
    public ResponseEntity<List<DepartamentEntity>> getDepartaments() {
        List<DepartamentEntity> departaments = departamentService.findAllDepartaments();
        if (departaments.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(departaments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartamentEntity> getDepartamentById(@PathVariable Long id) throws ResourceNotFoundException {
        Optional<DepartamentEntity> departament = departamentService.findDepartamentById(id);
        if (!departament.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(departament.get());
    }

    @PostMapping
    public ResponseEntity<DepartamentEntity> createDepartament(@Validated @RequestBody DepartamentEntity departament) throws BadRequestException {
        validateRequiredFields(departament);
        DepartamentEntity createdDepartament = departamentService.createDepartament(departament);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDepartament);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DepartamentEntity> updateDepartament(@PathVariable Long id, @Validated @RequestBody DepartamentEntity departament) throws BadRequestException, ResourceNotFoundException {
        if (!departament.getId().equals(id)) {
            throw new BadRequestException("ID in request body does not match ID in path variable.");
        }
        validateRequiredFields(departament);
        Optional<DepartamentEntity> existingDepartament = departamentService.findDepartamentById(id);
        if (!existingDepartament.isPresent()) {
            throw new ResourceNotFoundException("Departament with ID " + id + " not found.");
        }
        existingDepartament.get().setName(departament.getName());
        existingDepartament.get().setDescription(departament.getDescription());
        return ResponseEntity.ok(departamentService.updateDepartament(id, existingDepartament.get()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartament(@PathVariable Long id) throws ResourceNotFoundException {
        departamentService.deleteDepartament(id);
        return ResponseEntity.noContent().build();
    }

    private void validateRequiredFields(DepartamentEntity departament) throws BadRequestException {
        if (departament.getName() == null || departament.getName().isEmpty()) {
            throw new BadRequestException("The name field is required.");
        }
        if (departament.getDescription() == null || departament.getDescription().isEmpty()) {
            throw new BadRequestException("The description field is required.");
        }
    }
}
