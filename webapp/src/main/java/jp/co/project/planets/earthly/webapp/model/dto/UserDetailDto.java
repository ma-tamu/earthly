package jp.co.project.planets.earthly.webapp.model.dto;

import jp.co.project.planets.earthly.schema.model.entity.UserEntity;

public record UserDetailDto(UserEntity userEntity, String qrcode,
        org.springframework.data.domain.PageImpl<jp.co.project.planets.earthly.schema.db.entity.Role> unassignedRolePage) {
}
