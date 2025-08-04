package jp.co.project.planets.earthly.schema.db.entity;

import org.seasar.doma.jdbc.entity.*;

/**
 * 
 */
public class LogoutRedirectUrlListener implements EntityListener<LogoutRedirectUrl> {

    @Override
    public void preInsert(final LogoutRedirectUrl entity, final PreInsertContext<LogoutRedirectUrl> context) {
        entity.setId(java.util.UUID.randomUUID().toString().replace("-", ""));
        final var now = java.time.LocalDateTime.now();
        entity.setCreatedAt(now);
        entity.setUpdatedAt(now);
    }

    @Override
    public void preUpdate(final LogoutRedirectUrl entity, final PreUpdateContext<LogoutRedirectUrl> context) {
        final var now = java.time.LocalDateTime.now();
        entity.setUpdatedAt(now);
    }

    @Override
    public void preDelete(final LogoutRedirectUrl entity, final PreDeleteContext<LogoutRedirectUrl> context) {
    }

    @Override
    public void postInsert(final LogoutRedirectUrl entity, final PostInsertContext<LogoutRedirectUrl> context) {
    }

    @Override
    public void postUpdate(final LogoutRedirectUrl entity, final PostUpdateContext<LogoutRedirectUrl> context) {
    }

    @Override
    public void postDelete(final LogoutRedirectUrl entity, final PostDeleteContext<LogoutRedirectUrl> context) {
    }
}
