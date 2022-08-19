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
public class OauthClientListener implements EntityListener<OauthClient> {

    @Override
    public void preInsert(OauthClient entity, PreInsertContext<OauthClient> context) {
    }

    @Override
    public void preUpdate(OauthClient entity, PreUpdateContext<OauthClient> context) {
    }

    @Override
    public void preDelete(OauthClient entity, PreDeleteContext<OauthClient> context) {
    }

    @Override
    public void postInsert(OauthClient entity, PostInsertContext<OauthClient> context) {
    }

    @Override
    public void postUpdate(OauthClient entity, PostUpdateContext<OauthClient> context) {
    }

    @Override
    public void postDelete(OauthClient entity, PostDeleteContext<OauthClient> context) {
    }
}
