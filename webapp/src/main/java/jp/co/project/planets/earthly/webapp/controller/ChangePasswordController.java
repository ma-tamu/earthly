package jp.co.project.planets.earthly.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jp.co.project.planets.earthly.webapp.controller.form.user.ChangePasswordForm;

@Controller
@RequestMapping("changes/passwords")
public class ChangePasswordController {

    @GetMapping("{userId}")
    public ModelAndView get(@PathVariable("userId") final String userId, @RequestParam("token") final String token) {
        final var modelAndView = new ModelAndView("passwords/changePassword");
        modelAndView.addObject("userId", userId);
        return modelAndView;
    }

    @PostMapping("{userId}")
    private ModelAndView updatePassword(@PathVariable("userId") final String userId,
            @ModelAttribute @Validated final ChangePasswordForm form, final BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            new ModelAndView("redirect:/changes/passwords/" + userId);
        }
        return new ModelAndView("redirect:/login");
    }
}
