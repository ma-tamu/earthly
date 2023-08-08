package jp.co.project.planets.earthly.schema.repository;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import jp.co.project.planets.earthly.core.enums.Timezone;
import jp.co.project.planets.earthly.schema.TestConfig;
import jp.co.project.planets.earthly.schema.db.entity.Role;
import jp.co.project.planets.earthly.schema.emuns.GenderEnum;
import jp.co.project.planets.earthly.schema.emuns.PermissionEnum;
import jp.co.project.planets.earthly.schema.model.entity.BelongCompanyEntity;
import jp.co.project.planets.earthly.schema.model.entity.CountryEntity;
import jp.co.project.planets.earthly.schema.model.entity.LanguageEntity;
import jp.co.project.planets.earthly.schema.model.entity.RegionEntity;
import jp.co.project.planets.earthly.schema.model.entity.UserEntity;
import jp.co.project.planets.earthly.schema.test.emuns.CountryEnum;
import jp.co.project.planets.earthly.schema.test.emuns.LanguageEnum;
import jp.co.project.planets.earthly.schema.test.emuns.RegionEnum;

@SpringBootTest(classes = TestConfig.class)
@Transactional
@Sql(scripts = { "classpath:/datasets/users.sql" })
class UserRepositoryTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @Disabled
    void 取得対象のユーザーIDが操作ユーザーで閲覧できない場合にEmptyのOptionalが返されること() {

        // test
        final var actual = userRepository.findAccessibleByPrimaryKey("NOT_FOUND_ID", Collections.emptyList(),
                "USER_ID_01");

        // verify
        assertThat(actual).isEmpty();
    }

    @Test
    @Disabled
    void 操作ユーザーが閲覧できない会社のユーザーを取得しようとした場合にOptionalが返されること() {

        // test
        final var actual = userRepository.findAccessibleByPrimaryKey("USER_ID_15",
                List.of(PermissionEnum.VIEW_ALL_COMPANY), "USER_ID_01");

        // verify
        assertThat(actual).isEmpty();
    }

    @Test
    @Disabled
    void 対象ユーザーが取得でき操作ユーザーが閲覧できるロールが閲覧できること() {

        // test
        final var actual = userRepository.findAccessibleByPrimaryKey("USER_ID_02", Collections.emptyList(),
                "USER_ID_01");

        final var regionEntity = new RegionEntity(RegionEnum.ASIA.getId(), RegionEnum.ASIA.getName());
        final var languageEntity = new LanguageEntity(LanguageEnum.JAPANESE.getId(), LanguageEnum.JAPANESE.getName());
        final var countryEntity = new CountryEntity(CountryEnum.JAPAN.getId(), CountryEnum.JAPAN.getName(),
                languageEntity, regionEntity);
        final var belongCompanyEntity = new BelongCompanyEntity("COMPANY_ID_01", "COMPANY_NAME_01", countryEntity);
        final var role01 = new Role("ROLE_ID_01", "ROLE_NAME_01", null, "NULL", null, "NULL", false);
        final var roleList = List.of(role01);
        final var expected = new UserEntity("USER_ID_02", "LOGIN_ID_02", "USER_NAME_02", GenderEnum.MALE.getValue(),
                "algie_dolanqyj@prize.gtt", "$2a$10$IfIpdWUeKUBFd0pN6dRV/.4IT3Lsln5zuw8bZgiV.nTH/RbVRlxP2", "ja",
                Timezone.ASIA_TOKYO.getId(), Boolean.FALSE, Boolean.FALSE, null, belongCompanyEntity, roleList,
                Collections.emptyList(), LocalDateTime.of(2022, Month.AUGUST, 3, 13, 18, 12), null,
                LocalDateTime.of(2022, Month.AUGUST, 14, 14, 46, 59), null, Boolean.FALSE);

        // verify
        assertThat(actual).isPresent().get().usingRecursiveComparison().ignoringFieldsOfTypes(LocalDateTime.class)
                .isEqualTo(expected);
    }

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    UserRepository userRepository;
}