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
public class OrganizationUserListener implements EntityListener<OrganizationUser> {

    @Override
    public void preInsert(OrganizationUser entity, PreInsertContext<OrganizationUser> context) {
        entity.setId(java.util.UUID.randomUUID().toString().replace("-", ""));
        
        
    }

    @Override
    public void preUpdate(OrganizationUser entity, PreUpdateContext<OrganizationUser> context) {
        
        
    }

    @Override
    public void preDelete(OrganizationUser entity, PreDeleteContext<OrganizationUser> context) {
    }

    @Override
    public void postInsert(OrganizationUser entity, PostInsertContext<OrganizationUser> context) {
    }

    @Override
    public void postUpdate(OrganizationUser entity, PostUpdateContext<OrganizationUser> context) {
    }

    @Override
    public void postDelete(OrganizationUser entity, PostDeleteContext<OrganizationUser> context) {
    }
}
