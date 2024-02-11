package com.isiweek.company;

import com.isiweek.person.Person;
import com.isiweek.person.PersonRepository;
import com.isiweek.util.NotFoundException;
import com.isiweek.util.WebUtils;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final PersonRepository personRepository;

    @Autowired
    public CompanyService(CompanyRepository companyRepository,
            PersonRepository personRepository) {
        this.companyRepository = companyRepository;
        this.personRepository = personRepository;
    }

    public List<Company> findAll() {
        return companyRepository.findAll(Sort.by("id"));
    }

    public Company get(final Long id) {
        return companyRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    public Company create(final Company inCompany) {
        return companyRepository.save(inCompany);
    }

    public Company update(final Long inId, final Company inCompany) {

        Optional<Company> existingCompanyOptional = companyRepository.findById(inId);

        if (existingCompanyOptional.isPresent()) {
            Company existingCompany = existingCompanyOptional.get();

            // Copia todos los campos de la entidad proporcionada a la entidad existente
            BeanUtils.copyProperties(inCompany, existingCompany);

            // Guarda el registro actualizado
            return companyRepository.save(existingCompany);
        } else {
            throw new NotFoundException("Company with ID " + inId + " not found");
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

    public boolean nameExists(final String name) {
        return companyRepository.existsByNameIgnoreCase(name);
    }

    public boolean taxidnumberExists(final String taxidnumber) {
        return companyRepository.existsByTaxidnumberIgnoreCase(taxidnumber);
    }

    public String getReferencedWarning(final Long id) {
        final Company company = companyRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Person companyPerson = personRepository.findFirstByCompanies(company);
        if (companyPerson != null) {
            return WebUtils.getMessage("company.person.company.referenced", companyPerson.getId());
        }
        return null;
    }

    public void addPersonToCompany(Long personId, Long companyId) {
        Person person = personRepository.findById(personId).orElse(null);
        Company company = companyRepository.findById(companyId).orElse(null);

        if (person != null && company != null) {
            company.addPerson(person);
            companyRepository.save(company); // Guarda la empresa con la nueva relación
        }
    }

    public void addCompanyToPerson(Long companyId, Long personId) {
        Company company = companyRepository.findById(companyId).orElse(null);
        Person person = personRepository.findById(personId).orElse(null);

        if (company != null && person != null) {
            person.addCompany(company);
            personRepository.save(person); // Guarda la persona con la nueva relación
        }
    }

    public Optional<Company> findById(Long inId) {
        return companyRepository.findById(inId);
    }
}
