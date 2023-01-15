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
public class RolePermissionListener implements EntityListener<RolePermission> {

    @Override
    public void preInsert(RolePermission entity, PreInsertContext<RolePermission> context) {
        entity.setId(java.util.UUID.randomUUID().toString().replace("-", ""));
    }

    @Override
    public void preUpdate(RolePermission entity, PreUpdateContext<RolePermission> context) {
    }

    @Override
    public void preDelete(RolePermission entity, PreDeleteContext<RolePermission> context) {
    }

    @Override
    public void postInsert(RolePermission entity, PostInsertContext<RolePermission> context) {
    }

    @Override
    public void postUpdate(RolePermission entity, PostUpdateContext<RolePermission> context) {
    }

    @Override
    public void postDelete(RolePermission entity, PostDeleteContext<RolePermission> context) {
    }
}
