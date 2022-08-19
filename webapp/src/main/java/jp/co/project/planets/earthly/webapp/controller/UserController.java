package jp.co.project.planets.earthly.webapp.controller;

import jp.co.project.planets.earthly.webapp.controller.form.UserSearchForm;
import jp.co.project.planets.earthly.webapp.model.dto.UserSearchDto;
import jp.co.project.planets.earthly.webapp.security.dto.EarthlyUserInfoDto;
import jp.co.project.planets.earthly.webapp.service.UserService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * user controller
 */
@Controller
@RequestMapping("users")
public class UserController {

    private final UserService userService;

    public UserController(final UserService userService) {
        this.userService = userService;
    }

    /**
     * ユーザーリスト検索
     *
     * @param userSearchForm
     *         ユーザー検索リクエスト
     * @param pageable
     *         ページャー
     * @param userInfoDto
     *         ユーザー情報
     * @return ユーザー情報
     */
    @GetMapping
    public ModelAndView search(@ModelAttribute final UserSearchForm userSearchForm,
            @PageableDefault final Pageable pageable,
            @AuthenticationPrincipal final EarthlyUserInfoDto userInfoDto) {
        final var userSearchDto = new UserSearchDto(userSearchForm.loginId(), userSearchForm.name(),
                userSearchForm.company(), pageable.getOffset(), pageable.getPageSize(), pageable.getSort());
        final var userSearchResultDto = userService.search(userSearchDto, pageable, userInfoDto);
        final var modelAndView = new ModelAndView("users/list");
        modelAndView.addObject(userSearchResultDto);
        return modelAndView;
    }

    /**
     * 対象ユーザーを取得する。
     *
     * @param id
     *         ユーザーID
     * @param userInfoDto
     *         ユーザー情報
     * @return ユーザー詳細画面
     */
    @GetMapping("{userId}")
    public ModelAndView get(@PathVariable("userId") final String id,
            @AuthenticationPrincipal final EarthlyUserInfoDto userInfoDto) {
        final var userEntity = userService.getById(id, userInfoDto);
        final var modelAndView = new ModelAndView("users/detail");
        modelAndView.addObject(userEntity);
        return modelAndView;
    }

    @GetMapping("entry")
    public ModelAndView entry(@AuthenticationPrincipal final EarthlyUserInfoDto userInfoDto) {
        return new ModelAndView("users/entry");
    }

    @PostMapping
    public ModelAndView createValidation(@AuthenticationPrincipal final EarthlyUserInfoDto userInfoDto) {
        return new ModelAndView("redirect:/users/entry");
    }
}
