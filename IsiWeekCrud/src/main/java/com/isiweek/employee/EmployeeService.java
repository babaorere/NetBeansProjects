package com.isiweek.employee;

import com.isiweek.departament.Departament;
import com.isiweek.departament.DepartamentRepository;
import com.isiweek.employee_status.EmployeeStatus;
import com.isiweek.employee_status.EmployeeStatusRepository;
import com.isiweek.job_title.JobTitle;
import com.isiweek.job_title.JobTitleRepository;
import com.isiweek.person.Person;
import com.isiweek.person.PersonRepository;
import com.isiweek.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeStatusRepository employeeStatusRepository;
    private final DepartamentRepository departamentRepository;
    private final PersonRepository personRepository;
    private final JobTitleRepository jobTitleRepository;

    public EmployeeService(final EmployeeRepository employeeRepository,
            final EmployeeStatusRepository employeeStatusRepository,
            final DepartamentRepository departamentRepository,
            final PersonRepository personRepository, final JobTitleRepository jobTitleRepository) {
        this.employeeRepository = employeeRepository;
        this.employeeStatusRepository = employeeStatusRepository;
        this.departamentRepository = departamentRepository;
        this.personRepository = personRepository;
        this.jobTitleRepository = jobTitleRepository;
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
        employeeDTO.setDateCreated(employee.getDateCreated());
        employeeDTO.setLastUpdated(employee.getLastUpdated());
        employeeDTO.setBenefits(employee.getBenefits());
        employeeDTO.setContactInformation(employee.getContactInformation());
        employeeDTO.setEducation(employee.getEducation());
        employeeDTO.setSkills(employee.getSkills());
        employeeDTO.setPerformanceReviews(employee.getPerformanceReviews());
        employeeDTO.setEmployeeStatus(employee.getEmployeeStatus() == null ? null : employee.getEmployeeStatus().getId());
        employeeDTO.setDepartment(employee.getDepartment() == null ? null : employee.getDepartment().getId());
        employeeDTO.setManager(employee.getManager() == null ? null : employee.getManager().getId());
        employeeDTO.setPerson(employee.getPerson() == null ? null : employee.getPerson().getId());
        employeeDTO.setJobTitle(employee.getJobTitle() == null ? null : employee.getJobTitle().getId());
        return employeeDTO;
    }

    private Employee mapToEntity(final EmployeeDTO employeeDTO, final Employee employee) {
        employee.setDateOfHire(employeeDTO.getDateOfHire());
        employee.setSalary(employeeDTO.getSalary());
        employee.setDateCreated(employeeDTO.getDateCreated());
        employee.setLastUpdated(employeeDTO.getLastUpdated());
        employee.setBenefits(employeeDTO.getBenefits());
        employee.setContactInformation(employeeDTO.getContactInformation());
        employee.setEducation(employeeDTO.getEducation());
        employee.setSkills(employeeDTO.getSkills());
        employee.setPerformanceReviews(employeeDTO.getPerformanceReviews());
        final EmployeeStatus employeeStatus = employeeDTO.getEmployeeStatus() == null ? null : employeeStatusRepository.findById(employeeDTO.getEmployeeStatus())
                .orElseThrow(() -> new NotFoundException("employeeStatus not found"));
        employee.setEmployeeStatus(employeeStatus);
        final Departament department = employeeDTO.getDepartment() == null ? null : departamentRepository.findById(employeeDTO.getDepartment())
                .orElseThrow(() -> new NotFoundException("department not found"));
        employee.setDepartment(department);
        final Person manager = employeeDTO.getManager() == null ? null : personRepository.findById(employeeDTO.getManager())
                .orElseThrow(() -> new NotFoundException("manager not found"));
        employee.setManager(manager);
        final Person person = employeeDTO.getPerson() == null ? null : personRepository.findById(employeeDTO.getPerson())
                .orElseThrow(() -> new NotFoundException("person not found"));
        employee.setPerson(person);
        final JobTitle jobTitle = employeeDTO.getJobTitle() == null ? null : jobTitleRepository.findById(employeeDTO.getJobTitle())
                .orElseThrow(() -> new NotFoundException("jobTitle not found"));
        employee.setJobTitle(jobTitle);
        return employee;
    }

}
