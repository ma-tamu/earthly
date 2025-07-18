package jp.co.project.planets.earthly.core.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

public final class DateUtils {
    private DateUtils() {
    }

    /**
     * instant to jst LocalDateTime
     *
     * @param instant
     *            instant
     * @return LocalDateTime
     */
    public static LocalDateTime toLocalDateTime(final Instant instant) {
        instant.atZone(ZoneId.systemDefault());
        return LocalDateTime.ofInstant(instant, ZoneOffset.systemDefault());
    }

    public static LocalDateTime parse(final String date, final String format) {
        try {
            final var formatter = DateTimeFormatter.ofPattern(format).withResolverStyle(ResolverStyle.STRICT);
            return LocalDateTime.parse(date, formatter);
        } catch (final DateTimeParseException e) {
            throw new RuntimeException("parse error format:%s".formatted(format), e);
        }
    }

    public static LocalDateTime parseOrNull(final String date, final String format) {
        try {
            return parse(date, format);
        }  catch (final RuntimeException e) {
            return null;
        }
    }
}
