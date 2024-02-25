package com.isiweek.loan_type.domain;

import com.isiweek.loan_type.domain.LoanTypeDTO;
import com.isiweek.loan_type.domain.LoanTypeService;
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
@RequestMapping("/loanTypes")
public class LoanTypeController {

    private final LoanTypeService loanTypeService;

    public LoanTypeController(final LoanTypeService loanTypeService) {
        this.loanTypeService = loanTypeService;
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("loanTypes", loanTypeService.findAll());
        return "loanType/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("loanType") final LoanTypeDTO loanTypeDTO) {
        return "loanType/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("loanType") @Valid final LoanTypeDTO loanTypeDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (!bindingResult.hasFieldErrors("name") && loanTypeService.nameExists(loanTypeDTO.getName())) {
            bindingResult.rejectValue("name", "Exists.loanType.name");
        }
        if (!bindingResult.hasFieldErrors("description") && loanTypeService.descriptionExists(loanTypeDTO.getDescription())) {
            bindingResult.rejectValue("description", "Exists.loanType.description");
        }
        if (bindingResult.hasErrors()) {
            return "loanType/add";
        }
        loanTypeService.create(loanTypeDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("loanType.create.success"));
        return "redirect:/loanTypes";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id, final Model model) {
        model.addAttribute("loanType", loanTypeService.get(id));
        return "loanType/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id,
            @ModelAttribute("loanType") @Valid final LoanTypeDTO loanTypeDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        final LoanTypeDTO currentLoanTypeDTO = loanTypeService.get(id);
        if (!bindingResult.hasFieldErrors("name") &&
                !loanTypeDTO.getName().equalsIgnoreCase(currentLoanTypeDTO.getName()) &&
                loanTypeService.nameExists(loanTypeDTO.getName())) {
            bindingResult.rejectValue("name", "Exists.loanType.name");
        }
        if (!bindingResult.hasFieldErrors("description") &&
                !loanTypeDTO.getDescription().equalsIgnoreCase(currentLoanTypeDTO.getDescription()) &&
                loanTypeService.descriptionExists(loanTypeDTO.getDescription())) {
            bindingResult.rejectValue("description", "Exists.loanType.description");
        }
        if (bindingResult.hasErrors()) {
            return "loanType/edit";
        }
        loanTypeService.update(id, loanTypeDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("loanType.update.success"));
        return "redirect:/loanTypes";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") final Long id,
            final RedirectAttributes redirectAttributes) {
        final String referencedWarning = loanTypeService.getReferencedWarning(id);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR, referencedWarning);
        } else {
            loanTypeService.delete(id);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("loanType.delete.success"));
        }
        return "redirect:/loanTypes";
    }

}
