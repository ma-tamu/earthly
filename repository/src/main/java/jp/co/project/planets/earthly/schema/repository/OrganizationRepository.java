package jp.co.project.planets.earthly.schema.repository;

import org.seasar.doma.jdbc.criteria.QueryDsl;
import org.springframework.stereotype.Repository;

import jp.co.project.planets.earthly.schema.db.dao.OrganizationDao;
import jp.co.project.planets.earthly.schema.db.entity.Organization_;

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

    /**
     * 会社IDで組織を削除
     * 
     * @param companyId
     *            会社ID
     * @return 削除件数
     */
    public int deleteByCompanyId(final String companyId) {
        final var criteria = new Organization_();
        return queryDsl.delete(criteria).where(where -> where.eq(criteria.companyId, companyId)).execute();
    }
}
