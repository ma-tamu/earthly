package jp.co.project.planets.earthly.core.account;

import java.util.List;

import org.jilt.Builder;

import jp.co.project.planets.earthly.core.enums.Authority;

/**
 * アカウント
 * 
 * @param id
 *            ユーザーID
 * @param loginId
 *            ログインID
 * @param name
 *            ユーザー名
 * @param mail
 *            メールアドレス
 * @param lockout
 *            ロックアウト
 * @param multiFactor
 *            多要素認証
 * @param belongCompany
 *            所属会社
 * @param permissions
 *            パーミッション
 */
@Builder(factoryMethod = "builder")
public record Account(String id, String loginId, String name, String mail, boolean lockout,
        MultiFactor multiFactor, Company belongCompany, List<? extends Authority> permissions) {
}
