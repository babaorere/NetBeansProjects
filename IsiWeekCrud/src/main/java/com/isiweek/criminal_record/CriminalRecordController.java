package com.isiweek.criminal_record;

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
@RequestMapping("/criminalRecords")
public class CriminalRecordController {

    private final CriminalRecordService criminalRecordService;

    public CriminalRecordController(final CriminalRecordService criminalRecordService) {
        this.criminalRecordService = criminalRecordService;
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("criminalRecords", criminalRecordService.findAll());
        return "criminalRecord/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("criminalRecord") final CriminalRecordDTO criminalRecordDTO) {
        return "criminalRecord/add";
    }

    @PostMapping("/add")
    public String add(
            @ModelAttribute("criminalRecord") @Valid final CriminalRecordDTO criminalRecordDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "criminalRecord/add";
        }
        criminalRecordService.create(criminalRecordDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("criminalRecord.create.success"));
        return "redirect:/criminalRecords";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id, final Model model) {
        model.addAttribute("criminalRecord", criminalRecordService.get(id));
        return "criminalRecord/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id,
            @ModelAttribute("criminalRecord") @Valid final CriminalRecordDTO criminalRecordDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "criminalRecord/edit";
        }
        criminalRecordService.update(id, criminalRecordDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("criminalRecord.update.success"));
        return "redirect:/criminalRecords";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") final Long id,
            final RedirectAttributes redirectAttributes) {
        final ReferencedWarning referencedWarning = criminalRecordService.getReferencedWarning(id);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR,
                    WebUtils.getMessage(referencedWarning.getKey(), referencedWarning.getParams().toArray()));
        } else {
            criminalRecordService.delete(id);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("criminalRecord.delete.success"));
        }
        return "redirect:/criminalRecords";
    }

}
