package jp.co.project.planets.earthly.schema.repository;

import java.util.List;
import java.util.Optional;

import org.seasar.doma.boot.Pageables;
import org.seasar.doma.jdbc.criteria.QueryDsl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import jp.co.project.planets.earthly.schema.db.dao.CompanyDao;
import jp.co.project.planets.earthly.schema.db.entity.Company;
import jp.co.project.planets.earthly.schema.db.entity.Company_;
import jp.co.project.planets.earthly.schema.emuns.PermissionEnum;
import jp.co.project.planets.earthly.schema.model.dto.CompanySearchResultDto;

/**
 * company repository
 */
@Repository
public class CompanyRepository {

    private final CompanyDao companyDao;
    private final QueryDsl queryDsl;

    /**
     * new instance company repository
     *
     * @param companyDao
     *            company dao
     */
    public CompanyRepository(final CompanyDao companyDao, final QueryDsl queryDsl) {
        this.companyDao = companyDao;
        this.queryDsl = queryDsl;
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
     * Finds an accessible company by its primary key based on user permissions
     * and access controls.
     *
     * @param id
     *            the primary key of the company to find
     * @param hasViewAllCompany
     *            a flag indicating if the user has permission to view all
     *            companies
     * @param executionUserId
     *            the ID of the user executing the operation
     * @return an {@code Optional} containing the accessible {@code Company} if
     *             found, otherwise an empty {@code Optional}
     */
    public Optional<Company> findAccessibleByPrimaryKey(final String id, final boolean hasViewAllCompany,
        final String executionUserId) {
        return companyDao.selectAccessibleByPrimaryKey(id, executionUserId, hasViewAllCompany);
    }

    /**
     * Finds a company by its name, excluding deleted records.
     * The results are ordered by the most recently updated entries.
     *
     * @param name
     *            the name of the company to search for
     * @return an {@link Optional} containing the found {@link Company}, or an
     *             empty {@link Optional} if not found
     */
    public Optional<Company> findByName(final String name) {
        final var criteria = new Company_();
        return queryDsl.from(criteria).where(where -> {
            where.eq(criteria.name, name);
            where.eq(criteria.isDeleted, false);
        }).orderBy(order -> order.desc(criteria.updatedAt)).execute().stream().findFirst();
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

    /**
     * 閲覧できる会社の会社名を部分検索する。
     *
     * @param name
     *            会社名
     * @param userId
     *            操作ユーザーID
     * @param permissionList
     *            パーミッションリスト
     * @param pageable
     *            ページャー
     * @return 会社検索結果
     */
    public CompanySearchResultDto findByLikeAnyName(final String name, final String userId,
        final List<PermissionEnum> permissionList, final Pageable pageable) {
        final boolean hasViewAllCompany = permissionList.contains(PermissionEnum.VIEW_ALL_COMPANY);
        final var options = Pageables.toSelectOptions(pageable).count();
        final var companyEntityList = companyDao.selectAccessibleByLikeAnyName(name, userId, hasViewAllCompany,
                options);
        return new CompanySearchResultDto(companyEntityList, options.getCount());
    }

    /**
     * 会社登録
     * 
     * @param company
     *            会社
     * @return 登録件数
     */
    public int insert(final Company company) {
        return companyDao.insert(company);
    }

    /**
     * 会社更新
     * 
     * @param company
     *            会社
     * @return 更新件数
     */
    public int update(final Company company) {
        return companyDao.update(company);
    }
}
