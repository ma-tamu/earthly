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
public class OauthClientScopeListener implements EntityListener<OauthClientScope> {

    @Override
    public void preInsert(OauthClientScope entity, PreInsertContext<OauthClientScope> context) {
        entity.setId(java.util.UUID.randomUUID().toString().replace("-", ""));
    }

    @Override
    public void preUpdate(OauthClientScope entity, PreUpdateContext<OauthClientScope> context) {
    }

    @Override
    public void preDelete(OauthClientScope entity, PreDeleteContext<OauthClientScope> context) {
    }

    @Override
    public void postInsert(OauthClientScope entity, PostInsertContext<OauthClientScope> context) {
    }

    @Override
    public void postUpdate(OauthClientScope entity, PostUpdateContext<OauthClientScope> context) {
    }

    @Override
    public void postDelete(OauthClientScope entity, PostDeleteContext<OauthClientScope> context) {
    }
}
