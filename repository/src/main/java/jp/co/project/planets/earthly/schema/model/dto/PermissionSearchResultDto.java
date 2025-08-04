package jp.co.project.planets.earthly.schema.model.dto;

import java.util.List;

import org.jilt.Builder;

import jp.co.project.planets.earthly.schema.db.entity.Permission;

@Builder(factoryMethod = "builder")
public record PermissionSearchResultDto(List<Permission> permissionList, long offset, long total) {
}
