package jp.co.project.planets.earthly.webapp.api.controller.suggest;

import jp.co.project.planets.earthly.webapp.api.helper.CompanyResponseHelper;
import jp.co.project.planets.earthly.webapp.security.dto.EarthlyUserInfoDto;
import jp.co.project.planets.earthly.webapp.service.SuggestService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * company suggest rest controller
 */
@RestController
@RequestMapping("api/suggests/companies")
public class CompanySuggestRestController {

    private final SuggestService suggestService;
    private final CompanyResponseHelper companyResponseHelper;

    /**
     * new instance company suggest rest controller
     *
     * @param suggestService
     *         suggest service
     * @param companyResponseHelper
     *         company response helper
     */
    public CompanySuggestRestController(final SuggestService suggestService,
            final CompanyResponseHelper companyResponseHelper) {
        this.suggestService = suggestService;
        this.companyResponseHelper = companyResponseHelper;
    }

    /**
     * 会社検索
     *
     * @param userInfoDto
     *         ユーザー情報
     * @return 会社リストレスポンス
     */
    @GetMapping
    public ResponseEntity searchCompanies(@RequestParam("keyword") final Optional<String> keywordOptional,
            @AuthenticationPrincipal final EarthlyUserInfoDto userInfoDto) {
        final var companyList = suggestService.searchAccessibleCompanyByUserId(keywordOptional, userInfoDto);
        final var companyResponse = companyResponseHelper.generateCompanyResponse(companyList);
        return ResponseEntity.ok(companyResponse);
    }
}
