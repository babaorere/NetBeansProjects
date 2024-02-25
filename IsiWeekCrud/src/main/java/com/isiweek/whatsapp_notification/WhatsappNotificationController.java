package com.isiweek.whatsapp_notification;

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
@RequestMapping("/whatsappNotifications")
public class WhatsappNotificationController {

    private final WhatsappNotificationService whatsappNotificationService;
    private final LoanContractRepository loanContractRepository;
    private final PersonRepository personRepository;

    public WhatsappNotificationController(
            final WhatsappNotificationService whatsappNotificationService,
            final LoanContractRepository loanContractRepository,
            final PersonRepository personRepository) {
        this.whatsappNotificationService = whatsappNotificationService;
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
        model.addAttribute("whatsappNotifications", whatsappNotificationService.findAll());
        return "whatsappNotification/list";
    }

    @GetMapping("/add")
    public String add(
            @ModelAttribute("whatsappNotification") final WhatsappNotificationDTO whatsappNotificationDTO) {
        return "whatsappNotification/add";
    }

    @PostMapping("/add")
    public String add(
            @ModelAttribute("whatsappNotification") @Valid final WhatsappNotificationDTO whatsappNotificationDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "whatsappNotification/add";
        }
        whatsappNotificationService.create(whatsappNotificationDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("whatsappNotification.create.success"));
        return "redirect:/whatsappNotifications";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id, final Model model) {
        model.addAttribute("whatsappNotification", whatsappNotificationService.get(id));
        return "whatsappNotification/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id,
            @ModelAttribute("whatsappNotification") @Valid final WhatsappNotificationDTO whatsappNotificationDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "whatsappNotification/edit";
        }
        whatsappNotificationService.update(id, whatsappNotificationDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("whatsappNotification.update.success"));
        return "redirect:/whatsappNotifications";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") final Long id,
            final RedirectAttributes redirectAttributes) {
        whatsappNotificationService.delete(id);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("whatsappNotification.delete.success"));
        return "redirect:/whatsappNotifications";
    }

}
