package com.isiweekloan.service;

import com.isiweekloan.entity.DepartamentEntity;
import com.isiweekloan.exception.BadRequestException;
import com.isiweekloan.exception.ResourceNotFoundException;
import com.isiweekloan.repository.DepartamentRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DepartamentService {

    @Autowired
    private DepartamentRepository departamentRepository;

    @Transactional(readOnly = true)
    public List<DepartamentEntity> findAllDepartaments() {
        return departamentRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<DepartamentEntity> findDepartamentById(Long id) {
        return departamentRepository.findById(id);
    }

    @Transactional
    public DepartamentEntity createDepartament(DepartamentEntity departament) throws BadRequestException {
        validateRequiredFields(departament);
        try {
            return departamentRepository.save(departament);
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException("Departament with name '" + departament.getName() + "' already exists.");
        }
    }

    @Transactional
    public DepartamentEntity updateDepartament(Long id, DepartamentEntity departament) throws BadRequestException, ResourceNotFoundException {
        if (departament.getId() != id) {
            throw new BadRequestException("ID in request body does not match ID in path variable.");
        }
        validateRequiredFields(departament);
        Optional<DepartamentEntity> existingDepartament = departamentRepository.findById(id);
        if (!existingDepartament.isPresent()) {
            throw new ResourceNotFoundException("Departament with ID " + id + " not found.");
        }
        existingDepartament.get().setName(departament.getName());
        existingDepartament.get().setDescription(departament.getDescription());
        return departamentRepository.save(existingDepartament.get());
    }

    @Transactional
    public void deleteDepartament(Long id) throws ResourceNotFoundException {
        Optional<DepartamentEntity> departament = findDepartamentById(id);
        if (!departament.isPresent()) {
            throw new ResourceNotFoundException("Departament with ID " + id + " not found.");
        }

        departamentRepository.delete(departament.get());
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
