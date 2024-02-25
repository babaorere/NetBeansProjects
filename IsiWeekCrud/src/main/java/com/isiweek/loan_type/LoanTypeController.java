package com.isiweek.loan_type;

import com.isiweek.util.ReferencedWarning;
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
        final ReferencedWarning referencedWarning = loanTypeService.getReferencedWarning(id);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR,
                    WebUtils.getMessage(referencedWarning.getKey(), referencedWarning.getParams().toArray()));
        } else {
            loanTypeService.delete(id);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("loanType.delete.success"));
        }
        return "redirect:/loanTypes";
    }

}
