package jp.co.project.planets.earthly.webapp.controller.system;

import static jp.co.project.planets.earthly.webapp.constant.ModelKey.*;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.co.project.planets.earthly.webapp.constant.MessageKey;
import jp.co.project.planets.earthly.webapp.controller.form.system.notice.NoticeEditForm;
import jp.co.project.planets.earthly.webapp.exception.BadRequestException;
import jp.co.project.planets.earthly.webapp.exception.ForbiddenException;
import jp.co.project.planets.earthly.webapp.security.dto.EarthlyUserInfoDto;
import jp.co.project.planets.earthly.webapp.service.NoticeService;
import jp.co.project.planets.earthly.webapp.util.RequestUtils;

@Controller
@RequestMapping("systems/notices/{id}")
public class NoticeDetailController {

    private final NoticeService noticeService;
    private final MessageSource messageSource;

    public NoticeDetailController(final NoticeService noticeService, final MessageSource messageSource) {
        this.noticeService = noticeService;
        this.messageSource = messageSource;
    }

    @GetMapping
    public ModelAndView index(@PathVariable("id") final String id, final Model model,
        @AuthenticationPrincipal final EarthlyUserInfoDto userInfoDto) {
        final var notice = noticeService.get(id, userInfoDto.account());
        final var noticeEditForm = new NoticeEditForm(notice.getTitle(), notice.getBody(), notice.getStartAt(),
                notice.getEndAt(), notice.getEmphasis());
        return new ModelAndView("systems/notices/detail", "notice", notice).addObject(noticeEditForm)
                .addAllObjects(model.asMap());
    }

    @PostMapping("edit")
    public ModelAndView edit(@PathVariable("id") final String id,
        @ModelAttribute @Validated final NoticeEditForm noticeEditForm, final BindingResult bindingResult,
        final RedirectAttributes redirectAttributes, final Model model,
        @AuthenticationPrincipal final EarthlyUserInfoDto userInfoDto) {

        final var modelAndView = new ModelAndView("redirect:/systems/notices/" + id);
        model.asMap().forEach(redirectAttributes::addFlashAttribute);
        NoticeEditForm.validator.validate(noticeEditForm).apply((name, messageKey, args, defaultMessage) -> {
            final var message = messageSource.getMessage(defaultMessage, args, RequestUtils.getRequest().getLocale());
            bindingResult.rejectValue(name, StringUtils.EMPTY, message);
        });
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute(EDIT_MODAL, true);
            return modelAndView;
        }

        try {
            redirectAttributes.addFlashAttribute(READ_ONLY, true);
        } catch (final BadRequestException | ForbiddenException e) {
            model.addAttribute(MESSAGE, e.getErrorCode().getMessageKey());
            model.addAttribute(MESSAGE_ARGS, e.getMessageKeyArgs());
        }
        return modelAndView;
    }

    @PostMapping("update")
    public ModelAndView update(@PathVariable("id") final String id,
        @ModelAttribute @Validated final NoticeEditForm noticeEditForm, final BindingResult bindingResult,
        final RedirectAttributes redirectAttributes, final Model model,
        @AuthenticationPrincipal final EarthlyUserInfoDto userInfoDto) {

        final var modelAndView = new ModelAndView("redirect:/systems/notices/%s".formatted(id));
        model.asMap().forEach(redirectAttributes::addFlashAttribute);
        NoticeEditForm.validator.validate(noticeEditForm).apply((name, messageKey, args, defaultMessage) -> {
            final var message = messageSource.getMessage(defaultMessage, args, RequestUtils.getRequest().getLocale());
            bindingResult.rejectValue(name, StringUtils.EMPTY, message);
        });

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute(EDIT_MODAL, true);
            return modelAndView;
        }

        try {
            final var message = noticeService.update(id, noticeEditForm.toDto(), userInfoDto.account());
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
            noticeService.delete(id, userInfoDto.account());
            redirectAttributes.addFlashAttribute(SUCCESS, MessageKey.DELETE_SUCCESS);
        } catch (final BadRequestException | ForbiddenException e) {
            model.asMap().forEach(redirectAttributes::addFlashAttribute);
            return new ModelAndView("redirect:/systems/notices/%s".formatted(id));
        }

        return new ModelAndView("redirect:/systems/notices");
    }
}
