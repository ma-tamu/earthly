package jp.co.project.planets.earthly.webapp.controller.form.system.notice;

import java.time.LocalDateTime;

import org.jilt.Builder;
import org.springframework.format.annotation.DateTimeFormat;

@Builder(factoryMethod = "builder")
public record NoticeEditForm(String title, String body,
        @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss") LocalDateTime startAt,
        @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss") LocalDateTime endAt, Boolean emphasis) {
}
