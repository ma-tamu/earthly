package jp.co.project.planets.earthly.auth.security.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import jp.co.project.planets.earthly.auth.model.dto.UserInfoDto;
import jp.co.project.planets.earthly.repository.UserRepository;

/**
 * login user detail service
 */
@Service
public class LoginUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    public LoginUserDetailService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {

        if (StringUtils.isBlank(username)) {
            throw new UsernameNotFoundException("login id is empty.");
        }
        final var user = userRepository.findByLoginId(username)
                .orElseThrow(() -> new UsernameNotFoundException("not found user"));

        return new UserInfoDto(user.getId(), user.getLoginId(), user.getPassword(), user.getName(), user.getLockout(),
                user.getIsDeleted());
    }
}
