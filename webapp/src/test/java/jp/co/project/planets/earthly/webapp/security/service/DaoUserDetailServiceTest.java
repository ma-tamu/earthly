package jp.co.project.planets.earthly.webapp.security.service;

import jp.co.project.planets.earthly.db.entity.User;
import jp.co.project.planets.earthly.emuns.GenderEnum;
import jp.co.project.planets.earthly.emuns.PermissionEnum;
import jp.co.project.planets.earthly.repository.UserRepository;
import jp.co.project.planets.earthly.webapp.logic.PermissionLogic;
import jp.co.project.planets.earthly.webapp.security.dto.EarthlyUserInfoDto;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

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

        final var user = new User("USER_ID_01", "LOGIN_ID_01", "USER_NAME_01", GenderEnum.MALE.getValue(),
                "norelay@exanmep.com", "PASSWORD", false, "COMPANY_ID_01", null, null, null, null, false);
        when(userRepository.findByLoginId(eq("LOGIN_ID_01"))).thenReturn(Optional.of(user));

        final var permissionEnumList = List.of(PermissionEnum.VIEW_ALL_USER, PermissionEnum.ADD_USER,
                PermissionEnum.EDIT_USER);
        when(permissionLogic.findPermissionEnumListByUserId(eq("USER_ID_01"))).thenReturn(permissionEnumList);

        // test
        final var actual = daoUserDetailService.loadUserByUsername("LOGIN_ID_01");

        // verify
        final var expected = new EarthlyUserInfoDto("USER_ID_01", "LOGIN_ID_01", "USER_NAME_01", "PASSWORD", false,
                permissionEnumList, List.of(new SimpleGrantedAuthority(PermissionEnum.VIEW_ALL_USER.name()),
                new SimpleGrantedAuthority(PermissionEnum.ADD_USER.name()),
                new SimpleGrantedAuthority(PermissionEnum.EDIT_USER.name())));
        assertThat(actual).isInstanceOf(EarthlyUserInfoDto.class).usingDefaultComparator().isEqualTo(expected);
    }

    @InjectMocks
    DaoUserDetailService daoUserDetailService;

    @Mock
    UserRepository userRepository;

    @Mock
    PermissionLogic permissionLogic;
}