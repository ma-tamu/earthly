package jp.co.project.planets.earthly.schema.repository;

import org.seasar.doma.jdbc.criteria.QueryDsl;
import org.springframework.stereotype.Repository;

import jp.co.project.planets.earthly.schema.db.dao.OrganizationUserDao;
import jp.co.project.planets.earthly.schema.db.entity.OrganizationUser_;

@Repository
public class OrganizationUserRepository {

    private final OrganizationUserDao organizationUserDao;
    private final QueryDsl queryDsl;

    public OrganizationUserRepository(final OrganizationUserDao organizationUserDao, final QueryDsl queryDsl) {
        this.organizationUserDao = organizationUserDao;
        this.queryDsl = queryDsl;
    }

    public int deleteByGroupId(final String organizationId) {
        final var criteria = new OrganizationUser_();
        return queryDsl.delete(criteria).where(where -> where.eq(criteria.organizationId, organizationId)).execute();
    }
}
