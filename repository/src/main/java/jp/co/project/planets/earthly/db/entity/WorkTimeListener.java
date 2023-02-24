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
public class WorkTimeListener implements EntityListener<WorkTime> {

    @Override
    public void preInsert(WorkTime entity, PreInsertContext<WorkTime> context) {
        entity.setId(java.util.UUID.randomUUID().toString().replace("-", ""));
    }

    @Override
    public void preUpdate(WorkTime entity, PreUpdateContext<WorkTime> context) {
    }

    @Override
    public void preDelete(WorkTime entity, PreDeleteContext<WorkTime> context) {
    }

    @Override
    public void postInsert(WorkTime entity, PostInsertContext<WorkTime> context) {
    }

    @Override
    public void postUpdate(WorkTime entity, PostUpdateContext<WorkTime> context) {
    }

    @Override
    public void postDelete(WorkTime entity, PostDeleteContext<WorkTime> context) {
    }
}
