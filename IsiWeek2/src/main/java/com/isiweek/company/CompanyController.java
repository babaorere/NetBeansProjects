package com.isiweek.company;

import com.isiweek.status.Status;
import com.isiweek.util.CustomCollectors;
import com.isiweek.util.WebUtils;
import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.isiweek.status.StatusRepository;


@Controller
@RequestMapping("/companies")
public class CompanyController {

    private final CompanyService companyService;
    private final StatusRepository statusEntityRepository;

    public CompanyController(final CompanyService companyService,
            final StatusRepository statusEntityRepository) {
        this.companyService = companyService;
        this.statusEntityRepository = statusEntityRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("statusValues", statusEntityRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Status::getId, Status::getId)));
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
        companyService.delete(id);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("company.delete.success"));
        return "redirect:/companies";
    }

}
