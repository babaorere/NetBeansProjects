package com.isiweek.loan_collector.controller;

import com.isiweek.loan_collector.model.LoanCollectorDTO;
import com.isiweek.loan_collector.service.LoanCollectorService;
import com.isiweek.loan_collector_status.domain.LoanCollectorStatus;
import com.isiweek.loan_collector_status.repos.LoanCollectorStatusRepository;
import com.isiweek.person.Person;
import com.isiweek.person.PersonRepository;
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

@Controller
@RequestMapping("/loanCollectors")
public class LoanCollectorController {

    private final LoanCollectorService loanCollectorService;
    private final PersonRepository personRepository;
    private final LoanCollectorStatusRepository loanCollectorStatusRepository;

    public LoanCollectorController(final LoanCollectorService loanCollectorService,
        final PersonRepository personRepository,
        final LoanCollectorStatusRepository loanCollectorStatusRepository) {
        this.loanCollectorService = loanCollectorService;
        this.personRepository = personRepository;
        this.loanCollectorStatusRepository = loanCollectorStatusRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("personValues", personRepository.findAll(Sort.by("id"))
            .stream()
            .collect(CustomCollectors.toSortedMap(Person::getId, Person::getIdDoc)));
        model.addAttribute("lcStatusValues", loanCollectorStatusRepository.findAll(Sort.by("id"))
            .stream()
            .collect(CustomCollectors.toSortedMap(LoanCollectorStatus::getId, LoanCollectorStatus::getName)));
        model.addAttribute("lcStatusValues", loanCollectorStatusRepository.findAll(Sort.by("id"))
            .stream()
            .collect(CustomCollectors.toSortedMap(LoanCollectorStatus::getId, LoanCollectorStatus::getName)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("loanCollectors", loanCollectorService.findAll());
        return "loanCollector/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("loanCollector") final LoanCollectorDTO loanCollectorDTO) {
        return "loanCollector/add";
    }

    @PostMapping("/add")
    public String add(
        @ModelAttribute("loanCollector") @Valid final LoanCollectorDTO loanCollectorDTO,
        final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (!bindingResult.hasFieldErrors("idPerson") && loanCollectorService.idPersonExists(loanCollectorDTO.getIdPerson())) {
            bindingResult.rejectValue("idPerson", "Exists.loanCollector.idPerson");
        }
        if (bindingResult.hasErrors()) {
            return "loanCollector/add";
        }
        loanCollectorService.create(loanCollectorDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("loanCollector.create.success"));
        return "redirect:/loanCollectors";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id, final Model model) {
        model.addAttribute("loanCollector", loanCollectorService.get(id));
        return "loanCollector/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id,
        @ModelAttribute("loanCollector") @Valid final LoanCollectorDTO loanCollectorDTO,
        final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        final LoanCollectorDTO currentLoanCollectorDTO = loanCollectorService.get(id);
        if (!bindingResult.hasFieldErrors("idPerson")
            && !loanCollectorDTO.getIdPerson().equals(currentLoanCollectorDTO.getIdPerson())
            && loanCollectorService.idPersonExists(loanCollectorDTO.getIdPerson())) {
            bindingResult.rejectValue("idPerson", "Exists.loanCollector.idPerson");
        }
        if (bindingResult.hasErrors()) {
            return "loanCollector/edit";
        }
        loanCollectorService.update(id, loanCollectorDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("loanCollector.update.success"));
        return "redirect:/loanCollectors";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") final Long id,
        final RedirectAttributes redirectAttributes) {
        loanCollectorService.delete(id);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("loanCollector.delete.success"));
        return "redirect:/loanCollectors";
    }

}
