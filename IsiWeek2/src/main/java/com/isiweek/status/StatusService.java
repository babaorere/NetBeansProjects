package com.isiweek.status;

import com.isiweek.company.Company;
import com.isiweek.company.CompanyRepository;
import com.isiweek.user.User;
import com.isiweek.user.UserRepository;
import com.isiweek.exceptions.NotFoundException;
import com.isiweek.util.ReferencedWarning;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class StatusService {

    private final StatusRepository statusRepository;
    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;

    public StatusService(final StatusRepository statusRepository,
            final CompanyRepository companyRepository, final UserRepository userRepository) {
        this.statusRepository = statusRepository;
        this.companyRepository = companyRepository;
        this.userRepository = userRepository;
    }

    public List<StatusDTO> findAll() {
        final List<Status> statuses = statusRepository.findAll(Sort.by("id"));
        return statuses.stream()
                .map(status -> mapToDTO(status, new StatusDTO()))
                .toList();
    }

    public StatusDTO get(final Long id) {
        return statusRepository.findById(id)
                .map(status -> mapToDTO(status, new StatusDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final StatusDTO statusDTO) {

        final Status status = new Status();

        mapToEntity(statusDTO, status);
        return statusRepository.save(status).getId();
    }

    public void update(final Long id, final StatusDTO statusDTO) {
        final Status status = statusRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(statusDTO, status);
        statusRepository.save(status);
    }

    public void delete(final Long id) {
        statusRepository.deleteById(id);
    }

    private StatusDTO mapToDTO(final Status status, final StatusDTO statusDTO) {
        statusDTO.setId(status.getId());
        statusDTO.setName(status.getName());
        return statusDTO;
    }

    private Status mapToEntity(final StatusDTO statusDTO, final Status status) {
        status.setName(statusDTO.getName());
        return status;
    }

    public Boolean nameExists(final StatusEnum inItem) {
        return statusRepository.existsByName(inItem.name());
    }

    public ReferencedWarning getReferencedWarning(final Long id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Status status = statusRepository.findById(id)
                .orElseThrow(NotFoundException::new);

        final Optional<Company> statusCompany = companyRepository.findFirstByStatus(status);

        if (statusCompany.isPresent()) {
            referencedWarning.setKey("status.company.status.referenced");
            referencedWarning.addParam(statusCompany.get().getId());
            return referencedWarning;
        }

        final Optional<User> statusUser = userRepository.findFirstByStatus(status);

        if (statusUser.isPresent()) {
            referencedWarning.setKey("status.user.status.referenced");
            referencedWarning.addParam(statusUser.get().getId());
            return referencedWarning;
        }

        return null;
    }

    public void persistAll() {
        List<StatusEnum> allStatus = Arrays.asList(StatusEnum.values());

        for (StatusEnum item : allStatus) {
            persistStatus(item.name());
        }
    }

    private void persistStatus(String name) {

        Optional<Status> existingStatusOptional = statusRepository.findByName(name);

        if (existingStatusOptional.isEmpty()) {
            Status newStatus = new Status();
            newStatus.setName(name);
            statusRepository.save(newStatus);
        }
    }

}
