package jp.co.project.planets.earthly.webapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.project.planets.earthly.schema.db.entity.Company;
import jp.co.project.planets.earthly.schema.repository.CompanyRepository;
import jp.co.project.planets.earthly.webapp.security.dto.EarthlyUserInfoDto;

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
     * @param userInfoDto
     *            ユーザー情報
     * @return 会社リスト
     */
    @Transactional
    public List<Company> searchAccessibleCompanyByUserId(final Optional<String> keywordOptional,
            final EarthlyUserInfoDto userInfoDto) {
        return companyRepository.findAccessibleByUserId(userInfoDto.id(), keywordOptional,
                userInfoDto.permissionEnumList());
    }
}
