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
public class OauthClientRedirectUrlListener implements EntityListener<OauthClientRedirectUrl> {

    @Override
    public void preInsert(OauthClientRedirectUrl entity, PreInsertContext<OauthClientRedirectUrl> context) {
        entity.setId(java.util.UUID.randomUUID().toString().replace("-", ""));
    }

    @Override
    public void preUpdate(OauthClientRedirectUrl entity, PreUpdateContext<OauthClientRedirectUrl> context) {
    }

    @Override
    public void preDelete(OauthClientRedirectUrl entity, PreDeleteContext<OauthClientRedirectUrl> context) {
    }

    @Override
    public void postInsert(OauthClientRedirectUrl entity, PostInsertContext<OauthClientRedirectUrl> context) {
    }

    @Override
    public void postUpdate(OauthClientRedirectUrl entity, PostUpdateContext<OauthClientRedirectUrl> context) {
    }

    @Override
    public void postDelete(OauthClientRedirectUrl entity, PostDeleteContext<OauthClientRedirectUrl> context) {
    }
}
