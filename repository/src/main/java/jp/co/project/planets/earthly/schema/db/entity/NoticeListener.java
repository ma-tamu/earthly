package jp.co.project.planets.earthly.schema.db.entity;

import org.seasar.doma.jdbc.entity.*;

/**
 * 
 */
public class NoticeListener implements EntityListener<Notice> {

    @Override
    public void preInsert(final Notice entity, final PreInsertContext<Notice> context) {
        entity.setId(java.util.UUID.randomUUID().toString().replace("-", ""));
        final var now = java.time.LocalDateTime.now();
        entity.setCreatedAt(now);
        entity.setUpdatedAt(now);
    }

    @Override
    public void preUpdate(final Notice entity, final PreUpdateContext<Notice> context) {
        final var now = java.time.LocalDateTime.now();
        entity.setUpdatedAt(now);
    }

    @Override
    public void preDelete(final Notice entity, final PreDeleteContext<Notice> context) {
    }

    @Override
    public void postInsert(final Notice entity, final PostInsertContext<Notice> context) {
    }

    @Override
    public void postUpdate(final Notice entity, final PostUpdateContext<Notice> context) {
    }

    @Override
    public void postDelete(final Notice entity, final PostDeleteContext<Notice> context) {
    }
}
