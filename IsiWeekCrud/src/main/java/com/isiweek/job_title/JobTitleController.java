package com.isiweek.job_title;

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
@RequestMapping("/jobTitles")
public class JobTitleController {

    private final JobTitleService jobTitleService;

    public JobTitleController(final JobTitleService jobTitleService) {
        this.jobTitleService = jobTitleService;
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("jobTitles", jobTitleService.findAll());
        return "jobTitle/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("jobTitle") final JobTitleDTO jobTitleDTO) {
        return "jobTitle/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("jobTitle") @Valid final JobTitleDTO jobTitleDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "jobTitle/add";
        }
        jobTitleService.create(jobTitleDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("jobTitle.create.success"));
        return "redirect:/jobTitles";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id, final Model model) {
        model.addAttribute("jobTitle", jobTitleService.get(id));
        return "jobTitle/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id,
            @ModelAttribute("jobTitle") @Valid final JobTitleDTO jobTitleDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "jobTitle/edit";
        }
        jobTitleService.update(id, jobTitleDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("jobTitle.update.success"));
        return "redirect:/jobTitles";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") final Long id,
            final RedirectAttributes redirectAttributes) {
        final ReferencedWarning referencedWarning = jobTitleService.getReferencedWarning(id);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR,
                    WebUtils.getMessage(referencedWarning.getKey(), referencedWarning.getParams().toArray()));
        } else {
            jobTitleService.delete(id);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("jobTitle.delete.success"));
        }
        return "redirect:/jobTitles";
    }

}
