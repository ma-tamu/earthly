package jp.co.project.planets.earthly.schema.db.entity;

import org.seasar.doma.jdbc.entity.*;

/**
 * 
 */
public class RolePermissionListener implements EntityListener<RolePermission> {

    @Override
    public void preInsert(final RolePermission entity, final PreInsertContext<RolePermission> context) {
        entity.setId(java.util.UUID.randomUUID().toString().replace("-", ""));
        final var now = java.time.LocalDateTime.now();
        entity.setCreatedAt(now);
    }

    @Override
    public void preUpdate(final RolePermission entity, final PreUpdateContext<RolePermission> context) {

    }

    @Override
    public void preDelete(final RolePermission entity, final PreDeleteContext<RolePermission> context) {
    }

    @Override
    public void postInsert(final RolePermission entity, final PostInsertContext<RolePermission> context) {
    }

    @Override
    public void postUpdate(final RolePermission entity, final PostUpdateContext<RolePermission> context) {
    }

    @Override
    public void postDelete(final RolePermission entity, final PostDeleteContext<RolePermission> context) {
    }
}
