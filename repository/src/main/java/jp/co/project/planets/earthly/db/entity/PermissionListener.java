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
public class PermissionListener implements EntityListener<Permission> {

    @Override
    public void preInsert(Permission entity, PreInsertContext<Permission> context) {
        entity.setId(java.util.UUID.randomUUID().toString().replace("-", ""));
    }

    @Override
    public void preUpdate(Permission entity, PreUpdateContext<Permission> context) {
    }

    @Override
    public void preDelete(Permission entity, PreDeleteContext<Permission> context) {
    }

    @Override
    public void postInsert(Permission entity, PostInsertContext<Permission> context) {
    }

    @Override
    public void postUpdate(Permission entity, PostUpdateContext<Permission> context) {
    }

    @Override
    public void postDelete(Permission entity, PostDeleteContext<Permission> context) {
    }
}
