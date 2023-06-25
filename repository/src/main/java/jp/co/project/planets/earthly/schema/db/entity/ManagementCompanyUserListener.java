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
public class ManagementCompanyUserListener implements EntityListener<ManagementCompanyUser> {

    @Override
    public void preInsert(ManagementCompanyUser entity, PreInsertContext<ManagementCompanyUser> context) {
        entity.setId(java.util.UUID.randomUUID().toString().replace("-", ""));
    }

    @Override
    public void preUpdate(ManagementCompanyUser entity, PreUpdateContext<ManagementCompanyUser> context) {
    }

    @Override
    public void preDelete(ManagementCompanyUser entity, PreDeleteContext<ManagementCompanyUser> context) {
    }

    @Override
    public void postInsert(ManagementCompanyUser entity, PostInsertContext<ManagementCompanyUser> context) {
    }

    @Override
    public void postUpdate(ManagementCompanyUser entity, PostUpdateContext<ManagementCompanyUser> context) {
    }

    @Override
    public void postDelete(ManagementCompanyUser entity, PostDeleteContext<ManagementCompanyUser> context) {
    }
}
