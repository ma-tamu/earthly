package jp.co.project.planets.earthly.schema.db.dao;

import org.seasar.doma.Dao;
import org.seasar.doma.boot.ConfigAutowireable;

import jp.co.project.planets.earthly.schema.db.dao.base.PasswordTokenBaseDao;

/**
 * password token dao
 */
@Dao
@ConfigAutowireable
public interface PasswordTokenDao extends PasswordTokenBaseDao {
}
