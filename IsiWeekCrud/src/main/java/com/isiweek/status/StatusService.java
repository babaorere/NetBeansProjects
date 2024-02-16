package com.isiweek.status;

import com.isiweek.company.Company;
import com.isiweek.company.CompanyRepository;
import com.isiweek.util.NotFoundException;
import com.isiweek.util.ReferencedWarning;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class StatusService {

    private final StatusRepository statusRepository;
    private final CompanyRepository companyRepository;

    public StatusService(final StatusRepository statusRepository,
            final CompanyRepository companyRepository) {
        this.statusRepository = statusRepository;
        this.companyRepository = companyRepository;
    }

    public List<Status> findAll() {
        return statusRepository.findAll(Sort.by("id"));
    }

    public Optional<Status> findById(final Long id) {
        return statusRepository.findById(id);
    }

    public Optional<Status> get(final Long id) {
        return statusRepository.findById(id);
    }

    public Status create(final Status inStatus) {

        Status status;

        Optional<Status> statusOp = statusRepository.findByStatusEnum(inStatus.getStatusEnum());

        if (statusOp.isPresent()) {
            status = statusOp.get();
        } else {
            status = statusRepository.save(inStatus);
        }

        return status;
    }

    public Optional<Status> read(final Long id) {
        return get(id);
    }

    public Status update(final Status inStatus) {

        Status status;

        return statusRepository.save(inStatus);
    }

    public Status update(final Long inId, final Status inStatus) {

        Status status = inStatus;
        status.setId(inId);

        return statusRepository.save(status);
    }

    public void delete(final Long id) {
        statusRepository.deleteById(id);
    }

    public void deleteAll() {
        statusRepository.deleteAll();
    }

    public boolean nameExists(final StatusEnum inStatusEnum) {
        return statusRepository.existsByStatusEnum(inStatusEnum);
    }

    public boolean existsById(Long inId) {
        return statusRepository.existsById(inId);
    }

    public boolean existsByStatus(StatusEnum inStatusEnum) {
        return statusRepository.existsByStatusEnum(inStatusEnum);
    }

    public Optional<Status> findFirstStatus() {
        return statusRepository.findFirst();
    }

    public Optional<Status> findByStatusEnum(StatusEnum inStatusEnum) {
        return statusRepository.findByStatusEnum(inStatusEnum);
    }

    public void persistStatusEnumValues() {

        deleteAll();

        for (StatusEnum statusEnum : StatusEnum.values()) {
            if (!existsByStatus(statusEnum)) {
                System.out.println("Persist Status -> " + statusEnum);
                create(Status.builder().statusEnum(statusEnum).build());
            }
        }
    }

    public ReferencedWarning getReferencedWarning(final Long id) {

        final ReferencedWarning referencedWarning = new ReferencedWarning();

        final Status status = statusRepository.findById(id)
                .orElseThrow(NotFoundException::new);

        final Optional<Company> statusCompany = companyRepository.findFirstByStatusOrderByIdAsc(status);

        if (statusCompany.isPresent()) {
            referencedWarning.setKey("status.company.status.referenced");
            referencedWarning.addParam(statusCompany.get().getId());
            return referencedWarning;
        }

        return null;
    }

}
