package jp.co.project.planets.earthly.schema.db.entity;

import org.seasar.doma.jdbc.entity.*;

/**
 * 
 */
public class OauthClientListener implements EntityListener<OauthClient> {

    @Override
    public void preInsert(final OauthClient entity, final PreInsertContext<OauthClient> context) {
        entity.setId(java.util.UUID.randomUUID().toString().replace("-", ""));
        final var now = java.time.LocalDateTime.now();
        entity.setCreatedAt(now);
        entity.setUpdatedAt(now);
    }

    @Override
    public void preUpdate(final OauthClient entity, final PreUpdateContext<OauthClient> context) {
        final var now = java.time.LocalDateTime.now();
        entity.setUpdatedAt(now);
    }

    @Override
    public void preDelete(final OauthClient entity, final PreDeleteContext<OauthClient> context) {
    }

    @Override
    public void postInsert(final OauthClient entity, final PostInsertContext<OauthClient> context) {
    }

    @Override
    public void postUpdate(final OauthClient entity, final PostUpdateContext<OauthClient> context) {
    }

    @Override
    public void postDelete(final OauthClient entity, final PostDeleteContext<OauthClient> context) {
    }
}
