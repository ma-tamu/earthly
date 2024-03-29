package jp.co.project.planets.earthly.webapp.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collections;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import jp.co.project.planets.earthly.core.enums.Timezone;
import jp.co.project.planets.earthly.schema.model.entity.BelongCompanyEntity;
import jp.co.project.planets.earthly.schema.model.entity.CountryEntity;
import jp.co.project.planets.earthly.schema.model.entity.LanguageEntity;
import jp.co.project.planets.earthly.schema.model.entity.RegionEntity;
import jp.co.project.planets.earthly.schema.model.entity.UserEntity;
import jp.co.project.planets.earthly.webapp.model.dto.UserDetailDto;
import jp.co.project.planets.earthly.webapp.security.dto.EarthlyUserInfoDto;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class UserControllerIT {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @Sql(scripts = { "classpath:/datasets/defaults.sql" })
    void 指定したユーザーIDのユーザー情報が取得できること() throws Exception {
        final var userInfoDto = new EarthlyUserInfoDto("USER_ID_01", "LOGIN_ID_01", "USER_NAME_01",
                "$2a$10$IfIpdWUeKUBFd0pN6dRV/.4IT3Lsln5zuw8bZgiV.nTH/RbVRlxP2", false, false, false, null, null,
                Collections.emptyList(), Collections.emptyList());

        final var regionEntity = new RegionEntity("45bd917836a599faf2a30c54d677d9a6", "Asia");
        final var languageEntity = new LanguageEntity("19154223c579d44a0fe8c9e5476d8f5e", "japanese");
        final var countryEntity = new CountryEntity("d92bf311652a77227d2725c204a5396b", "Japan", languageEntity,
                regionEntity);
        final var belongCompanyEntity = new BelongCompanyEntity("COMPANY_ID_01", "COMPANY_NAME_01", countryEntity);
        final var userEntity = new UserEntity("USER_ID_01", "LOGIN_ID_01", "USER_NAME_01", "M",
                "orren_hannana7co@conservation.fm", "T4gXZYL6qQK84", "ja", Timezone.ASIA_TOKYO.getId(), false, false,
                "NULL", belongCompanyEntity, Collections.emptyList(), Collections.emptyList(),
                LocalDateTime.of(2018, Month.JULY, 13, 15, 59, 03), null,
                LocalDateTime.of(2018, Month.JULY, 13, 15, 59, 03), null, Boolean.FALSE);
        final var expected = new UserDetailDto(userEntity, null);

        // test & verify
        final var mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
        final var result = mockMvc.perform(get("/users/USER_ID_01").with(user(userInfoDto)).with(csrf())) //
                .andExpect(status().isOk())//
                .andExpect(view().name("users/detail")) //
                .andExpect(model().attribute("userDetailDto", expected)) //
                .andReturn();
    }

    @Autowired
    WebApplicationContext context;
}