package jp.co.project.planets.earthly.common.logic;

import org.springframework.stereotype.Component;

import jp.co.project.planets.earthly.schema.db.entity.UserRole;

/**
 * role logic
 */
@Component
public class RoleLogic {

    public static final String POPULAR_ID = "c34088eb93e211ed80c50242ac120003";

    /**
     * ユーザー作成時のデフォルトロール
     * 
     * @param userId
     *            付与するユーザーID
     * @param operator
     *            操作ユーザーID
     */
    public void grantDefaultRole(final String userId, final String operator) {
        new UserRole(null, userId, POPULAR_ID, null, operator);
    }
}
