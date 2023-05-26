package jp.co.project.planets.earthly.auth.api.health.endpoint;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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

    /**
     * awesome health
     * 
     * @return health result
     */
    @ReadOperation(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> health() {
        final var healthResultDto = healthService.healthAwesome();
        final var awesomeTestResponse = AwesomeTestResponseMapper.INSTANCE.convertToResponse(healthResultDto);
        try {
            // TODO Bean登録しているObjectMapperを使用するとjson内にjava.util.ArrayListが混入されるので見直しが必要。
            final var json = new ObjectMapper().writeValueAsString(awesomeTestResponse);
            return ResponseEntity.ok(json);
        } catch (final JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
