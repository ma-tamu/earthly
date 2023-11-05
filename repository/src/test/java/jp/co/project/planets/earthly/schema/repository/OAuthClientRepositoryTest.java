package jp.co.project.planets.earthly.schema.repository;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import jp.co.project.planets.earthly.schema.TestConfig;
import jp.co.project.planets.earthly.schema.db.entity.GrantType;
import jp.co.project.planets.earthly.schema.db.entity.Scope;
import jp.co.project.planets.earthly.schema.model.entity.OAuthClientEntity;

@SpringBootTest(classes = TestConfig.class)
@Transactional
class OAuthClientRepositoryTest {

    static final Scope ME = new Scope("15b4a732660d11ec8a0b0242ac120003", "me", null, "NULL", null, "NULL", false);
    static final Scope OPENID = new Scope("a85c1c6665fc11ec8a0b0242ac120003", "openid", null, "NULL", null, "NULL",
            false);
    static final GrantType CLIENT_CREDENTIALS = new GrantType("092519c3660d11ec8a0b0242ac120003", "client_credentials",
            null, "NULL", null, "NULL", false);
    static final GrantType PASSWORD = new GrantType("0f4c8f7b660d11ec8a0b0242ac120003", "password", null, "NULL", null,
            "NULL", false);
    static final GrantType AUTHORIZATION_CODE = new GrantType("1b6a0d2a65f911ec8a0b0242ac120003", "authorization_code",
            null, "NULL", null, "NULL", false);
    static final GrantType IMPLICIT = new GrantType("f35c63dd660c11ec8a0b0242ac120003", "implicit", null, "NULL", null,
            "NULL", false);
    static final GrantType REFRESH_TOKEN = new GrantType("fdc24af6660c11ec8a0b0242ac120003", "refresh_token", null,
            "NULL", null, "NULL", false);
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    OAuthClientRepository oauthClientRepository;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void OAuthクライアントIDに紐づくOAuthクライアントが存在しない場合にnullを返すこと() {
        // test
        final var actual = oauthClientRepository.findById("NOT_FOUND_ID");

        // verify
        assertThat(actual).isNull();
    }

    @Test
    @Sql(scripts = { "classpath:/datasets/default_oauth_clients.sql" })
    void OAuthクライアントIDに紐づくOAuthクライアントが存在する場合はEntityが返されること() {
        // test
        final var actual = oauthClientRepository.findById("1");

        final var scopes = List.of(ME);
        final var grantTypes = List.of(AUTHORIZATION_CODE, CLIENT_CREDENTIALS, IMPLICIT, PASSWORD, REFRESH_TOKEN);
        final var redirectUrls = List.of("http://127.0.0.1/dummy/login/oauth2/code/earthly",
                "http://127.0.0.1/dummy/swagger-ui/oauth2-redirect.html");
        final var logoutRedirectUrls = List.of("http://127.0.0.1/dummy/welcome");
        final var expected = new OAuthClientEntity("1", "client_1", "secret_1", "OAuthクライアント1", scopes, grantTypes,
                redirectUrls, logoutRedirectUrls);
        // verify
        assertThat(actual).usingRecursiveComparison().ignoringFieldsOfTypes(LocalDateTime.class)
                .ignoringFields("createdAt", "updatedAt").isEqualTo(expected);
    }

    @Test
    void クライアントIDに紐づくOAuthクライアントが存在しない場合にnullを返すこと() {
        // test
        final var actual = oauthClientRepository.findByClientId("NOT_FOUND_CLIENT_ID");

        // verify
        assertThat(actual).isNull();
    }

    @Test
    @Sql(scripts = { "classpath:/datasets/default_oauth_clients.sql" })
    void クライアントIDに紐づくOAuthクライアントが存在する場合はEntityが返されること() {
        // test
        final var actual = oauthClientRepository.findByClientId("client_2");

        final var scopes = List.of(ME, OPENID);
        final var grantTypes = List.of(AUTHORIZATION_CODE, CLIENT_CREDENTIALS, IMPLICIT, PASSWORD, REFRESH_TOKEN);
        final var redirectUrls = List.of("http://127.0.0.1/dummy-oidc/login/oauth2/code/earthly",
                "http://127.0.0.1/dummy-oidc/swagger-ui/oauth2-redirect.html");
        final var logoutRedirectUrls = List.of("http://127.0.0.1/dummy-oidc/welcome");
        final var expected = new OAuthClientEntity("2", "client_2", "secret_2", "OIDCクライアント2", scopes, grantTypes,
                redirectUrls, logoutRedirectUrls);
        // verify
        assertThat(actual).usingRecursiveComparison().ignoringFieldsOfTypes(LocalDateTime.class)
                .ignoringFields("createdAt", "updatedAt").isEqualTo(expected);
    }

}