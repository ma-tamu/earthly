package jp.co.project.planets.earthly.schema.repository;

import java.util.List;
import java.util.Optional;

import org.seasar.doma.jdbc.criteria.QueryDsl;
import org.springframework.stereotype.Repository;

import jp.co.project.planets.earthly.schema.db.dao.ManagementCompanyUserDao;
import jp.co.project.planets.earthly.schema.db.entity.ManagementCompanyUser;
import jp.co.project.planets.earthly.schema.db.entity.ManagementCompanyUser_;

/**
 * management company user repository
 */
@Repository
public class ManagementCompanyUserRepository {

    private final ManagementCompanyUserDao managementCompanyUserDao;
    private final QueryDsl queryDsl;

    public ManagementCompanyUserRepository(final ManagementCompanyUserDao managementCompanyUserDao,
        final QueryDsl queryDsl) {
        this.managementCompanyUserDao = managementCompanyUserDao;
        this.queryDsl = queryDsl;
    }

    /**
     * 会社IDとユーザーIDで会社管理者を取得する。
     * 
     * @param companyId
     *            会社ID
     * @param userId
     *            ユーザーID
     * @return 会社管理者
     */
    public Optional<ManagementCompanyUser> findByUniqueKey(final String companyId, final String userId) {
        final var criteria = new ManagementCompanyUser_();
        return queryDsl.from(criteria).where(where -> {
            where.eq(criteria.companyId, companyId);
            where.eq(criteria.userId, userId);
        }).fetchOptional();
    }

    /**
     * 会社IDに紐づく会社管理者を取得
     * 
     * @param companyId
     *            会社ID
     * @return 会社管理者
     */
    public List<ManagementCompanyUser> findByCompanyId(final String companyId) {
        final var criteria = new ManagementCompanyUser_();
        return queryDsl.from(criteria).where(where -> where.eq(criteria.companyId, companyId)).fetch();
    }

    /**
     * 会社管理者登録
     * 
     * @param managementCompanyUser
     *            管理者ユーザー
     * @return 登録件数
     */
    public int insert(final ManagementCompanyUser managementCompanyUser) {
        return managementCompanyUserDao.insert(managementCompanyUser);
    }

    /**
     * 対象会社IDの会社管理者を削除
     * 
     * @param companyId
     *            会社ID
     * @return 削除兼巣
     */
    public int deleteByCompanyId(final String companyId) {
        final var criteria = new ManagementCompanyUser_();
        return queryDsl.delete(criteria).where(where -> where.eq(criteria.companyId, companyId)).execute();
    }

}
