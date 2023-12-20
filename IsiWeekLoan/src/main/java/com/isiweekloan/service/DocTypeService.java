package com.isiweekloan.service;

import com.isiweekloan.entity.DocTypeEntity;
import com.isiweekloan.exception.BadRequestException;
import com.isiweekloan.exception.ResourceNotFoundException;
import com.isiweekloan.repository.DocTypeRepository;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DocTypeService {

    @Autowired
    private DocTypeRepository docTypeRepository;

    @Transactional
    public List<DocTypeEntity> findAllDocTypes() {
        return docTypeRepository.findAll();
    }

    @Transactional
    public Optional<DocTypeEntity> findDocTypeById(Long id) {
        return docTypeRepository.findById(id);
    }

    @Transactional
    public DocTypeEntity createDocType(DocTypeEntity docType) throws BadRequestException {
        validateRequiredFields(docType);
        try {
            return docTypeRepository.save(docType);
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException("DocType with name '" + docType.getName() + "' already exists.");
        }
    }

    @Transactional
    public DocTypeEntity updateDocType(Long id, DocTypeEntity docType) throws ResourceNotFoundException, BadRequestException {
        if (!Objects.equals(docType.getId(), id)) {
            throw new BadRequestException("ID in request body does not match ID in path variable.");
        }
        validateRequiredFields(docType);
        Optional<DocTypeEntity> existingDocType = findDocTypeById(id);
        if (!existingDocType.isPresent()) {
            throw new ResourceNotFoundException("DocType with ID " + id + " not found.");
        }
        existingDocType.get().setName(docType.getName());
        existingDocType.get().setDescription(docType.getDescription());
        return docTypeRepository.save(existingDocType.get());
    }

    @Transactional
    public void deleteDocType(Long id) throws ResourceNotFoundException {
        DocTypeEntity docType = findDocTypeById(id).orElseThrow(() -> new ResourceNotFoundException("DocType with ID " + id + " not found."));
        docTypeRepository.delete(docType);
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
