package jp.co.project.planets.earthly.schema.repository;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import jp.co.project.planets.earthly.schema.db.dao.CountryDao;
import jp.co.project.planets.earthly.schema.db.entity.Country;

/**
 * CountryRepository handles data access operations for country-related data.
 * It serves as a repository component in the application,
 * interacting with the database or other storage mechanisms to perform CRUD
 * operations.
 */
@Repository
public class CountryRepository {

    private final CountryDao countryDao;

    public CountryRepository(final CountryDao countryDao) {
        this.countryDao = countryDao;
    }

    /**
     * Retrieves a list of all countries.
     *
     * @return a list of Country objects representing all the countries in the
     *             database
     */
    @Cacheable("countries")
    public List<Country> findAll() {
        return countryDao.selectAll();
    }
}
