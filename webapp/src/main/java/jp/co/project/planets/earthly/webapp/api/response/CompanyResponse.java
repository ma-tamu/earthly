package jp.co.project.planets.earthly.webapp.api.response;

import jp.co.project.planets.earthly.webapp.api.response.model.CompanyResponseModel;

import java.util.List;

/**
 * company response
 *
 * @param components
 *         会社一覧
 */
public record CompanyResponse(List<CompanyResponseModel> components) {
}
