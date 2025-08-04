package jp.co.project.planets.earthly.webapp.controller.form.client;

import java.io.Serializable;
import java.util.List;

import org.jilt.Builder;

import jakarta.validation.constraints.NotEmpty;

/**
 * OAuthクライアント管理者紐づけ解除FROM
 * 
 * @param userId
 *            ユーザーID
 */
@Builder(factoryMethod = "builder")
public record OAuthClientManagementUserUnassignForm(@NotEmpty List<String> userId) implements Serializable {
}
