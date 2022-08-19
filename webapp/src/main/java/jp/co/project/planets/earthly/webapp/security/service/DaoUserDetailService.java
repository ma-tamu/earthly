package jp.co.project.planets.earthly.webapp.security.service;

import jp.co.project.planets.earthly.repository.UserRepository;
import jp.co.project.planets.earthly.webapp.logic.PermissionLogic;
import jp.co.project.planets.earthly.webapp.security.dto.EarthlyUserInfoDto;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * dao user detail service
 */
@Service
public class DaoUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PermissionLogic permissionLogic;

    public DaoUserDetailService(final UserRepository userRepository, final PermissionLogic permissionLogic) {
        this.userRepository = userRepository;
        this.permissionLogic = permissionLogic;
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {

        final var user = userRepository.findByLoginId(username).orElseThrow(
                () -> new UsernameNotFoundException("user not found."));
        final var permissionEnumList = permissionLogic.findPermissionEnumListByUserId(user.getId());
        final var grantedAuthorities = permissionEnumList.stream().map(
                it -> new SimpleGrantedAuthority(it.name())).toList();

        return new EarthlyUserInfoDto(user.getId(), user.getLoginId(), user.getName(), user.getPassword(),
                user.getLockout(), permissionEnumList, grantedAuthorities);
    }
}
