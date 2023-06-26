package jp.co.project.planets.earthly.schema.repository;

import org.springframework.stereotype.Repository;

import jp.co.project.planets.earthly.schema.db.dao.RecoveryCodeDao;
import jp.co.project.planets.earthly.schema.db.entity.RecoveryCode;

/**
 * recovery code repository
 */
@Repository
public class RecoveryCodeRepository {

    public final RecoveryCodeDao recoveryCodeDao;

    public RecoveryCodeRepository(final RecoveryCodeDao recoveryCodeDao) {
        this.recoveryCodeDao = recoveryCodeDao;
    }

    /**
     * insert recovery code
     * 
     * @param recoveryCode
     *            recovery code
     * @return insert count
     */
    public int insert(final RecoveryCode recoveryCode) {
        return recoveryCodeDao.insert(recoveryCode);
    }

}
