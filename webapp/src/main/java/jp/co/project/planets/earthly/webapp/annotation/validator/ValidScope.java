package jp.co.project.planets.earthly.webapp.annotation.validator;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jp.co.project.planets.earthly.webapp.validation.ValidScopeValidator;

/**
 * スコープバリデーション
 */
@Documented
@Constraint(validatedBy = { ValidScopeValidator.class })
@Target({ TYPE_USE, TYPE, FIELD, ANNOTATION_TYPE, TYPE_PARAMETER })
@Retention(RUNTIME)
public @interface ValidScope {

    String message() default "{validate.client.valid.scope}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target({ TYPE })
    @Retention(RUNTIME)
    @interface List {
        ValidScope[] value();
    }
}
