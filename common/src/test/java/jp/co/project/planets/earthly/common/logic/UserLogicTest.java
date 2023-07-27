package jp.co.project.planets.earthly.common.logic;

import static jp.co.project.planets.earthly.common.test.CompanyConstant.*;
import static jp.co.project.planets.earthly.common.test.UserConstant.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import jp.co.project.planets.earthly.common.model.dto.UserDto;
import jp.co.project.planets.earthly.schema.db.entity.User;
import jp.co.project.planets.earthly.schema.repository.PasswordTokenRepository;
import jp.co.project.planets.earthly.schema.repository.UserRepository;

@Tag("unit")
@ExtendWith(MockitoExtension.class)
class UserLogicTest {

    @InjectMocks
    UserLogic userLogic;
    @Mock
    UserRepository userRepository;
    @Mock
    PasswordTokenRepository passwordTokenRepository;
    @Mock
    RoleLogic roleLogic;
    @Mock
    TotpLogic totpLogic;
    @Mock
    CryptoLogic cryptoLogic;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void 入力された内容でユーザーが作成されること() {

        when(userRepository.insert(any(User.class))).thenReturn(1);
        final var user = new User(User01.ID, User01.LOGIN_ID, User01.NAME, User01.GENDER, User01.LANGUAGE, User01.TIMEZONE, User01.MAIL, DUMMY_PASSWORD, User01.LOCKOUT, User01.MFA, MFA_DUMMY_SECRET, User01.COMPANY, null, OPERATOR, null, OPERATOR, false);
        when(userRepository.findByLoginId(anyString())).thenReturn(Optional.of(user));
        when(totpLogic.generateSecret()).thenReturn(MFA_DUMMY_SECRET);
        when(cryptoLogic.encodeSHA256(anyString())).thenReturn(DUMMY_PASSWORD);

        // test
        final var userDto = new UserDto(User01.LOGIN_ID, User01.NAME, User01.MAIL, User01.GENDER, User01.LANGUAGE, User01.TIMEZONE, User01.COMPANY, Company01.NAME, User01.LOCKOUT, User01.MFA);
        final var actual = userLogic.create(userDto, OPERATOR);

        // verify
        final var expected = new User(User01.ID, User01.LOGIN_ID, User01.NAME, User01.GENDER, User01.LANGUAGE, User01.TIMEZONE, User01.MAIL, DUMMY_PASSWORD, User01.LOCKOUT, User01.MFA, MFA_DUMMY_SECRET, User01.COMPANY, null, OPERATOR, null, OPERATOR, false);
        assertThat(actual).isPresent().get().usingRecursiveComparison().ignoringFieldsOfTypes(LocalDateTime.class).isEqualTo(expected);
    }

    @Test
    void ユーザーテーブルに登録失敗() {
        when(totpLogic.generateSecret()).thenReturn(MFA_DUMMY_SECRET);
        when(userRepository.insert(any(User.class))).thenReturn(0);

        // test
        final var userDto = new UserDto(User01.LOGIN_ID, User01.NAME, User01.MAIL, User01.GENDER, User01.LANGUAGE, User01.TIMEZONE, User01.COMPANY, Company01.NAME, User01.LOCKOUT, User01.MFA);
        final var actual = userLogic.create(userDto, OPERATOR);

        // verify
        assertThat(actual).isEmpty();
    }

    @Test
    void 登録したユーザーがログインIDで取得できない() {
        when(totpLogic.generateSecret()).thenReturn(MFA_DUMMY_SECRET);
        when(userRepository.insert(any(User.class))).thenReturn(1);
        when(userRepository.findByLoginId(anyString())).thenReturn(Optional.empty());

        // test
        final var userDto = new UserDto(User01.LOGIN_ID, User01.NAME, User01.MAIL, User01.GENDER, User01.LANGUAGE, User01.TIMEZONE, User01.COMPANY, Company01.NAME, User01.LOCKOUT, User01.MFA);
        final var actual = userLogic.create(userDto, OPERATOR);

        // verify
        assertThat(actual).isEmpty();
    }
}