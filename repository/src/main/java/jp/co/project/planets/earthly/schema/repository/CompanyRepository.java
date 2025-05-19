package jp.co.project.planets.earthly.schema.repository;

import java.util.List;
import java.util.Optional;

import org.seasar.doma.boot.Pageables;
import org.seasar.doma.jdbc.criteria.QueryDsl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import jp.co.project.planets.earthly.core.account.Account;
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
     * Finds an accessible company by its primary key, considering the user's
     * permissions.
     *
     * @param id
     *            the unique identifier of the company
     * @param account
     *            the account of the user performing the operation
     * @return an {@link Optional} containing the accessible {@link Company}, or
     *             an empty {@link Optional}
     *             if no accessible company is found
     */
    public Optional<Company> findAccessibleByPrimaryKey(final String id, final Account account) {
        final boolean hasViewAllCompany = account.permissions().contains(PermissionEnum.VIEW_ALL_COMPANY);
        return companyDao.selectAccessibleByPrimaryKey(id, account.id(), hasViewAllCompany);
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
     * @param account
     *            実行ユーザー
     * @return 会社
     */
    public Optional<Company> findByAccessiblePrimaryKey(final String id, final Account account) {
        final var hasViewAllCompany = account.permissions().contains(PermissionEnum.VIEW_ALL_COMPANY);
        return companyDao.selectAccessibleByPrimaryKey(id, account.id(), hasViewAllCompany);
    }

    /**
     * 閲覧できる会社一覧を取得
     *
     * @param userId
     *            ユーザーID
     * @param keywordOptional
     *            キーワード
     * @param account
     *            実行ユーザー
     * @return 会社一覧
     */
    public List<Company> findAccessibleByUserId(final String userId, final Optional<String> keywordOptional,
        final Account account) {
        final boolean hasViewAllCompany = account.permissions().contains(PermissionEnum.VIEW_ALL_COMPANY);
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
     * @param pageable
     *            ページャー
     * @param account
     *            ユーザー情報
     * @return 会社検索結果
     */
    public CompanySearchResultDto findByLikeAnyName(final String name,
        final Pageable pageable, final Account account) {
        final boolean hasViewAllCompany = account.permissions().contains(PermissionEnum.VIEW_ALL_COMPANY);
        final var options = Pageables.toSelectOptions(pageable).count();
        final var companyList = companyDao.selectAccessibleByLikeAnyName(name, account.id(), hasViewAllCompany,
                options);
        return new CompanySearchResultDto(companyList, pageable.getOffset(), options.getCount());
    }

    public CompanySearchResultDto findByUserId(final String userId, final Pageable pageable, final Account account) {
        final var options = Pageables.toSelectOptions(pageable).count();
        final boolean hasViewAllCompany = account.permissions().contains(PermissionEnum.VIEW_ALL_COMPANY);
        final var companies = companyDao.selectByUserId(userId, hasViewAllCompany, account.id(), options);
        return new CompanySearchResultDto(companies, pageable.getOffset(), options.getCount());
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
