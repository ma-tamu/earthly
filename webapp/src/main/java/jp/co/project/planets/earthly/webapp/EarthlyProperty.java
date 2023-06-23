package jp.co.project.planets.earthly.webapp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public record EarthlyProperty(@Value("${earthly.base-url}") String baseUrl) {
}
