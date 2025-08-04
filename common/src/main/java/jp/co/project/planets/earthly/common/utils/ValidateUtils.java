package jp.co.project.planets.earthly.common.utils;

import java.time.LocalDateTime;
import java.util.Objects;

public final class ValidateUtils {

    private ValidateUtils() {
    }

    public static boolean isFuture(final LocalDateTime src, final LocalDateTime dest) {
        if (Objects.isNull(src) || Objects.isNull(dest)) {
            return true;
        }
        return src.isAfter(dest);
    }

    public static boolean isPast(final LocalDateTime src, final LocalDateTime dest) {
        if (Objects.isNull(src) || Objects.isNull(dest)) {
            return true;
        }
        return src.isBefore(dest);
    }
}
