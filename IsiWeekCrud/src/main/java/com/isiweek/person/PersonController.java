package com.isiweek.person;

import com.isiweek.company.Company;
import com.isiweek.company.CompanyRepository;
import com.isiweek.country.Country;
import com.isiweek.country.CountryRepository;
import com.isiweek.criminal_record.CriminalRecord;
import com.isiweek.criminal_record.CriminalRecordRepository;
import com.isiweek.doc_type.DocType;
import com.isiweek.doc_type.DocTypeRepository;
import com.isiweek.marital_status.MaritalStatus;
import com.isiweek.marital_status.MaritalStatusRepository;
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
@RequestMapping("/people")
public class PersonController {

    private final PersonService personService;
    private final MaritalStatusRepository maritalStatusRepository;
    private final DocTypeRepository docTypeRepository;
    private final CountryRepository countryRepository;
    private final CompanyRepository companyRepository;
    private final CriminalRecordRepository criminalRecordRepository;

    public PersonController(final PersonService personService,
        final MaritalStatusRepository maritalStatusRepository,
        final DocTypeRepository docTypeRepository, final CountryRepository countryRepository,
        final CompanyRepository companyRepository,
        final CriminalRecordRepository criminalRecordRepository) {
        this.personService = personService;
        this.maritalStatusRepository = maritalStatusRepository;
        this.docTypeRepository = docTypeRepository;
        this.countryRepository = countryRepository;
        this.companyRepository = companyRepository;
        this.criminalRecordRepository = criminalRecordRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("maritalStatusValues", maritalStatusRepository.findAll(Sort.by("id"))
            .stream()
            .collect(CustomCollectors.toSortedMap(MaritalStatus::getId, MaritalStatus::getName)));
        model.addAttribute("docTypeValues", docTypeRepository.findAll(Sort.by("id"))
            .stream()
            .collect(CustomCollectors.toSortedMap(DocType::getId, DocType::getName)));
        model.addAttribute("countryValues", countryRepository.findAll(Sort.by("id"))
            .stream()
            .collect(CustomCollectors.toSortedMap(Country::getId, Country::getName)));
        model.addAttribute("companyValues", companyRepository.findAll(Sort.by("id"))
            .stream()
            .collect(CustomCollectors.toSortedMap(Company::getId, Company::getAddress)));
        model.addAttribute("criminalRecordValues", criminalRecordRepository.findAll(Sort.by("id"))
            .stream()
            .collect(CustomCollectors.toSortedMap(CriminalRecord::getId, CriminalRecord::getName)));
        model.addAttribute("docTypeValues", docTypeRepository.findAll(Sort.by("id"))
            .stream()
            .collect(CustomCollectors.toSortedMap(DocType::getId, DocType::getName)));
        model.addAttribute("countryValues", countryRepository.findAll(Sort.by("id"))
            .stream()
            .collect(CustomCollectors.toSortedMap(Country::getId, Country::getName)));
        model.addAttribute("criminalRecordValues", criminalRecordRepository.findAll(Sort.by("id"))
            .stream()
            .collect(CustomCollectors.toSortedMap(CriminalRecord::getId, CriminalRecord::getName)));
        model.addAttribute("maritalStatusValues", maritalStatusRepository.findAll(Sort.by("id"))
            .stream()
            .collect(CustomCollectors.toSortedMap(MaritalStatus::getId, MaritalStatus::getName)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("persons", personService.findAll());
        return "person/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("person") final Person inEntity) {
        return "person/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("person") @Valid final Person inEntity,
        final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (!bindingResult.hasFieldErrors("idDoc") && personService.idDocExists(inEntity.getIdDoc())) {
            bindingResult.rejectValue("idDoc", "Exists.person.idDoc");
        }
        if (!bindingResult.hasFieldErrors("email") && personService.emailExists(inEntity.getEmail())) {
            bindingResult.rejectValue("email", "Exists.person.email");
        }
        if (bindingResult.hasErrors()) {
            return "person/add";
        }
        personService.create(inEntity);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("person.create.success"));
        return "redirect:/people";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id, final Model model) {
        model.addAttribute("person", personService.get(id));
        return "person/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id,
        @ModelAttribute("person") @Valid final Person inEntity,
        final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        final Person currentEntity = personService.get(id);
        if (!bindingResult.hasFieldErrors("idDoc")
            && !inEntity.getIdDoc().equalsIgnoreCase(currentEntity.getIdDoc())
            && personService.idDocExists(inEntity.getIdDoc())) {
            bindingResult.rejectValue("idDoc", "Exists.person.idDoc");
        }
        if (!bindingResult.hasFieldErrors("email")
            && !inEntity.getEmail().equalsIgnoreCase(currentEntity.getEmail())
            && personService.emailExists(inEntity.getEmail())) {
            bindingResult.rejectValue("email", "Exists.person.email");
        }
        if (bindingResult.hasErrors()) {
            return "person/edit";
        }
        personService.update(id, inEntity);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("person.update.success"));
        return "redirect:/people";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") final Long id,
        final RedirectAttributes redirectAttributes) {
        final String referencedWarning = personService.getReferencedWarning(id);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR, referencedWarning);
        } else {
            personService.delete(id);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("person.delete.success"));
        }
        return "redirect:/people";
    }

}
