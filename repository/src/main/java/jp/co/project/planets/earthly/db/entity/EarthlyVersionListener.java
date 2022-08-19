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
public class EarthlyVersionListener implements EntityListener<EarthlyVersion> {

    @Override
    public void preInsert(EarthlyVersion entity, PreInsertContext<EarthlyVersion> context) {
    }

    @Override
    public void preUpdate(EarthlyVersion entity, PreUpdateContext<EarthlyVersion> context) {
    }

    @Override
    public void preDelete(EarthlyVersion entity, PreDeleteContext<EarthlyVersion> context) {
    }

    @Override
    public void postInsert(EarthlyVersion entity, PostInsertContext<EarthlyVersion> context) {
    }

    @Override
    public void postUpdate(EarthlyVersion entity, PostUpdateContext<EarthlyVersion> context) {
    }

    @Override
    public void postDelete(EarthlyVersion entity, PostDeleteContext<EarthlyVersion> context) {
    }
}
