package com.isiweek.company;

import com.isiweek.status.Status;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/company-statuses")
public class CompanyStatusController {

    private final CompanyStatusService companyStatusService;

    @Autowired
    public CompanyStatusController(CompanyStatusService companyStatusService) {
        this.companyStatusService = companyStatusService;
    }

    @GetMapping
    public List<Status> getAllCompanyStatuses() {
        return companyStatusService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Status> getCompanyStatusById(@PathVariable Long id) {
        Status companyStatus = companyStatusService.getById(id);
        return new ResponseEntity<>(companyStatus, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Status> createCompanyStatus(@RequestBody Status companyStatus) {
        Status createdCompanyStatus = companyStatusService.create(companyStatus);
        return new ResponseEntity<>(createdCompanyStatus, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Status> updateCompanyStatus(@PathVariable Long id, @RequestBody Status updatedCompanyStatus) {
        Status updated = companyStatusService.update(id, updatedCompanyStatus);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompanyStatus(@PathVariable Long id) {
        companyStatusService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
