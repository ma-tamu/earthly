package jp.co.project.planets.earthly.db.dao;

import org.seasar.doma.Dao;

import jp.co.project.planets.earthly.db.annotation.ConfigAutowireable;
import jp.co.project.planets.earthly.db.dao.base.ManagementCompanyUserBaseDao;

/**
 * management company user dao
 */
@Dao
@ConfigAutowireable
public interface ManagementCompanyUserDao extends ManagementCompanyUserBaseDao {
}
