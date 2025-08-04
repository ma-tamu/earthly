package jp.co.project.planets.earthly.webapp.util;

import java.time.Duration;

import org.springframework.http.ResponseCookie;

public final class CookieUtils {

    private CookieUtils() {
    }

    public static ResponseCookie generate(final String name, final String value) {
        return ResponseCookie.from(name, value).path("/").httpOnly(true).secure(true)
                .maxAge(Duration.ofSeconds(2147483647L)).sameSite("Lax").build();
    }

}
