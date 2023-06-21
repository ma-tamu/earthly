package jp.co.project.planets.earthly.db.dao;

import org.seasar.doma.Dao;

import jp.co.project.planets.earthly.db.annotation.ConfigAutowireable;
import jp.co.project.planets.earthly.db.dao.base.PasswordTokenBaseDao;

/**
 * password token dao
 */
@Dao
@ConfigAutowireable
public interface PasswordTokenDao extends PasswordTokenBaseDao {
}
