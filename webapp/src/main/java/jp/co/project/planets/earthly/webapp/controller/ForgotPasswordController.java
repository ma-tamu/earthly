package jp.co.project.planets.earthly.webapp.controller;

import static jp.co.project.planets.earthly.webapp.constant.ViewName.*;

import org.apache.commons.lang3.StringUtils;
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
import jp.co.project.planets.earthly.webapp.controller.form.forgot.ResetPasswordMailForm;
import jp.co.project.planets.earthly.webapp.exception.BadRequestException;
import jp.co.project.planets.earthly.webapp.service.ForgotPasswordService;

/**
 * パスワード忘れ
 */
@Controller
@RequestMapping("forgets/password")
public class ForgotPasswordController {

    private final ForgotPasswordService forgotPasswordService;

    public ForgotPasswordController(final ForgotPasswordService forgotPasswordService) {
        this.forgotPasswordService = forgotPasswordService;
    }

    /**
     * 初期表示
     * 
     * @param model
     *            Model
     * @return パスワード忘れた画面
     */
    @GetMapping
    public ModelAndView index(final Model model) {
        final var modelAndView = new ModelAndView(FORGOT_PASSWORD);
        modelAndView.addObject(new ResetPasswordMailForm(StringUtils.EMPTY, StringUtils.EMPTY));
        modelAndView.addAllObjects(model.asMap());
        return modelAndView;
    }

    /**
     * パスワード再設定メール送信
     * 
     * @param form
     *            パスワード忘れFORM
     * @param bindingResult
     *            binding result
     * @param redirectAttributes
     *            redirect attribute
     * @param model
     *            model
     * @return 通知完了画面
     * @throws BadRequestException
     *             対象ユーザーが存在しない場合に発生
     */
    @PostMapping
    public ModelAndView send(@ModelAttribute @Validated final ResetPasswordMailForm form,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes, final Model model) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addAllAttributes(model.asMap());
            return new ModelAndView("redirect:/" + FORGOT_PASSWORD);
        }
        try {
            forgotPasswordService.send(form.loginId(), form.mail());
            return new ModelAndView("redirect:/forgets/passwords/complete");
        } catch (final BadRequestException e) {
            redirectAttributes.addAllAttributes(model.asMap());
            redirectAttributes.addFlashAttribute(ModelKey.EXCEPTION, e);
            return new ModelAndView("redirect:/" + FORGOT_PASSWORD);
        }
    }

    /**
     * パスワード再設定メール完了
     * 
     * @return 通知完了画面
     */
    @GetMapping("complete")
    public ModelAndView complete() {
        return new ModelAndView("forgets/passwords/complete");
    }
}
