package com.isiweek.employee;

import com.isiweek.departament.Departament;
import com.isiweek.employee.Employee;
import com.isiweek.employee_status.EmployeeStatus;
import com.isiweek.job_title.domain.JobTitle;
import com.isiweek.person.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Employee findFirstByPerson(Person person);

    Employee findFirstByEmployeeStatus(EmployeeStatus employeeStatus);

    Employee findFirstByJobTitle(JobTitle jobTitle);

    Employee findFirstByDepartment(Departament departament);

    Employee findFirstByManager(Person person);

}
