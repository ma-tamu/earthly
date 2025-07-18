package jp.co.project.planets.earthly.schema.db.entity;

import org.seasar.doma.jdbc.entity.*;

/**
 * 
 */
public class RoleListener implements EntityListener<Role> {

    @Override
    public void preInsert(final Role entity, final PreInsertContext<Role> context) {
        entity.setId(java.util.UUID.randomUUID().toString().replace("-", ""));
        final var now = java.time.LocalDateTime.now();
        entity.setCreatedAt(now);
        entity.setUpdatedAt(now);
    }

    @Override
    public void preUpdate(final Role entity, final PreUpdateContext<Role> context) {
        final var now = java.time.LocalDateTime.now();
        entity.setUpdatedAt(now);
    }

    @Override
    public void preDelete(final Role entity, final PreDeleteContext<Role> context) {
    }

    @Override
    public void postInsert(final Role entity, final PostInsertContext<Role> context) {
    }

    @Override
    public void postUpdate(final Role entity, final PostUpdateContext<Role> context) {
    }

    @Override
    public void postDelete(final Role entity, final PostDeleteContext<Role> context) {
    }
}
