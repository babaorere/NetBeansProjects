package com.isiweek.employee.controller;

import com.isiweek.departament.domain.Departament;
import com.isiweek.departament.repos.DepartamentRepository;
import com.isiweek.employee.model.EmployeeDTO;
import com.isiweek.employee.service.EmployeeService;
import com.isiweek.employee_status.domain.EmployeeStatus;
import com.isiweek.employee_status.repos.EmployeeStatusRepository;
import com.isiweek.job_title.domain.JobTitle;
import com.isiweek.job_title.repos.JobTitleRepository;
import com.isiweek.person.domain.Person;
import com.isiweek.person.repos.PersonRepository;
import com.isiweek.util.CustomCollectors;
import com.isiweek.util.WebUtils;
import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final PersonRepository personRepository;
    private final EmployeeStatusRepository employeeStatusRepository;
    private final JobTitleRepository jobTitleRepository;
    private final DepartamentRepository departamentRepository;

    public EmployeeController(final EmployeeService employeeService,
        final PersonRepository personRepository,
        final EmployeeStatusRepository employeeStatusRepository,
        final JobTitleRepository jobTitleRepository,
        final DepartamentRepository departamentRepository) {
        this.employeeService = employeeService;
        this.personRepository = personRepository;
        this.employeeStatusRepository = employeeStatusRepository;
        this.jobTitleRepository = jobTitleRepository;
        this.departamentRepository = departamentRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("personValues", personRepository.findAll(Sort.by("id"))
            .stream()
            .collect(CustomCollectors.toSortedMap(Person::getId, Person::getIdDoc)));
        model.addAttribute("employeeStatusValues", employeeStatusRepository.findAll(Sort.by("id"))
            .stream()
            .collect(CustomCollectors.toSortedMap(EmployeeStatus::getId, EmployeeStatus::getName)));
        model.addAttribute("jobTitleValues", jobTitleRepository.findAll(Sort.by("id"))
            .stream()
            .collect(CustomCollectors.toSortedMap(JobTitle::getId, JobTitle::getName)));
        model.addAttribute("departmentValues", departamentRepository.findAll(Sort.by("id"))
            .stream()
            .collect(CustomCollectors.toSortedMap(Departament::getId, Departament::getName)));
        model.addAttribute("managerValues", personRepository.findAll(Sort.by("id"))
            .stream()
            .collect(CustomCollectors.toSortedMap(Person::getId, Person::getIdDoc)));
        model.addAttribute("managerValues", personRepository.findAll(Sort.by("id"))
            .stream()
            .collect(CustomCollectors.toSortedMap(Person::getId, Person::getIdDoc)));
        model.addAttribute("employeeStatusValues", employeeStatusRepository.findAll(Sort.by("id"))
            .stream()
            .collect(CustomCollectors.toSortedMap(EmployeeStatus::getId, EmployeeStatus::getName)));
        model.addAttribute("departmentValues", departamentRepository.findAll(Sort.by("id"))
            .stream()
            .collect(CustomCollectors.toSortedMap(Departament::getId, Departament::getName)));
        model.addAttribute("jobTitleValues", jobTitleRepository.findAll(Sort.by("id"))
            .stream()
            .collect(CustomCollectors.toSortedMap(JobTitle::getId, JobTitle::getName)));
        model.addAttribute("personValues", personRepository.findAll(Sort.by("id"))
            .stream()
            .collect(CustomCollectors.toSortedMap(Person::getId, Person::getIdDoc)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("employees", employeeService.findAll());
        return "employee/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("employee") final EmployeeDTO employeeDTO) {
        return "employee/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("employee") @Valid final EmployeeDTO employeeDTO,
        final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "employee/add";
        }
        employeeService.create(employeeDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("employee.create.success"));
        return "redirect:/employees";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id, final Model model) {
        model.addAttribute("employee", employeeService.get(id));
        return "employee/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id,
        @ModelAttribute("employee") @Valid final EmployeeDTO employeeDTO,
        final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "employee/edit";
        }
        employeeService.update(id, employeeDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("employee.update.success"));
        return "redirect:/employees";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") final Long id,
        final RedirectAttributes redirectAttributes) {
        employeeService.delete(id);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("employee.delete.success"));
        return "redirect:/employees";
    }

}
