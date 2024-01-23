package com.isiweek.employee.service;

import com.isiweek.departament.domain.Departament;
import com.isiweek.departament.repos.DepartamentRepository;
import com.isiweek.employee.domain.Employee;
import com.isiweek.employee.model.EmployeeDTO;
import com.isiweek.employee.repos.EmployeeRepository;
import com.isiweek.employee_status.domain.EmployeeStatus;
import com.isiweek.employee_status.repos.EmployeeStatusRepository;
import com.isiweek.job_title.domain.JobTitle;
import com.isiweek.job_title.repos.JobTitleRepository;
import com.isiweek.person.domain.Person;
import com.isiweek.person.repos.PersonRepository;
import com.isiweek.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final PersonRepository personRepository;
    private final EmployeeStatusRepository employeeStatusRepository;
    private final JobTitleRepository jobTitleRepository;
    private final DepartamentRepository departamentRepository;

    public EmployeeService(final EmployeeRepository employeeRepository,
            final PersonRepository personRepository,
            final EmployeeStatusRepository employeeStatusRepository,
            final JobTitleRepository jobTitleRepository,
            final DepartamentRepository departamentRepository) {
        this.employeeRepository = employeeRepository;
        this.personRepository = personRepository;
        this.employeeStatusRepository = employeeStatusRepository;
        this.jobTitleRepository = jobTitleRepository;
        this.departamentRepository = departamentRepository;
    }

    public List<EmployeeDTO> findAll() {
        final List<Employee> employees = employeeRepository.findAll(Sort.by("id"));
        return employees.stream()
                .map(employee -> mapToDTO(employee, new EmployeeDTO()))
                .toList();
    }

    public EmployeeDTO get(final Long id) {
        return employeeRepository.findById(id)
                .map(employee -> mapToDTO(employee, new EmployeeDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final EmployeeDTO employeeDTO) {
        final Employee employee = new Employee();
        mapToEntity(employeeDTO, employee);
        return employeeRepository.save(employee).getId();
    }

    public void update(final Long id, final EmployeeDTO employeeDTO) {
        final Employee employee = employeeRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(employeeDTO, employee);
        employeeRepository.save(employee);
    }

    public void delete(final Long id) {
        employeeRepository.deleteById(id);
    }

    private EmployeeDTO mapToDTO(final Employee employee, final EmployeeDTO employeeDTO) {
        employeeDTO.setId(employee.getId());
        employeeDTO.setDateOfHire(employee.getDateOfHire());
        employeeDTO.setSalary(employee.getSalary());
        employeeDTO.setBenefits(employee.getBenefits());
        employeeDTO.setContactInformation(employee.getContactInformation());
        employeeDTO.setEducation(employee.getEducation());
        employeeDTO.setSkills(employee.getSkills());
        employeeDTO.setPerformanceReviews(employee.getPerformanceReviews());
        employeeDTO.setPerson(employee.getPerson() == null ? null : employee.getPerson().getId());
        employeeDTO.setEmployeeStatus(employee.getEmployeeStatus() == null ? null : employee.getEmployeeStatus().getId());
        employeeDTO.setJobTitle(employee.getJobTitle() == null ? null : employee.getJobTitle().getId());
        employeeDTO.setDepartment(employee.getDepartment() == null ? null : employee.getDepartment().getId());
        employeeDTO.setManager(employee.getManager() == null ? null : employee.getManager().getId());
        employeeDTO.setManager(employee.getManager() == null ? null : employee.getManager().getId());
        employeeDTO.setEmployeeStatus(employee.getEmployeeStatus() == null ? null : employee.getEmployeeStatus().getId());
        employeeDTO.setDepartment(employee.getDepartment() == null ? null : employee.getDepartment().getId());
        employeeDTO.setJobTitle(employee.getJobTitle() == null ? null : employee.getJobTitle().getId());
        employeeDTO.setPerson(employee.getPerson() == null ? null : employee.getPerson().getId());
        return employeeDTO;
    }

    private Employee mapToEntity(final EmployeeDTO employeeDTO, final Employee employee) {
        employee.setDateOfHire(employeeDTO.getDateOfHire());
        employee.setSalary(employeeDTO.getSalary());
        employee.setBenefits(employeeDTO.getBenefits());
        employee.setContactInformation(employeeDTO.getContactInformation());
        employee.setEducation(employeeDTO.getEducation());
        employee.setSkills(employeeDTO.getSkills());
        employee.setPerformanceReviews(employeeDTO.getPerformanceReviews());
        final Person person = employeeDTO.getPerson() == null ? null : personRepository.findById(employeeDTO.getPerson())
                .orElseThrow(() -> new NotFoundException("person not found"));
        employee.setPerson(person);
        final EmployeeStatus employeeStatus = employeeDTO.getEmployeeStatus() == null ? null : employeeStatusRepository.findById(employeeDTO.getEmployeeStatus())
                .orElseThrow(() -> new NotFoundException("employeeStatus not found"));
        employee.setEmployeeStatus(employeeStatus);
        final JobTitle jobTitle = employeeDTO.getJobTitle() == null ? null : jobTitleRepository.findById(employeeDTO.getJobTitle())
                .orElseThrow(() -> new NotFoundException("jobTitle not found"));
        employee.setJobTitle(jobTitle);
        final Departament department = employeeDTO.getDepartment() == null ? null : departamentRepository.findById(employeeDTO.getDepartment())
                .orElseThrow(() -> new NotFoundException("department not found"));
        employee.setDepartment(department);
        final Person manager = employeeDTO.getManager() == null ? null : personRepository.findById(employeeDTO.getManager())
                .orElseThrow(() -> new NotFoundException("manager not found"));
        employee.setManager(manager);

        return employee;
    }

}
