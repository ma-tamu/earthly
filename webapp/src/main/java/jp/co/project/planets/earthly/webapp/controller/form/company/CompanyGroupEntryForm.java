package jp.co.project.planets.earthly.webapp.controller.form.company;

import org.jilt.Builder;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Builder(factoryMethod = "builder")
public record CompanyGroupEntryForm(@NotBlank @Size(min = 1, max = 50) String groupName) {
}
