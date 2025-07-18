package jp.co.project.planets.earthly.schema.db.entity;

import org.seasar.doma.jdbc.entity.*;

/**
 * 
 */
public class OrganizationListener implements EntityListener<Organization> {

    @Override
    public void preInsert(final Organization entity, final PreInsertContext<Organization> context) {
        entity.setId(java.util.UUID.randomUUID().toString().replace("-", ""));
        final var now = java.time.LocalDateTime.now();
        entity.setCreatedAt(now);
        entity.setUpdatedAt(now);
    }

    @Override
    public void preUpdate(final Organization entity, final PreUpdateContext<Organization> context) {
        final var now = java.time.LocalDateTime.now();
        entity.setUpdatedAt(now);
    }

    @Override
    public void preDelete(final Organization entity, final PreDeleteContext<Organization> context) {
    }

    @Override
    public void postInsert(final Organization entity, final PostInsertContext<Organization> context) {
    }

    @Override
    public void postUpdate(final Organization entity, final PostUpdateContext<Organization> context) {
    }

    @Override
    public void postDelete(final Organization entity, final PostDeleteContext<Organization> context) {
    }
}
