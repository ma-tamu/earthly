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
public class PasswordTokenListener implements EntityListener<PasswordToken> {

    @Override
    public void preInsert(PasswordToken entity, PreInsertContext<PasswordToken> context) {
        entity.setId(java.util.UUID.randomUUID().toString().replace("-", ""));
    }

    @Override
    public void preUpdate(PasswordToken entity, PreUpdateContext<PasswordToken> context) {
    }

    @Override
    public void preDelete(PasswordToken entity, PreDeleteContext<PasswordToken> context) {
    }

    @Override
    public void postInsert(PasswordToken entity, PostInsertContext<PasswordToken> context) {
    }

    @Override
    public void postUpdate(PasswordToken entity, PostUpdateContext<PasswordToken> context) {
    }

    @Override
    public void postDelete(PasswordToken entity, PostDeleteContext<PasswordToken> context) {
    }
}
