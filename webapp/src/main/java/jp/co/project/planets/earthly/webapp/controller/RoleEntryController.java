package jp.co.project.planets.earthly.webapp.controller;

import static jp.co.project.planets.earthly.webapp.constant.ViewName.*;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.co.project.planets.earthly.webapp.constant.ModelKey;
import jp.co.project.planets.earthly.webapp.controller.form.role.RoleEntryForm;
import jp.co.project.planets.earthly.webapp.security.dto.EarthlyUserInfoDto;
import jp.co.project.planets.earthly.webapp.service.RoleService;

@Controller
@RequestMapping("roles")
public class RoleEntryController {

    private final RoleService roleService;

    public RoleEntryController(final RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("entries")
    public ModelAndView entry(final Model model, @AuthenticationPrincipal final EarthlyUserInfoDto userInfoDto) {
        final var roleEntryForm = new RoleEntryForm(null, null, null);
        return new ModelAndView("roles/entry").addObject(roleEntryForm).addAllObjects(model.asMap());
    }

    @PostMapping("entries")
    public ModelAndView entry(@ModelAttribute @Validated final RoleEntryForm roleEntryForm,
        final BindingResult bindingResult, final Model model, final RedirectAttributes redirectAttributes,
        @AuthenticationPrincipal final EarthlyUserInfoDto userInfoDto) {

        final var modelAndView = new ModelAndView(REDIRECT_COMPANY_ENTRY);
        model.asMap().forEach(redirectAttributes::addFlashAttribute);

        if (bindingResult.hasErrors()) {
            return modelAndView;
        }

        roleService.validateEntryOperation(userInfoDto.account());
        redirectAttributes.addFlashAttribute(ModelKey.READ_ONLY, true);
        return new ModelAndView("redirect:/roles/entries");
    }

    @PostMapping("create")
    public ModelAndView create(@ModelAttribute @Validated final RoleEntryForm roleEntryForm,
        final BindingResult bindingResult, final Model model, final RedirectAttributes redirectAttributes,
        @AuthenticationPrincipal final EarthlyUserInfoDto userInfoDto) {

        model.asMap().forEach(redirectAttributes::addFlashAttribute);

        if (bindingResult.hasErrors()) {
            return new ModelAndView(REDIRECT_COMPANY_ENTRY);
        }

        final var id = roleService.create(roleEntryForm.toDto(), userInfoDto.account());
        return new ModelAndView("redirect:/roles/" + id);
    }

}
