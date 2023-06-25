package jp.co.project.planets.earthly.schema.db.dao;

import org.seasar.doma.Dao;

import jp.co.project.planets.earthly.schema.db.annotation.ConfigAutowireable;
import jp.co.project.planets.earthly.schema.db.dao.base.RecoveryCodeBaseDao;

/**
 * recovery code dao
 */
@Dao
@ConfigAutowireable
public interface RecoveryCodeDao extends RecoveryCodeBaseDao {
}
