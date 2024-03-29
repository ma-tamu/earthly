package jp.co.project.planets.earthly.webapp.controller;

import static jp.co.project.planets.earthly.webapp.constant.ModelKey.*;
import static jp.co.project.planets.earthly.webapp.test.constant.OAuthClientConstant.*;
import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.util.Lists.list;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.ModelAndViewAssert;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.WebApplicationContext;

import jp.co.project.planets.earthly.schema.db.entity.GrantType;
import jp.co.project.planets.earthly.schema.db.entity.LogoutRedirectUrl;
import jp.co.project.planets.earthly.schema.db.entity.OauthClient;
import jp.co.project.planets.earthly.schema.db.entity.OauthClientManagement;
import jp.co.project.planets.earthly.schema.db.entity.OauthClientRedirectUrl;
import jp.co.project.planets.earthly.schema.db.entity.OauthClientScope;
import jp.co.project.planets.earthly.schema.emuns.PermissionEnum;
import jp.co.project.planets.earthly.schema.emuns.Scope;
import jp.co.project.planets.earthly.schema.model.entity.OAuthClientDetailEntity;
import jp.co.project.planets.earthly.webapp.controller.form.client.OAuthClientEntryForm;
import jp.co.project.planets.earthly.webapp.security.dto.EarthlyUserInfoDto;
import jp.co.project.planets.earthly.webapp.test.util.OAuthClientGenerator;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Sql(scripts = { "classpath:/datasets/defaults.sql", "classpath:/datasets/default_oauth_clients.sql" })
class OAuthClientControllerIT {

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

    @Test
    void add_oauth_clientを付与されていない場合はNotFoundが返されること() throws Exception {

        final var userInfoDto = new EarthlyUserInfoDto("USER_ID_04", "LOGIN_ID_04", "USER_NAME_04", "Tc4NUOcdm2V34",
                false, false, false, null, null, Collections.emptyList(), Collections.emptyList());
        final var mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
        mockMvc.perform(get("/clients/entry").with(user(userInfoDto)).with(csrf())) //
                .andExpect(status().isNotFound()) //
                .andExpect(view().name("errors/404")) //
                .andReturn();
    }

    @Test
    void add_oauth_clientを付与されている場合はOAuthクライアント登録画面が返されること() throws Exception {

        final var mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
        final var mvcResult = mockMvc.perform(get("/clients/entry").with(user(ADD_OAUTH_CLIENT_USER)).with(csrf())) //
                .andExpect(status().isOk()) //
                .andExpect(view().name("clients/entry/index")) //
                .andReturn();
        final var modelAndView = mvcResult.getModelAndView();
        ModelAndViewAssert.assertModelAttributeValue(modelAndView, READ_ONLY, false);
        final var expectedForm = new OAuthClientEntryForm(null, Collections.emptyList());
        ModelAndViewAssert.assertModelAttributeValue(modelAndView, OAuthClientEntryForm.class.getSimpleName(),
                expectedForm);
    }

    @Test
    void add_oauth_clientを付与されている且つ必須項目が未入力の場合はエラーメッセージが返されること() throws Exception {
        final var formParamMap = new LinkedMultiValueMap<String, String>();
        final var mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
        final var mvcResult = mockMvc
                .perform(post("/clients/entry").params(formParamMap).with(user(ADD_OAUTH_CLIENT_USER)).with(csrf())) //
                .andExpect(status().isFound()) //
                .andExpect(view().name("redirect:/clients/entry")) //
                .andReturn();

        // verify
        final var flashMap = mvcResult.getFlashMap();
        final var bindingResult = (BindingResult) flashMap
                .get("org.springframework.validation.BindingResult.OAuthClientEntryForm");
        final var actualNameError = bindingResult.getFieldError("name");
        assertThat(actualNameError).extracting("field", "defaultMessage")
                .contains("name", "必須入力です。");
        final var actualScopeError = bindingResult.getFieldError("scope");
        assertThat(actualScopeError).extracting("field", "defaultMessage")
                .contains("scope", "必須入力です。");
    }

    @Test
    void add_oauth_clientを付与されていない且つ必須項目に入力されている場合はNotFoundが返されること() throws Exception {
        final var userInfoDto = new EarthlyUserInfoDto("USER_ID_04", "LOGIN_ID_04", "USER_NAME_04", "Tc4NUOcdm2V34",
                false, false, false, null, null, Collections.emptyList(), Collections.emptyList());

        final var formParamMap = new LinkedMultiValueMap<String, String>();
        formParamMap.add("name", "OAUTH_CLINET_NAME_01");
        formParamMap.add("scope", Scope.OPENID.getId());
        final var mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
        mockMvc.perform(post("/clients/entry").params(formParamMap).with(user(userInfoDto)).with(csrf())) //
                .andExpect(status().isNotFound()) //
                .andExpect(view().name("errors/404")) //
                .andReturn();
    }

    @Test
    void add_oauth_clientを付与されている且つ必須項目に入力されている場合に読み取り専用になっていること() throws Exception {
        final var formParamMap = new LinkedMultiValueMap<String, String>();
        formParamMap.add("name", "OAUTH_CLINET_NAME_01");
        formParamMap.addAll("scope", List.of(Scope.OPENID.getId(), Scope.ME.getId()));
        final var mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
        final var mvcResult = mockMvc
                .perform(post("/clients/entry").params(formParamMap).with(user(ADD_OAUTH_CLIENT_USER)).with(csrf())) //
                .andExpect(status().isFound()) //
                .andExpect(view().name("redirect:/clients/entry")) //
                .andReturn();

        final var flashMap = mvcResult.getFlashMap();
        final var actualReadOnly = (Boolean) flashMap.get(READ_ONLY);
        assertThat(actualReadOnly).isTrue();
    }

    @Test
    void add_oauth_clientを付与されている且つ必須項目が未入力で登録した場合にエラーメッセージが返されること() throws Exception {
        final var formParamMap = new LinkedMultiValueMap<String, String>();
        final var mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
        final var mvcResult = mockMvc
                .perform(post("/clients/create").params(formParamMap).with(user(ADD_OAUTH_CLIENT_USER)).with(csrf())) //
                .andExpect(status().isFound()) //
                .andExpect(view().name("redirect:/clients/entry")) //
                .andReturn();

        // verify
        final var flashMap = mvcResult.getFlashMap();
        final var bindingResult = (BindingResult) flashMap
                .get("org.springframework.validation.BindingResult.OAuthClientEntryForm");
        final var actualNameError = bindingResult.getFieldError("name");
        assertThat(actualNameError).extracting("field", "defaultMessage")
                .contains("name", "必須入力です。");
        final var actualScopeError = bindingResult.getFieldError("scope");
        assertThat(actualScopeError).extracting("field", "defaultMessage")
                .contains("scope", "必須入力です。");
    }

    @Test
    void add_oauth_clientを付与されていない且つ必須項目に入力されている状態で登録した場合にNotFoundが返されること() throws Exception {
        final var userInfoDto = new EarthlyUserInfoDto("USER_ID_04", "LOGIN_ID_04", "USER_NAME_04", "Tc4NUOcdm2V34",
                false, false, false, null, null, Collections.emptyList(), Collections.emptyList());

        final var formParamMap = new LinkedMultiValueMap<String, String>();
        formParamMap.add("name", "OAUTH_CLINET_NAME_01");
        formParamMap.add("scope", Scope.OPENID.getId());
        final var mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
        mockMvc.perform(post("/clients/create").params(formParamMap).with(user(userInfoDto)).with(csrf())) //
                .andExpect(status().isNotFound()) //
                .andExpect(view().name("errors/404")) //
                .andReturn();
    }

    @Test
    void add_oauth_clientを付与されている且つ必須項目に入力されている状態で登録できること() throws Exception {
        final var formParamMap = new LinkedMultiValueMap<String, String>();
        final var oauthClinetName = "OAUTH_CLINET_NAME_01";
        formParamMap.add("name", oauthClinetName);
        formParamMap.add("scope", Scope.OPENID.getId());
        final var mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
        final var mvcResult = mockMvc
                .perform(post("/clients/create").params(formParamMap).with(user(ADD_OAUTH_CLIENT_USER)).with(csrf())) //
                .andExpect(status().isFound()) //
                .andReturn();

        // verify
        final var modelAndView = mvcResult.getModelAndView();
        assertThat(modelAndView.getViewName()).startsWith("redirect:/clients/");
        final var id = modelAndView.getViewName().replace("redirect:/clients/", StringUtils.EMPTY);

        final var oauthClient = jdbcTemplate.queryForObject("""
                SELECT
                  *
                FROM
                  oauth_client
                WHERE
                  id = ?
                  AND is_deleted = 0
                """,
                BeanPropertyRowMapper.newInstance(OauthClient.class), id);
        assertThat(oauthClient).extracting("name").isEqualTo(oauthClinetName);

        final var oauthClientScopeList = jdbcTemplate.query("""
                SELECT
                  *
                FROM
                  oauth_client_scope
                WHERE
                  oauth_client_id = ?
                """, BeanPropertyRowMapper.newInstance(OauthClientScope.class), id);
        final var openid = new OauthClientScope(null, id, Scope.OPENID.getId());
        assertThat(oauthClientScopeList).usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
                .contains(openid);

        final var oauthClientManagement = jdbcTemplate.queryForObject("""
                SELECT
                  *
                FROM
                  oauth_client_management
                WHERE
                  oauth_client_id = ?
                  AND user_id = ?
                """, BeanPropertyRowMapper.newInstance(OauthClientManagement.class), id, ADD_OAUTH_CLIENT_USER.id());
        assertThat(oauthClientManagement).extracting("oauthClientId", "userId").containsExactly(id,
                ADD_OAUTH_CLIENT_USER.id());
    }

    @Test
    void view_all_oauth_clientを保持していない且つOAuthクライアントの管理者でない場合は404ページが返されること() throws Exception {
        final var mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
        // test & verify
        mockMvc.perform(get("/clients/{id}", "001").with(user(NO_PERMISSION_USER)).with(csrf())) //
                .andExpect(status().isNotFound()) //
                .andExpect(view().name("errors/404")) //
                .andReturn();
    }

    @Test
    void view_all_oauth_clientを保持している且つOAuthクライアントの管理者でない場合はOAuthクライアント詳細画面が返されること() throws Exception {

        final var userInfoDto = new EarthlyUserInfoDto("USER_ID_05", "LOGIN_ID_05",
                "USER_NAME_05", "$2a$10$IfIpdWUeKUBFd0pN6dRV/.4IT3Lsln5zuw8bZgiV.nTH/RbVRlxP2", false, false, false,
                null, null, List.of(PermissionEnum.VIEW_ALL_OAUTH_CLIENT),
                List.of(new SimpleGrantedAuthority(PermissionEnum.VIEW_ALL_OAUTH_CLIENT.name())));

        final var mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
        // test & verify
        final var mvcResult = mockMvc.perform(get("/clients/{id}", "001").with(user(userInfoDto)).with(csrf())) //
                .andExpect(status().isOk()) //
                .andExpect(view().name("clients/detail")) //
                .andReturn();

        final var modelAndView = mvcResult.getModelAndView();
        final var scopes = List.of(Scope.ME.getId());
        final var authorizationCode = new GrantType(
                jp.co.project.planets.earthly.schema.emuns.GrantType.AUTHORIZATION_CODE.getId(),
                jp.co.project.planets.earthly.schema.emuns.GrantType.AUTHORIZATION_CODE.name().toLowerCase(), null,
                "NULL", null, "NULL", false);
        final var clientCredentials = new GrantType(
                jp.co.project.planets.earthly.schema.emuns.GrantType.CLIENT_CREDENTIALS.getId(),
                jp.co.project.planets.earthly.schema.emuns.GrantType.CLIENT_CREDENTIALS.name().toLowerCase(), null,
                "NULL", null, "NULL", false);
        final var refreshToken = new GrantType(
                jp.co.project.planets.earthly.schema.emuns.GrantType.REFRESH_TOKEN.getId(),
                jp.co.project.planets.earthly.schema.emuns.GrantType.REFRESH_TOKEN.name().toLowerCase(), null, "NULL",
                null,
                "NULL", false);
        final var implicit = new GrantType(
                jp.co.project.planets.earthly.schema.emuns.GrantType.IMPLICIT.getId(),
                jp.co.project.planets.earthly.schema.emuns.GrantType.IMPLICIT.name().toLowerCase(), null, "NULL", null,
                "NULL",
                false);
        final var password = new GrantType(
                jp.co.project.planets.earthly.schema.emuns.GrantType.PASSWORD.getId(),
                jp.co.project.planets.earthly.schema.emuns.GrantType.PASSWORD.name().toLowerCase(), null, "NULL", null,
                "NULL",
                false);

        final var grantTypes = List.of(authorizationCode, clientCredentials, implicit, password, refreshToken);
        final var redirectUrl01 = new OauthClientRedirectUrl("001", "001",
                "http://127.0.0.1/dummy/login/oauth2/code/earthly", null, "NULL", null, "NULL", false);
        final var redirectUrl02 = new OauthClientRedirectUrl("002", "001",
                "http://127.0.0.1/dummy/swagger-ui/oauth2-redirect.html", null, "NULL", null, "NULL", false);
        final var redirectUrls = List.of(redirectUrl01, redirectUrl02);
        final var logoutRedirectUrl = new LogoutRedirectUrl("001", "001", "http://127.0.0.1/dummy/welcome", null,
                "NULL",
                null, "NULL", false);
        final var logoutRedirectUrls = List.of(logoutRedirectUrl);
        final var oauthClientDetailEntity = new OAuthClientDetailEntity("001", "client_1", "secret_1", "OAuthクライアント1",
                scopes, grantTypes, redirectUrls, logoutRedirectUrls, Collections.emptyList());

        ModelAndViewAssert.assertModelAttributeValue(modelAndView, "canEditableClient", false);
        final var model = modelAndView.getModel();
        final var oauthClientActual = model.get("OAuthClientDetailEntity");
        assertThat(oauthClientActual).usingRecursiveComparison().ignoringFieldsOfTypes(LocalDateTime.class)
                .ignoringFields("createdAt", "updatedAt").isEqualTo(oauthClientDetailEntity);

    }

    @Autowired
    WebApplicationContext context;

    @Autowired
    JdbcTemplate jdbcTemplate;

    static final EarthlyUserInfoDto ADD_OAUTH_CLIENT_USER = new EarthlyUserInfoDto("USER_ID_01", "LOGIN_ID_01",
            "USER_NAME_01", "$2a$10$IfIpdWUeKUBFd0pN6dRV/.4IT3Lsln5zuw8bZgiV.nTH/RbVRlxP2", false, false, false, null,
            null, List.of(PermissionEnum.ADD_OAUTH_CLIENT),
            List.of(new SimpleGrantedAuthority(PermissionEnum.ADD_OAUTH_CLIENT.name())));

    static final EarthlyUserInfoDto NO_PERMISSION_USER = new EarthlyUserInfoDto("USER_ID_04", "LOGIN_ID_04",
            "USER_NAME_04", "Tc4NUOcdm2V34", false, false, false, null, null, Collections.emptyList(),
            Collections.emptyList());

}