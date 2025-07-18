package jp.co.project.planets.earthly.schema.db.entity;

import org.seasar.doma.jdbc.entity.*;

/**
 * 
 */
public class UserRoleListener implements EntityListener<UserRole> {

    @Override
    public void preInsert(final UserRole entity, final PreInsertContext<UserRole> context) {
        entity.setId(java.util.UUID.randomUUID().toString().replace("-", ""));
        final var now = java.time.LocalDateTime.now();
        entity.setCreatedAt(now);
    }

    @Override
    public void preUpdate(final UserRole entity, final PreUpdateContext<UserRole> context) {

    }

    @Override
    public void preDelete(final UserRole entity, final PreDeleteContext<UserRole> context) {
    }

    @Override
    public void postInsert(final UserRole entity, final PostInsertContext<UserRole> context) {
    }

    @Override
    public void postUpdate(final UserRole entity, final PostUpdateContext<UserRole> context) {
    }

    @Override
    public void postDelete(final UserRole entity, final PostDeleteContext<UserRole> context) {
    }
}
