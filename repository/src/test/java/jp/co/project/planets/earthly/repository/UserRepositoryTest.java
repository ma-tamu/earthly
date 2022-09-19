package jp.co.project.planets.earthly.repository;

import jp.co.project.planets.earthly.TestConfig;
import jp.co.project.planets.earthly.db.entity.User;
import jp.co.project.planets.earthly.emuns.GenderEnum;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest(classes = TestConfig.class)
@Transactional
class UserRepositoryTest {

    @BeforeEach
    void setUp() {
        final var user01 = new User("USER_ID_01", "LOGIN_ID_01", "USER_NAME_01", GenderEnum.MALE.getValue(),
                "teah_haralsonh51@distribute.hm", "VzXBzzZG9tPqhmZvU", false, "COMPANY_ID_01", LocalDateTime.now(),
                "NULL", LocalDateTime.now(), "NULL", false);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void 取得対象のユーザーIDが操作ユーザーで閲覧できない場合にEmptyのOptionalが返されること() {
        final var actual = userRepository.findAccessibleByPrimaryKey("NOT_FOUND_ID",
                Collections.emptyList(), "USER_ID_01");
        assertThat(actual).isEmpty();
    }

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    UserRepository userRepository;
}