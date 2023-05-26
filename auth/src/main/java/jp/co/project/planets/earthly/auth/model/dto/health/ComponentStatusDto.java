package jp.co.project.planets.earthly.auth.model.dto.health;

/**
 * component status dto
 * 
 * @param service
 *            service name
 * @param status
 *            service status
 */
public record ComponentStatusDto(String service, String status) {
}
