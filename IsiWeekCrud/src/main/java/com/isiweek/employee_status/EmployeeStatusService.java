package com.isiweek.employee_status;

import com.isiweek.employee.Employee;
import com.isiweek.employee.EmployeeRepository;
import com.isiweek.util.NotFoundException;
import com.isiweek.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class EmployeeStatusService {

    private final EmployeeStatusRepository employeeStatusRepository;
    private final EmployeeRepository employeeRepository;

    public EmployeeStatusService(final EmployeeStatusRepository employeeStatusRepository,
            final EmployeeRepository employeeRepository) {
        this.employeeStatusRepository = employeeStatusRepository;
        this.employeeRepository = employeeRepository;
    }

    public List<EmployeeStatusDTO> findAll() {
        final List<EmployeeStatus> employeeStatuses = employeeStatusRepository.findAll(Sort.by("id"));
        return employeeStatuses.stream()
                .map(employeeStatus -> mapToDTO(employeeStatus, new EmployeeStatusDTO()))
                .toList();
    }

    public EmployeeStatusDTO get(final Long id) {
        return employeeStatusRepository.findById(id)
                .map(employeeStatus -> mapToDTO(employeeStatus, new EmployeeStatusDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final EmployeeStatusDTO employeeStatusDTO) {
        final EmployeeStatus employeeStatus = new EmployeeStatus();
        mapToEntity(employeeStatusDTO, employeeStatus);
        return employeeStatusRepository.save(employeeStatus).getId();
    }

    public void update(final Long id, final EmployeeStatusDTO employeeStatusDTO) {
        final EmployeeStatus employeeStatus = employeeStatusRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(employeeStatusDTO, employeeStatus);
        employeeStatusRepository.save(employeeStatus);
    }

    public void delete(final Long id) {
        employeeStatusRepository.deleteById(id);
    }

    private EmployeeStatusDTO mapToDTO(final EmployeeStatus employeeStatus,
            final EmployeeStatusDTO employeeStatusDTO) {
        employeeStatusDTO.setId(employeeStatus.getId());
        employeeStatusDTO.setDateCreated(employeeStatus.getDateCreated());
        employeeStatusDTO.setLastUpdated(employeeStatus.getLastUpdated());
        employeeStatusDTO.setName(employeeStatus.getName());
        employeeStatusDTO.setDescription(employeeStatus.getDescription());
        return employeeStatusDTO;
    }

    private EmployeeStatus mapToEntity(final EmployeeStatusDTO employeeStatusDTO,
            final EmployeeStatus employeeStatus) {
        employeeStatus.setDateCreated(employeeStatusDTO.getDateCreated());
        employeeStatus.setLastUpdated(employeeStatusDTO.getLastUpdated());
        employeeStatus.setName(employeeStatusDTO.getName());
        employeeStatus.setDescription(employeeStatusDTO.getDescription());
        return employeeStatus;
    }

    public boolean nameExists(final String name) {
        return employeeStatusRepository.existsByNameIgnoreCase(name);
    }

    public ReferencedWarning getReferencedWarning(final Long id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final EmployeeStatus employeeStatus = employeeStatusRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Employee employeeStatusEmployee = employeeRepository.findFirstByEmployeeStatus(employeeStatus);
        if (employeeStatusEmployee != null) {
            referencedWarning.setKey("employeeStatus.employee.employeeStatus.referenced");
            referencedWarning.addParam(employeeStatusEmployee.getId());
            return referencedWarning;
        }
        return null;
    }

}
