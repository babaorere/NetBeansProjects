package com.isiweek.company;

import com.isiweek.status.StatusRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final StatusRepository statusEntityRepository;

    @Autowired
    public CompanyService(final CompanyRepository inCompanyRepository,
            final StatusRepository inStatusEntityRepository) {
        this.companyRepository = inCompanyRepository;
        this.statusEntityRepository = inStatusEntityRepository;
    }

    public List<Company> findAll() {
        return companyRepository.findAll(Sort.by("id"));
    }

    public Optional<Company> findById(final Long id) {
        return companyRepository.findById(id);
    }

    public Optional<Company> get(final Long id) {
        return companyRepository.findById(id);
    }

    public Company create(final Company inCompany) {

        return companyRepository.save(inCompany);
    }

    public Company update(final Long inId, final Company inCompany) {

        Company company = inCompany;
        company.setId(inId);

        return companyRepository.save(company);
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
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
