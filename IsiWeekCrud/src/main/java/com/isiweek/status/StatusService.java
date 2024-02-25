package com.isiweek.status;

import com.isiweek.company.CompanyRepository;
import com.isiweek.util.ReferencedWarning;
import jakarta.annotation.PostConstruct;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StatusService {

    private final StatusRepository statusRepository;
    private CompanyRepository companyRepository;

    @Autowired(required = true)
    public StatusService(StatusRepository inStatusRepository) {
        this.statusRepository = inStatusRepository;
    }

    /**
     * Se utiliza para evitar la referencia circular con CompanyService
     *
     * @param inCompanyRepository
     */
    @Autowired(required = true)
    public void SetAutowired(final CompanyRepository inCompanyRepository) {
        this.companyRepository = inCompanyRepository;
    }

    @Transactional
    @PostConstruct
    public void init() {
        persistStatusEnumValues();
    }

    @Transactional
    public List<Status> findAll() {
        return statusRepository.findAll(Sort.by("id"));
    }

    @Transactional
    public Optional<Status> findById(final Long id) {
        return statusRepository.findById(id);
    }

    @Transactional
    public Optional<Status> get(final Long id) {
        return statusRepository.findById(id);
    }

    @Transactional
    public Status create(final Status inStatus) {

        Status status;

        Optional<Status> statusOp = statusRepository.findByStatusEnum(inStatus.getStatusEnum());

        status = statusOp.orElseGet(() -> statusRepository.save(inStatus));

        return status;
    }

    @Transactional
    public Optional<Status> read(final Long id) {
        return get(id);
    }

    @Transactional
    public Status update(final Status inStatus) {

        Status status;

        return statusRepository.save(inStatus);
    }

    @Transactional
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

    @Transactional
    public void persistStatusEnumValues() {

//        deleteNotDesiredStatusAndCompanies();
        for (StatusEnum statusEnum : StatusEnum.values()) {
            if (!existsByStatus(statusEnum)) {
                create(Status.builder().statusEnum(statusEnum).build());
            }
        }
    }

    public void deleteNotDesiredStatusAndCompanies() {

        // Obtén el conjunto de StatusEnum que deseas conservar
        Set<StatusEnum> desiredStatusEnums = EnumSet.of(StatusEnum.PENDING, StatusEnum.APPROVED, StatusEnum.BLOCKEDUP);

        // Eliminar compañías asociadas a los Status no deseados
        companyRepository.deleteCompaniesByStatusNotIn(desiredStatusEnums);

        // Eliminar los Status no deseados
        statusRepository.deleteNotStatusEnum(desiredStatusEnums);
    }

    public ReferencedWarning getReferencedWarning(final Long id) {

//        final ReferencedWarning referencedWarning = new ReferencedWarning();
//
//        final Status status = statusRepository.findById(id)
//                .orElseThrow(NotFoundException::new);
//
//        final Optional<Company> statusCompany = companyRepository.findFirstByStatusOrderByIdAsc(status);
//
//        if (statusCompany.isPresent()) {
//            referencedWarning.setKey("status.company.status.referenced");
//            referencedWarning.addParam(statusCompany.get().getId());
//            return referencedWarning;
//        }
        return null;
    }

}
