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
public class CompanyListener implements EntityListener<Company> {

    @Override
    public void preInsert(Company entity, PreInsertContext<Company> context) {
        entity.setId(java.util.UUID.randomUUID().toString().replace("-", ""));
    }

    @Override
    public void preUpdate(Company entity, PreUpdateContext<Company> context) {
    }

    @Override
    public void preDelete(Company entity, PreDeleteContext<Company> context) {
    }

    @Override
    public void postInsert(Company entity, PostInsertContext<Company> context) {
    }

    @Override
    public void postUpdate(Company entity, PostUpdateContext<Company> context) {
    }

    @Override
    public void postDelete(Company entity, PostDeleteContext<Company> context) {
    }
}
