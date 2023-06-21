package jp.co.project.planets.earthly.db.dao;

import java.util.List;
import java.util.Optional;

import org.seasar.doma.Dao;
import org.seasar.doma.Select;

import jp.co.project.planets.earthly.db.annotation.ConfigAutowireable;
import jp.co.project.planets.earthly.db.dao.base.CompanyBaseDao;
import jp.co.project.planets.earthly.db.entity.Company;
import jp.co.project.planets.earthly.model.entity.CompanyEntity;

/**
 * company dao
 */
@Dao
@ConfigAutowireable
public interface CompanyDao extends CompanyBaseDao {

    @Select
    Optional<CompanyEntity> selectByPrimaryKey(String id);

    @Select
    Optional<Company> selectAccessibleByPrimaryKey(String id, String userId, boolean hasViewAllCompany);

    @Select
    List<Company> selectAccessibleByUserId(String userId, Optional<String> keywordOptional, boolean hasViewAllCompany);

    @Select
    List<Company> selectManagementCompanyByUserId(String userId);
}
