package com.isiweek.loan_collector_status.controller;

import com.isiweek.loan_collector_status.model.LoanCollectorStatusDTO;
import com.isiweek.loan_collector_status.service.LoanCollectorStatusService;
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
@RequestMapping("/loanCollectorStatuses")
public class LoanCollectorStatusController {

    private final LoanCollectorStatusService loanCollectorStatusService;

    public LoanCollectorStatusController(
            final LoanCollectorStatusService loanCollectorStatusService) {
        this.loanCollectorStatusService = loanCollectorStatusService;
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("loanCollectorStatuses", loanCollectorStatusService.findAll());
        return "loanCollectorStatus/list";
    }

    @GetMapping("/add")
    public String add(
            @ModelAttribute("loanCollectorStatus") final LoanCollectorStatusDTO loanCollectorStatusDTO) {
        return "loanCollectorStatus/add";
    }

    @PostMapping("/add")
    public String add(
            @ModelAttribute("loanCollectorStatus") @Valid final LoanCollectorStatusDTO loanCollectorStatusDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (!bindingResult.hasFieldErrors("name") && loanCollectorStatusService.nameExists(loanCollectorStatusDTO.getName())) {
            bindingResult.rejectValue("name", "Exists.loanCollectorStatus.name");
        }
        if (bindingResult.hasErrors()) {
            return "loanCollectorStatus/add";
        }
        loanCollectorStatusService.create(loanCollectorStatusDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("loanCollectorStatus.create.success"));
        return "redirect:/loanCollectorStatuses";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id, final Model model) {
        model.addAttribute("loanCollectorStatus", loanCollectorStatusService.get(id));
        return "loanCollectorStatus/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id,
            @ModelAttribute("loanCollectorStatus") @Valid final LoanCollectorStatusDTO loanCollectorStatusDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        final LoanCollectorStatusDTO currentLoanCollectorStatusDTO = loanCollectorStatusService.get(id);
        if (!bindingResult.hasFieldErrors("name") &&
                !loanCollectorStatusDTO.getName().equalsIgnoreCase(currentLoanCollectorStatusDTO.getName()) &&
                loanCollectorStatusService.nameExists(loanCollectorStatusDTO.getName())) {
            bindingResult.rejectValue("name", "Exists.loanCollectorStatus.name");
        }
        if (bindingResult.hasErrors()) {
            return "loanCollectorStatus/edit";
        }
        loanCollectorStatusService.update(id, loanCollectorStatusDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("loanCollectorStatus.update.success"));
        return "redirect:/loanCollectorStatuses";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") final Long id,
            final RedirectAttributes redirectAttributes) {
        final String referencedWarning = loanCollectorStatusService.getReferencedWarning(id);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR, referencedWarning);
        } else {
            loanCollectorStatusService.delete(id);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("loanCollectorStatus.delete.success"));
        }
        return "redirect:/loanCollectorStatuses";
    }

}
