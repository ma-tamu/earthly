package jp.co.project.planets.earthly.webapp.security.service;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import jp.co.project.planets.earthly.core.account.Account;
import jp.co.project.planets.earthly.core.account.Company;
import jp.co.project.planets.earthly.core.account.MultiFactor;
import jp.co.project.planets.earthly.schema.repository.UserRepository;
import jp.co.project.planets.earthly.webapp.logic.PermissionLogic;
import jp.co.project.planets.earthly.webapp.security.dto.EarthlyUserInfoDto;

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

        final var multiFactor = new MultiFactor(user.getTwoFactorAuthentication(), user.getSecret());
        final var company = new Company(user.getCompany().getId(), user.getCompany().getName());
        final var account = new Account(user.getId(), user.getLoginId(), user.getName(), user.getMail(),
                user.getLockout(), multiFactor, company, permissionEnumList);
        return new EarthlyUserInfoDto(account, user.getPassword(), grantedAuthorities);
    }
}
