package jp.co.project.planets.earthly.schema.repository;

import java.util.List;
import java.util.Optional;

import org.seasar.doma.boot.Pageables;
import org.seasar.doma.jdbc.criteria.QueryDsl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import jp.co.project.planets.earthly.core.account.Account;
import jp.co.project.planets.earthly.schema.db.dao.PermissionDao;
import jp.co.project.planets.earthly.schema.db.entity.Permission;
import jp.co.project.planets.earthly.schema.db.entity.Permission_;
import jp.co.project.planets.earthly.schema.model.dto.PermissionSearchResultDto;

/**
 * permission repository
 */
@Repository
public class PermissionRepository {

    private final PermissionDao permissionDao;
    private final QueryDsl queryDsl;

    public PermissionRepository(final PermissionDao permissionDao, final QueryDsl queryDsl) {
        this.permissionDao = permissionDao;
        this.queryDsl = queryDsl;
    }

    public Optional<Permission> findByPrimaryKey(String id) {
        return Optional.ofNullable(permissionDao.selectById(id));
    }

    public List<Permission> findByPrimaryKeys(final List<String> permissionIdList) {
        final var criteria = new Permission_();
        return queryDsl.from(criteria).where(where -> where.in(criteria.id, permissionIdList)).fetch();
    }

    /**
     * find by grant permission by user id
     *
     * @param userId
     *            user id
     * @return permission list
     */
    public List<Permission> findGrantPermissionByUserId(final String userId) {
        return permissionDao.selectGrantPermissionByUserId(userId);
    }

    public PermissionSearchResultDto findRoleAssignedByRoleIdAndAnyName(final String roleId, final String name,
        final Pageable pageable, final Account account) {
        final var selectOptions = Pageables.toSelectOptions(pageable).count();
        final var permissions = permissionDao.selectRoleAssignedByRoleIdAndAnyName(roleId, name, selectOptions);
        return new PermissionSearchResultDto(permissions, pageable.getOffset(), selectOptions.getCount());
    }

    public PermissionSearchResultDto findRoleUnassignedByRoleIdAndAnyName(final String roleId, final String name,
        final Pageable pageable, final Account account) {
        final var selectOptions = Pageables.toSelectOptions(pageable).count();
        final var permissions = permissionDao.selectRoleUnassignedByRoleIdAndAnyName(roleId, name, selectOptions);
        return new PermissionSearchResultDto(permissions, pageable.getOffset(), selectOptions.getCount());
    }

    public PermissionSearchResultDto findByName(final String name, final Pageable pageable) {
        final var selectOptions = Pageables.toSelectOptions(pageable).count();
        final var permissions = permissionDao.selectByName(name, selectOptions);
        return new PermissionSearchResultDto(permissions, pageable.getOffset(), selectOptions.getCount());
    }

}
