package jp.co.project.planets.earthly.webapp.api.controller.health;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

/**
 * quick health endpoint
 */
@Component
@Endpoint(id = "quickTEST")
public class QuickEndpoint {

    @ReadOperation
    public String health() {
        return """
                {
                  "message": "OK"
                }
                """;
    }
}
