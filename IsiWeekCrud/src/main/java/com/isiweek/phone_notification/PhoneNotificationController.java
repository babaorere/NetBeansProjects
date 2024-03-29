package com.isiweek.phone_notification;

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
@RequestMapping("/phoneNotifications")
public class PhoneNotificationController {

    private final PhoneNotificationService phoneNotificationService;
    private final LoanContractRepository loanContractRepository;
    private final PersonRepository personRepository;

    public PhoneNotificationController(final PhoneNotificationService phoneNotificationService,
            final LoanContractRepository loanContractRepository,
            final PersonRepository personRepository) {
        this.phoneNotificationService = phoneNotificationService;
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
        model.addAttribute("phoneNotifications", phoneNotificationService.findAll());
        return "phoneNotification/list";
    }

    @GetMapping("/add")
    public String add(
            @ModelAttribute("phoneNotification") final PhoneNotificationDTO phoneNotificationDTO) {
        return "phoneNotification/add";
    }

    @PostMapping("/add")
    public String add(
            @ModelAttribute("phoneNotification") @Valid final PhoneNotificationDTO phoneNotificationDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "phoneNotification/add";
        }
        phoneNotificationService.create(phoneNotificationDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("phoneNotification.create.success"));
        return "redirect:/phoneNotifications";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id, final Model model) {
        model.addAttribute("phoneNotification", phoneNotificationService.get(id));
        return "phoneNotification/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id,
            @ModelAttribute("phoneNotification") @Valid final PhoneNotificationDTO phoneNotificationDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "phoneNotification/edit";
        }
        phoneNotificationService.update(id, phoneNotificationDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("phoneNotification.update.success"));
        return "redirect:/phoneNotifications";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") final Long id,
            final RedirectAttributes redirectAttributes) {
        phoneNotificationService.delete(id);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("phoneNotification.delete.success"));
        return "redirect:/phoneNotifications";
    }

}
