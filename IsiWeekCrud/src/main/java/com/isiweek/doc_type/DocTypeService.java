package com.isiweek.doc_type;

import com.isiweek.person.Person;
import com.isiweek.person.PersonRepository;
import com.isiweek.util.NotFoundException;
import com.isiweek.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class DocTypeService {

    private final DocTypeRepository docTypeRepository;
    private final PersonRepository personRepository;

    public DocTypeService(final DocTypeRepository docTypeRepository,
            final PersonRepository personRepository) {
        this.docTypeRepository = docTypeRepository;
        this.personRepository = personRepository;
    }

    public List<DocTypeDTO> findAll() {
        final List<DocType> docTypes = docTypeRepository.findAll(Sort.by("id"));
        return docTypes.stream()
                .map(docType -> mapToDTO(docType, new DocTypeDTO()))
                .toList();
    }

    public DocTypeDTO get(final Long id) {
        return docTypeRepository.findById(id)
                .map(docType -> mapToDTO(docType, new DocTypeDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final DocTypeDTO docTypeDTO) {
        final DocType docType = new DocType();
        mapToEntity(docTypeDTO, docType);
        return docTypeRepository.save(docType).getId();
    }

    public void update(final Long id, final DocTypeDTO docTypeDTO) {
        final DocType docType = docTypeRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(docTypeDTO, docType);
        docTypeRepository.save(docType);
    }

    public void delete(final Long id) {
        docTypeRepository.deleteById(id);
    }

    private DocTypeDTO mapToDTO(final DocType docType, final DocTypeDTO docTypeDTO) {
        docTypeDTO.setId(docType.getId());
        docTypeDTO.setDateCreated(docType.getDateCreated());
        docTypeDTO.setLastUpdated(docType.getLastUpdated());
        docTypeDTO.setName(docType.getName());
        docTypeDTO.setDescription(docType.getDescription());
        return docTypeDTO;
    }

    private DocType mapToEntity(final DocTypeDTO docTypeDTO, final DocType docType) {
        docType.setDateCreated(docTypeDTO.getDateCreated());
        docType.setLastUpdated(docTypeDTO.getLastUpdated());
        docType.setName(docTypeDTO.getName());
        docType.setDescription(docTypeDTO.getDescription());
        return docType;
    }

    public boolean nameExists(final String name) {
        return docTypeRepository.existsByNameIgnoreCase(name);
    }

    public ReferencedWarning getReferencedWarning(final Long id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final DocType docType = docTypeRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Person docTypePerson = personRepository.findFirstByDocType(docType);
        if (docTypePerson != null) {
            referencedWarning.setKey("docType.person.docType.referenced");
            referencedWarning.addParam(docTypePerson.getId());
            return referencedWarning;
        }
        return null;
    }

}
