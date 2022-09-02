package jp.co.project.planets.earthly.repository;

import jp.co.project.planets.earthly.db.dao.CompanyDao;
import jp.co.project.planets.earthly.db.entity.Company;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * company repository
 */
@Repository
public class CompanyRepository {

    private final CompanyDao companyDao;

    /**
     * new instance company repository
     *
     * @param companyDao
     *         company dao
     */
    public CompanyRepository(final CompanyDao companyDao) {
        this.companyDao = companyDao;
    }

    /**
     * 閲覧できる会社一覧を取得
     *
     * @param userId
     *         ユーザーID
     * @param keywordOptional
     *         キーワード
     * @param isViewAllCompany
     *         全社閲覧できるか
     * @return 会社一覧
     */
    public List<Company> findAccessibleByUserId(final String userId, final Optional<String> keywordOptional,
            final boolean isViewAllCompany) {
        return companyDao.selectAccessibleByUserId(userId, keywordOptional, isViewAllCompany);
    }
}
