package jp.co.project.planets.earthly.webapp.model.dto;

import java.time.LocalDateTime;

import org.jilt.Builder;

@Builder(factoryMethod = "builder")
public record NoticeEditDto(String title, String body, LocalDateTime startAt, LocalDateTime endAt, Boolean emphasis) {
}
