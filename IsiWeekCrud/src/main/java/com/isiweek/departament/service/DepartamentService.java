package com.isiweek.departament.service;

import com.isiweek.departament.domain.Departament;
import com.isiweek.departament.model.DepartamentDTO;
import com.isiweek.departament.repos.DepartamentRepository;
import com.isiweek.employee.domain.Employee;
import com.isiweek.employee.repos.EmployeeRepository;
import com.isiweek.util.NotFoundException;
import com.isiweek.util.WebUtils;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class DepartamentService {

    private final DepartamentRepository departamentRepository;
    private final EmployeeRepository employeeRepository;

    public DepartamentService(final DepartamentRepository departamentRepository,
            final EmployeeRepository employeeRepository) {
        this.departamentRepository = departamentRepository;
        this.employeeRepository = employeeRepository;
    }

    public List<DepartamentDTO> findAll() {
        final List<Departament> departaments = departamentRepository.findAll(Sort.by("id"));
        return departaments.stream()
                .map(departament -> mapToDTO(departament, new DepartamentDTO()))
                .toList();
    }

    public DepartamentDTO get(final Long id) {
        return departamentRepository.findById(id)
                .map(departament -> mapToDTO(departament, new DepartamentDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final DepartamentDTO departamentDTO) {
        final Departament departament = new Departament();
        mapToEntity(departamentDTO, departament);
        return departamentRepository.save(departament).getId();
    }

    public void update(final Long id, final DepartamentDTO departamentDTO) {
        final Departament departament = departamentRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(departamentDTO, departament);
        departamentRepository.save(departament);
    }

    public void delete(final Long id) {
        departamentRepository.deleteById(id);
    }

    private DepartamentDTO mapToDTO(final Departament departament,
            final DepartamentDTO departamentDTO) {
        departamentDTO.setId(departament.getId());
        departamentDTO.setName(departament.getName());
        departamentDTO.setDescription(departament.getDescription());
        return departamentDTO;
    }

    private Departament mapToEntity(final DepartamentDTO departamentDTO,
            final Departament departament) {
        departament.setName(departamentDTO.getName());
        departament.setDescription(departamentDTO.getDescription());
        return departament;
    }

    public boolean nameExists(final String name) {
        return departamentRepository.existsByNameIgnoreCase(name);
    }

    public String getReferencedWarning(final Long id) {
        final Departament departament = departamentRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Employee departmentEmployee = employeeRepository.findFirstByDepartment(departament);
        if (departmentEmployee != null) {
            return WebUtils.getMessage("departament.employee.department.referenced", departmentEmployee.getId());
        }

        return null;
    }

}
