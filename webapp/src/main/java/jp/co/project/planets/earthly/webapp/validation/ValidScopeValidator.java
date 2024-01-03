package jp.co.project.planets.earthly.webapp.validation;

import java.util.Objects;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jp.co.project.planets.earthly.schema.emuns.Scope;
import jp.co.project.planets.earthly.webapp.annotation.validator.ValidScope;

/**
 * スコープバリデータ
 */
public class ValidScopeValidator implements ConstraintValidator<ValidScope, String> {
    @Override
    public boolean isValid(final String value, final ConstraintValidatorContext context) {
        final var scope = Scope.of(value);
        return Objects.nonNull(scope);
    }
}
