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
public class GrantTypeListener implements EntityListener<GrantType> {

    @Override
    public void preInsert(GrantType entity, PreInsertContext<GrantType> context) {
        entity.setId(java.util.UUID.randomUUID().toString().replace("-", ""));
    }

    @Override
    public void preUpdate(GrantType entity, PreUpdateContext<GrantType> context) {
    }

    @Override
    public void preDelete(GrantType entity, PreDeleteContext<GrantType> context) {
    }

    @Override
    public void postInsert(GrantType entity, PostInsertContext<GrantType> context) {
    }

    @Override
    public void postUpdate(GrantType entity, PostUpdateContext<GrantType> context) {
    }

    @Override
    public void postDelete(GrantType entity, PostDeleteContext<GrantType> context) {
    }
}
