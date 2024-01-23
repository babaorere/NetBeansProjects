package com.isiweek.employee_status.service;

import com.isiweek.employee.domain.Employee;
import com.isiweek.employee.repos.EmployeeRepository;
import com.isiweek.employee_status.domain.EmployeeStatus;
import com.isiweek.employee_status.model.EmployeeStatusDTO;
import com.isiweek.employee_status.repos.EmployeeStatusRepository;
import com.isiweek.util.NotFoundException;
import com.isiweek.util.WebUtils;
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
        employeeStatusDTO.setName(employeeStatus.getName());
        employeeStatusDTO.setDescription(employeeStatus.getDescription());
        return employeeStatusDTO;
    }

    private EmployeeStatus mapToEntity(final EmployeeStatusDTO employeeStatusDTO,
            final EmployeeStatus employeeStatus) {
        employeeStatus.setName(employeeStatusDTO.getName());
        employeeStatus.setDescription(employeeStatusDTO.getDescription());
        return employeeStatus;
    }

    public boolean nameExists(final String name) {
        return employeeStatusRepository.existsByNameIgnoreCase(name);
    }

    public String getReferencedWarning(final Long id) {
        final EmployeeStatus employeeStatus = employeeStatusRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Employee employeeStatusEmployee = employeeRepository.findFirstByEmployeeStatus(employeeStatus);
        if (employeeStatusEmployee != null) {
            return WebUtils.getMessage("employeeStatus.employee.employeeStatus.referenced", employeeStatusEmployee.getId());
        }
        return null;
    }

}
