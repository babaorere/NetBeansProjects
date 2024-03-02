package com.isiweek.debtor;

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
@RequestMapping("/debtors")
public class DebtorController {

    private final DebtorService debtorService;

    public DebtorController(final DebtorService debtorService) {
        this.debtorService = debtorService;
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("debtors", debtorService.findAll());
        return "debtor/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("debtor") final DebtorDTO debtorDTO) {
        return "debtor/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("debtor") @Valid final DebtorDTO debtorDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "debtor/add";
        }
        debtorService.create(debtorDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("debtor.create.success"));
        return "redirect:/debtors";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id, final Model model) {
        model.addAttribute("debtor", debtorService.get(id));
        return "debtor/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id,
            @ModelAttribute("debtor") @Valid final DebtorDTO debtorDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "debtor/edit";
        }
        debtorService.update(id, debtorDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("debtor.update.success"));
        return "redirect:/debtors";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") final Long id,
            final RedirectAttributes redirectAttributes) {
        final ReferencedWarning referencedWarning = debtorService.getReferencedWarning(id);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR,
                    WebUtils.getMessage(referencedWarning.getKey(), referencedWarning.getParams().toArray()));
        } else {
            debtorService.delete(id);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("debtor.delete.success"));
        }
        return "redirect:/debtors";
    }

}
