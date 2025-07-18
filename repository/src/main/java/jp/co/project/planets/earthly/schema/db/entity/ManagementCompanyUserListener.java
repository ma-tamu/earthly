package jp.co.project.planets.earthly.schema.db.entity;

import org.seasar.doma.jdbc.entity.*;

/**
 * 
 */
public class ManagementCompanyUserListener implements EntityListener<ManagementCompanyUser> {

    @Override
    public void preInsert(final ManagementCompanyUser entity, final PreInsertContext<ManagementCompanyUser> context) {
        entity.setId(java.util.UUID.randomUUID().toString().replace("-", ""));
        final var now = java.time.LocalDateTime.now();
        entity.setCreatedAt(now);
        entity.setUpdatedAt(now);
    }

    @Override
    public void preUpdate(final ManagementCompanyUser entity, final PreUpdateContext<ManagementCompanyUser> context) {
        final var now = java.time.LocalDateTime.now();
        entity.setUpdatedAt(now);
    }

    @Override
    public void preDelete(final ManagementCompanyUser entity, final PreDeleteContext<ManagementCompanyUser> context) {
    }

    @Override
    public void postInsert(final ManagementCompanyUser entity, final PostInsertContext<ManagementCompanyUser> context) {
    }

    @Override
    public void postUpdate(final ManagementCompanyUser entity, final PostUpdateContext<ManagementCompanyUser> context) {
    }

    @Override
    public void postDelete(final ManagementCompanyUser entity, final PostDeleteContext<ManagementCompanyUser> context) {
    }
}
