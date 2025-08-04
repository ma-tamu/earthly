package jp.co.project.planets.earthly.schema.db.dao;

import org.seasar.doma.Dao;
import org.seasar.doma.boot.ConfigAutowireable;

import jp.co.project.planets.earthly.schema.db.dao.base.OpenNoticeBaseDao;

/**
 * 既読管理
 */
@Dao
@ConfigAutowireable
public interface OpenNoticeDao extends OpenNoticeBaseDao {
}
