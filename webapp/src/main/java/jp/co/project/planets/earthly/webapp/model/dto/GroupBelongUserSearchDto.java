package jp.co.project.planets.earthly.webapp.model.dto;

import org.jilt.Builder;

@Builder(factoryMethod = "builder")
public record GroupBelongUserSearchDto(String loginId, String name) {
}
