package jp.co.project.planets.earthly.webapp.service;

import jp.co.project.planets.earthly.common.logic.UserLogic;
import jp.co.project.planets.earthly.emuns.GenderEnum;
import jp.co.project.planets.earthly.emuns.PermissionEnum;
import jp.co.project.planets.earthly.model.entity.BelongCompanyEntity;
import jp.co.project.planets.earthly.model.entity.CountryEntity;
import jp.co.project.planets.earthly.model.entity.LanguageEntity;
import jp.co.project.planets.earthly.model.entity.RegionEntity;
import jp.co.project.planets.earthly.model.entity.RoleSimpleEntity;
import jp.co.project.planets.earthly.model.entity.UserEntity;
import jp.co.project.planets.earthly.repository.UserRepository;
import jp.co.project.planets.earthly.webapp.exception.ForbiddenException;
import jp.co.project.planets.earthly.webapp.exception.NotFoundException;
import jp.co.project.planets.earthly.webapp.security.dto.EarthlyUserInfoDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static jp.co.project.planets.earthly.webapp.emuns.ErrorCode.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

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
    void VIEW_ALL_USERを保持している場合に存在するユーザーIDを指定すると指定したユーザーが取得できること() {

        final var permissionEnumList = List.of(PermissionEnum.VIEW_ALL_USER);
        final var userInfoDto = new EarthlyUserInfoDto("USER_ID_01", null, null, null, false,
                permissionEnumList, List.of(new SimpleGrantedAuthority(PermissionEnum.VIEW_ALL_USER.name())));

        // condition
        final var regionEntity = new RegionEntity("45bd917836a599faf2a30c54d677d9a6", "Asia");
        final var languageEntity = new LanguageEntity("19154223c579d44a0fe8c9e5476d8f5e", "japanese");
        final var countryEntity = new CountryEntity("d92bf311652a77227d2725c204a5396b", "Japan",
                languageEntity, regionEntity);
        final var belongCompanyEntity = new BelongCompanyEntity("COMPANY_ID_01", "COMPANY_NAME_01", countryEntity);
        final var role01 = new RoleSimpleEntity("ROLE_ID_01", "ROLE_NAME_01");
        final var roleList = List.of(role01);
        final var expected = new UserEntity("USER_ID_02", "LOGIN_ID_02", "USER_NAME_02", GenderEnum.MALE.getValue(),
                "algie_dolanqyj@prize.gtt", "$2a$10$IfIpdWUeKUBFd0pN6dRV/.4IT3Lsln5zuw8bZgiV.nTH/RbVRlxP2",
                Boolean.FALSE, belongCompanyEntity, roleList, LocalDateTime.of(2022, Month.AUGUST, 3, 13, 18, 12),
                null, LocalDateTime.of(2022, Month.AUGUST, 14, 14, 46, 59), null, Boolean.FALSE);
        when(userLogic.getAccessibleEntity(eq("USER_ID_02"), eq(permissionEnumList), eq("USER_ID_01"))).thenReturn(
                Optional.of(expected));

        // test
        final var actual = userService.getById("USER_ID_02", userInfoDto);

        // verify
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void VIEW_ALL_USERを保持している場合に存在しないユーザーIDを指定するとNotFoundExceptionが発生すること() {

        final var permissionEnumList = List.of(PermissionEnum.VIEW_ALL_USER);
        final var userInfoDto = new EarthlyUserInfoDto("USER_ID_01", null, null, null, false,
                permissionEnumList, List.of(new SimpleGrantedAuthority(PermissionEnum.VIEW_ALL_USER.name())));

        when(userLogic.getAccessibleEntity(eq("USER_ID_02"), eq(permissionEnumList), eq("USER_ID_01"))).thenReturn(
                Optional.empty());

        try {
            // test
            userService.getById("USER_ID_02", userInfoDto);
        } catch (final NotFoundException e) {
            // verify
            final var expected = new NotFoundException("not found user user=USER_ID_02.", EWA4XX002);
            assertThat(e).usingRecursiveComparison().isEqualTo(expected);
        }
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