package jp.co.project.planets.earthly.webapp.controller;

import static jp.co.project.planets.earthly.webapp.constant.ModelKey.*;
import static jp.co.project.planets.earthly.webapp.constant.ViewName.*;

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

import jp.co.project.planets.earthly.webapp.constant.MessageKey;
import jp.co.project.planets.earthly.webapp.controller.form.role.RoleAssignForm;
import jp.co.project.planets.earthly.webapp.controller.form.role.RoleAssignedPermissionSearchForm;
import jp.co.project.planets.earthly.webapp.controller.form.role.RoleEditForm;
import jp.co.project.planets.earthly.webapp.controller.form.role.RoleGrantForm;
import jp.co.project.planets.earthly.webapp.controller.form.role.RoleGrantedUserSearchForm;
import jp.co.project.planets.earthly.webapp.controller.form.role.RoleNotGrantUserSearchForm;
import jp.co.project.planets.earthly.webapp.controller.form.role.RoleRevokeForm;
import jp.co.project.planets.earthly.webapp.controller.form.role.RoleUnassignForm;
import jp.co.project.planets.earthly.webapp.controller.form.role.RoleUnassignedPermissionSearchForm;
import jp.co.project.planets.earthly.webapp.emuns.ErrorCode;
import jp.co.project.planets.earthly.webapp.exception.BadRequestException;
import jp.co.project.planets.earthly.webapp.exception.ForbiddenException;
import jp.co.project.planets.earthly.webapp.exception.NotFoundException;
import jp.co.project.planets.earthly.webapp.security.dto.EarthlyUserInfoDto;
import jp.co.project.planets.earthly.webapp.service.RoleService;

@Controller
@RequestMapping("roles/{id}")
public class RoleDetailController {

    private final RoleService roleService;

    public RoleDetailController(final RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public ModelAndView index(@PathVariable("id") final String id, final Model model,
        @AuthenticationPrincipal final EarthlyUserInfoDto userInfoDto) {

        final var detail = roleService.getDetail(id, userInfoDto.account());
        final var role = detail.role();
        final var roleEditForm = new RoleEditForm(role.getName(), role.getDescription(), role.getGrantable());
        final var roleGrantedUserSearchForm = new RoleGrantedUserSearchForm(null, null, null, false);
        final var roleAssignedPermissionSearchForm = new RoleAssignedPermissionSearchForm(null, false);
        return new ModelAndView("roles/detail") //
                .addObject(role).addObject(roleEditForm).addObject(roleGrantedUserSearchForm)
                .addObject(roleAssignedPermissionSearchForm)
                .addObject("grantedUserPage", detail.grantedUserPage()) //
                .addObject("notGrantedUserPage", detail.notGrantedUserPage()) //
                .addObject("assignPermissionPage", detail.assignedPermissionPage()) //
                .addObject("unassignedPermissionPage", detail.unassignedPermissionPage())
                .addAllObjects(model.asMap());
    }

    @PostMapping("edit")
    public ModelAndView edit(@PathVariable("id") final String id,
        @ModelAttribute @Validated final RoleEditForm roleEditForm, final BindingResult bindingResult,
        final RedirectAttributes redirectAttributes, final Model model,
        @AuthenticationPrincipal final EarthlyUserInfoDto userInfoDto) {

        final var modelAndView = new ModelAndView("redirect:/roles/" + id);
        model.asMap().forEach(redirectAttributes::addFlashAttribute);

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute(EDIT_MODAL, true);
            return modelAndView;
        }

        try {
            roleService.validateEditOperation(id, userInfoDto.account());
            redirectAttributes.addFlashAttribute(READ_ONLY, true);
        } catch (final BadRequestException | ForbiddenException e) {
            model.addAttribute(MESSAGE, e.getErrorCode().getMessageKey());
            model.addAttribute(MESSAGE_ARGS, e.getMessageKeyArgs());
        }
        return modelAndView;
    }

    @PostMapping("update")
    public ModelAndView update(@PathVariable("id") final String id,
        @ModelAttribute @Validated final RoleEditForm roleEditForm, final BindingResult bindingResult,
        final RedirectAttributes redirectAttributes, final Model model,
        @AuthenticationPrincipal final EarthlyUserInfoDto userInfoDto) {

        final var modelAndView = new ModelAndView("redirect:/roles/%s".formatted(id));
        model.asMap().forEach(redirectAttributes::addFlashAttribute);

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute(EDIT_MODAL, true);
            return modelAndView;
        }

        try {
            final var message = roleService.update(id, roleEditForm.toDto(), userInfoDto.account());
            redirectAttributes.addFlashAttribute(SUCCESS, message);
        } catch (final BadRequestException | ForbiddenException e) {
            model.addAttribute(MESSAGE, e.getErrorCode().getMessageKey());
            model.addAttribute(MESSAGE_ARGS, e.getMessageKeyArgs());
        }
        return modelAndView;
    }

    @PostMapping("delete")
    public ModelAndView delete(@PathVariable("id") final String id, final RedirectAttributes redirectAttributes,
        final Model model, @AuthenticationPrincipal final EarthlyUserInfoDto userInfoDto) {

        try {
            roleService.delete(id, userInfoDto.account());
            redirectAttributes.addFlashAttribute(SUCCESS, MessageKey.DELETE_SUCCESS);
        } catch (final BadRequestException | ForbiddenException e) {
            model.asMap().forEach(redirectAttributes::addFlashAttribute);
            return new ModelAndView("redirect:/roles/%s".formatted(id));
        }

        return new ModelAndView("redirect:/roles");
    }

    @GetMapping("granted-users")
    public ModelAndView searchGrantedUser(@PathVariable("id") final String id,
        final RoleGrantedUserSearchForm roleGrantedUserSearchForm, @PageableDefault final Pageable pageable,
        @AuthenticationPrincipal final EarthlyUserInfoDto userInfoDto) {

        final var grantedUserPage = roleService.searchGrantedUser(id, roleGrantedUserSearchForm.loginId(),
                roleGrantedUserSearchForm.name(), roleGrantedUserSearchForm.companyName(), pageable,
                userInfoDto.account());
        return new ModelAndView("roles/detail::grantedUserContent", "grantedUserPage", grantedUserPage);
    }

    @GetMapping("not-grant-users")
    public ModelAndView searchNotGrantUser(@PathVariable("id") final String id,
        final RoleNotGrantUserSearchForm notGrantUserSearchForm, @PageableDefault final Pageable pageable,
        @AuthenticationPrincipal final EarthlyUserInfoDto userInfoDto) {
        final var notGantUserPage = roleService.searchNotGrantUser(id, notGrantUserSearchForm.loginId(),
                notGrantUserSearchForm.name(), notGrantUserSearchForm.companyName(), pageable,
                userInfoDto.account());

        return new ModelAndView("roles/modal::notGrantedUserPage", "notGrantedUserPage", notGantUserPage);
    }

    @PostMapping("grants")
    public ModelAndView grant(@PathVariable("id") final String id,
        @ModelAttribute @Validated final RoleGrantForm roleGrantForm, final BindingResult bindingResult,
        @AuthenticationPrincipal final EarthlyUserInfoDto userInfoDto) {

        if (bindingResult.hasErrors()) {
            return new ModelAndView(TOAST_DANGER).addObject(MESSAGE, ErrorCode.EWA5XX999.getMessageKey());
        }

        try {
            roleService.grant(id, roleGrantForm.userId(), userInfoDto.account());
            return new ModelAndView(TOAST_SUCCESS).addObject(MESSAGE, MessageKey.UNASSIGN_SUCCESS);
        } catch (final ForbiddenException | NotFoundException e) {
            return new ModelAndView(TOAST_DANGER).addObject(MESSAGE, e.getErrorCode().getMessageKey())
                    .addObject(MESSAGE_ARGS, e.getMessageKeyArgs());
        }
    }

    @PostMapping("revoke")
    public ModelAndView revoke(@PathVariable("id") final String id,
        @ModelAttribute @Validated final RoleRevokeForm roleRevokeForm, final BindingResult bindingResult,
        @AuthenticationPrincipal final EarthlyUserInfoDto userInfoDto) {

        if (bindingResult.hasErrors()) {
            return new ModelAndView(TOAST_DANGER).addObject(MESSAGE, ErrorCode.EWA5XX999.getMessageKey());
        }

        try {
            roleService.revoke(id, roleRevokeForm.userId(), userInfoDto.account());
            return new ModelAndView(TOAST_SUCCESS).addObject(MESSAGE, MessageKey.UNASSIGN_SUCCESS);
        } catch (final ForbiddenException | NotFoundException e) {
            return new ModelAndView(TOAST_DANGER).addObject(MESSAGE, e.getErrorCode().getMessageKey())
                    .addObject(MESSAGE_ARGS, e.getMessageKeyArgs());
        }
    }

    @GetMapping("assigned-permissions")
    public ModelAndView searchAssignedPermission(@PathVariable("id") final String id,
        final RoleAssignedPermissionSearchForm roleAssignedPermissionSearchForm,
        @PageableDefault final Pageable pageable, @AuthenticationPrincipal final EarthlyUserInfoDto userInfoDto) {
        final var assignedPermissionPage = roleService.searchAssignedPermission(id,
                roleAssignedPermissionSearchForm.permissionName(), pageable, userInfoDto.account());
        return new ModelAndView("roles/detail::assignPermissionContent", "assignPermissionPage",
                assignedPermissionPage);
    }

    @GetMapping("unassign-permissions")
    public ModelAndView searchUnassignedPermission(@PathVariable("id") final String id,
        final RoleUnassignedPermissionSearchForm roleUnassignedPermissionSearchForm,
        @PageableDefault final Pageable pageable, @AuthenticationPrincipal final EarthlyUserInfoDto userInfoDto) {

        final var permissionPage = roleService.searchUnassignPermission(id,
                roleUnassignedPermissionSearchForm.permissionName(), pageable, userInfoDto.account());
        return new ModelAndView("roles/modal::unassignedPermissionPage", "unassignedPermissionPage", permissionPage);
    }

    @PostMapping("assign-permissions")
    public ModelAndView assign(@PathVariable("id") final String id,
        @ModelAttribute @Validated final RoleAssignForm roleAssignForm, final BindingResult bindingResult,
        @AuthenticationPrincipal final EarthlyUserInfoDto userInfoDto) {

        if (bindingResult.hasErrors()) {
            return new ModelAndView(TOAST_DANGER).addObject(MESSAGE, ErrorCode.EWA5XX999.getMessageKey());
        }

        try {
            roleService.assign(id, roleAssignForm.permissionId(), userInfoDto.account());
            return new ModelAndView(TOAST_SUCCESS).addObject(MESSAGE, MessageKey.ASSIGN_SUCCESS);
        } catch (final ForbiddenException | NotFoundException e) {
            return new ModelAndView(TOAST_DANGER).addObject(MESSAGE, e.getErrorCode().getMessageKey())
                    .addObject(MESSAGE_ARGS, e.getMessageKeyArgs());
        }
    }

    @PostMapping("unassign-permissions")
    public ModelAndView unassign(@PathVariable("id") final String id,
        @ModelAttribute @Validated final RoleUnassignForm roleUnassignForm, final BindingResult bindingResult,
        @AuthenticationPrincipal final EarthlyUserInfoDto userInfoDto) {

        if (bindingResult.hasErrors()) {
            return new ModelAndView(TOAST_DANGER).addObject(MESSAGE, ErrorCode.EWA5XX999.getMessageKey());
        }

        try {
            roleService.unassign(id, roleUnassignForm.permissionId(), userInfoDto.account());
            return new ModelAndView(TOAST_SUCCESS).addObject(MESSAGE, MessageKey.UNASSIGN_SUCCESS);
        } catch (final BadRequestException e) {
            return new ModelAndView(TOAST_DANGER).addObject(MESSAGE, e.getErrorCode().getMessageKey())
                    .addObject(MESSAGE_ARGS, e.getMessageKeyArgs());
        }
    }
}
