package com.isiweek.employee;

import com.isiweek.departament.Departament;
import com.isiweek.employee_status.EmployeeStatus;
import com.isiweek.job_title.JobTitle;
import com.isiweek.person.Person;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Employee findFirstByEmployeeStatus(EmployeeStatus employeeStatus);

    Employee findFirstByDepartment(Departament departament);

    Employee findFirstByManager(Person person);

    Employee findFirstByPerson(Person person);

    Employee findFirstByJobTitle(JobTitle jobTitle);

}
