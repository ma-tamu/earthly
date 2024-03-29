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
public class RoleListener implements EntityListener<Role> {

    @Override
    public void preInsert(Role entity, PreInsertContext<Role> context) {
        entity.setId(java.util.UUID.randomUUID().toString().replace("-", ""));
    }

    @Override
    public void preUpdate(Role entity, PreUpdateContext<Role> context) {
    }

    @Override
    public void preDelete(Role entity, PreDeleteContext<Role> context) {
    }

    @Override
    public void postInsert(Role entity, PostInsertContext<Role> context) {
    }

    @Override
    public void postUpdate(Role entity, PostUpdateContext<Role> context) {
    }

    @Override
    public void postDelete(Role entity, PostDeleteContext<Role> context) {
    }
}
