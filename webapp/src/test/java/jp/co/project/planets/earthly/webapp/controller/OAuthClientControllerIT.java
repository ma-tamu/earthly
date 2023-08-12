package jp.co.project.planets.earthly.webapp.controller;

import static jp.co.project.planets.earthly.webapp.test.constant.OAuthClientConstant.*;
import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.util.Lists.list;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.ModelAndViewAssert;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import jp.co.project.planets.earthly.schema.emuns.PermissionEnum;
import jp.co.project.planets.earthly.webapp.security.dto.EarthlyUserInfoDto;
import jp.co.project.planets.earthly.webapp.test.util.OAuthClientGenerator;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Sql(scripts = { "classpath:/datasets/defaults.sql", "classpath:/datasets/default_oauth_clients.sql" })
class OAuthClientControllerIT {

    @Autowired
    WebApplicationContext context;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void view_all_oauth_clientを付与されているユーザーでOAuthクライアントリストにアクセスすると閲覧できるOAuthクライアントが返される() throws Exception {

        final var userInfoDto = new EarthlyUserInfoDto("USER_ID_01", "LOGIN_ID_01", "USER_NAME_01",
                "$2a$10$IfIpdWUeKUBFd0pN6dRV/.4IT3Lsln5zuw8bZgiV.nTH/RbVRlxP2", false, false, false, null, null,
                List.of(PermissionEnum.VIEW_ALL_OAUTH_CLIENT),
                List.of(new SimpleGrantedAuthority(PermissionEnum.VIEW_ALL_OAUTH_CLIENT.name())));

        // test
        final var mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
        final var result = mockMvc.perform(get("/clients").with(user(userInfoDto)).with(csrf())) //
                .andExpect(status().isOk())//
                .andExpect(view().name("clients/index")) //
                .andReturn();

        final var oauthClientList = OAuthClientGenerator.generate(OAuthClient001.INSTANCES, OAUthClient002.INSTANCES,
                OAUthClient003.INSTANCES, OAUthClient004.INSTANCES, OAUthClient005.INSTANCES, OAUthClient006.INSTANCES,
                OAUthClient007.INSTANCES, OAUthClient008.INSTANCES, OAUthClient009.INSTANCES, OAUthClient010.INSTANCES);

        // verify
        final var modelAndView = result.getModelAndView();
        final var actualPage = ModelAndViewAssert.assertAndReturnModelAttributeOfType(modelAndView, "pageImpl",
                Page.class);
        assertThat(list(actualPage.getContent()))
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("createdAt", "updatedAt")
                .containsExactly(oauthClientList);
        assertThat(actualPage.getTotalPages()).isEqualTo(2);
        assertThat(actualPage.getSize()).isEqualTo(10);
    }

    @Test
    void view_all_oauth_clientを付与されていない且つ管理するクライアントもない場合にOAuthクライアントリストにアクセスするとNotFoundがかえされること() throws Exception {

        final var userInfoDto = new EarthlyUserInfoDto("USER_ID_04", "LOGIN_ID_04", "USER_NAME_04", "Tc4NUOcdm2V34",
                false, false, false, null, null, Collections.emptyList(), Collections.emptyList());

        // test
        final var mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
        mockMvc.perform(get("/clients").with(user(userInfoDto)).with(csrf())) //
                .andExpect(status().isNotFound()) //
                .andExpect(view().name("errors/404")) //
                .andReturn();
    }
}