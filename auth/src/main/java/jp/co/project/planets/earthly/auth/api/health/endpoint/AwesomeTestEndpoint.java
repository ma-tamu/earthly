package jp.co.project.planets.earthly.auth.api.health.endpoint;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

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

    public ResponseEntity<?> health() {
        final var healthResultDto = healthService.healthAwesome();
        return ResponseEntity.ok("");
    }
}
