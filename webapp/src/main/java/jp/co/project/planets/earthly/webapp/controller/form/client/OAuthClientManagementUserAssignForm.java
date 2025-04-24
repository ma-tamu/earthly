package jp.co.project.planets.earthly.webapp.controller.form.client;

import java.io.Serializable;
import java.util.List;

import org.jilt.Builder;

import jakarta.validation.constraints.NotEmpty;

/**
 * OAuthクライアント管理者紐づけFROM
 * 
 * @param userId
 *            ユーザーID
 */
@Builder(factoryMethod = "builder")
public record OAuthClientManagementUserAssignForm(@NotEmpty List<String> userId) implements Serializable {
}
