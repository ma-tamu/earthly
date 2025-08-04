package jp.co.project.planets.earthly.schema.db.entity;

import org.seasar.doma.jdbc.entity.*;

/**
 * 
 */
public class CompanyListener implements EntityListener<Company> {

    @Override
    public void preInsert(final Company entity, final PreInsertContext<Company> context) {
        entity.setId(java.util.UUID.randomUUID().toString().replace("-", ""));
        final var now = java.time.LocalDateTime.now();
        entity.setCreatedAt(now);
        entity.setUpdatedAt(now);
    }

    @Override
    public void preUpdate(final Company entity, final PreUpdateContext<Company> context) {
        final var now = java.time.LocalDateTime.now();
        entity.setUpdatedAt(now);
    }

    @Override
    public void preDelete(final Company entity, final PreDeleteContext<Company> context) {
    }

    @Override
    public void postInsert(final Company entity, final PostInsertContext<Company> context) {
    }

    @Override
    public void postUpdate(final Company entity, final PostUpdateContext<Company> context) {
    }

    @Override
    public void postDelete(final Company entity, final PostDeleteContext<Company> context) {
    }
}
