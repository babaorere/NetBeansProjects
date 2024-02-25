package com.isiweek.loan_contract;

import com.isiweek.lender.Lender;
import com.isiweek.lender.LenderRepository;
import com.isiweek.loan_status.LoanStatus;
import com.isiweek.loan_status.LoanStatusRepository;
import com.isiweek.loan_type.LoanType;
import com.isiweek.loan_type.LoanTypeRepository;
import com.isiweek.util.CustomCollectors;
import com.isiweek.util.ReferencedWarning;
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
@RequestMapping("/loanContracts")
public class LoanContractController {

    private final LoanContractService loanContractService;
    private final LenderRepository lenderRepository;
    private final LoanTypeRepository loanTypeRepository;
    private final LoanStatusRepository loanStatusRepository;

    public LoanContractController(final LoanContractService loanContractService,
            final LenderRepository lenderRepository, final LoanTypeRepository loanTypeRepository,
            final LoanStatusRepository loanStatusRepository) {
        this.loanContractService = loanContractService;
        this.lenderRepository = lenderRepository;
        this.loanTypeRepository = loanTypeRepository;
        this.loanStatusRepository = loanStatusRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("customerValues", lenderRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Lender::getId, Lender::getObservations)));
        model.addAttribute("loanTypeValues", loanTypeRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(LoanType::getId, LoanType::getName)));
        model.addAttribute("loanStatusValues", loanStatusRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(LoanStatus::getId, LoanStatus::getName)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("loanContracts", loanContractService.findAll());
        return "loanContract/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("loanContract") final LoanContractDTO loanContractDTO) {
        return "loanContract/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("loanContract") @Valid final LoanContractDTO loanContractDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "loanContract/add";
        }
        loanContractService.create(loanContractDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("loanContract.create.success"));
        return "redirect:/loanContracts";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id, final Model model) {
        model.addAttribute("loanContract", loanContractService.get(id));
        return "loanContract/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id,
            @ModelAttribute("loanContract") @Valid final LoanContractDTO loanContractDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "loanContract/edit";
        }
        loanContractService.update(id, loanContractDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("loanContract.update.success"));
        return "redirect:/loanContracts";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") final Long id,
            final RedirectAttributes redirectAttributes) {
        final ReferencedWarning referencedWarning = loanContractService.getReferencedWarning(id);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR,
                    WebUtils.getMessage(referencedWarning.getKey(), referencedWarning.getParams().toArray()));
        } else {
            loanContractService.delete(id);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("loanContract.delete.success"));
        }
        return "redirect:/loanContracts";
    }

}
