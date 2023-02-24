package jp.co.project.planets.earthly.webapp.model.dto;

import jp.co.project.planets.earthly.model.entity.UserEntity;

public record UserDetailDto(UserEntity userEntity, String qrcode) {
}
