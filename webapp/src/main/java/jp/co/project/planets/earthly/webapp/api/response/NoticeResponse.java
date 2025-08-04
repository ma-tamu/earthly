package jp.co.project.planets.earthly.webapp.api.response;

import java.util.List;

import org.jilt.Builder;

import jp.co.project.planets.earthly.schema.db.entity.Notice;

@Builder(factoryMethod = "builder")
public record NoticeResponse(int total, List<Notice> notices) {
}
