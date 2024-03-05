package com.isiweek.status;

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
@RequestMapping("/statuses")
public class StatusController {

    private final StatusService statusService;

    public StatusController(final StatusService statusService) {
        this.statusService = statusService;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("nameValues", StatusEnum.values());
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("statuses", statusService.findAll());
        return "status/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("status") final StatusDTO statusDTO) {
        return "status/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("status") @Valid final StatusDTO statusDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "status/add";
        }
        statusService.create(statusDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("status.create.success"));
        return "redirect:/statuses";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id, final Model model) {
        model.addAttribute("status", statusService.get(id));
        return "status/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id,
            @ModelAttribute("status") @Valid final StatusDTO statusDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "status/edit";
        }
        statusService.update(id, statusDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("status.update.success"));
        return "redirect:/statuses";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") final Long id,
            final RedirectAttributes redirectAttributes) {
        final ReferencedWarning referencedWarning = statusService.getReferencedWarning(id);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR,
                    WebUtils.getMessage(referencedWarning.getKey(), referencedWarning.getParams().toArray()));
        } else {
            statusService.delete(id);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("status.delete.success"));
        }
        return "redirect:/statuses";
    }

}
