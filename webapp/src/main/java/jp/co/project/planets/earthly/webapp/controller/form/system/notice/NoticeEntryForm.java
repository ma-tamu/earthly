package jp.co.project.planets.earthly.webapp.controller.form.system.notice;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.jilt.Builder;
import org.springframework.format.annotation.DateTimeFormat;

import am.ik.yavi.builder.ValidatorBuilder;
import am.ik.yavi.core.Validator;
import jp.co.project.planets.earthly.common.model.dto.NoticeEntryDto;
import jp.co.project.planets.earthly.common.utils.ValidateUtils;

@Builder(factoryMethod = "builder")
public record NoticeEntryForm(String title, String body,
        @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss") LocalDateTime startAt,
        @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss") LocalDateTime endAt, Boolean emphasis)
        implements Serializable {

    public static Validator<NoticeEntryForm> validator = ValidatorBuilder.<NoticeEntryForm> of()
            .constraint(NoticeEntryForm::title, "title",
                    c -> c.notBlank().message("jakarta.validation.constraints.NotBlank.message")
                            .greaterThanOrEqual(1).message("validate.greater.than.or.equal")
                            .lessThanOrEqual(50).message("validate.less.than.or.equal"))
            .constraint(NoticeEntryForm::body, "body",
                    c -> c.notBlank().message("jakarta.validation.constraints.NotBlank.message")
                            .greaterThanOrEqual(1).message("validate.greater.than.or.equal")
                            .lessThanOrEqual(50).message("validate.less.than.or.equal"))
            .constraint(NoticeEntryForm::startAt, "startAt",
                    c -> c.notNull().message("jakarta.validation.constraints.NotBlank.message"))
            .constraintOnTarget(
                    form -> ValidateUtils.isPast(form.startAt, form.endAt),
                    "startAt", "", "validate.notice.end.date.less.than")
            .constraintOnTarget(form -> ValidateUtils.isFuture(form.endAt, form.startAt), "endAt", "",
                    "validate.notice.end.date.less.than")
            .constraint(NoticeEntryForm::emphasis, "emphasis",
                    c -> c.notNull().message("jakarta.validation.constraints.NotBlank.message"))
            .build();

    public NoticeEntryDto toDto() {
        return new NoticeEntryDto(title, body, startAt, endAt, emphasis);
    }
}
