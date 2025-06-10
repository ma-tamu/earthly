package jp.co.project.planets.earthly.webapp.controller.system;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import jp.co.project.planets.earthly.webapp.controller.form.system.permission.PermissionSearchForm;
import jp.co.project.planets.earthly.webapp.security.dto.EarthlyUserInfoDto;
import jp.co.project.planets.earthly.webapp.service.PermissionService;

/**
 * パーミッションコントローラー
 */
@Controller
@RequestMapping("systems/permissions")
public class PermissionController {

    private final PermissionService permissionService;

    public PermissionController(final PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @GetMapping
    public ModelAndView search(@ModelAttribute final PermissionSearchForm permissionSearchForm,
        @PageableDefault final Pageable pageable, @AuthenticationPrincipal final EarthlyUserInfoDto userInfoDto) {
        final var permissionPage = permissionService.search(permissionSearchForm.name(), pageable,
                userInfoDto.account());
        return new ModelAndView("systems/permissions/index").addObject(permissionPage);
    }
}
