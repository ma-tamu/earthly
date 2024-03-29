package jp.co.project.planets.earthly.schema.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import jp.co.project.planets.earthly.schema.db.dao.CompanyDao;
import jp.co.project.planets.earthly.schema.db.entity.Company;
import jp.co.project.planets.earthly.schema.emuns.PermissionEnum;

/**
 * company repository
 */
@Repository
public class CompanyRepository {

    private final CompanyDao companyDao;

    /**
     * new instance company repository
     *
     * @param companyDao
     *            company dao
     */
    public CompanyRepository(final CompanyDao companyDao) {
        this.companyDao = companyDao;
    }

    /**
     * find by primary key
     * 
     * @param id
     *            company id
     * @return Company
     */
    public Optional<Company> findByPrimaryKey(final String id) {
        return Optional.ofNullable(companyDao.selectById(id));
    }

    /**
     * 閲覧できる対象会社を取得
     *
     * @param id
     *            会社ID
     * @param permissionEnumList
     *            パーミッションリスト
     * @param executionUserId
     *            実行ユーザーID
     * @return 会社
     */
    public Optional<Company> findByAccessiblePrimaryKey(final String id, final List<PermissionEnum> permissionEnumList,
            final String executionUserId) {
        final var hasViewAllCompany = permissionEnumList.contains(PermissionEnum.VIEW_ALL_COMPANY);
        return companyDao.selectAccessibleByPrimaryKey(id, executionUserId, hasViewAllCompany);
    }

    /**
     * 閲覧できる会社一覧を取得
     *
     * @param userId
     *            ユーザーID
     * @param keywordOptional
     *            キーワード
     * @param permissionEnumList
     *            パーミッションリスト
     * @return 会社一覧
     */
    public List<Company> findAccessibleByUserId(final String userId, final Optional<String> keywordOptional,
            final List<PermissionEnum> permissionEnumList) {
        final boolean hasViewAllCompany = permissionEnumList.contains(PermissionEnum.VIEW_ALL_COMPANY);
        return companyDao.selectAccessibleByUserId(userId, keywordOptional, hasViewAllCompany);
    }

    /**
     * 管理している会社一覧を取得
     * 
     * @param userId
     *            ユーザーID
     * @return 管理している会社一覧
     */
    public List<Company> findManagementCompanyByUserId(final String userId) {
        return companyDao.selectManagementCompanyByUserId(userId);
    }

}
