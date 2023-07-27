package jp.co.project.planets.earthly.common.validate;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import jakarta.validation.Validation;
import jp.co.project.planets.earthly.common.annotation.validate.Timezone;

@Tag("unit")
class TimezoneValidatorTest {

    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {
    }

    @ParameterizedTest
    @EnumSource(jp.co.project.planets.earthly.core.enums.Timezone.class)
    void 取り扱っているタイムゾーンの場合はtrueを返す(final jp.co.project.planets.earthly.core.enums.Timezone timezone) {

        try (final var validatorFactory = Validation.buildDefaultValidatorFactory()) {
            final var validator = validatorFactory.getValidator();
            final var input = new TestBean(timezone.getId());

            // test
            final var actual = validator.validate(input);

            // verify
            assertThat(actual).isEmpty();
        }
    }

    @Test
    void 取り扱っていないタイムゾーンの場合はfalseを返す() {
        try (final var validatorFactory = Validation.buildDefaultValidatorFactory()) {
            final var validator = validatorFactory.getValidator();
            final var input = new TestBean("not-found-timezone");

            // test
            final var actual = validator.validate(input);

            // verify
            assertThat(actual).isNotEmpty().extracting("message").containsOnly("{validate.timezone}");
        }
    }

    record TestBean(@Timezone String timezone) {

    }
}