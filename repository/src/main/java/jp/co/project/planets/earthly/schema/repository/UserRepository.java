package jp.co.project.planets.earthly.schema.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.seasar.doma.boot.Pageables;
import org.seasar.doma.jdbc.criteria.QueryDsl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import jp.co.project.planets.earthly.core.account.Account;
import jp.co.project.planets.earthly.schema.db.dao.CompanyDao;
import jp.co.project.planets.earthly.schema.db.dao.RoleDao;
import jp.co.project.planets.earthly.schema.db.dao.UserDao;
import jp.co.project.planets.earthly.schema.db.entity.User;
import jp.co.project.planets.earthly.schema.db.entity.User_;
import jp.co.project.planets.earthly.schema.emuns.PermissionEnum;
import jp.co.project.planets.earthly.schema.model.dto.UserPageResultDto;
import jp.co.project.planets.earthly.schema.model.dto.UserSearchResultDto;

/**
 * user repository
 */
@Repository
public class UserRepository {

    private final UserDao userDao;
    private final CompanyDao companyDao;
    private final RoleDao roleDao;

    private final QueryDsl queryDsl;

    /**
     * new instance user repository
     *
     * @param userDao
     *            user dao
     * @param companyDao
     *            company dao
     * @param roleDao
     *            role dao
     * @param queryDsl
     *            query dsl
     */
    public UserRepository(final UserDao userDao, final CompanyDao companyDao, final RoleDao roleDao,
        final QueryDsl queryDsl) {
        this.userDao = userDao;
        this.companyDao = companyDao;
        this.roleDao = roleDao;
        this.queryDsl = queryDsl;
    }

    /**
     * find by primary key
     *
     * @param id
     *            id
     * @return User
     */
    public Optional<User> findByPrimaryKey(final String id) {
        return Optional.ofNullable(userDao.selectById(id));
    }

    public List<User> findByPrimaryKeysAccessibly(final List<String> ids, final Account account) {
        final boolean hasViewAllCompany = account.permissions().contains(PermissionEnum.VIEW_ALL_COMPANY);
        return userDao.selectByPrimaryKeysAccessibly(ids, hasViewAllCompany, account.id());
    }

    /**
     * find by login id
     *
     * @param loginId
     *            login id
     * @return user
     */
    public Optional<jp.co.project.planets.earthly.schema.model.entity.User> findByLoginId(final String loginId) {
        return userDao.selectByLoginId(loginId);
    }

    /**
     * 閲覧可能なユーザーを取得
     *
     * @param id
     *            ユーザーID
     * @param account
     *            実行ユーザー
     * @return UserEntity
     */
    public Optional<jp.co.project.planets.earthly.schema.model.entity.User> findAccessibleByPrimaryKey(final String id,
        final Account account) {
        final boolean hasViewAllCompany = account.permissions().contains(PermissionEnum.VIEW_ALL_COMPANY);
        return userDao.selectAccessibleByPrimaryKey(id, hasViewAllCompany, account.id());
    }

    public UserSearchResultDto findByLoginIdAndNameAndCompany(final String loginId, final String name,
        final String company, final Pageable pageable, final Account account) {
        final var selectOptions = Pageables.toSelectOptions(pageable).count();
        final boolean hasViewAllCompany = account.permissions().contains(PermissionEnum.VIEW_ALL_COMPANY);
        final var userList = userDao.selectByLoginIdAndNameAndCompany(loginId, name, company, hasViewAllCompany,
                account.id(), selectOptions);
        return new UserSearchResultDto(userList, pageable.getOffset(), selectOptions.getCount());
    }

    /**
     * find by mail
     * 
     * @param mail
     *            メールアドレス
     * @return user
     */
    public Optional<User> findByMail(final String loginId, final String mail) {
        final var user = new User_();
        return queryDsl.from(user).where(w -> {
            w.eq(user.loginId, loginId);
            w.eq(user.mail, mail);
            w.eq(user.isDeleted, false);
        }).fetchOptional();
    }

    /**
     * 会社管理者のユーザーを取得
     *
     * @param loginId
     *            ログインID
     * @param name
     *            ユーザー名
     * @param companyId
     *            会社ID
     * @param companyName
     *            会社名
     * @param pageable
     *            ページャー
     * @param account
     *            実行ユーザーID
     * @return 会社管理者
     */
    public UserPageResultDto findCompanyManagerByLikeLoginIdAndNameAndCompanyName(final String loginId,
        final String name, final String companyId, final String companyName, final Pageable pageable,
        final Account account) {
        final var selectOptions = Pageables.toSelectOptions(pageable).count();
        final boolean hasViewAllUser = account.permissions().contains(PermissionEnum.VIEW_ALL_USER);
        final var userList = userDao.selectCompanyManagerByName(loginId, name, companyId, companyName,
                hasViewAllUser, account.id(), selectOptions);
        return new UserPageResultDto(userList, pageable.getOffset(), selectOptions.getCount());
    }

    public UserPageResultDto findNotCompanyManagerByNameAndCompanyName(final String loginId,
        final String name, final String companyName, final String companyId, final Pageable pageable,
        final Account account) {
        final var selectOptions = Pageables.toSelectOptions(pageable).count();
        final boolean hasViewAllUser = account.permissions().contains(PermissionEnum.VIEW_ALL_USER);
        final var userList = userDao.selectNotCompanyManagerByNameAndCompanyName(loginId, name, companyName, companyId,
                hasViewAllUser, account.id(), selectOptions);
        return new UserPageResultDto(userList, pageable.getOffset(), selectOptions.getCount());
    }

    public UserPageResultDto findBelongOrganizationByLikeLoginIdAndName(final String loginId, final String name,
        final String companyId, final String organizationId, final Pageable pageable, final Account account) {
        final var selectOptions = Pageables.toSelectOptions(pageable).count();
        final boolean hasViewAllUser = account.permissions().contains(PermissionEnum.VIEW_ALL_USER);
        final var userList = userDao.selectBelongOrganizationByLikeLoginIdAndName(loginId, name, companyId,
                organizationId, hasViewAllUser, account.id(), selectOptions);
        return new UserPageResultDto(userList, pageable.getOffset(), selectOptions.getCount());
    }

    public UserPageResultDto findNotBelongOrganizationByLikeLoginIdAndName(final String loginId, final String name,
        final String companyId, final String organizationId, final Pageable pageable, final Account account) {
        final var selectOptions = Pageables.toSelectOptions(pageable).count();
        final boolean hasViewAllUser = account.permissions().contains(PermissionEnum.VIEW_ALL_USER);
        final var userList = userDao.selectNotBelongOrganizationByLikeLoginIdAndName(loginId, name, companyId,
                organizationId, hasViewAllUser, account.id(), selectOptions);
        return new UserPageResultDto(userList, pageable.getOffset(), selectOptions.getCount());
    }

    /**
     * insert user
     *
     * @param user
     *            user
     * @return insert count
     */
    public int insert(final User user) {
        final var localDateTime = LocalDateTime.now();
        user.setCreatedAt(localDateTime);
        user.setUpdatedAt(localDateTime);
        return userDao.insert(user);
    }

    /**
     * update user
     *
     * @param user
     *            user
     * @return update count
     */
    public int update(final User user) {
        user.setUpdatedAt(LocalDateTime.now());
        return userDao.update(user);
    }

    /**
     * delete user
     *
     * @param user
     *            user
     * @return delete count
     */
    public int delete(final User user) {
        user.setUpdatedAt(LocalDateTime.now());
        user.setIsDeleted(true);
        return userDao.update(user);
    }
}
