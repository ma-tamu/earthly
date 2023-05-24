package jp.co.project.planets.earthly.auth.api.health.response;

import java.util.List;

import jp.co.project.planets.earthly.auth.api.health.response.dto.ComponentResultResponseDto;

/**
 * awesome test response
 * 
 * @param version
 *            application version
 * @param status
 *            application status
 * @param components
 *            components status
 */
public record AwesomeTestResponse(String version, String status, List<ComponentResultResponseDto> components) {
}
