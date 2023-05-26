package jp.co.project.planets.earthly.auth.model.dto.health;

import java.util.List;

/**
 * health result dto
 * 
 * @param version
 *            application version
 * @param status
 *            application status
 * @param components
 *            component status list
 */
public record HealthResultDto(String version, String status, List<ComponentStatusDto> components) {
}
