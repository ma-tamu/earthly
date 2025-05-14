package jp.co.project.planets.earthly.schema.model.dto;

import java.util.List;

import org.jilt.Builder;

import jp.co.project.planets.earthly.schema.model.entity.Organization;

@Builder(factoryMethod = "builder")
public record GroupPageResultDto(List<Organization> organizationList, long offset, long total) {
}
