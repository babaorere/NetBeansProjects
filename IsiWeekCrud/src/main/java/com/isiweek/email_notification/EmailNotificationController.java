package com.isiweek.email_notification;

import com.isiweek.loan_contract.LoanContract;
import com.isiweek.loan_contract.LoanContractRepository;
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
@RequestMapping("/emailNotifications")
public class EmailNotificationController {

    private final EmailNotificationService emailNotificationService;
    private final LoanContractRepository loanContractRepository;
    private final PersonRepository personRepository;

    public EmailNotificationController(final EmailNotificationService emailNotificationService,
            final LoanContractRepository loanContractRepository,
            final PersonRepository personRepository) {
        this.emailNotificationService = emailNotificationService;
        this.loanContractRepository = loanContractRepository;
        this.personRepository = personRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("loanContractValues", loanContractRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(LoanContract::getId, LoanContract::getId)));
        model.addAttribute("personValues", personRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Person::getId, Person::getIdDoc)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("emailNotifications", emailNotificationService.findAll());
        return "emailNotification/list";
    }

    @GetMapping("/add")
    public String add(
            @ModelAttribute("emailNotification") final EmailNotificationDTO emailNotificationDTO) {
        return "emailNotification/add";
    }

    @PostMapping("/add")
    public String add(
            @ModelAttribute("emailNotification") @Valid final EmailNotificationDTO emailNotificationDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "emailNotification/add";
        }
        emailNotificationService.create(emailNotificationDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("emailNotification.create.success"));
        return "redirect:/emailNotifications";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id, final Model model) {
        model.addAttribute("emailNotification", emailNotificationService.get(id));
        return "emailNotification/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id,
            @ModelAttribute("emailNotification") @Valid final EmailNotificationDTO emailNotificationDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "emailNotification/edit";
        }
        emailNotificationService.update(id, emailNotificationDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("emailNotification.update.success"));
        return "redirect:/emailNotifications";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") final Long id,
            final RedirectAttributes redirectAttributes) {
        emailNotificationService.delete(id);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("emailNotification.delete.success"));
        return "redirect:/emailNotifications";
    }

}
