package jp.co.project.planets.earthly.model.entity;

import org.seasar.doma.Entity;
import org.seasar.doma.jdbc.entity.NamingType;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * company entity
 */
@Entity(naming = NamingType.SNAKE_LOWER_CASE)
public record CompanyEntity(String id, String name, String countryId, String countryName, String languageId,
                            String languageName, String regionId, String regionName, String createdBy,
                            LocalDateTime createdAt, String updatedBy, LocalDateTime updatedAt,
                            boolean isDeleted) implements Serializable {
}
