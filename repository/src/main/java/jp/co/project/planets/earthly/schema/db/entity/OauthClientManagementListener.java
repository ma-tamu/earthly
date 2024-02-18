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
public class OauthClientManagementListener implements EntityListener<OauthClientManagement> {

    @Override
    public void preInsert(OauthClientManagement entity, PreInsertContext<OauthClientManagement> context) {
        entity.setId(java.util.UUID.randomUUID().toString().replace("-", ""));
    }

    @Override
    public void preUpdate(OauthClientManagement entity, PreUpdateContext<OauthClientManagement> context) {
    }

    @Override
    public void preDelete(OauthClientManagement entity, PreDeleteContext<OauthClientManagement> context) {
    }

    @Override
    public void postInsert(OauthClientManagement entity, PostInsertContext<OauthClientManagement> context) {
    }

    @Override
    public void postUpdate(OauthClientManagement entity, PostUpdateContext<OauthClientManagement> context) {
    }

    @Override
    public void postDelete(OauthClientManagement entity, PostDeleteContext<OauthClientManagement> context) {
    }
}
