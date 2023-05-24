package jp.co.project.planets.earthly.auth.api.health.endpoint;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import jp.co.project.planets.earthly.auth.api.health.response.mapper.AwesomeTestResponseMapper;
import jp.co.project.planets.earthly.auth.service.HealthService;

/**
 * awesome health endpoint
 */
@Component
@Endpoint(id = "awesomeTEST")
public class AwesomeTestEndpoint {

    private final HealthService healthService;

    public AwesomeTestEndpoint(final HealthService healthService) {
        this.healthService = healthService;
    }

    @ReadOperation
    public ResponseEntity<?> health() {
        final var healthResultDto = healthService.healthAwesome();
        final var awesomeTestResponse = AwesomeTestResponseMapper.INSTANCE.convertToResponse(healthResultDto);
        return ResponseEntity.ok(awesomeTestResponse);
    }
}
