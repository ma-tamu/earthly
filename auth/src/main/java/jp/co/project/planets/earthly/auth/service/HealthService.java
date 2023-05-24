package jp.co.project.planets.earthly.auth.service;

import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Service;

import com.google.common.annotations.VisibleForTesting;

import jp.co.project.planets.earthly.auth.model.dto.health.ComponentStatusDto;
import jp.co.project.planets.earthly.auth.model.dto.health.HealthResultDto;

/**
 * health check service
 */
@Service
public class HealthService {

    private final String version;
    private final HealthEndpoint healthEndpoint;

    private static final String STATUS_UP = "UP";
    private static final String STATUS_DOWN = "DOWN";

    public HealthService(@Value("${auth.version}") final String version, final HealthEndpoint healthEndpoint) {
        this.version = version;
        this.healthEndpoint = healthEndpoint;
    }

    /**
     * full test health
     * 
     * @return true: healthy false: not healthy
     */
    public boolean healthFull() {
        return systemHealth();
    }

    public HealthResultDto healthAwesome() {
        final boolean isHealthyDb = systemHealth();
        final var dbStatus = isHealthyDb ? STATUS_UP : STATUS_DOWN;
        final var componentStatusDto = new ComponentStatusDto("DB", dbStatus);
        final var componentStatusDtoList = List.of(componentStatusDto);
        final var status = componentStatusDtoList.stream().anyMatch(it -> StringUtils.equals(it.status(), STATUS_DOWN))
                ? STATUS_UP
                : STATUS_DOWN;
        return new HealthResultDto(version, status, componentStatusDtoList);
    }

    /**
     * system health
     * 
     * @return true: healthy false: not healthy
     */
    @VisibleForTesting
    boolean systemHealth() {
        try {
            final var health = healthEndpoint.health();
            return Objects.equals(health.getStatus(), Status.UP);
        } catch (final Exception e) {
            return false;
        }
    }
}
