package jp.co.project.planets.earthly.webapp.service;

import static jp.co.project.planets.earthly.webapp.emuns.ErrorCode.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import jp.co.project.planets.earthly.common.logic.UserLogic;
import jp.co.project.planets.earthly.core.enums.Timezone;
import jp.co.project.planets.earthly.schema.db.entity.Role;
import jp.co.project.planets.earthly.schema.emuns.GenderEnum;
import jp.co.project.planets.earthly.schema.emuns.PermissionEnum;
import jp.co.project.planets.earthly.schema.model.entity.BelongCompanyEntity;
import jp.co.project.planets.earthly.schema.model.entity.CountryEntity;
import jp.co.project.planets.earthly.schema.model.entity.LanguageEntity;
import jp.co.project.planets.earthly.schema.model.entity.RegionEntity;
import jp.co.project.planets.earthly.schema.model.entity.UserEntity;
import jp.co.project.planets.earthly.schema.repository.CompanyRepository;
import jp.co.project.planets.earthly.schema.repository.UserRepository;
import jp.co.project.planets.earthly.webapp.exception.ForbiddenException;
import jp.co.project.planets.earthly.webapp.exception.NotFoundException;
import jp.co.project.planets.earthly.webapp.model.dto.UserDetailDto;
import jp.co.project.planets.earthly.webapp.security.dto.EarthlyUserInfoDto;

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
        final var userInfoDto = new EarthlyUserInfoDto("USER_ID_01", null, null, null, false, false, false, null, null,
                permissionEnumList, List.of(new SimpleGrantedAuthority(PermissionEnum.VIEW_ALL_USER.name())));

        // condition
        final var regionEntity = new RegionEntity("45bd917836a599faf2a30c54d677d9a6", "Asia");
        final var languageEntity = new LanguageEntity("19154223c579d44a0fe8c9e5476d8f5e", "japanese");
        final var countryEntity = new CountryEntity("d92bf311652a77227d2725c204a5396b", "Japan", languageEntity,
                regionEntity);
        final var belongCompanyEntity = new BelongCompanyEntity("COMPANY_ID_01", "COMPANY_NAME_01", countryEntity);
        final var role01 = new Role("ROLE_ID_01", "ROLE_NAME_01", null, null, null, null, false);
        final var roleList = List.of(role01);
        final var userEntity = new UserEntity("USER_ID_02", "LOGIN_ID_02", "USER_NAME_02", GenderEnum.MALE.getValue(),
                "algie_dolanqyj@prize.gtt", "$2a$10$IfIpdWUeKUBFd0pN6dRV/.4IT3Lsln5zuw8bZgiV.nTH/RbVRlxP2", "ja",
                Timezone.ASIA_TOKYO.getId(), Boolean.FALSE, false, "NULL", belongCompanyEntity, roleList,
                Collections.emptyList(), LocalDateTime.of(2022, Month.AUGUST, 3, 13, 18, 12), null,
                LocalDateTime.of(2022, Month.AUGUST, 14, 14, 46, 59), null, Boolean.FALSE);
        when(userLogic.getAccessibleEntity(eq("USER_ID_02"), eq(permissionEnumList), eq("USER_ID_01"))).thenReturn(
                Optional.of(userEntity));

        // test
        final var actual = userService.getById("USER_ID_02", userInfoDto);

        // verify
        final var expected = new UserDetailDto(userEntity, null);
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void VIEW_ALL_USERを保持していない且つ対象ユーザーが自分自身でない場合にForbiddenExceptionが返される() {

        final var userInfoDto = new EarthlyUserInfoDto("USER_ID_01", null, null, null, false, false, false, null, null,
                Collections.emptyList(), Collections.emptyList());

        // test & verify
        final var expected = new NotFoundException("not accessible user user=USER_ID_02.", EWA4XX003);
        assertThatThrownBy(() -> userService.validateAccessible("USER_ID_02", userInfoDto)).isInstanceOfSatisfying(
                ForbiddenException.class, e -> assertThat(e).usingRecursiveComparison().isEqualTo(expected));
    }

    @Test
    void VIEW_ALL_USERを保持している場合に存在しないユーザーIDを指定するとNotFoundExceptionが発生すること() {

        final var permissionEnumList = List.of(PermissionEnum.VIEW_ALL_USER);
        final var userInfoDto = new EarthlyUserInfoDto("USER_ID_01", null, null, null, false, false, false, null, null,
                permissionEnumList, List.of(new SimpleGrantedAuthority(PermissionEnum.VIEW_ALL_USER.name())));

        when(userLogic.getAccessibleEntity(eq("USER_ID_02"), eq(permissionEnumList), eq("USER_ID_01"))).thenReturn(
                Optional.empty());

        // test & verify
        final var expected = new NotFoundException("not found user user=USER_ID_02.", EWA4XX002);
        assertThatThrownBy(() -> userService.getById("USER_ID_02", userInfoDto)).isInstanceOfSatisfying(
                NotFoundException.class, e -> assertThat(e).usingRecursiveComparison().isEqualTo(expected));
    }

    @Test
    void ADD_USERを保持していない且つ登録可能な会社がない場合にForbiddenExceptionが発生すること() {

        // condition
        when(companyRepository.findAccessibleByUserId(anyString(), any(Optional.class), anyList())).thenReturn(
                Collections.emptyList());

        final var userInfoDto = new EarthlyUserInfoDto("USER_ID_01", null, null, null, false, false, false, null, null,
                Collections.emptyList(), Collections.emptyList());
        final var expected = new ForbiddenException(EWA4XX004);

        // test & verify
        assertThatThrownBy(() -> userService.validateUserAddOperationPermission(userInfoDto)).isInstanceOfSatisfying(
                ForbiddenException.class, e -> assertThat(e).usingRecursiveComparison().isEqualTo(expected));

    }

    @Test
    void 指定した会社IDが閲覧できない場合にForbiddenExceptionが発生すること() {

        // condition
        when(companyRepository.findByAccessiblePrimaryKey(anyString(), anyList(), eq("USER_ID_01"))).thenReturn(
                Optional.empty());

        final var userInfoDto = new EarthlyUserInfoDto("USER_ID_01", null, null, null, false, false, false, null, null,
                Collections.emptyList(), Collections.emptyList());
        final var expected = new ForbiddenException(EWA4XX004);

        // test & verify
        assertThatThrownBy(() -> userService.validateUserAddingCompany("NOT_FOUND_COMPANY_ID", userInfoDto))
                .isInstanceOfSatisfying(ForbiddenException.class,
                        e -> assertThat(e).usingRecursiveComparison().isEqualTo(expected));
    }

    @InjectMocks
    UserService userService;

    @Mock
    UserLogic userLogic;

    @Mock
    UserRepository userRepository;

    @Mock
    CompanyRepository companyRepository;
}