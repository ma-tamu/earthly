package jp.co.project.planets.earthly.schema.db.entity;

import org.seasar.doma.jdbc.entity.*;

/**
 * 
 */
public class ScopeListener implements EntityListener<Scope> {

    @Override
    public void preInsert(final Scope entity, final PreInsertContext<Scope> context) {
        entity.setId(java.util.UUID.randomUUID().toString().replace("-", ""));
        final var now = java.time.LocalDateTime.now();
        entity.setCreatedAt(now);
        entity.setUpdatedAt(now);
    }

    @Override
    public void preUpdate(final Scope entity, final PreUpdateContext<Scope> context) {
        final var now = java.time.LocalDateTime.now();
        entity.setUpdatedAt(now);
    }

    @Override
    public void preDelete(final Scope entity, final PreDeleteContext<Scope> context) {
    }

    @Override
    public void postInsert(final Scope entity, final PostInsertContext<Scope> context) {
    }

    @Override
    public void postUpdate(final Scope entity, final PostUpdateContext<Scope> context) {
    }

    @Override
    public void postDelete(final Scope entity, final PostDeleteContext<Scope> context) {
    }
}
