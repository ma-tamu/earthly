package jp.co.project.planets.earthly.schema.db.dao;

import org.seasar.doma.Dao;
import org.seasar.doma.boot.ConfigAutowireable;

import jp.co.project.planets.earthly.schema.db.dao.base.RecoveryCodeBaseDao;

/**
 * recovery code dao
 */
@Dao
@ConfigAutowireable
public interface RecoveryCodeDao extends RecoveryCodeBaseDao {
}
