package com.isiweek.marital_status;

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
@RequestMapping("/maritalStatuses")
public class MaritalStatusController {

    private final MaritalStatusService maritalStatusService;

    public MaritalStatusController(final MaritalStatusService maritalStatusService) {
        this.maritalStatusService = maritalStatusService;
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("maritalStatuses", maritalStatusService.findAll());
        return "maritalStatus/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("maritalStatus") final MaritalStatusDTO maritalStatusDTO) {
        return "maritalStatus/add";
    }

    @PostMapping("/add")
    public String add(
            @ModelAttribute("maritalStatus") @Valid final MaritalStatusDTO maritalStatusDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "maritalStatus/add";
        }
        maritalStatusService.create(maritalStatusDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("maritalStatus.create.success"));
        return "redirect:/maritalStatuses";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id, final Model model) {
        model.addAttribute("maritalStatus", maritalStatusService.get(id));
        return "maritalStatus/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id,
            @ModelAttribute("maritalStatus") @Valid final MaritalStatusDTO maritalStatusDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "maritalStatus/edit";
        }
        maritalStatusService.update(id, maritalStatusDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("maritalStatus.update.success"));
        return "redirect:/maritalStatuses";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") final Long id,
            final RedirectAttributes redirectAttributes) {
        final ReferencedWarning referencedWarning = maritalStatusService.getReferencedWarning(id);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR,
                    WebUtils.getMessage(referencedWarning.getKey(), referencedWarning.getParams().toArray()));
        } else {
            maritalStatusService.delete(id);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("maritalStatus.delete.success"));
        }
        return "redirect:/maritalStatuses";
    }

}
