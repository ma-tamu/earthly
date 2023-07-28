package jp.co.project.planets.earthly.webapp.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Collections;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import jp.co.project.planets.earthly.schema.repository.OAuthClientRepository;
import jp.co.project.planets.earthly.webapp.emuns.ErrorCode;
import jp.co.project.planets.earthly.webapp.exception.ForbiddenException;
import jp.co.project.planets.earthly.webapp.security.dto.EarthlyUserInfoDto;

@Tag("unit")
@ExtendWith(MockitoExtension.class)
class OAuthClientServiceTest {

    @InjectMocks
    OAuthClientService oauthClientService;
    @Mock
    OAuthClientRepository oauthClientRepository;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void OAuthクライアントが1件も閲覧できないためForbiddenExceptionが発生する() {

        when(oauthClientRepository.findByAccessible(anyList(), anyString())).thenReturn(Collections.emptyList());

        // test & verify
        final var userInfoDto = new EarthlyUserInfoDto("dummy", null, null, null, false, false, true, null, null, Collections.emptyList(),
                null);
        final var expected = new ForbiddenException(ErrorCode.EWA4XX016);
        assertThatThrownBy(() -> oauthClientService.validateAccessibleClient(userInfoDto)).isInstanceOfSatisfying(
                ForbiddenException.class, e -> assertThat(e).usingRecursiveComparison().isEqualTo(expected));
    }
}