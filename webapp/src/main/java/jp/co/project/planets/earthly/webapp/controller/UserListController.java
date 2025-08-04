package jp.co.project.planets.earthly.webapp.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import jp.co.project.planets.earthly.webapp.controller.form.user.UserSearchForm;
import jp.co.project.planets.earthly.webapp.model.dto.UserSearchDto;
import jp.co.project.planets.earthly.webapp.security.dto.EarthlyUserInfoDto;
import jp.co.project.planets.earthly.webapp.service.UserService;

/**
 * user controller
 */
@Controller
@RequestMapping("users")
public class UserListController {

    private final UserService userService;

    public UserListController(final UserService userService) {
        this.userService = userService;
    }

    /**
     * ユーザーリスト検索
     *
     * @param userSearchForm
     *            ユーザー検索リクエスト
     * @param pageable
     *            ページャー
     * @param userInfoDto
     *            ユーザー情報
     * @return ユーザー情報
     */
    @GetMapping
    public ModelAndView search(@ModelAttribute final UserSearchForm userSearchForm,
        @PageableDefault final Pageable pageable, @AuthenticationPrincipal final EarthlyUserInfoDto userInfoDto) {
        final var userSearchDto = new UserSearchDto(userSearchForm.loginId(), userSearchForm.name(),
                userSearchForm.company(), pageable.getOffset(), pageable.getPageSize(), pageable.getSort());
        final var userSearchResultDto = userService.search(userSearchDto, pageable, userInfoDto.account());
        final var modelAndView = new ModelAndView("users/index");
        modelAndView.addObject(userSearchResultDto);
        return modelAndView;
    }
}
