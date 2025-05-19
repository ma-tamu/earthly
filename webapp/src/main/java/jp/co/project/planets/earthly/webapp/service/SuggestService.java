package jp.co.project.planets.earthly.webapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.project.planets.earthly.core.account.Account;
import jp.co.project.planets.earthly.schema.db.entity.Company;
import jp.co.project.planets.earthly.schema.repository.CompanyRepository;

/**
 * suggest service
 */
@Service
public class SuggestService {

    private final CompanyRepository companyRepository;

    /**
     * new instance suggest service
     *
     * @param companyRepository
     *            company repository
     */
    public SuggestService(final CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    /**
     * 操作ユーザーの閲覧できる会社を検索。
     *
     * @param keywordOptional
     *            検索キーワード
     * @param account
     *            ユーザー情報
     * @return 会社リスト
     */
    @Transactional
    public List<Company> searchAccessibleCompanyByUserId(final Optional<String> keywordOptional,
        final Account account) {
        return companyRepository.findAccessibleByUserId(account.id(), keywordOptional, account);
    }
}
