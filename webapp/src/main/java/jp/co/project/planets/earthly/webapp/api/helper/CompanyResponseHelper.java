package jp.co.project.planets.earthly.webapp.api.helper;

import java.util.List;

import org.springframework.stereotype.Component;

import jp.co.project.planets.earthly.schema.db.entity.Company;
import jp.co.project.planets.earthly.webapp.api.response.CompanyResponse;
import jp.co.project.planets.earthly.webapp.api.response.model.CompanyResponseModel;

/**
 * company response helper
 */
@Component
public class CompanyResponseHelper {

    /**
     * 会社レスポンスを生成
     *
     * @param companyList
     *            会社一覧
     * @return 会社レスポンス
     */
    public CompanyResponse generateCompanyResponse(final List<Company> companyList) {
        final var companyResponseModelList = companyList.stream().map(
                it -> new CompanyResponseModel(it.getId(), it.getName())).toList();
        return new CompanyResponse(companyResponseModelList);
    }
}
