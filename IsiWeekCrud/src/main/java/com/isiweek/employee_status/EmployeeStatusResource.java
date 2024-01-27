package com.isiweek.employee_status;

import com.isiweek.employee_status.EmployeeStatusDTO;
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
@RequestMapping(value = "/api/employeeStatuses", produces = MediaType.APPLICATION_JSON_VALUE)
public class EmployeeStatusResource {

    private final EmployeeStatusService employeeStatusService;

    public EmployeeStatusResource(final EmployeeStatusService employeeStatusService) {
        this.employeeStatusService = employeeStatusService;
    }

    @GetMapping
    public ResponseEntity<List<EmployeeStatusDTO>> getAllEmployeeStatuses() {
        return ResponseEntity.ok(employeeStatusService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeStatusDTO> getEmployeeStatus(
            @PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(employeeStatusService.get(id));
    }

    @PostMapping
    public ResponseEntity<Long> createEmployeeStatus(
            @RequestBody @Valid final EmployeeStatusDTO employeeStatusDTO) {
        final Long createdId = employeeStatusService.create(employeeStatusDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateEmployeeStatus(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final EmployeeStatusDTO employeeStatusDTO) {
        employeeStatusService.update(id, employeeStatusDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployeeStatus(@PathVariable(name = "id") final Long id) {
        employeeStatusService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
