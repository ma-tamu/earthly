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
public class ScopeListener implements EntityListener<Scope> {

    @Override
    public void preInsert(Scope entity, PreInsertContext<Scope> context) {
        entity.setId(java.util.UUID.randomUUID().toString().replace("-", ""));
    }

    @Override
    public void preUpdate(Scope entity, PreUpdateContext<Scope> context) {
    }

    @Override
    public void preDelete(Scope entity, PreDeleteContext<Scope> context) {
    }

    @Override
    public void postInsert(Scope entity, PostInsertContext<Scope> context) {
    }

    @Override
    public void postUpdate(Scope entity, PostUpdateContext<Scope> context) {
    }

    @Override
    public void postDelete(Scope entity, PostDeleteContext<Scope> context) {
    }
}
