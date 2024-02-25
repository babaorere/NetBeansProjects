package com.isiweek.company;

import com.isiweek.status.Status;
import com.isiweek.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.isiweek.status.StatusRepository;


@Service
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final StatusRepository statusEntityRepository;

    public CompanyService(final CompanyRepository companyRepository,
            final StatusRepository statusEntityRepository) {
        this.companyRepository = companyRepository;
        this.statusEntityRepository = statusEntityRepository;
    }

    public List<CompanyDTO> findAll() {
        final List<Company> companies = companyRepository.findAll(Sort.by("id"));
        return companies.stream()
                .map(company -> mapToDTO(company, new CompanyDTO()))
                .toList();
    }

    public CompanyDTO get(final Long id) {
        return companyRepository.findById(id)
                .map(company -> mapToDTO(company, new CompanyDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final CompanyDTO companyDTO) {
        final Company company = new Company();
        mapToEntity(companyDTO, company);
        return companyRepository.save(company).getId();
    }

    public void update(final Long id, final CompanyDTO companyDTO) {
        final Company company = companyRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(companyDTO, company);
        companyRepository.save(company);
    }

    public void delete(final Long id) {
        companyRepository.deleteById(id);
    }

    private CompanyDTO mapToDTO(final Company company, final CompanyDTO companyDTO) {
        companyDTO.setId(company.getId());
        companyDTO.setDateCreated(company.getDateCreated());
        companyDTO.setLastUpdated(company.getLastUpdated());
        companyDTO.setPhone1(company.getPhone1());
        companyDTO.setPhone2(company.getPhone2());
        companyDTO.setEmail(company.getEmail());
        companyDTO.setTaxidnumber(company.getTaxidnumber());
        companyDTO.setName(company.getName());
        companyDTO.setAddress(company.getAddress());
        companyDTO.setPrimaryContact(company.getPrimaryContact());
        companyDTO.setDescription(company.getDescription());
        companyDTO.setStatus(company.getStatus() == null ? null : company.getStatus().getId());
        return companyDTO;
    }

    private Company mapToEntity(final CompanyDTO companyDTO, final Company company) {
        company.setDateCreated(companyDTO.getDateCreated());
        company.setLastUpdated(companyDTO.getLastUpdated());
        company.setPhone1(companyDTO.getPhone1());
        company.setPhone2(companyDTO.getPhone2());
        company.setEmail(companyDTO.getEmail());
        company.setTaxidnumber(companyDTO.getTaxidnumber());
        company.setName(companyDTO.getName());
        company.setAddress(companyDTO.getAddress());
        company.setPrimaryContact(companyDTO.getPrimaryContact());
        company.setDescription(companyDTO.getDescription());
        final Status status = companyDTO.getStatus() == null ? null : statusEntityRepository.findById(companyDTO.getStatus())
                .orElseThrow(() -> new NotFoundException("status not found"));
        company.setStatus(status);
        return company;
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

}
