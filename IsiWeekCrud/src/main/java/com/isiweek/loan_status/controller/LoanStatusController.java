package com.isiweek.loan_status.controller;

import com.isiweek.loan_status.model.LoanStatusDTO;
import com.isiweek.loan_status.service.LoanStatusService;
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
@RequestMapping("/loanStatuses")
public class LoanStatusController {

    private final LoanStatusService loanStatusService;

    public LoanStatusController(final LoanStatusService loanStatusService) {
        this.loanStatusService = loanStatusService;
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("loanStatuses", loanStatusService.findAll());
        return "loanStatus/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("loanStatus") final LoanStatusDTO loanStatusDTO) {
        return "loanStatus/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("loanStatus") @Valid final LoanStatusDTO loanStatusDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (!bindingResult.hasFieldErrors("name") && loanStatusService.nameExists(loanStatusDTO.getName())) {
            bindingResult.rejectValue("name", "Exists.loanStatus.name");
        }
        if (bindingResult.hasErrors()) {
            return "loanStatus/add";
        }
        loanStatusService.create(loanStatusDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("loanStatus.create.success"));
        return "redirect:/loanStatuses";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id, final Model model) {
        model.addAttribute("loanStatus", loanStatusService.get(id));
        return "loanStatus/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id,
            @ModelAttribute("loanStatus") @Valid final LoanStatusDTO loanStatusDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        final LoanStatusDTO currentLoanStatusDTO = loanStatusService.get(id);
        if (!bindingResult.hasFieldErrors("name") &&
                !loanStatusDTO.getName().equalsIgnoreCase(currentLoanStatusDTO.getName()) &&
                loanStatusService.nameExists(loanStatusDTO.getName())) {
            bindingResult.rejectValue("name", "Exists.loanStatus.name");
        }
        if (bindingResult.hasErrors()) {
            return "loanStatus/edit";
        }
        loanStatusService.update(id, loanStatusDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("loanStatus.update.success"));
        return "redirect:/loanStatuses";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") final Long id,
            final RedirectAttributes redirectAttributes) {
        final String referencedWarning = loanStatusService.getReferencedWarning(id);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR, referencedWarning);
        } else {
            loanStatusService.delete(id);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("loanStatus.delete.success"));
        }
        return "redirect:/loanStatuses";
    }

}
