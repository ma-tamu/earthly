package jp.co.project.planets.earthly.auth.api.health.endpoint;

import java.util.Map;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import jp.co.project.planets.earthly.auth.service.HealthService;

/**
 * full health endpoint
 */
@Component
@Endpoint(id = "fullTEST")
public class FullTestEndpoint {

    private final HealthService healthService;

    public FullTestEndpoint(final HealthService healthService) {
        this.healthService = healthService;
    }

    @ReadOperation(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> health() {
        if (healthService.healthFull()) {
            return ResponseEntity.ok(Map.of("message", "OK"));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "NG"));
    }
}
