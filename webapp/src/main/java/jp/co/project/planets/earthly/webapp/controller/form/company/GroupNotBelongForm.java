package jp.co.project.planets.earthly.webapp.controller.form.company;

import java.util.List;

import org.jilt.Builder;

import jakarta.validation.constraints.NotEmpty;

@Builder(factoryMethod = "builder")
public record GroupNotBelongForm(@NotEmpty List<String> userId) {
}
