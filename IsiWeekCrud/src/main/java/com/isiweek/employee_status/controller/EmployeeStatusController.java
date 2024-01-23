package com.isiweek.employee_status.controller;

import com.isiweek.employee_status.model.EmployeeStatusDTO;
import com.isiweek.employee_status.service.EmployeeStatusService;
import com.isiweek.util.WebUtils;
import jakarta.validation.Valid;
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
@RequestMapping("/employeeStatuses")
public class EmployeeStatusController {

    private final EmployeeStatusService employeeStatusService;

    public EmployeeStatusController(final EmployeeStatusService employeeStatusService) {
        this.employeeStatusService = employeeStatusService;
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("employeeStatuses", employeeStatusService.findAll());
        return "employeeStatus/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("employeeStatus") final EmployeeStatusDTO employeeStatusDTO) {
        return "employeeStatus/add";
    }

    @PostMapping("/add")
    public String add(
            @ModelAttribute("employeeStatus") @Valid final EmployeeStatusDTO employeeStatusDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (!bindingResult.hasFieldErrors("name") && employeeStatusService.nameExists(employeeStatusDTO.getName())) {
            bindingResult.rejectValue("name", "Exists.employeeStatus.name");
        }
        if (bindingResult.hasErrors()) {
            return "employeeStatus/add";
        }
        employeeStatusService.create(employeeStatusDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("employeeStatus.create.success"));
        return "redirect:/employeeStatuses";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id, final Model model) {
        model.addAttribute("employeeStatus", employeeStatusService.get(id));
        return "employeeStatus/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id,
            @ModelAttribute("employeeStatus") @Valid final EmployeeStatusDTO employeeStatusDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        final EmployeeStatusDTO currentEmployeeStatusDTO = employeeStatusService.get(id);
        if (!bindingResult.hasFieldErrors("name") &&
                !employeeStatusDTO.getName().equalsIgnoreCase(currentEmployeeStatusDTO.getName()) &&
                employeeStatusService.nameExists(employeeStatusDTO.getName())) {
            bindingResult.rejectValue("name", "Exists.employeeStatus.name");
        }
        if (bindingResult.hasErrors()) {
            return "employeeStatus/edit";
        }
        employeeStatusService.update(id, employeeStatusDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("employeeStatus.update.success"));
        return "redirect:/employeeStatuses";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") final Long id,
            final RedirectAttributes redirectAttributes) {
        final String referencedWarning = employeeStatusService.getReferencedWarning(id);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR, referencedWarning);
        } else {
            employeeStatusService.delete(id);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("employeeStatus.delete.success"));
        }
        return "redirect:/employeeStatuses";
    }

}
