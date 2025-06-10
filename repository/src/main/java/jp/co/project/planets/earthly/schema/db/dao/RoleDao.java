package jp.co.project.planets.earthly.schema.db.dao;

import java.util.List;
import java.util.Optional;

import org.seasar.doma.Dao;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.SelectOptions;

import jp.co.project.planets.earthly.core.account.Account;
import jp.co.project.planets.earthly.schema.db.dao.base.RoleBaseDao;
import jp.co.project.planets.earthly.schema.db.entity.Role;

/**
 * role dao
 */
@Dao
@ConfigAutowireable
public interface RoleDao extends RoleBaseDao {

    /**
     * 対象ユーザーに割り当てられているロールを取得 ただし、実行ユーザーが閲覧できるものに限る
     *
     * @param userId
     *            ユーザーID
     * @param hasViewAllRole
     *            view_all_roleパーミッションを持っているか
     * @param executionUserId
     *            実行ユーザー
     * @return ロールリスト
     */
    @Select
    List<Role> selectGrantedRoleByUserId(String userId, boolean hasViewAllRole, String executionUserId);

    @Select
    List<Role> selectAssignedRoleByUserIdAndLikeName(String userId, String name, boolean hasViewAllRole,
        String executionUserId, SelectOptions options);

    /**
     * 対象ユーザーの未割りてのロール一覧を取得
     *
     * @param userId
     *            ユーザーID
     * @param name
     *            ロール名
     * @param hasViewAllRole
     *            view_all_roleパーミッションを持っているか
     * @param executionUserId
     *            実行ユーザーID
     * @param options
     *            select option
     * @return ロールリスト
     */
    @Select
    List<Role> selectUnassignedRoleByUserIdAndLikeName(String userId, String name,
        boolean hasViewAllRole, String executionUserId, SelectOptions options);

    /**
     * 対象ユーザーに割り当てられているロールを取得
     *
     * @param userId
     *            ユーザーID
     * @return ロールリスト
     */
    @Select
    List<Role> selectByAssignedRoleByUserId(String userId);

    @Select
    List<Role> selectByName(String name, boolean hasViewAllRole, String executionUserId, SelectOptions selectOptions);

    @Select
    Optional<Role> selectAccessibleByPrimaryKey(String id, boolean hasViewAllRole, String executionUserId);

    @Select
    List<Role> selectAssignedRoleByPermissionId(String permissionId, String name, boolean hasViewAllRole,
        Account account,
        SelectOptions selectOptions);

    @Select
    List<Role> selectUnassignedRoleByPermissionId(String permissionId, String name, boolean hasViewAllRole,
        Account account,
        SelectOptions selectOptions);

    @Select
    List<Role> selectAccessibleByPrimaryKeys(List<String> ids, boolean hasViewAllRole, Account account);
}
