package jp.co.project.planets.earthly.schema.model.dto;

import java.util.List;

import jp.co.project.planets.earthly.schema.db.entity.OauthClient;

public record OAuthClientSearchResultDto(List<OauthClient> oauthClientList, long offset, long total) {
}
