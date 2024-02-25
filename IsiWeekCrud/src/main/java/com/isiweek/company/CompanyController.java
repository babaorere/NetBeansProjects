package com.isiweek.company;

import com.isiweek.status.Status;
import com.isiweek.status.StatusEnum;
import com.isiweek.status.StatusRepository;
import com.isiweek.status.StatusService;
import com.isiweek.util.CustomCollectors;
import com.isiweek.util.WebUtils;
import jakarta.validation.Valid;
import java.util.Optional;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Log4j2
@Controller
@RequestMapping("/companies")
public class CompanyController {

    private StatusRepository statusRepository;
    private StatusService statusService;
    private final CompanyService companyService;

    @Autowired(required = true)
    public CompanyController(final CompanyService inCompanyService) {
        this.companyService = inCompanyService;

        log.info("Constructor CompanyControler");
    }

    /**
     * Se utiliza para evitar la referencia circular con CompanyService
     *
     * @param companyRepository
     */
    @Autowired(required = true)
    public void SetAutoWired(final StatusRepository inStatusRepository, final StatusService inStatusService) {
        this.statusRepository = inStatusRepository;
        this.statusService = inStatusService;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("statusValues", statusRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Status::getId, Status::getId)));
    }

    @GetMapping
    public String list(final Model model) {
//        System.out.println("Lista de company: " + companyService.findAll());
        model.addAttribute("companies", companyService.findAll());
        return "company/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("company") final Company companyDTO) {
        return "company/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("company") @Valid final Company company,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "company/add";
        }

        // Establecer los valores por defecto para una compañia nueva
        Optional<Status> statusOp = statusService.findByStatusEnum(StatusEnum.PENDING);
        company.setStatus(statusOp.get());

        // Establecer los valores por defecto para una compañia nueva
        companyService.create(company);

        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("company.create.success"));
        return "redirect:/companies";
    }

    @GetMapping("/edit/{id}")
    @Transactional
    public String edit(@PathVariable(name = "id") final Long id, final Model model) {

        Optional<Company> companyOp = companyService.get(id);

        log.info("get /edit/{id}" + companyOp.get() + ", status: " + companyOp.get().getStatus());

        model.addAttribute("company", companyService.get(id));
        return "company/edit";
    }

    /**
     * Actualiza los campos de Company, a excepcion del Status
     *
     * @param id
     * @param company
     * @param bindingResult
     * @param redirectAttributes
     * @return
     */
    @PostMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id,
            @ModelAttribute("company") @Valid final Company company,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "company/edit";
        }

        log.info("post /edit/{id}" + company + ", status: " + company.getStatus());

        companyService.update(id, company);
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
