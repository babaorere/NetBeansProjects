package com.isiweek.company;

import com.isiweek.status.Status;
import com.isiweek.status.StatusEnum;
import com.isiweek.status.StatusRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
public class CompanyService {

    private StatusRepository statusRepository;
    private final CompanyRepository companyRepository;

    @Autowired(required = true)
    public CompanyService(final CompanyRepository inCompanyRepository) {
        this.companyRepository = inCompanyRepository;
    }

    /**
     * Se utiliza para evitar la referencia circular con CompanyService
     *
     * @param companyRepository
     */
    @Autowired(required = true)
    public void SetAutowired(final StatusRepository inStatusRepository) {
        this.statusRepository = inStatusRepository;
    }

    @Transactional
    public List<Company> findAll() {
        return companyRepository.findAll(Sort.by("id"));
    }

    @Transactional
    public Optional<Company> findById(final Long id) {
        return companyRepository.findById(id);
    }

    @Transactional
    public Optional<Company> get(final Long id) {
        return companyRepository.findById(id);
    }

    public Company create(final Company inCompany) {
        final Status statusPending = statusRepository.findByStatusEnum(StatusEnum.PENDING).get();

        inCompany.setId(null);
        inCompany.setStatus(statusPending);

        System.out.println("Creando Compañia");
        return companyRepository.save(inCompany);
    }

    /**
     * Este metodo actualiza los atributos de Company, a excepcion del Status
     *
     * @param inId
     * @param inCompany
     * @return
     */
    @Transactional
    public Company update(final Long inId, final Company inCompany) {

        // Ejemplo de registro de mensajes
        log.info("inCompany " + inCompany);
        Optional<Company> optionalCompany = companyRepository.findById(inId);

        log.info("optionalCompany.get() " + optionalCompany.get());

        if (optionalCompany.isPresent()) {
            Company existingCompany = optionalCompany.get();
            log.info("existingCompany.getStatus() " + existingCompany.getStatus());

            // Actualizar solo los campos necesarios de la entidad existente
            existingCompany.setName(inCompany.getName());
            existingCompany.setDescription(inCompany.getDescription());
            existingCompany.setTaxidnumber(inCompany.getTaxidnumber());
            existingCompany.setAddress(inCompany.getAddress());
            existingCompany.setEmail(inCompany.getEmail());
            existingCompany.setPhone1(inCompany.getPhone1());
            existingCompany.setPhone2(inCompany.getPhone2());
            existingCompany.setPrimaryContact(inCompany.getPrimaryContact());

            // Ejemplo de registro de mensajes
            log.info("existingCompany before save " + existingCompany);

            // Guardar la entidad actualizada
            return companyRepository.save(existingCompany);
        } else {
            log.info("throw new EntityNotFoundException");
            // Manejo adecuado si la empresa no existe con el ID proporcionado
            throw new EntityNotFoundException("Company with ID " + inId + " not found");
        }

    }

    public void delete(final Long id) {
        companyRepository.deleteById(id);
    }

    public void deleteAll() {
        companyRepository.deleteAll();
    }

    public boolean emailExists(final String email) {
        return companyRepository.existsByEmailIgnoreCase(email);
    }

    public boolean taxidnumberExists(final String taxidnumber) {
        return companyRepository.existsByTaxidnumberIgnoreCase(taxidnumber);
    }

    public boolean nameExists(final String name) {
        return companyRepository.existsByNameIgnoreCase(name);
    }

    String getReferencedWarning(Long id) {

        return null;
    }

}
