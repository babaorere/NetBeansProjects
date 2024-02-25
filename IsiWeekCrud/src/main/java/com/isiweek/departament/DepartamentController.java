package com.isiweek.departament;

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
@RequestMapping("/departaments")
public class DepartamentController {

    private final DepartamentService departamentService;

    public DepartamentController(final DepartamentService departamentService) {
        this.departamentService = departamentService;
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("departaments", departamentService.findAll());
        return "departament/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("departament") final DepartamentDTO departamentDTO) {
        return "departament/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("departament") @Valid final DepartamentDTO departamentDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "departament/add";
        }
        departamentService.create(departamentDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("departament.create.success"));
        return "redirect:/departaments";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id, final Model model) {
        model.addAttribute("departament", departamentService.get(id));
        return "departament/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id,
            @ModelAttribute("departament") @Valid final DepartamentDTO departamentDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "departament/edit";
        }
        departamentService.update(id, departamentDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("departament.update.success"));
        return "redirect:/departaments";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") final Long id,
            final RedirectAttributes redirectAttributes) {
        final ReferencedWarning referencedWarning = departamentService.getReferencedWarning(id);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR,
                    WebUtils.getMessage(referencedWarning.getKey(), referencedWarning.getParams().toArray()));
        } else {
            departamentService.delete(id);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("departament.delete.success"));
        }
        return "redirect:/departaments";
    }

}
