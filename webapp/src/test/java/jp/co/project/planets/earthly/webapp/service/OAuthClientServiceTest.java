package jp.co.project.planets.earthly.webapp.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;

import jp.co.project.planets.earthly.common.logic.OAuthClientLogic;
import jp.co.project.planets.earthly.schema.db.entity.GrantType;
import jp.co.project.planets.earthly.schema.db.entity.LogoutRedirectUrl;
import jp.co.project.planets.earthly.schema.db.entity.OauthClientRedirectUrl;
import jp.co.project.planets.earthly.schema.emuns.PermissionEnum;
import jp.co.project.planets.earthly.schema.emuns.Scope;
import jp.co.project.planets.earthly.schema.model.entity.OAuthClientDetailEntity;
import jp.co.project.planets.earthly.schema.model.entity.OAuthClientManagementUserEntity;
import jp.co.project.planets.earthly.schema.repository.OAuthClientRepository;
import jp.co.project.planets.earthly.webapp.emuns.ErrorCode;
import jp.co.project.planets.earthly.webapp.exception.ForbiddenException;
import jp.co.project.planets.earthly.webapp.exception.NotFoundException;
import jp.co.project.planets.earthly.webapp.model.dto.OAuthClientDetailDto;
import jp.co.project.planets.earthly.webapp.security.dto.EarthlyUserInfoDto;
import jp.co.project.planets.earthly.webapp.test.constant.OAuthClientConstant;

@Tag("unit")
@ExtendWith(MockitoExtension.class)
class OAuthClientServiceTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void OAuthクライアントが1件も閲覧できないためForbiddenExceptionが発生すること() {

        when(oauthClientRepository.findByAccessible(anyList(), anyString())).thenReturn(Collections.emptyList());

        // test & verify
        final var userInfoDto = new EarthlyUserInfoDto("dummy", null, null, null, false, false, true, null, null, Collections.emptyList(),
                null);
        final var expected = new ForbiddenException(ErrorCode.EWA4XX016);
        assertThatThrownBy(() -> oauthClientService.validateAccessibleClient(userInfoDto)).isInstanceOfSatisfying(
                ForbiddenException.class, e -> assertThat(e).usingRecursiveComparison().isEqualTo(expected));
    }

    @Test
    void add_oauth_clientパーミッションを保持していない場合にForbiddenExceptionが発生すること() {

        // test & verify
        final var userInfoDto = new EarthlyUserInfoDto("dummy", null, null, null, false, false, true, null, null,
                Collections.emptyList(), null);
        final var expected = new ForbiddenException(ErrorCode.EWA4XX017);
        assertThatThrownBy(() -> oauthClientService.validateEntryPermission(userInfoDto)).isInstanceOfSatisfying(
                ForbiddenException.class, e -> assertThat(e).usingRecursiveComparison().isEqualTo(expected));
    }

    @Test
    void 対象OAuthクライアントの閲覧権限がない場合にForbiddenExceptionが発生すること() {
        when(oauthClientLogic.canAccessibleClient(anyString(), anyList(), anyString())).thenReturn(false);

        // test & verify
        final var userInfoDto = new EarthlyUserInfoDto("dummy", null, null, null, false, false, true, null, null, Collections.emptyList(),
                null);
        final var expected = new ForbiddenException(ErrorCode.EWA4XX016);
        assertThatThrownBy(() -> oauthClientService.get("dummy", userInfoDto)).isInstanceOfSatisfying(
                ForbiddenException.class, e -> assertThat(e).usingRecursiveComparison().isEqualTo(expected));
    }

    @Test
    void view_all_oauth_clientを保持しているが対象OAuthクライアントが存在しない場合にNotFoundExceptionが返されること() {
        when(oauthClientLogic.canAccessibleClient(anyString(), anyList(), anyString())).thenReturn(true);
        when(oauthClientRepository.findAccessibleById(anyString(), anyList(), anyString())).thenReturn(Optional.empty());

        // test & verify
        final var userInfoDto = new EarthlyUserInfoDto("dummy", null, null, null, false, false, true, null, null, Collections.emptyList(),
                null);
        final var expected = new NotFoundException(ErrorCode.EWA4XX018);
        assertThatThrownBy(() -> oauthClientService.get("dummy", userInfoDto)).isInstanceOfSatisfying(
                NotFoundException.class, e -> assertThat(e).usingRecursiveComparison().isEqualTo(expected));

    }

    @Test
    void 対象OAuthクライアントの閲覧権限がある場合はOAuthクライアントDTOが返されること() {
        when(oauthClientLogic.canAccessibleClient(anyString(), anyList(), anyString())).thenReturn(true);
        final var client001 = OAuthClientConstant.OAuthClient001.INSTANCES;
        final var oauthClientDetailEntity = getOAuthClientDetailEntity(client001);
        when(oauthClientRepository.findAccessibleById(anyString(), anyList(), anyString())).thenReturn(Optional.of(oauthClientDetailEntity));
        final var userInfoDto = new EarthlyUserInfoDto("dummy", null, null, null, false, false, true, null, null, List.of(PermissionEnum.VIEW_ALL_OAUTH_CLIENT),
                null);
        when(oauthClientLogic.canEditableClient(anyString(), anyList(), anyString())).thenReturn(true);

        // test
        final var actual = oauthClientService.get(client001.getId(), userInfoDto);

        // verify
        final var redirectUrlPage = new PageImpl<>(oauthClientDetailEntity.redirectUrls());
        final var logoutRedirectUrlPage = new PageImpl<>(oauthClientDetailEntity.logoutRedirectUrls());
        final var managementUserPage = new PageImpl<>(oauthClientDetailEntity.managementUserList());
        final var expected = new OAuthClientDetailDto(oauthClientDetailEntity, redirectUrlPage, logoutRedirectUrlPage, managementUserPage, true);
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @NotNull
    private static OAuthClientDetailEntity getOAuthClientDetailEntity(final OAuthClientConstant.OAuthClient001 client) {
        final var scopes = List.of(Scope.OPENID.getId());
        final var authorizationCodeEnum = jp.co.project.planets.earthly.schema.emuns.GrantType.AUTHORIZATION_CODE;
        final var authorizationCode = new GrantType(authorizationCodeEnum.getId(), authorizationCodeEnum.getValue(),
                null, null, null, null, false);
        final var grantTypes = List.of(authorizationCode);
        final var redirectUrl = new OauthClientRedirectUrl("dummy", client.getId(), "http://127.0.0.1/dummy/callback",
                null, null, null, null, false);
        final var redirectUrls = List.of(redirectUrl);
        final var logoutRedirectUrl = new LogoutRedirectUrl("dummy", client.getId(), "http://127.0.0.1/dummy/logout",
                null, null, null, null, false);
        final var logoutRedirectUrls = List.of(logoutRedirectUrl);
        final var managementUser = new OAuthClientManagementUserEntity("CLIENT_MANAGEMENT_USER_ID_01", "CLIENT_NAME_01",
                "NULL", "admin", "COMPANY_ID_01", "COMPANY_NAME_01");
        final var managementUsers = List.of(managementUser);
        return new OAuthClientDetailEntity(client.getId(), client.getClientId(), client.getSecret(),
                client.getName(), scopes, grantTypes, redirectUrls, logoutRedirectUrls, managementUsers);
    }

    @InjectMocks
    OAuthClientService oauthClientService;
    @Mock
    OAuthClientRepository oauthClientRepository;
    @Mock
    OAuthClientLogic oauthClientLogic;

}