package jp.co.project.planets.earthly.webapp.controller.form.user;

import java.io.Serializable;

import org.jilt.Builder;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Builder(factoryMethod = "builder")
public record ChangeLanguageForm(@NotBlank @Pattern(regexp = "ja|en") String lang, @NotBlank String pathname)
        implements Serializable {
}
