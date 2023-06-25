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
public class OauthClientConsentListener implements EntityListener<OauthClientConsent> {

    @Override
    public void preInsert(OauthClientConsent entity, PreInsertContext<OauthClientConsent> context) {
        entity.setId(java.util.UUID.randomUUID().toString().replace("-", ""));
    }

    @Override
    public void preUpdate(OauthClientConsent entity, PreUpdateContext<OauthClientConsent> context) {
    }

    @Override
    public void preDelete(OauthClientConsent entity, PreDeleteContext<OauthClientConsent> context) {
    }

    @Override
    public void postInsert(OauthClientConsent entity, PostInsertContext<OauthClientConsent> context) {
    }

    @Override
    public void postUpdate(OauthClientConsent entity, PostUpdateContext<OauthClientConsent> context) {
    }

    @Override
    public void postDelete(OauthClientConsent entity, PostDeleteContext<OauthClientConsent> context) {
    }
}
