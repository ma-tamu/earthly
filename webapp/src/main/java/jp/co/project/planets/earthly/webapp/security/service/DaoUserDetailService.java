package jp.co.project.planets.earthly.webapp.security.service;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import jp.co.project.planets.earthly.core.account.Account;
import jp.co.project.planets.earthly.core.account.Company;
import jp.co.project.planets.earthly.core.account.Emphasis;
import jp.co.project.planets.earthly.core.account.MultiFactor;
import jp.co.project.planets.earthly.schema.db.entity.OpenNotice;
import jp.co.project.planets.earthly.schema.repository.NoticeRepository;
import jp.co.project.planets.earthly.schema.repository.OpenNoticeRepository;
import jp.co.project.planets.earthly.schema.repository.UserRepository;
import jp.co.project.planets.earthly.webapp.logic.PermissionLogic;
import jp.co.project.planets.earthly.webapp.security.dto.EarthlyUserInfoDto;
import jp.co.project.planets.earthly.webapp.util.RequestUtils;

/**
 * dao user detail service
 */
@Service
public class DaoUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;
    private final NoticeRepository noticeRepository;
    private final OpenNoticeRepository openNoticeRepository;
    private final PermissionLogic permissionLogic;

    public DaoUserDetailService(final UserRepository userRepository, final NoticeRepository noticeRepository,
        final OpenNoticeRepository openNoticeRepository, final PermissionLogic permissionLogic) {
        this.userRepository = userRepository;
        this.noticeRepository = noticeRepository;
        this.openNoticeRepository = openNoticeRepository;
        this.permissionLogic = permissionLogic;
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {

        final var user = userRepository.findByLoginId(username).orElseThrow(
                () -> new UsernameNotFoundException("user not found."));
        final var permissionEnumList = permissionLogic.findPermissionEnumListByUserId(user.getId());
        final var grantedAuthorities = permissionEnumList.stream().map(
                it -> new SimpleGrantedAuthority(it.name())).toList();

        final var lastNoticeAt = openNoticeRepository.findByUserId(user.getId()).map(OpenNotice::getOpenedAt)
                .orElseGet(RequestUtils::getLastNoticeDate);
        final var emphasisNoticeList = noticeRepository.findUnreadEmphasisNoticeByPublicationDate(lastNoticeAt);
        final boolean showEmphasisNotice = CollectionUtils.isNotEmpty(emphasisNoticeList);
        final var notice = new Emphasis(showEmphasisNotice, lastNoticeAt);

        final var multiFactor = new MultiFactor(user.getTwoFactorAuthentication(), user.getSecret());
        final var company = new Company(user.getCompany().getId(), user.getCompany().getName());
        final var account = new Account(user.getId(), user.getLoginId(), user.getName(), user.getMail(),
                user.getLockout(), multiFactor, company, permissionEnumList, notice);
        return new EarthlyUserInfoDto(account, user.getPassword(), grantedAuthorities);
    }
}
