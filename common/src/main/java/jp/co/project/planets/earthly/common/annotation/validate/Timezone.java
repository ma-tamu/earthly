package jp.co.project.planets.earthly.common.annotation.validate;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jp.co.project.planets.earthly.common.validate.TimezoneValidator;

@Documented
@Constraint(validatedBy = { TimezoneValidator.class })
@Target({ ANNOTATION_TYPE, FIELD })
@Retention(RUNTIME)
public @interface Timezone {

    String message() default "{validate.timezone}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target({ TYPE, ANNOTATION_TYPE })
    @Retention(RUNTIME)
    @Documented
    public @interface List {
        Timezone[] value(); // ④
    }
}
