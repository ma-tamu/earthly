package jp.co.project.planets.earthly.webapp.service;

import jp.co.project.planets.earthly.common.logic.UserLogic;
import jp.co.project.planets.earthly.repository.UserRepository;
import jp.co.project.planets.earthly.webapp.exception.ForbiddenException;
import jp.co.project.planets.earthly.webapp.security.dto.EarthlyUserInfoDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static jp.co.project.planets.earthly.webapp.emuns.ErrorCode.*;
import static org.junit.jupiter.api.Assertions.*;

@Tag("unit")
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void VIEW_ALL_USERを保持していない且つ操作ユーザーと異なる場合にForbiddenExceptionが発生する() {

        try {
            final var userInfoDto = new EarthlyUserInfoDto("USER_ID_01", null, null, null, false,
                    Collections.emptyList(), Collections.emptyList());
            userService.validateAccessible("MISMATCH_USER_ID", userInfoDto);
            fail("exception発生するケースで正常終了はテストNGです。");
        } catch (final ForbiddenException e) {
            new ForbiddenException("not accessible user user=MISMATCH_USER_ID", EWA4XX003);
        }
    }

    @InjectMocks
    UserService userService;

    @Mock
    UserLogic userLogic;

    @Mock
    UserRepository userRepository;
}