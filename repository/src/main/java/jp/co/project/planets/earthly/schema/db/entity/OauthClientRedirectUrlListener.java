package jp.co.project.planets.earthly.schema.db.entity;

import org.seasar.doma.jdbc.entity.*;

/**
 * 
 */
public class OauthClientRedirectUrlListener implements EntityListener<OauthClientRedirectUrl> {

    @Override
    public void preInsert(final OauthClientRedirectUrl entity, final PreInsertContext<OauthClientRedirectUrl> context) {
        entity.setId(java.util.UUID.randomUUID().toString().replace("-", ""));
        final var now = java.time.LocalDateTime.now();
        entity.setCreatedAt(now);
        entity.setUpdatedAt(now);
    }

    @Override
    public void preUpdate(final OauthClientRedirectUrl entity, final PreUpdateContext<OauthClientRedirectUrl> context) {
        final var now = java.time.LocalDateTime.now();
        entity.setUpdatedAt(now);
    }

    @Override
    public void preDelete(final OauthClientRedirectUrl entity, final PreDeleteContext<OauthClientRedirectUrl> context) {
    }

    @Override
    public void postInsert(final OauthClientRedirectUrl entity,
        final PostInsertContext<OauthClientRedirectUrl> context) {
    }

    @Override
    public void postUpdate(final OauthClientRedirectUrl entity,
        final PostUpdateContext<OauthClientRedirectUrl> context) {
    }

    @Override
    public void postDelete(final OauthClientRedirectUrl entity,
        final PostDeleteContext<OauthClientRedirectUrl> context) {
    }
}
