package com.isiweek.company;

import com.isiweek.status.Status;
import com.isiweek.util.NotFoundException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyStatusService {

    private final CompanyStatusRepository companyStatusRepository;

    @Autowired
    public CompanyStatusService(CompanyStatusRepository companyStatusRepository) {
        this.companyStatusRepository = companyStatusRepository;
    }

    public List<Status> getAll() {
        return companyStatusRepository.findAll();
    }

    public Status getById(Long id) {
        return companyStatusRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    public Status create(Status companyStatus) {
        // Puedes agregar validaciones adicionales antes de guardar
        return companyStatusRepository.save(companyStatus);
    }

    public Status update(Long id, Status updatedCompanyStatus) {
        getById(id); // Verificar si existe el registro

        // Actualizar solo los campos necesarios
        return companyStatusRepository.save(updatedCompanyStatus);
    }

    public void delete(Long id) {
        Status companyStatusToDelete = getById(id);
        companyStatusRepository.delete(companyStatusToDelete);
    }

    public void deleteAll() {
        companyStatusRepository.deleteAll();
    }

    public Optional<Status> findById(Long inId) {
        return companyStatusRepository.findById(inId);
    }
}
