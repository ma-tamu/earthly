package jp.co.project.planets.earthly.schema.repository;

import java.util.List;

import org.seasar.doma.jdbc.criteria.QueryDsl;
import org.springframework.stereotype.Repository;

import jp.co.project.planets.earthly.schema.db.dao.OrganizationUserDao;
import jp.co.project.planets.earthly.schema.db.entity.OrganizationUser;
import jp.co.project.planets.earthly.schema.db.entity.OrganizationUser_;

@Repository
public class OrganizationUserRepository {

    private final OrganizationUserDao organizationUserDao;
    private final QueryDsl queryDsl;

    public OrganizationUserRepository(final OrganizationUserDao organizationUserDao, final QueryDsl queryDsl) {
        this.organizationUserDao = organizationUserDao;
        this.queryDsl = queryDsl;
    }

    public List<OrganizationUser> findByOrganizationIdAndInUserId(final String organizationId,
        final List<String> userIds) {
        final var criteria = new OrganizationUser_();
        return queryDsl.from(criteria).where(where -> {
            where.eq(criteria.organizationId, organizationId);
            where.in(criteria.userId, userIds);
        }).fetch();
    }

    public int insert(final OrganizationUser organizationUser) {
        return organizationUserDao.insert(organizationUser);
    }

    public int deleteByGroupId(final String organizationId) {
        final var criteria = new OrganizationUser_();
        return queryDsl.delete(criteria).where(where -> where.eq(criteria.organizationId, organizationId)).execute();
    }

    public int deleteByOrganizationIdAndInUserId(final String organizationId, final List<String> userIds) {
        final var criteria = new OrganizationUser_();
        return queryDsl.delete(criteria).where(where -> {
            where.eq(criteria.organizationId, organizationId);
            where.in(criteria.userId, userIds);
        }).execute();

    }
}
