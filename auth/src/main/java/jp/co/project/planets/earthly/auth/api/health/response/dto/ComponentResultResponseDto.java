package jp.co.project.planets.earthly.auth.api.health.response.dto;

/**
 * component result response dto
 * 
 * @param service
 *            used service
 * @param status
 *            service status
 */
public record ComponentResultResponseDto(String service, String status) {
}
