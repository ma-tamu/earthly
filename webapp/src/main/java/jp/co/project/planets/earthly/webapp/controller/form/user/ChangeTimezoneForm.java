package jp.co.project.planets.earthly.webapp.controller.form.user;

import java.io.Serializable;

import org.jilt.Builder;

import jakarta.validation.constraints.NotBlank;
import jp.co.project.planets.earthly.common.annotation.validate.Timezone;

@Builder(factoryMethod = "builder")
public record ChangeTimezoneForm(@NotBlank @Timezone String timezone, @NotBlank String pathname)
        implements Serializable {
}
