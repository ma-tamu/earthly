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
public class LogoutRedirectUrlListener implements EntityListener<LogoutRedirectUrl> {

    @Override
    public void preInsert(LogoutRedirectUrl entity, PreInsertContext<LogoutRedirectUrl> context) {
        entity.setId(java.util.UUID.randomUUID().toString().replace("-", ""));
    }

    @Override
    public void preUpdate(LogoutRedirectUrl entity, PreUpdateContext<LogoutRedirectUrl> context) {
    }

    @Override
    public void preDelete(LogoutRedirectUrl entity, PreDeleteContext<LogoutRedirectUrl> context) {
    }

    @Override
    public void postInsert(LogoutRedirectUrl entity, PostInsertContext<LogoutRedirectUrl> context) {
    }

    @Override
    public void postUpdate(LogoutRedirectUrl entity, PostUpdateContext<LogoutRedirectUrl> context) {
    }

    @Override
    public void postDelete(LogoutRedirectUrl entity, PostDeleteContext<LogoutRedirectUrl> context) {
    }
}
