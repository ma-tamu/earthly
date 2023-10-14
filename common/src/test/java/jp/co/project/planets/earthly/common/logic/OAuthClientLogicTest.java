package jp.co.project.planets.earthly.common.logic;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import jp.co.project.planets.earthly.common.enums.Scope;
import jp.co.project.planets.earthly.schema.db.entity.OauthClient;
import jp.co.project.planets.earthly.schema.repository.OAuthClientRepository;
import jp.co.project.planets.earthly.schema.repository.OAuthClientScopeRepository;

@Tag("unit")
@ExtendWith(MockitoExtension.class)
class OAuthClientLogicTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void OAuthクライアントの登録に失敗した場合にnullが返されること() {

        when(oauthClientRepository.insert(any(OauthClient.class))).thenReturn(0);

        // test
        final var scopes = List.of(Scope.OPENID.getValue());
        final var actual = oauthClientLogic.create(null, scopes, null);

        // verify
        assertThat(actual).isNull();
    }

    @Test
    void 登録したOAuthクライアントが取得できなかった場合にnullが返される() {

        when(oauthClientRepository.insert(any(OauthClient.class))).thenReturn(1);
        when(oauthClientRepository.findAccessibleByName(anyString(), anyList(), anyString())).thenReturn(Optional.empty());

        // test
        final var scopes = List.of(Scope.OPENID.getValue());
        final var actual = oauthClientLogic.create("test_oauth_client_name", scopes, "USER_ID_01");

        // verify
        assertThat(actual).isNull();

    }

    @InjectMocks
    OAuthClientLogic oauthClientLogic;

    @Mock
    OAuthClientRepository oauthClientRepository;
    @Mock
    OAuthClientScopeRepository oauthClientScopeRepository;
    @Mock
    CryptoLogic cryptoLogic;

}