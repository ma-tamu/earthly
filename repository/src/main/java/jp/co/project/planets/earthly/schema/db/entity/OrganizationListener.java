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
public class OrganizationListener implements EntityListener<Organization> {

    @Override
    public void preInsert(Organization entity, PreInsertContext<Organization> context) {
        entity.setId(java.util.UUID.randomUUID().toString().replace("-", ""));
    }

    @Override
    public void preUpdate(Organization entity, PreUpdateContext<Organization> context) {
    }

    @Override
    public void preDelete(Organization entity, PreDeleteContext<Organization> context) {
    }

    @Override
    public void postInsert(Organization entity, PostInsertContext<Organization> context) {
    }

    @Override
    public void postUpdate(Organization entity, PostUpdateContext<Organization> context) {
    }

    @Override
    public void postDelete(Organization entity, PostDeleteContext<Organization> context) {
    }
}
