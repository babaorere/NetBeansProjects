package com.isiweek.company;

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
@RequestMapping("/companies")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(final CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("companies", companyService.findAll());
        return "company/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("company") final CompanyDTO companyDTO) {
        return "company/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("company") @Valid final CompanyDTO companyDTO,
        final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (!bindingResult.hasFieldErrors("email") && companyService.emailExists(companyDTO.getEmail())) {
            bindingResult.rejectValue("email", "Exists.company.email");
        }
        if (!bindingResult.hasFieldErrors("name") && companyService.nameExists(companyDTO.getName())) {
            bindingResult.rejectValue("name", "Exists.company.name");
        }
        if (!bindingResult.hasFieldErrors("taxidnumber") && companyService.taxidnumberExists(companyDTO.getTaxidnumber())) {
            bindingResult.rejectValue("taxidnumber", "Exists.company.taxidnumber");
        }
        if (bindingResult.hasErrors()) {
            return "company/add";
        }

        companyService.create(companyDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("company.create.success"));
        return "redirect:/companies";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id, final Model model) {
        model.addAttribute("company", companyService.get(id));
        return "company/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id,
        @ModelAttribute("company") @Valid final CompanyDTO companyDTO,
        final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        final CompanyDTO currentCompanyDTO = companyService.get(id);
        if (!bindingResult.hasFieldErrors("email")
            && !companyDTO.getEmail().equalsIgnoreCase(currentCompanyDTO.getEmail())
            && companyService.emailExists(companyDTO.getEmail())) {
            bindingResult.rejectValue("email", "Exists.company.email");
        }
        if (!bindingResult.hasFieldErrors("name")
            && !companyDTO.getName().equalsIgnoreCase(currentCompanyDTO.getName())
            && companyService.nameExists(companyDTO.getName())) {
            bindingResult.rejectValue("name", "Exists.company.name");
        }
        if (!bindingResult.hasFieldErrors("taxidnumber")
            && !companyDTO.getTaxidnumber().equalsIgnoreCase(currentCompanyDTO.getTaxidnumber())
            && companyService.taxidnumberExists(companyDTO.getTaxidnumber())) {
            bindingResult.rejectValue("taxidnumber", "Exists.company.taxidnumber");
        }
        if (bindingResult.hasErrors()) {
            return "company/edit";
        }

        companyService.update(id, companyDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("company.update.success"));
        return "redirect:/companies";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") final Long id,
        final RedirectAttributes redirectAttributes) {
        final String referencedWarning = companyService.getReferencedWarning(id);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR, referencedWarning);
        } else {
            companyService.delete(id);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("company.delete.success"));
        }
        return "redirect:/companies";
    }

}
