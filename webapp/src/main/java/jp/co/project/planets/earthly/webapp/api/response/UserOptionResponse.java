package jp.co.project.planets.earthly.webapp.api.response;

import java.io.Serializable;

import org.jilt.Builder;

@Builder(factoryMethod = "builder")
public record UserOptionResponse(String language, String timezone) implements Serializable {
}
