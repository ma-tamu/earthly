package jp.co.project.planets.earthly.schema.db.entity;

import org.seasar.doma.jdbc.entity.*;

/**
 * 
 */
public class OrganizationUserListener implements EntityListener<OrganizationUser> {

    @Override
    public void preInsert(final OrganizationUser entity, final PreInsertContext<OrganizationUser> context) {
        entity.setId(java.util.UUID.randomUUID().toString().replace("-", ""));
        final var now = java.time.LocalDateTime.now();
        entity.setCreatedAt(now);
    }

    @Override
    public void preUpdate(final OrganizationUser entity, final PreUpdateContext<OrganizationUser> context) {

    }

    @Override
    public void preDelete(final OrganizationUser entity, final PreDeleteContext<OrganizationUser> context) {
    }

    @Override
    public void postInsert(final OrganizationUser entity, final PostInsertContext<OrganizationUser> context) {
    }

    @Override
    public void postUpdate(final OrganizationUser entity, final PostUpdateContext<OrganizationUser> context) {
    }

    @Override
    public void postDelete(final OrganizationUser entity, final PostDeleteContext<OrganizationUser> context) {
    }
}
