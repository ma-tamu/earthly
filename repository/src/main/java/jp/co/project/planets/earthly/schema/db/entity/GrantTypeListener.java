package jp.co.project.planets.earthly.schema.db.entity;

import org.seasar.doma.jdbc.entity.*;

/**
 * 
 */
public class GrantTypeListener implements EntityListener<GrantType> {

    @Override
    public void preInsert(final GrantType entity, final PreInsertContext<GrantType> context) {
        entity.setId(java.util.UUID.randomUUID().toString().replace("-", ""));
        final var now = java.time.LocalDateTime.now();
        entity.setCreatedAt(now);
        entity.setUpdatedAt(now);
    }

    @Override
    public void preUpdate(final GrantType entity, final PreUpdateContext<GrantType> context) {
        final var now = java.time.LocalDateTime.now();
        entity.setUpdatedAt(now);
    }

    @Override
    public void preDelete(final GrantType entity, final PreDeleteContext<GrantType> context) {
    }

    @Override
    public void postInsert(final GrantType entity, final PostInsertContext<GrantType> context) {
    }

    @Override
    public void postUpdate(final GrantType entity, final PostUpdateContext<GrantType> context) {
    }

    @Override
    public void postDelete(final GrantType entity, final PostDeleteContext<GrantType> context) {
    }
}
