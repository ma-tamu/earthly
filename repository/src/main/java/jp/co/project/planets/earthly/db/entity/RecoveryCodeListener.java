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
public class RecoveryCodeListener implements EntityListener<RecoveryCode> {

    @Override
    public void preInsert(RecoveryCode entity, PreInsertContext<RecoveryCode> context) {
        entity.setId(java.util.UUID.randomUUID().toString().replace("-", ""));
    }

    @Override
    public void preUpdate(RecoveryCode entity, PreUpdateContext<RecoveryCode> context) {
    }

    @Override
    public void preDelete(RecoveryCode entity, PreDeleteContext<RecoveryCode> context) {
    }

    @Override
    public void postInsert(RecoveryCode entity, PostInsertContext<RecoveryCode> context) {
    }

    @Override
    public void postUpdate(RecoveryCode entity, PostUpdateContext<RecoveryCode> context) {
    }

    @Override
    public void postDelete(RecoveryCode entity, PostDeleteContext<RecoveryCode> context) {
    }
}
