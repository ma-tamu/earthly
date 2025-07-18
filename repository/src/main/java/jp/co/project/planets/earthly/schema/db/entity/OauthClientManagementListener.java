package jp.co.project.planets.earthly.schema.db.entity;

import org.seasar.doma.jdbc.entity.*;

/**
 * 
 */
public class OauthClientManagementListener implements EntityListener<OauthClientManagement> {

    @Override
    public void preInsert(final OauthClientManagement entity, final PreInsertContext<OauthClientManagement> context) {
        entity.setId(java.util.UUID.randomUUID().toString().replace("-", ""));
        final var now = java.time.LocalDateTime.now();
        entity.setCreatedAt(now);
    }

    @Override
    public void preUpdate(final OauthClientManagement entity, final PreUpdateContext<OauthClientManagement> context) {

    }

    @Override
    public void preDelete(final OauthClientManagement entity, final PreDeleteContext<OauthClientManagement> context) {
    }

    @Override
    public void postInsert(final OauthClientManagement entity, final PostInsertContext<OauthClientManagement> context) {
    }

    @Override
    public void postUpdate(final OauthClientManagement entity, final PostUpdateContext<OauthClientManagement> context) {
    }

    @Override
    public void postDelete(final OauthClientManagement entity, final PostDeleteContext<OauthClientManagement> context) {
    }
}
