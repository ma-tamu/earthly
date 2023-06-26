package jp.co.project.planets.earthly.schema.repository;

import org.springframework.stereotype.Repository;

import jp.co.project.planets.earthly.schema.db.dao.LogoutRedirectUrlDao;

/**
 * logout redirect repository
 */
@Repository
public class LogoutRedirectRepository {

    private final LogoutRedirectUrlDao logoutRedirectUrlDao;

    public LogoutRedirectRepository(final LogoutRedirectUrlDao logoutRedirectUrlDao) {
        this.logoutRedirectUrlDao = logoutRedirectUrlDao;
    }
}
