package jp.co.project.planets.earthly.auth.api.health.endpoint;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

/**
 * quick health endpoint
 */
@Component
@Endpoint(id = "quickTEST")
public class QuickTestEndpoint {

    /**
     * quick health
     *
     * @return health result
     */
    @ReadOperation(produces = MediaType.APPLICATION_JSON_VALUE)
    public String health() {
        return """
                {
                  "message": "OK"
                }
                """;
    }
}
