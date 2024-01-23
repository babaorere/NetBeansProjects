package com.isiweek.payment_type.controller;

import com.isiweek.payment_type.model.PaymentTypeDTO;
import com.isiweek.payment_type.service.PaymentTypeService;
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
@RequestMapping("/paymentTypes")
public class PaymentTypeController {

    private final PaymentTypeService paymentTypeService;

    public PaymentTypeController(final PaymentTypeService paymentTypeService) {
        this.paymentTypeService = paymentTypeService;
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("paymentTypes", paymentTypeService.findAll());
        return "paymentType/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("paymentType") final PaymentTypeDTO paymentTypeDTO) {
        return "paymentType/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("paymentType") @Valid final PaymentTypeDTO paymentTypeDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (!bindingResult.hasFieldErrors("name") && paymentTypeService.nameExists(paymentTypeDTO.getName())) {
            bindingResult.rejectValue("name", "Exists.paymentType.name");
        }
        if (bindingResult.hasErrors()) {
            return "paymentType/add";
        }
        paymentTypeService.create(paymentTypeDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("paymentType.create.success"));
        return "redirect:/paymentTypes";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id, final Model model) {
        model.addAttribute("paymentType", paymentTypeService.get(id));
        return "paymentType/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id,
            @ModelAttribute("paymentType") @Valid final PaymentTypeDTO paymentTypeDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        final PaymentTypeDTO currentPaymentTypeDTO = paymentTypeService.get(id);
        if (!bindingResult.hasFieldErrors("name") &&
                !paymentTypeDTO.getName().equalsIgnoreCase(currentPaymentTypeDTO.getName()) &&
                paymentTypeService.nameExists(paymentTypeDTO.getName())) {
            bindingResult.rejectValue("name", "Exists.paymentType.name");
        }
        if (bindingResult.hasErrors()) {
            return "paymentType/edit";
        }
        paymentTypeService.update(id, paymentTypeDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("paymentType.update.success"));
        return "redirect:/paymentTypes";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") final Long id,
            final RedirectAttributes redirectAttributes) {
        final String referencedWarning = paymentTypeService.getReferencedWarning(id);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR, referencedWarning);
        } else {
            paymentTypeService.delete(id);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("paymentType.delete.success"));
        }
        return "redirect:/paymentTypes";
    }

}
