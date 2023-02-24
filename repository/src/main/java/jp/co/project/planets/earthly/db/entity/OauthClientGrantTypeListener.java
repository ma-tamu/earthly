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
public class OauthClientGrantTypeListener implements EntityListener<OauthClientGrantType> {

    @Override
    public void preInsert(OauthClientGrantType entity, PreInsertContext<OauthClientGrantType> context) {
        entity.setId(java.util.UUID.randomUUID().toString().replace("-", ""));
    }

    @Override
    public void preUpdate(OauthClientGrantType entity, PreUpdateContext<OauthClientGrantType> context) {
    }

    @Override
    public void preDelete(OauthClientGrantType entity, PreDeleteContext<OauthClientGrantType> context) {
    }

    @Override
    public void postInsert(OauthClientGrantType entity, PostInsertContext<OauthClientGrantType> context) {
    }

    @Override
    public void postUpdate(OauthClientGrantType entity, PostUpdateContext<OauthClientGrantType> context) {
    }

    @Override
    public void postDelete(OauthClientGrantType entity, PostDeleteContext<OauthClientGrantType> context) {
    }
}
