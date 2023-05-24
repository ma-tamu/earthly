package jp.co.project.planets.earthly.auth.api.health.endpoint;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

/**
 * quick health endpoint
 */
@Component
@Endpoint(id = "quickTEST")
public class QuickTestEndpoint {

    @ReadOperation
    public String health() {
        return """
                {
                  "message": "OK"
                }
                """;
    }
}
