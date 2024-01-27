package com.isiweek.doc_type;

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
@RequestMapping("/docTypes")
public class DocTypeController {

    private final DocTypeService docTypeService;

    public DocTypeController(final DocTypeService docTypeService) {
        this.docTypeService = docTypeService;
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("docTypes", docTypeService.findAll());
        return "docType/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("docType") final DocTypeDTO docTypeDTO) {
        return "docType/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("docType") @Valid final DocTypeDTO docTypeDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (!bindingResult.hasFieldErrors("name") && docTypeService.nameExists(docTypeDTO.getName())) {
            bindingResult.rejectValue("name", "Exists.docType.name");
        }
        if (bindingResult.hasErrors()) {
            return "docType/add";
        }
        docTypeService.create(docTypeDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("docType.create.success"));
        return "redirect:/docTypes";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id, final Model model) {
        model.addAttribute("docType", docTypeService.get(id));
        return "docType/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id,
            @ModelAttribute("docType") @Valid final DocTypeDTO docTypeDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        final DocTypeDTO currentDocTypeDTO = docTypeService.get(id);
        if (!bindingResult.hasFieldErrors("name") &&
                !docTypeDTO.getName().equalsIgnoreCase(currentDocTypeDTO.getName()) &&
                docTypeService.nameExists(docTypeDTO.getName())) {
            bindingResult.rejectValue("name", "Exists.docType.name");
        }
        if (bindingResult.hasErrors()) {
            return "docType/edit";
        }
        docTypeService.update(id, docTypeDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("docType.update.success"));
        return "redirect:/docTypes";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") final Long id,
            final RedirectAttributes redirectAttributes) {
        final String referencedWarning = docTypeService.getReferencedWarning(id);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR, referencedWarning);
        } else {
            docTypeService.delete(id);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("docType.delete.success"));
        }
        return "redirect:/docTypes";
    }

}
