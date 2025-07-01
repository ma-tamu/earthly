package jp.co.project.planets.earthly.core.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

public final class DateUtils {
    private DateUtils() {
    }

    public static LocalDateTime pares(final String date, final String format) {
        try {
            final var formatter = DateTimeFormatter.ofPattern(format).withResolverStyle(ResolverStyle.STRICT);
            return LocalDateTime.parse(date, formatter);
        } catch (final DateTimeParseException e) {
            throw new RuntimeException("parse error format:%s".formatted(format), e);
        }
    }
}
