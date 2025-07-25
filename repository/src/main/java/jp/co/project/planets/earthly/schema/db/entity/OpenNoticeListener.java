package jp.co.project.planets.earthly.schema.db.entity;

import org.seasar.doma.jdbc.entity.*;

/**
 * 
 */
public class OpenNoticeListener implements EntityListener<OpenNotice> {

    @Override
    public void preInsert(final OpenNotice entity, final PreInsertContext<OpenNotice> context) {
        entity.setId(java.util.UUID.randomUUID().toString().replace("-", ""));

    }

    @Override
    public void preUpdate(final OpenNotice entity, final PreUpdateContext<OpenNotice> context) {

    }

    @Override
    public void preDelete(final OpenNotice entity, final PreDeleteContext<OpenNotice> context) {
    }

    @Override
    public void postInsert(final OpenNotice entity, final PostInsertContext<OpenNotice> context) {
    }

    @Override
    public void postUpdate(final OpenNotice entity, final PostUpdateContext<OpenNotice> context) {
    }

    @Override
    public void postDelete(final OpenNotice entity, final PostDeleteContext<OpenNotice> context) {
    }
}
