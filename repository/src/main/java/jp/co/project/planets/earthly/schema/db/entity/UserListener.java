package jp.co.project.planets.earthly.schema.db.entity;

import org.seasar.doma.jdbc.entity.*;

/**
 * 
 */
public class UserListener implements EntityListener<User> {

    @Override
    public void preInsert(final User entity, final PreInsertContext<User> context) {
        entity.setId(java.util.UUID.randomUUID().toString().replace("-", ""));
        final var now = java.time.LocalDateTime.now();
        entity.setCreatedAt(now);
        entity.setUpdatedAt(now);
    }

    @Override
    public void preUpdate(final User entity, final PreUpdateContext<User> context) {
        final var now = java.time.LocalDateTime.now();
        entity.setUpdatedAt(now);
    }

    @Override
    public void preDelete(final User entity, final PreDeleteContext<User> context) {
    }

    @Override
    public void postInsert(final User entity, final PostInsertContext<User> context) {
    }

    @Override
    public void postUpdate(final User entity, final PostUpdateContext<User> context) {
    }

    @Override
    public void postDelete(final User entity, final PostDeleteContext<User> context) {
    }
}
