package jp.co.project.planets.earthly.db.entity;

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
    public void preInsert(SubOffice entity, PreInsertContext<SubOffice> context) {
        entity.setId(java.util.UUID.randomUUID().toString().replace("-", ""));
    }

    @Override
    public void preUpdate(SubOffice entity, PreUpdateContext<SubOffice> context) {
    }

    @Override
    public void preDelete(SubOffice entity, PreDeleteContext<SubOffice> context) {
    }

    @Override
    public void postInsert(SubOffice entity, PostInsertContext<SubOffice> context) {
    }

    @Override
    public void postUpdate(SubOffice entity, PostUpdateContext<SubOffice> context) {
    }

    @Override
    public void postDelete(SubOffice entity, PostDeleteContext<SubOffice> context) {
    }
}
