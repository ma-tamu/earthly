package jp.co.project.planets.earthly.webapp.controller.system;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import jp.co.project.planets.earthly.webapp.constant.MessageKey;
import jp.co.project.planets.earthly.webapp.controller.form.system.permission.PermissionAssignRoleForm;
import jp.co.project.planets.earthly.webapp.controller.form.system.permission.PermissionAssignedRoleSearchForm;
import jp.co.project.planets.earthly.webapp.controller.form.system.permission.PermissionUnassignRoleForm;
import jp.co.project.planets.earthly.webapp.controller.form.system.permission.PermissionUnassignedRoleSearchForm;
import jp.co.project.planets.earthly.webapp.emuns.ErrorCode;
import jp.co.project.planets.earthly.webapp.exception.ForbiddenException;
import jp.co.project.planets.earthly.webapp.exception.NotFoundException;
import jp.co.project.planets.earthly.webapp.security.dto.EarthlyUserInfoDto;
import jp.co.project.planets.earthly.webapp.service.PermissionService;

@Controller
@RequestMapping("systems/permissions/{id}")
public class PermissionDetailController {

    private final PermissionService permissionService;

    public PermissionDetailController(final PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @GetMapping
    public ModelAndView index(@PathVariable final String id, final Model model,
        @AuthenticationPrincipal final EarthlyUserInfoDto userInfoDto) {
        final var permissionDetailDto = permissionService.getDetails(id, userInfoDto.account());
        final var permissionAssignedRoleSearchForm = new PermissionAssignedRoleSearchForm(null, false);
        return new ModelAndView("systems/permissions/detail") //
                .addObject(permissionDetailDto.permission()).addObject(permissionAssignedRoleSearchForm)
                .addObject("assignRolePage", permissionDetailDto.assignedRolePage()) //
                .addObject("unassignedRolePage", permissionDetailDto.unassignedRolePage());
    }

    @GetMapping("assigned-permissions")
    public ModelAndView searchAssignedRole(@PathVariable final String id,
        final PermissionAssignedRoleSearchForm permissionAssignedRoleSearchForm,
        @PageableDefault final Pageable pageable, @AuthenticationPrincipal final EarthlyUserInfoDto userInfoDto) {
        final var assignRolePage = permissionService.searchAssignedRole(id, permissionAssignedRoleSearchForm.roleName(),
                pageable, userInfoDto.account());
        return new ModelAndView("systems/permissions/detail::assignedRoleContent", "assignRolePage", assignRolePage);
    }

    @PostMapping("unassign")
    public ModelAndView unassign(@PathVariable final String id,
        @Validated final PermissionUnassignRoleForm permissionUnassignRoleForm, final BindingResult bindingResult,
        @AuthenticationPrincipal final EarthlyUserInfoDto userInfoDto) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView(TOAST_DANGER).addObject(MESSAGE, ErrorCode.EWA5XX999.getMessageKey());
        }

        try {
            permissionService.unassign(id, permissionUnassignRoleForm.roleId(), userInfoDto.account());
            return new ModelAndView(TOAST_SUCCESS).addObject(MESSAGE, MessageKey.UNASSIGN_SUCCESS);
        } catch (final ForbiddenException | NotFoundException e) {
            return new ModelAndView(TOAST_DANGER).addObject(MESSAGE, e.getErrorCode().getMessageKey())
                    .addObject(MESSAGE_ARGS, e.getMessageKeyArgs());
        }
    }

    @GetMapping("unassigned-roles")
    public ModelAndView searchUnassignedRole(@PathVariable final String id,
        final PermissionUnassignedRoleSearchForm permissionUnassignedRoleSearchForm,
        @PageableDefault final Pageable pageable, @AuthenticationPrincipal final EarthlyUserInfoDto userInfoDto) {
        final var unassignRolePage = permissionService.searchUnassignedRole(id,
                permissionUnassignedRoleSearchForm.roleName(), pageable, userInfoDto.account());
        return new ModelAndView("systems/permissions/modal::unassignedRoleContent", "unassignedRolePage",
                unassignRolePage);
    }

    @PostMapping("assign")
    public ModelAndView assign(@PathVariable final String id,
        @Validated final PermissionAssignRoleForm permissionAssignRoleForm, final BindingResult bindingResult,
        @AuthenticationPrincipal final EarthlyUserInfoDto userInfoDto) {

        if (bindingResult.hasErrors()) {
            return new ModelAndView(TOAST_DANGER).addObject(MESSAGE, ErrorCode.EWA5XX999.getMessageKey());
        }

        try {
            permissionService.assign(id, permissionAssignRoleForm.roleId(), userInfoDto.account());
            return new ModelAndView(TOAST_SUCCESS).addObject(MESSAGE, MessageKey.UNASSIGN_SUCCESS);
        } catch (final ForbiddenException | NotFoundException e) {
            return new ModelAndView(TOAST_DANGER).addObject(MESSAGE, e.getErrorCode().getMessageKey())
                    .addObject(MESSAGE_ARGS, e.getMessageKeyArgs());
        }
    }
}
