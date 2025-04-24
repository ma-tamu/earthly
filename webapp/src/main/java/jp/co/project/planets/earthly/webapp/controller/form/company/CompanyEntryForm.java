package jp.co.project.planets.earthly.webapp.controller.form.company;

import org.jilt.Builder;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * 会社登録FROM
 * 
 * @param name
 *            会社名
 * @param country
 *            所属国
 */
@Builder(factoryMethod = "builder")
public record CompanyEntryForm(@NotBlank @Size(min = 1, max = 64) String name, @NotBlank String country) {
}
