package jp.co.project.planets.earthly.schema.repository;

import java.util.Optional;

import org.seasar.doma.boot.Pageables;
import org.seasar.doma.jdbc.criteria.QueryDsl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import jp.co.project.planets.earthly.core.account.Account;
import jp.co.project.planets.earthly.schema.db.dao.OrganizationDao;
import jp.co.project.planets.earthly.schema.db.entity.Organization;
import jp.co.project.planets.earthly.schema.db.entity.Organization_;
import jp.co.project.planets.earthly.schema.emuns.PermissionEnum;
import jp.co.project.planets.earthly.schema.model.dto.GroupPageResultDto;

/**
 * 組織リポジトリ
 */
@Repository
public class OrganizationRepository {

    private final OrganizationDao organizationDao;
    private final QueryDsl queryDsl;

    public OrganizationRepository(final OrganizationDao organizationDao, final QueryDsl queryDsl) {
        this.organizationDao = organizationDao;
        this.queryDsl = queryDsl;
    }

    public Optional<jp.co.project.planets.earthly.schema.model.entity.Organization>
            findAccessibleByPrimaryKey(final String id, final Account account) {
        final boolean hasViewAllCompany = account.permissions().contains(PermissionEnum.VIEW_ALL_COMPANY);
        return organizationDao.selectAccessibleByPrimaryKey(id, hasViewAllCompany, account.id());
    }

    public Optional<Organization> findByCompanyIdAndName(final String companyId, final String name) {
        final var criteria = new Organization_();
        return queryDsl.from(criteria).where(where -> {
            where.eq(criteria.companyId, companyId);
            where.eq(criteria.name, name);
        }).orderBy(order -> order.desc(criteria.createdAt)).fetchOptional();
    }

    public GroupPageResultDto findByCompanyIdAndLikeName(final String companyId, final String name,
        final Pageable pageable) {
        final var selectOptions = Pageables.toSelectOptions(pageable).count();
        final var list = organizationDao.selectByCompanyIdAndLikeName(companyId, name, selectOptions);
        return new GroupPageResultDto(list, pageable.getOffset(), selectOptions.getCount());
    }

    public int insert(final Organization organization) {
        return organizationDao.insert(organization);
    }

    public int update(final Organization organization) {
        return organizationDao.update(organization);
    }

    /**
     * 会社IDで組織を削除
     * 
     * @param companyId
     *            会社ID
     * @return 削除件数
     */
    public int deleteByCompanyId(final String companyId) {
        final var criteria = new Organization_();
        return queryDsl.update(criteria).set(set -> set.value(criteria.isDeleted, true))
                .where(where -> where.eq(criteria.companyId, companyId)).execute();
    }

    public int delete(final String id) {
        final var criteria = new Organization_();
        return queryDsl.update(criteria).set(set -> set.value(criteria.isDeleted, true))
                .where(where -> where.eq(criteria.id, id)).execute();
    }
}
