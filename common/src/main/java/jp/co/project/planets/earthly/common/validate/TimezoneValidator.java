package jp.co.project.planets.earthly.common.validate;

import java.util.Objects;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jp.co.project.planets.earthly.common.annotation.validate.Timezone;

/**
 * timezone validator
 */
public final class TimezoneValidator implements ConstraintValidator<Timezone, String> {

    @Override
    public void initialize(final Timezone timezone) {
    }

    @Override
    public boolean isValid(final String value, final ConstraintValidatorContext context) {
        final var timezone = jp.co.project.planets.earthly.core.enums.Timezone.of(value);
        return !Objects.isNull(timezone);
    }
}
