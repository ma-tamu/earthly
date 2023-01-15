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
public class RegionListener implements EntityListener<Region> {

    @Override
    public void preInsert(Region entity, PreInsertContext<Region> context) {
        entity.setId(java.util.UUID.randomUUID().toString().replace("-", ""));
    }

    @Override
    public void preUpdate(Region entity, PreUpdateContext<Region> context) {
    }

    @Override
    public void preDelete(Region entity, PreDeleteContext<Region> context) {
    }

    @Override
    public void postInsert(Region entity, PostInsertContext<Region> context) {
    }

    @Override
    public void postUpdate(Region entity, PostUpdateContext<Region> context) {
    }

    @Override
    public void postDelete(Region entity, PostDeleteContext<Region> context) {
    }
}
