package jp.co.project.planets.earthly.webapp.controller.system;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
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
import jp.co.project.planets.earthly.webapp.controller.form.system.notice.NoticeEntryForm;
import jp.co.project.planets.earthly.webapp.controller.form.system.notice.NoticeEntryFormBuilder;
import jp.co.project.planets.earthly.webapp.security.dto.EarthlyUserInfoDto;
import jp.co.project.planets.earthly.webapp.service.NoticeService;
import jp.co.project.planets.earthly.webapp.util.RequestUtils;

@Controller
@RequestMapping("systems/notices")
public class NoticeEntryController {

    private final NoticeService noticeService;
    private final MessageSource messageSource;

    public NoticeEntryController(final NoticeService noticeService, final MessageSource messageSource) {
        this.noticeService = noticeService;
        this.messageSource = messageSource;
    }

    @GetMapping("entries")
    public ModelAndView index(final Model model, @AuthenticationPrincipal final EarthlyUserInfoDto userInfoDto) {
        return new ModelAndView("systems/notices/entry")
                .addObject(NoticeEntryFormBuilder.builder().build())
                .addAllObjects(model.asMap());
    }

    @PostMapping("entries")
    public ModelAndView entry(@ModelAttribute @Validated final NoticeEntryForm noticeEntryForm,
        final BindingResult bindingResult, final Model model, final RedirectAttributes redirectAttributes,
        @AuthenticationPrincipal final EarthlyUserInfoDto userInfoDto) {

        model.asMap().forEach(redirectAttributes::addFlashAttribute);
        NoticeEntryForm.validator.validate(noticeEntryForm).apply((name, messageKey, args, defaultMessage) -> {
            final var message = messageSource.getMessage(defaultMessage, args, RequestUtils.getRequest().getLocale());
            bindingResult.rejectValue(name, StringUtils.EMPTY, message);
        });
        if (bindingResult.hasErrors()) {
            return new ModelAndView("redirect:/systems/notices/entries");
        }
        noticeService.validateEntry(userInfoDto.account());
        redirectAttributes.addFlashAttribute(ModelKey.READ_ONLY, true);
        return new ModelAndView("redirect:/systems/notices/entries");
    }

    @PostMapping("create")
    public ModelAndView create(@ModelAttribute @Validated final NoticeEntryForm noticeEntryForm,
        final BindingResult bindingResult, final Model model, final RedirectAttributes redirectAttributes,
        @AuthenticationPrincipal final EarthlyUserInfoDto userInfoDto) {

        model.asMap().forEach(redirectAttributes::addFlashAttribute);
        NoticeEntryForm.validator.validate(noticeEntryForm).apply((name, messageKey, args, defaultMessage) -> {
            final var message = messageSource.getMessage(defaultMessage, args, RequestUtils.getRequest().getLocale());
            bindingResult.rejectValue(name, StringUtils.EMPTY, message);
        });
        if (bindingResult.hasErrors()) {
            return new ModelAndView("redirect:/systems/notices/entries");
        }

        final var id = noticeService.create(noticeEntryForm.toDto(), userInfoDto.account());
        return new ModelAndView("redirect:/systems/notices/%s".formatted(id));
    }
}
