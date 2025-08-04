package jp.co.project.planets.earthly.common.model.dto;

import java.time.LocalDateTime;

import org.jilt.Builder;

@Builder(factoryMethod = "builder")
public record NoticeEntryDto(String title, String body, LocalDateTime startAt, LocalDateTime endAt, Boolean emphasis) {
}
