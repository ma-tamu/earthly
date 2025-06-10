package jp.co.project.planets.earthly.schema.repository;

import java.util.List;

import org.seasar.doma.jdbc.criteria.QueryDsl;
import org.springframework.stereotype.Repository;

import jp.co.project.planets.earthly.schema.db.dao.RolePermissionDao;
import jp.co.project.planets.earthly.schema.db.entity.RolePermission;
import jp.co.project.planets.earthly.schema.db.entity.RolePermission_;

@Repository
public class RolePermissionRepository {

    private final RolePermissionDao rolePermissionDao;
    private final QueryDsl queryDsl;

    public RolePermissionRepository(final RolePermissionDao rolePermissionDao, final QueryDsl queryDsl) {
        this.rolePermissionDao = rolePermissionDao;
        this.queryDsl = queryDsl;
    }

    public List<RolePermission> findByRoleIdAndPermissionIds(final String id, final List<String> permissionIds) {
        final var criteria = new RolePermission_();
        return queryDsl.from(criteria).where(where -> {
            where.eq(criteria.roleId, id);
            where.in(criteria.permissionId, permissionIds);
        }).fetch();
    }

    public List<RolePermission> findByRoleIdsAndPermissionId(final List<String> roleIds, final String permissionId) {
        final var criteria = new RolePermission_();
        return queryDsl.from(criteria).where(where -> {
            where.in(criteria.roleId, roleIds);
            where.eq(criteria.permissionId, permissionId);
        }).fetch();
    }

    public int insert(final RolePermission rolePermission) {
        return rolePermissionDao.insert(rolePermission);
    }

    public int deleteByRoleId(final String id) {
        final var criteria = new RolePermission_();
        return queryDsl.delete(criteria).where(where -> where.eq(criteria.roleId, id)).execute();
    }

    public int deleteByRoleIdAndPermissionIds(final String roleId, final List<String> permissionIds) {
        final var criteria = new RolePermission_();
        return queryDsl.delete(criteria).where(where -> {
            where.eq(criteria.roleId, roleId);
            where.in(criteria.permissionId, permissionIds);
        }).execute();
    }

    public int deleteByRoleIdsAndPermissionId(final List<String> roleIds, final String permissionId) {
        final var criteria = new RolePermission_();
        return queryDsl.delete(criteria).where(where -> {
            where.in(criteria.roleId, roleIds);
            where.eq(criteria.permissionId, permissionId);
        }).execute();
    }
}
