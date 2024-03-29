package jp.co.project.planets.earthly.webapp.security.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import jp.co.project.planets.earthly.core.enums.Timezone;
import jp.co.project.planets.earthly.schema.db.entity.Company;
import jp.co.project.planets.earthly.schema.db.entity.User;
import jp.co.project.planets.earthly.schema.emuns.GenderEnum;
import jp.co.project.planets.earthly.schema.emuns.PermissionEnum;
import jp.co.project.planets.earthly.schema.repository.CompanyRepository;
import jp.co.project.planets.earthly.schema.repository.UserRepository;
import jp.co.project.planets.earthly.webapp.logic.PermissionLogic;
import jp.co.project.planets.earthly.webapp.security.dto.CompanyDto;
import jp.co.project.planets.earthly.webapp.security.dto.EarthlyUserInfoDto;

@Tag("unit")
@ExtendWith(MockitoExtension.class)
class DaoUserDetailServiceTest {

    @Test
    void ログインIDに紐づくユーザーが存在しない場合にExceptionが発生すること() {

        when(userRepository.findByLoginId(any())).thenReturn(Optional.empty());

        try {
            // test
            daoUserDetailService.loadUserByUsername("hoge");
        } catch (final UsernameNotFoundException e) {
            // verify
            assertThat(e).hasMessage("user not found.");
        }
    }

    @Test
    void ログインIDに紐づくユーザーが存在する場合にUserDetailsがかえされること() {

        final var user = new User("USER_ID_01", "LOGIN_ID_01", "USER_NAME_01", GenderEnum.MALE.getValue(), "ja",
                Timezone.ASIA_TOKYO.getId(), "norelay@exanmep.com", "PASSWORD", false, false, null, "COMPANY_ID_01",
                null, null, null, null, false);
        when(userRepository.findByLoginId(eq("LOGIN_ID_01"))).thenReturn(Optional.of(user));

        final var permissionEnumList = List.of(PermissionEnum.VIEW_ALL_USER, PermissionEnum.ADD_USER,
                PermissionEnum.EDIT_USER);
        when(permissionLogic.findPermissionEnumListByUserId(eq("USER_ID_01"))).thenReturn(permissionEnumList);

        final var company = new Company("COMPANY_ID_01", "COMPANY_NAME_01", null, null, null, null, null, false);
        when(companyRepository.findByAccessiblePrimaryKey(anyString(), anyList(), anyString()))
                .thenReturn(Optional.of(company));

        // test
        final var actual = daoUserDetailService.loadUserByUsername("LOGIN_ID_01");

        // verify
        final var expected = new EarthlyUserInfoDto("USER_ID_01", "LOGIN_ID_01", "USER_NAME_01", "PASSWORD", false,
                false, false, null, new CompanyDto("COMPANY_ID_01", "COMPANY_NAME_01"), permissionEnumList,
                List.of(new SimpleGrantedAuthority(PermissionEnum.VIEW_ALL_USER.name()),
                        new SimpleGrantedAuthority(PermissionEnum.ADD_USER.name()),
                        new SimpleGrantedAuthority(PermissionEnum.EDIT_USER.name())));
        assertThat(actual).isInstanceOf(EarthlyUserInfoDto.class).usingDefaultComparator().isEqualTo(expected);
    }

    @InjectMocks
    DaoUserDetailService daoUserDetailService;

    @Mock
    UserRepository userRepository;
    @Mock
    CompanyRepository companyRepository;
    @Mock
    PermissionLogic permissionLogic;

}