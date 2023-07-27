package jp.co.project.planets.earthly.common.logic;

import static org.assertj.core.api.Assertions.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@Tag("unit")
@ExtendWith(MockitoExtension.class)
class CryptoLogicTest {

    @InjectMocks
    CryptoLogic cryptoLogic;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void 入力された文字列がSHA256で暗号化されていること() {

        // test
        final var actual = cryptoLogic.encodeSHA256("test-input");

        // verify
        assertThat(actual).matches("[a-f0-9]{64}");
    }

    @Test
    void NoSuchAlgorithmExceptionが発生すること() {

        try (final var mocked = Mockito.mockStatic(MessageDigest.class)) {

            final var noSuchAlgorithmException = new NoSuchAlgorithmException();
            mocked.when(() -> MessageDigest.getInstance("SHA-256")).thenThrow(noSuchAlgorithmException);

            // test & verify
            assertThatThrownBy(() -> cryptoLogic.encodeSHA256("test-input")).isInstanceOfSatisfying(
                    RuntimeException.class,
                    e -> assertThat(e).cause().isInstanceOf(NoSuchAlgorithmException.class));
        }

    }
}