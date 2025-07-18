package jp.co.project.planets.earthly.schema.db.entity;

import org.seasar.doma.jdbc.entity.*;

/**
 * 
 */
public class RegionListener implements EntityListener<Region> {

    @Override
    public void preInsert(final Region entity, final PreInsertContext<Region> context) {
        entity.setId(java.util.UUID.randomUUID().toString().replace("-", ""));
        final var now = java.time.LocalDateTime.now();
        entity.setCreatedAt(now);
        entity.setUpdatedAt(now);
    }

    @Override
    public void preUpdate(final Region entity, final PreUpdateContext<Region> context) {
        final var now = java.time.LocalDateTime.now();
        entity.setUpdatedAt(now);
    }

    @Override
    public void preDelete(final Region entity, final PreDeleteContext<Region> context) {
    }

    @Override
    public void postInsert(final Region entity, final PostInsertContext<Region> context) {
    }

    @Override
    public void postUpdate(final Region entity, final PostUpdateContext<Region> context) {
    }

    @Override
    public void postDelete(final Region entity, final PostDeleteContext<Region> context) {
    }
}
