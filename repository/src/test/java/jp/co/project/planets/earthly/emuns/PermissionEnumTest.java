package jp.co.project.planets.earthly.emuns;

import com.google.testing.junit.testparameterinjector.junit5.TestParameter;
import com.google.testing.junit.testparameterinjector.junit5.TestParameterInjectorTest;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class PermissionEnumTest {


    @TestParameterInjectorTest
    void 存在しないパーミッションIDの場合にExceptionが発生すること(
            @TestParameter({StringUtils.EMPTY, StringUtils.SPACE, "NOT_FOUND_ID"}) final String input) {

        try {
            PermissionEnum.of(input);
            fail("Exceptionが発生するケースで正常終了した場合は、NGとする。");
        } catch (final Exception e) {
            final var expected = String.format("permission not found. id:%s", input);
            assertThat(e).isInstanceOf(RuntimeException.class).hasMessage(expected);
        }
    }

    @Test
    void 有効なパーミッションIDの場合に紐づくPermissionEnumが返されること() {

        final var actual = PermissionEnum.of("f212985ca90d11ec88720242ac120003");
        assertThat(actual).isEqualTo(PermissionEnum.ADD_USER);
    }
}