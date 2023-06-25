package jp.co.project.planets.earthly.schema.repository;

import org.springframework.stereotype.Repository;

import jp.co.project.planets.earthly.schema.db.dao.PasswordTokenDao;
import jp.co.project.planets.earthly.schema.db.entity.PasswordToken;

/**
 * password token repository
 */
@Repository
public class PasswordTokenRepository {

    public final PasswordTokenDao passwordTokenDao;

    public PasswordTokenRepository(final PasswordTokenDao passwordTokenDao) {
        this.passwordTokenDao = passwordTokenDao;
    }

    /**
     * パスワードトークンを登録
     * 
     * @param passwordToken
     *            password token
     * @return 登録件数
     */
    public int insert(final PasswordToken passwordToken) {
        return passwordTokenDao.insert(passwordToken);
    }

    public int delete(final String id) {
        final var passwordToken = new PasswordToken(id, null, null, null);
        return passwordTokenDao.delete(passwordToken);
    }
}
