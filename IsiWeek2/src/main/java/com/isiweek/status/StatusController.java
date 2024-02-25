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
@RequestMapping("/statusEntities")
public class StatusController {

    private final StatusService statusEntityService;

    public StatusController(final StatusService statusEntityService) {
        this.statusEntityService = statusEntityService;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("nameValues", StatusEnum.values());
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("statusEntities", statusEntityService.findAll());
        return "statusEntity/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("statusEntity") final StatusDTO statusEntityDTO) {
        return "statusEntity/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("statusEntity") @Valid final StatusDTO statusEntityDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "statusEntity/add";
        }
        statusEntityService.create(statusEntityDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("statusEntity.create.success"));
        return "redirect:/statusEntities";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id, final Model model) {
        model.addAttribute("statusEntity", statusEntityService.get(id));
        return "statusEntity/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id,
            @ModelAttribute("statusEntity") @Valid final StatusDTO statusEntityDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "statusEntity/edit";
        }
        statusEntityService.update(id, statusEntityDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("statusEntity.update.success"));
        return "redirect:/statusEntities";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") final Long id,
            final RedirectAttributes redirectAttributes) {
        final ReferencedWarning referencedWarning = statusEntityService.getReferencedWarning(id);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR,
                    WebUtils.getMessage(referencedWarning.getKey(), referencedWarning.getParams().toArray()));
        } else {
            statusEntityService.delete(id);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("statusEntity.delete.success"));
        }
        return "redirect:/statusEntities";
    }

}
