package jp.co.project.planets.earthly.webapp.api.helper;

import jp.co.project.planets.earthly.db.entity.Company;
import jp.co.project.planets.earthly.webapp.api.response.CompanyResponse;
import jp.co.project.planets.earthly.webapp.api.response.model.CompanyResponseModel;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * company response helper
 */
@Component
public class CompanyResponseHelper {

    /**
     * 会社レスポンスを生成
     *
     * @param companyList
     *         会社一覧
     * @return 会社レスポンス
     */
    public CompanyResponse generateCompanyResponse(final List<Company> companyList) {
        final var companyResponseModelList = companyList.stream().map(
                it -> new CompanyResponseModel(it.getId(), it.getName())).toList();
        return new CompanyResponse(companyResponseModelList);
    }
}
