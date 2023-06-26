package jp.co.project.planets.earthly.schema.emuns;

import static org.assertj.core.api.Assertions.*;

import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class GenderEnumTest {

    @ParameterizedTest
    @MethodSource
    void of(final String input, final GenderEnum expected) {
        final var actual = GenderEnum.of(input);
        assertThat(actual).isEqualTo(expected);
    }

    static Stream<Arguments> of() {
        return Stream.of( //
                Arguments.of((String) null, (GenderEnum) null), //
                Arguments.of(StringUtils.EMPTY, (GenderEnum) null), //
                Arguments.of(StringUtils.SPACE, (GenderEnum) null), //
                Arguments.of("hoge", (GenderEnum) null), //
                Arguments.of("M", GenderEnum.MALE), //
                Arguments.of("F", GenderEnum.FEMALE), //
                Arguments.of("-", GenderEnum.OTHER) //
        );
    }
}