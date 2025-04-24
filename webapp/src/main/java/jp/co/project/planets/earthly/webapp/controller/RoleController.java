package jp.co.project.planets.earthly.webapp.controller;

import java.util.Collections;
import java.util.UUID;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.co.project.planets.earthly.schema.db.entity.Company;
import jp.co.project.planets.earthly.schema.db.entity.Permission;
import jp.co.project.planets.earthly.schema.db.entity.Role;
import jp.co.project.planets.earthly.schema.db.entity.User;
import jp.co.project.planets.earthly.webapp.constant.ModelKey;
import jp.co.project.planets.earthly.webapp.controller.form.role.RoleEditForm;
import jp.co.project.planets.earthly.webapp.controller.form.role.RoleEntryForm;
import jp.co.project.planets.earthly.webapp.controller.form.role.RoleSearchForm;
import jp.co.project.planets.earthly.webapp.security.dto.EarthlyUserInfoDto;

/**
 * ロールコントローラー
 */
@Controller
@RequestMapping("roles")
public class RoleController {

    @GetMapping
    public ModelAndView search(@ModelAttribute final RoleSearchForm roleSearchForm,
        @PageableDefault final Pageable pageable, @AuthenticationPrincipal final EarthlyUserInfoDto userInfoDto) {
        return new ModelAndView("roles/index").addObject(new PageImpl<Company>(Collections.emptyList()));
    }

    @GetMapping("entry")
    public ModelAndView entry(final Model model, @AuthenticationPrincipal final EarthlyUserInfoDto userInfoDto) {
        return new ModelAndView("roles/entry").addObject(new RoleEntryForm("", "")).addAllObjects(model.asMap());
    }

    @PostMapping("entry")
    public ModelAndView entry(@ModelAttribute @Validated final RoleEntryForm roleEntryForm,
        final BindingResult bindingResult, final Model model, final RedirectAttributes redirectAttributes,
        @AuthenticationPrincipal final EarthlyUserInfoDto userInfoDto) {
        redirectAttributes.addFlashAttribute(ModelKey.READ_ONLY, true);
        return new ModelAndView("redirect:/roles/entry");
    }

    @PostMapping("create")
    public ModelAndView create(@ModelAttribute @Validated final RoleEntryForm roleEntryForm,
        final BindingResult bindingResult, final Model model, final RedirectAttributes redirectAttributes,
        @AuthenticationPrincipal final EarthlyUserInfoDto userInfoDto) {
        return new ModelAndView("redirect:/roles/" + UUID.randomUUID().toString());
    }

    @GetMapping("{id}")
    public ModelAndView detail(@PathVariable final String id, final Model model,
        @AuthenticationPrincipal final EarthlyUserInfoDto userInfoDto) {
        return new ModelAndView("roles/detail") //
                .addObject(new Role("1", "サンプルロール", "サンプル概要", null, null, null, null, false))
                .addObject(new RoleEditForm("サンプルロール", ""))
                .addObject("managementUserPage", new PageImpl<User>(Collections.emptyList())) //
                .addObject("unassignedUserPage", new PageImpl<User>(Collections.emptyList())) //
                .addObject("assignPermissionPage", new PageImpl<Permission>(Collections.emptyList())) //
                .addObject("unassignedPermissionPage", new PageImpl<Permission>(Collections.emptyList()));
    }
}
