package jp.co.project.planets.earthly.schema.db.entity;

import org.seasar.doma.jdbc.entity.*;

/**
 * 
 */
public class WorkTimeListener implements EntityListener<WorkTime> {

    @Override
    public void preInsert(final WorkTime entity, final PreInsertContext<WorkTime> context) {
        entity.setId(java.util.UUID.randomUUID().toString().replace("-", ""));
        final var now = java.time.LocalDateTime.now();
        entity.setCreatedAt(now);
        entity.setUpdatedAt(now);
    }

    @Override
    public void preUpdate(final WorkTime entity, final PreUpdateContext<WorkTime> context) {
        final var now = java.time.LocalDateTime.now();
        entity.setUpdatedAt(now);
    }

    @Override
    public void preDelete(final WorkTime entity, final PreDeleteContext<WorkTime> context) {
    }

    @Override
    public void postInsert(final WorkTime entity, final PostInsertContext<WorkTime> context) {
    }

    @Override
    public void postUpdate(final WorkTime entity, final PostUpdateContext<WorkTime> context) {
    }

    @Override
    public void postDelete(final WorkTime entity, final PostDeleteContext<WorkTime> context) {
    }
}
