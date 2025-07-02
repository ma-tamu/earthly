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
public class NoticeListener implements EntityListener<Notice> {

    @Override
    public void preInsert(Notice entity, PreInsertContext<Notice> context) {
        entity.setId(java.util.UUID.randomUUID().toString().replace("-", ""));
        
        
    }

    @Override
    public void preUpdate(Notice entity, PreUpdateContext<Notice> context) {
        
        
    }

    @Override
    public void preDelete(Notice entity, PreDeleteContext<Notice> context) {
    }

    @Override
    public void postInsert(Notice entity, PostInsertContext<Notice> context) {
    }

    @Override
    public void postUpdate(Notice entity, PostUpdateContext<Notice> context) {
    }

    @Override
    public void postDelete(Notice entity, PostDeleteContext<Notice> context) {
    }
}
