package com.isiweek.employee.repos;

import com.isiweek.departament.domain.Departament;
import com.isiweek.employee.domain.Employee;
import com.isiweek.employee_status.domain.EmployeeStatus;
import com.isiweek.job_title.domain.JobTitle;
import com.isiweek.person.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Employee findFirstByPerson(Person person);

    Employee findFirstByEmployeeStatus(EmployeeStatus employeeStatus);

    Employee findFirstByJobTitle(JobTitle jobTitle);

    Employee findFirstByDepartment(Departament departament);

    Employee findFirstByManager(Person person);

}
