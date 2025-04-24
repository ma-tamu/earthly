package jp.co.project.planets.earthly.common.logic;

import org.springframework.stereotype.Component;

import jp.co.project.planets.earthly.schema.db.entity.UserRole;
import jp.co.project.planets.earthly.schema.repository.RoleRepository;

/**
 * role logic
 */
@Component
public class RoleLogic {

    private final RoleRepository roleRepository;

    public static final String POPULAR_ID = "c34088eb93e211ed80c50242ac120003";

    public RoleLogic(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

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
