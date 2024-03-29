package jp.co.project.planets.earthly.schema.repository;

import org.springframework.stereotype.Repository;

import jp.co.project.planets.earthly.schema.db.dao.ManagementCompanyUserDao;

/**
 * management company user repository
 */
@Repository
public class ManagementCompanyUserRepository {

    private final ManagementCompanyUserDao managementCompanyUserDao;

    public ManagementCompanyUserRepository(final ManagementCompanyUserDao managementCompanyUserDao) {
        this.managementCompanyUserDao = managementCompanyUserDao;
    }
}
