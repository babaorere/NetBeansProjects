package com.isiweekloan.controller;

import com.isiweekloan.entity.MaritalStatusEntity;
import com.isiweekloan.service.MaritalStatusService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/marital-statuses")
public class MaritalStatusController {

    private final MaritalStatusService maritalStatusService;

    @Autowired
    public MaritalStatusController(MaritalStatusService maritalStatusService) {
        this.maritalStatusService = maritalStatusService;
    }

    @GetMapping
    public List<MaritalStatusEntity> getAllMaritalStatuses() {
        return maritalStatusService.getAllMaritalStatuses();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MaritalStatusEntity> getMaritalStatusById(@PathVariable Long id) {
        return maritalStatusService.getMaritalStatusById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<MaritalStatusEntity> createMaritalStatus(@Validated @RequestBody MaritalStatusEntity maritalStatusEntity) {
        return ResponseEntity.ok(maritalStatusService.createMaritalStatus(maritalStatusEntity));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MaritalStatusEntity> updateMaritalStatus(@PathVariable Long id, @Validated @RequestBody MaritalStatusEntity maritalStatusEntity) {
        return maritalStatusService.updateMaritalStatus(id, maritalStatusEntity)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMaritalStatus(@PathVariable Long id) {
        return maritalStatusService.deleteMaritalStatus(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
