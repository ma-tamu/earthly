package jp.co.project.planets.earthly.model.dto;

import jp.co.project.planets.earthly.model.entity.UserSimpleEntity;

import java.util.List;

public record UserSearchResultDto(List<UserSimpleEntity> userSimpleEntityList, long offset, long total) {
}
