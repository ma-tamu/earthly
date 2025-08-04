package jp.co.project.planets.earthly.schema.db.entity;

import org.seasar.doma.jdbc.entity.*;

/**
 * 
 */
public class CountryListener implements EntityListener<Country> {

    @Override
    public void preInsert(final Country entity, final PreInsertContext<Country> context) {
        entity.setId(java.util.UUID.randomUUID().toString().replace("-", ""));
        final var now = java.time.LocalDateTime.now();
        entity.setCreatedAt(now);
        entity.setUpdatedAt(now);
    }

    @Override
    public void preUpdate(final Country entity, final PreUpdateContext<Country> context) {
        final var now = java.time.LocalDateTime.now();
        entity.setUpdatedAt(now);
    }

    @Override
    public void preDelete(final Country entity, final PreDeleteContext<Country> context) {
    }

    @Override
    public void postInsert(final Country entity, final PostInsertContext<Country> context) {
    }

    @Override
    public void postUpdate(final Country entity, final PostUpdateContext<Country> context) {
    }

    @Override
    public void postDelete(final Country entity, final PostDeleteContext<Country> context) {
    }
}
