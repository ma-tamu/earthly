package jp.co.project.planets.earthly.schema.db.entity;

import org.seasar.doma.jdbc.entity.*;

/**
 * 
 */
public class PermissionListener implements EntityListener<Permission> {

    @Override
    public void preInsert(final Permission entity, final PreInsertContext<Permission> context) {
        entity.setId(java.util.UUID.randomUUID().toString().replace("-", ""));
        final var now = java.time.LocalDateTime.now();
        entity.setCreatedAt(now);
        entity.setUpdatedAt(now);
    }

    @Override
    public void preUpdate(final Permission entity, final PreUpdateContext<Permission> context) {
        final var now = java.time.LocalDateTime.now();
        entity.setUpdatedAt(now);
    }

    @Override
    public void preDelete(final Permission entity, final PreDeleteContext<Permission> context) {
    }

    @Override
    public void postInsert(final Permission entity, final PostInsertContext<Permission> context) {
    }

    @Override
    public void postUpdate(final Permission entity, final PostUpdateContext<Permission> context) {
    }

    @Override
    public void postDelete(final Permission entity, final PostDeleteContext<Permission> context) {
    }
}
