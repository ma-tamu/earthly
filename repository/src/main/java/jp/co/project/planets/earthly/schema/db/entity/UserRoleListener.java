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
public class UserRoleListener implements EntityListener<UserRole> {

    @Override
    public void preInsert(UserRole entity, PreInsertContext<UserRole> context) {
        entity.setId(java.util.UUID.randomUUID().toString().replace("-", ""));
    }

    @Override
    public void preUpdate(UserRole entity, PreUpdateContext<UserRole> context) {
    }

    @Override
    public void preDelete(UserRole entity, PreDeleteContext<UserRole> context) {
    }

    @Override
    public void postInsert(UserRole entity, PostInsertContext<UserRole> context) {
    }

    @Override
    public void postUpdate(UserRole entity, PostUpdateContext<UserRole> context) {
    }

    @Override
    public void postDelete(UserRole entity, PostDeleteContext<UserRole> context) {
    }
}
