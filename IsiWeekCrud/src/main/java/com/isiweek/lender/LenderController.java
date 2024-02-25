package com.isiweek.lender;

import com.isiweek.person.Person;
import com.isiweek.person.PersonRepository;
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
@RequestMapping("/lenders")
public class LenderController {

    private final LenderService lenderService;
    private final PersonRepository personRepository;

    public LenderController(final LenderService lenderService,
            final PersonRepository personRepository) {
        this.lenderService = lenderService;
        this.personRepository = personRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("personValues", personRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Person::getId, Person::getIdDoc)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("lenders", lenderService.findAll());
        return "lender/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("lender") final LenderDTO lenderDTO) {
        return "lender/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("lender") @Valid final LenderDTO lenderDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "lender/add";
        }
        lenderService.create(lenderDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("lender.create.success"));
        return "redirect:/lenders";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id, final Model model) {
        model.addAttribute("lender", lenderService.get(id));
        return "lender/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id,
            @ModelAttribute("lender") @Valid final LenderDTO lenderDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "lender/edit";
        }
        lenderService.update(id, lenderDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("lender.update.success"));
        return "redirect:/lenders";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") final Long id,
            final RedirectAttributes redirectAttributes) {
        final ReferencedWarning referencedWarning = lenderService.getReferencedWarning(id);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR,
                    WebUtils.getMessage(referencedWarning.getKey(), referencedWarning.getParams().toArray()));
        } else {
            lenderService.delete(id);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("lender.delete.success"));
        }
        return "redirect:/lenders";
    }

}
