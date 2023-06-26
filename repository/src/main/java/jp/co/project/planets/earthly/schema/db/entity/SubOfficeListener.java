package jp.co.project.planets.earthly.schema.db.entity;

import org.seasar.doma.jdbc.entity.EntityListener;
import org.seasar.doma.jdbc.entity.PostDeleteContext;
import org.seasar.doma.jdbc.entity.PostInsertContext;
import org.seasar.doma.jdbc.entity.PostUpdateContext;
import org.seasar.doma.jdbc.entity.PreDeleteContext;
import org.seasar.doma.jdbc.entity.PreInsertContext;
import org.seasar.doma.jdbc.entity.PreUpdateContext;

/**
 * 
 */
public class SubOfficeListener implements EntityListener<SubOffice> {

    @Override
    public void preInsert(final SubOffice entity, final PreInsertContext<SubOffice> context) {
        entity.setId(java.util.UUID.randomUUID().toString().replace("-", ""));
    }

    @Override
    public void preUpdate(final SubOffice entity, final PreUpdateContext<SubOffice> context) {
    }

    @Override
    public void preDelete(final SubOffice entity, final PreDeleteContext<SubOffice> context) {
    }

    @Override
    public void postInsert(final SubOffice entity, final PostInsertContext<SubOffice> context) {
    }

    @Override
    public void postUpdate(final SubOffice entity, final PostUpdateContext<SubOffice> context) {
    }

    @Override
    public void postDelete(final SubOffice entity, final PostDeleteContext<SubOffice> context) {
    }
}
