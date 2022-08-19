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
public class Oauth2AuthorizationListener implements EntityListener<Oauth2Authorization> {

    @Override
    public void preInsert(Oauth2Authorization entity, PreInsertContext<Oauth2Authorization> context) {
    }

    @Override
    public void preUpdate(Oauth2Authorization entity, PreUpdateContext<Oauth2Authorization> context) {
    }

    @Override
    public void preDelete(Oauth2Authorization entity, PreDeleteContext<Oauth2Authorization> context) {
    }

    @Override
    public void postInsert(Oauth2Authorization entity, PostInsertContext<Oauth2Authorization> context) {
    }

    @Override
    public void postUpdate(Oauth2Authorization entity, PostUpdateContext<Oauth2Authorization> context) {
    }

    @Override
    public void postDelete(Oauth2Authorization entity, PostDeleteContext<Oauth2Authorization> context) {
    }
}
