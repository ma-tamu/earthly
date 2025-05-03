package jp.co.project.planets.earthly.schema.db.dao;

import org.seasar.doma.Dao;
import org.seasar.doma.boot.ConfigAutowireable;

import jp.co.project.planets.earthly.schema.db.dao.base.OrganizationBaseDao;

/**
 * 組織DAO
 */
@Dao
@ConfigAutowireable
public interface OrganizationDao extends OrganizationBaseDao {
}
