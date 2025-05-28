package jp.co.project.planets.earthly.webapp.controller.form.role;

import java.io.Serializable;
import java.util.List;

import org.jilt.Builder;

import jakarta.validation.constraints.NotEmpty;

@Builder(factoryMethod = "builder")
public record RoleGrantForm(@NotEmpty List<String> userId) implements Serializable {
}
