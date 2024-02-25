package com.isiweek.payment_status;

import com.isiweek.util.ReferencedWarning;
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
@RequestMapping("/paymentStatuses")
public class PaymentStatusController {

    private final PaymentStatusService paymentStatusService;

    public PaymentStatusController(final PaymentStatusService paymentStatusService) {
        this.paymentStatusService = paymentStatusService;
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("paymentStatuses", paymentStatusService.findAll());
        return "paymentStatus/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("paymentStatus") final PaymentStatusDTO paymentStatusDTO) {
        return "paymentStatus/add";
    }

    @PostMapping("/add")
    public String add(
            @ModelAttribute("paymentStatus") @Valid final PaymentStatusDTO paymentStatusDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "paymentStatus/add";
        }
        paymentStatusService.create(paymentStatusDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("paymentStatus.create.success"));
        return "redirect:/paymentStatuses";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id, final Model model) {
        model.addAttribute("paymentStatus", paymentStatusService.get(id));
        return "paymentStatus/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id,
            @ModelAttribute("paymentStatus") @Valid final PaymentStatusDTO paymentStatusDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "paymentStatus/edit";
        }
        paymentStatusService.update(id, paymentStatusDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("paymentStatus.update.success"));
        return "redirect:/paymentStatuses";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") final Long id,
            final RedirectAttributes redirectAttributes) {
        final ReferencedWarning referencedWarning = paymentStatusService.getReferencedWarning(id);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR,
                    WebUtils.getMessage(referencedWarning.getKey(), referencedWarning.getParams().toArray()));
        } else {
            paymentStatusService.delete(id);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("paymentStatus.delete.success"));
        }
        return "redirect:/paymentStatuses";
    }

}
