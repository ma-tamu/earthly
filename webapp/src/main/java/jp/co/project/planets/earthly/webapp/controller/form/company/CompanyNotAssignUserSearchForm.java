package jp.co.project.planets.earthly.webapp.controller.form.company;

import java.io.Serializable;

import org.jilt.Builder;

import jp.co.project.planets.earthly.webapp.model.dto.CompanyManagementUserSearchDto;

/**
 * 会社管理者未割り当てユーザー検索FORM
 *
 * @param loginId
 *            ログインID
 * @param name
 *            ユーザー名
 * @param companyName
 *            会社名
 */
@Builder(factoryMethod = "builder")
public record CompanyNotAssignUserSearchForm(String loginId, String name, String companyName) implements Serializable {

    public CompanyManagementUserSearchDto toDto() {
        return new CompanyManagementUserSearchDto(loginId, name, companyName);
    }
}
