package jp.co.project.planets.earthly.webapp.annotation.validator;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;
import static jp.co.project.planets.earthly.webapp.emuns.ErrorMessageKey.*;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.Pattern;

/**
 * 英字数字バリデーション
 */
@Documented
@Constraint(validatedBy = {})
@Target({ TYPE, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Pattern(regexp = "^[\\w-]*$", message = "{" + VALIDATION_ALPHANUMERIC + "}")
public @interface Alphanumeric {

    @AliasFor(annotation = Pattern.class)
    String message() default "{" + VALIDATION_ALPHANUMERIC + "}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target({ TYPE })
    @Retention(RUNTIME)
    @interface List {
        Alphanumeric[] values();
    }

}
