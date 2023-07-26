package jp.co.project.planets.earthly.schema.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.seasar.doma.boot.Pageables;
import org.seasar.doma.jdbc.criteria.Entityql;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import jp.co.project.planets.earthly.schema.db.dao.CompanyDao;
import jp.co.project.planets.earthly.schema.db.dao.RoleDao;
import jp.co.project.planets.earthly.schema.db.dao.UserDao;
import jp.co.project.planets.earthly.schema.db.entity.Company;
import jp.co.project.planets.earthly.schema.db.entity.Role;
import jp.co.project.planets.earthly.schema.db.entity.User;
import jp.co.project.planets.earthly.schema.db.entity.User_;
import jp.co.project.planets.earthly.schema.emuns.PermissionEnum;
import jp.co.project.planets.earthly.schema.model.dto.UserSearchResultDto;
import jp.co.project.planets.earthly.schema.model.entity.BelongCompanyEntity;
import jp.co.project.planets.earthly.schema.model.entity.CompanyEntity;
import jp.co.project.planets.earthly.schema.model.entity.CompanySimpleEntity;
import jp.co.project.planets.earthly.schema.model.entity.CountryEntity;
import jp.co.project.planets.earthly.schema.model.entity.LanguageEntity;
import jp.co.project.planets.earthly.schema.model.entity.RegionEntity;
import jp.co.project.planets.earthly.schema.model.entity.UserEntity;

/**
 * user repository
 */
@Repository
public class UserRepository {

    private final UserDao userDao;
    private final CompanyDao companyDao;
    private final RoleDao roleDao;

    private final Entityql entityql;

    /**
     * new instance user repository
     *
     * @param userDao
     *            user dao
     * @param companyDao
     *            company dao
     * @param roleDao
     *            role dao
     * @param entityql
     */
    public UserRepository(final UserDao userDao, final CompanyDao companyDao, final RoleDao roleDao,
            final Entityql entityql) {
        this.userDao = userDao;
        this.companyDao = companyDao;
        this.roleDao = roleDao;
        this.entityql = entityql;
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

    /**
     * find by login id
     *
     * @param loginId
     *            login id
     * @return user
     */
    public Optional<User> findByLoginId(final String loginId) {
        return userDao.selectByLoginId(loginId);
    }

    /**
     * 閲覧可能なユーザーを取得
     *
     * @param id
     *            ユーザーID
     * @param permissionEnumList
     *            実行ユーザーのパーミッションリスト
     * @param executionUserId
     *            実行ユーザー
     * @return UserEntity
     */
    public Optional<UserEntity> findAccessibleByPrimaryKey(final String id,
            final List<PermissionEnum> permissionEnumList, final String executionUserId) {
        final boolean hasViewAllCompany = permissionEnumList.contains(PermissionEnum.VIEW_ALL_COMPANY);
        final var userOptional = userDao.selectAccessibleByPrimaryKey(id, hasViewAllCompany, executionUserId);
        if (userOptional.isEmpty()) {
            return Optional.empty();
        }
        final var user = userOptional.get();
        final var companyEntityOptional = companyDao.selectByPrimaryKey(user.getCompanyId());
        if (companyEntityOptional.isEmpty()) {
            return Optional.empty();
        }
        final boolean hasViewAllRole = permissionEnumList.contains(PermissionEnum.VIEW_ALL_ROLE);
        final var roleList = roleDao.selectGrantedRoleByUserId(id, hasViewAllRole, executionUserId);
        final var managementCompanyList = companyDao.selectManagementCompanyByUserId(id);
        return Optional.of(generateUserEntity(user, companyEntityOptional.get(), roleList, managementCompanyList));
    }

    private UserEntity generateUserEntity(final User user, final CompanyEntity companyEntity,
            final List<Role> roleList, final List<Company> managementCompanyList) {
        final var regionEntity = new RegionEntity(companyEntity.regionId(), companyEntity.regionName());
        final var languageEntity = new LanguageEntity(companyEntity.languageId(), companyEntity.languageName());
        final var countryEntity = new CountryEntity(companyEntity.countryId(), companyEntity.countryName(),
                languageEntity, regionEntity);
        final var belongCompanyEntity = new BelongCompanyEntity(companyEntity.id(), companyEntity.name(),
                countryEntity);
        final var companySimpleEntityList = managementCompanyList.stream()
                .map(it -> new CompanySimpleEntity(it.getId(), it.getName())).toList();

        return new UserEntity(user.getId(), user.getLoginId(), user.getName(), user.getGender(), user.getMail(),
                user.getPassword(), user.getLanguage(), user.getTimezone(), user.getLockout(),
                user.getTwoFactorAuthentication(), user.getSecret(), belongCompanyEntity, roleList,
                companySimpleEntityList, user.getCreatedAt(), null, user.getUpdatedAt(), null, user.getIsDeleted());
    }

    public UserSearchResultDto findByLoginIdAndNameAndCompany(final String loginId, final String name,
            final String company, final Pageable pageable, final List<PermissionEnum> permissionEnumList,
            final String executionUserId) {
        final var selectOptions = Pageables.toSelectOptions(pageable).count();
        final boolean hasViewAllCompany = permissionEnumList.contains(PermissionEnum.VIEW_ALL_COMPANY);
        final var userList = userDao.selectByLoginIdAndNameAndCompany(loginId, name, company, hasViewAllCompany,
                executionUserId, selectOptions);
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
        return entityql.from(user).where(w -> {
            w.eq(user.loginId, loginId);
            w.eq(user.mail, mail);
            w.eq(user.isDeleted, false);
        }).fetchOptional();
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
