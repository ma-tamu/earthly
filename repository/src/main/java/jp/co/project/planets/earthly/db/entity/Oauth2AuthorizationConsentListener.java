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
public class Oauth2AuthorizationConsentListener implements EntityListener<Oauth2AuthorizationConsent> {

    @Override
    public void preInsert(Oauth2AuthorizationConsent entity, PreInsertContext<Oauth2AuthorizationConsent> context) {
    }

    @Override
    public void preUpdate(Oauth2AuthorizationConsent entity, PreUpdateContext<Oauth2AuthorizationConsent> context) {
    }

    @Override
    public void preDelete(Oauth2AuthorizationConsent entity, PreDeleteContext<Oauth2AuthorizationConsent> context) {
    }

    @Override
    public void postInsert(Oauth2AuthorizationConsent entity, PostInsertContext<Oauth2AuthorizationConsent> context) {
    }

    @Override
    public void postUpdate(Oauth2AuthorizationConsent entity, PostUpdateContext<Oauth2AuthorizationConsent> context) {
    }

    @Override
    public void postDelete(Oauth2AuthorizationConsent entity, PostDeleteContext<Oauth2AuthorizationConsent> context) {
    }
}
