package com.isiweek.payment_datails.controller;

import com.isiweek.loan_contract.domain.LoanContract;
import com.isiweek.loan_contract.repos.LoanContractRepository;
import com.isiweek.payment_datails.model.PaymentDatailsDTO;
import com.isiweek.payment_datails.service.PaymentDatailsService;
import com.isiweek.payment_status.domain.PaymentStatus;
import com.isiweek.payment_status.repos.PaymentStatusRepository;
import com.isiweek.payment_type.domain.PaymentType;
import com.isiweek.payment_type.repos.PaymentTypeRepository;
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
@RequestMapping("/paymentDatailss")
public class PaymentDatailsController {

    private final PaymentDatailsService paymentDatailsService;
    private final LoanContractRepository loanContractRepository;
    private final PaymentTypeRepository paymentTypeRepository;
    private final PaymentStatusRepository paymentStatusRepository;

    public PaymentDatailsController(final PaymentDatailsService paymentDatailsService,
            final LoanContractRepository loanContractRepository,
            final PaymentTypeRepository paymentTypeRepository,
            final PaymentStatusRepository paymentStatusRepository) {
        this.paymentDatailsService = paymentDatailsService;
        this.loanContractRepository = loanContractRepository;
        this.paymentTypeRepository = paymentTypeRepository;
        this.paymentStatusRepository = paymentStatusRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("loanContractValues", loanContractRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(LoanContract::getId, LoanContract::getCollateral)));
        model.addAttribute("paymentTypeValues", paymentTypeRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(PaymentType::getId, PaymentType::getName)));
        model.addAttribute("paymentStatusValues", paymentStatusRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(PaymentStatus::getId, PaymentStatus::getName)));
        model.addAttribute("loanContractValues", loanContractRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(LoanContract::getId, LoanContract::getCollateral)));
        model.addAttribute("paymentTypeValues", paymentTypeRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(PaymentType::getId, PaymentType::getName)));
        model.addAttribute("paymentStatusValues", paymentStatusRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(PaymentStatus::getId, PaymentStatus::getName)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("paymentDatailses", paymentDatailsService.findAll());
        return "paymentDatails/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("paymentDatails") final PaymentDatailsDTO paymentDatailsDTO) {
        return "paymentDatails/add";
    }

    @PostMapping("/add")
    public String add(
            @ModelAttribute("paymentDatails") @Valid final PaymentDatailsDTO paymentDatailsDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "paymentDatails/add";
        }
        paymentDatailsService.create(paymentDatailsDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("paymentDatails.create.success"));
        return "redirect:/paymentDatailss";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id, final Model model) {
        model.addAttribute("paymentDatails", paymentDatailsService.get(id));
        return "paymentDatails/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id,
            @ModelAttribute("paymentDatails") @Valid final PaymentDatailsDTO paymentDatailsDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "paymentDatails/edit";
        }
        paymentDatailsService.update(id, paymentDatailsDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("paymentDatails.update.success"));
        return "redirect:/paymentDatailss";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") final Long id,
            final RedirectAttributes redirectAttributes) {
        paymentDatailsService.delete(id);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("paymentDatails.delete.success"));
        return "redirect:/paymentDatailss";
    }

}
