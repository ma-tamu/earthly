package jp.co.project.planets.earthly.webapp.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.co.project.planets.earthly.webapp.api.response.UserOptionResponse;
import jp.co.project.planets.earthly.webapp.security.dto.EarthlyUserInfoDto;
import jp.co.project.planets.earthly.webapp.service.UserService;

@RestController
@RequestMapping("api/users")
public class UserRestController {

    private final UserService userService;

    public UserRestController(final UserService userService) {
        this.userService = userService;
    }

    @GetMapping("{id}/timezones")
    public ResponseEntity<UserOptionResponse> getTimezone(@PathVariable("id") final String id,
        @AuthenticationPrincipal final EarthlyUserInfoDto earthlyUserInfoDto) {
        final var timezone = userService.getTimezone(id, earthlyUserInfoDto.account());
        return ResponseEntity.ok(new UserOptionResponse(null, timezone.getId()));
    }
}
