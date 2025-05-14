package jp.co.project.planets.earthly.schema.db.dao;

import org.seasar.doma.Dao;
import org.seasar.doma.boot.ConfigAutowireable;

import jp.co.project.planets.earthly.schema.db.dao.base.OrganizationUserBaseDao;

@Dao
@ConfigAutowireable
public interface OrganizationUserDao extends OrganizationUserBaseDao {
}
