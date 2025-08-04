package jp.co.project.planets.earthly.schema.emuns;

import static org.assertj.core.api.Assertions.*;

import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class GenderTest {

    @ParameterizedTest
    @MethodSource
    void of(final String input, final Gender expected) {
        final var actual = Gender.of(input);
        assertThat(actual).isEqualTo(expected);
    }

    static Stream<Arguments> of() {
        return Stream.of( //
                Arguments.of((String) null, (Gender) null), //
                Arguments.of(StringUtils.EMPTY, (Gender) null), //
                Arguments.of(StringUtils.SPACE, (Gender) null), //
                Arguments.of("hoge", (Gender) null), //
                Arguments.of("M", Gender.MALE), //
                Arguments.of("F", Gender.FEMALE), //
                Arguments.of("-", Gender.OTHER) //
        );
    }
}