package jp.co.project.planets.earthly.webapp.annotation.validator;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.Pattern;

/**
 * url validation
 */
@Documented
@Constraint(validatedBy = {})
@Target({ TYPE, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Pattern(regexp = "^https?://[\\w/:%#$&?()~.=+\\-]+$")
public @interface Url {

    String message() default "{validate.client.regx.url}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target({ TYPE })
    @Retention(RUNTIME)
    @interface List {
        Url[] values();
    }
}
