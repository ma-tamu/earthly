package jp.co.project.planets.earthly.auth.security;

import jp.co.project.planets.moon.model.dto.UserInfoDto;
import jp.co.project.planets.pleiades.db.dao.UserDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * login user detail service
 */
@Service
public class LoginUserDetailService implements UserDetailsService {

    private final UserDao userDao;

    @Autowired
    public LoginUserDetailService(UserDao userDao) {
        this.userDao = userDao;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        if (StringUtils.isBlank(username)) {
            throw new UsernameNotFoundException("login id is empty.");
        }
        final var user = userDao.selectByLoginId(username).orElseThrow(() -> new UsernameNotFoundException(""));

        return new UserInfoDto(user.getId(), user.getLoginId(), user.getPassword(), user.getName(), user.getLockout(), user.getIsDeleted());
    }
}
