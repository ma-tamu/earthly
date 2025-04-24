package jp.co.project.planets.earthly.webapp.controller.form.system.notice;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.jilt.Builder;

@Builder(factoryMethod = "builder")
public record NoticeEntryForm(String title, String body, LocalDateTime startDate, LocalDateTime endDate,
        Boolean isEmphasis) implements Serializable {
}
