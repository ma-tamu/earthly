package jp.co.project.planets.earthly.schema.db.entity;

import org.seasar.doma.jdbc.entity.*;

/**
 * 
 */
public class LanguageListener implements EntityListener<Language> {

    @Override
    public void preInsert(final Language entity, final PreInsertContext<Language> context) {
        entity.setId(java.util.UUID.randomUUID().toString().replace("-", ""));
        final var now = java.time.LocalDateTime.now();
        entity.setCreatedAt(now);
        entity.setUpdatedAt(now);
    }

    @Override
    public void preUpdate(final Language entity, final PreUpdateContext<Language> context) {
        final var now = java.time.LocalDateTime.now();
        entity.setUpdatedAt(now);
    }

    @Override
    public void preDelete(final Language entity, final PreDeleteContext<Language> context) {
    }

    @Override
    public void postInsert(final Language entity, final PostInsertContext<Language> context) {
    }

    @Override
    public void postUpdate(final Language entity, final PostUpdateContext<Language> context) {
    }

    @Override
    public void postDelete(final Language entity, final PostDeleteContext<Language> context) {
    }
}
