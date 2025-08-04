package jp.co.project.planets.earthly.schema.db.dao;

import java.util.List;

import org.seasar.doma.Dao;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;

import jp.co.project.planets.earthly.schema.db.dao.base.CountryBaseDao;
import jp.co.project.planets.earthly.schema.db.entity.Country;

/**
 * DAO interface for accessing and managing data related to countries.
 * This interface extends the base functionality provided by
 * {@link CountryBaseDao}.
 * Common database operations such as select, insert, update, and delete are
 * defined
 * in the base DAO and are inherited by this interface.
 * The purpose of this interface is to handle country-related data operations
 * within the application, leveraging the functionality provided by Doma
 * framework.
 */
@Dao
@ConfigAutowireable
public interface CountryDao extends CountryBaseDao {

    /**
     * Retrieves the list of all countries from the database.
     *
     * @return a list of all countries
     */
    @Select
    List<Country> selectAll();
}
