package jp.co.project.planets.earthly.schema.repository;

import java.util.List;
import java.util.Optional;

import org.seasar.doma.boot.Pageables;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import jp.co.project.planets.earthly.schema.db.dao.RoleDao;
import jp.co.project.planets.earthly.schema.db.entity.Role;
import jp.co.project.planets.earthly.schema.emuns.PermissionEnum;
import jp.co.project.planets.earthly.schema.model.dto.RoleSearchResultDto;

/**
 * role repository
 */
@Repository
public class RoleRepository {

    private final RoleDao roleDao;

    public RoleRepository(final RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    /**
     * find by primary key
     * 
     * @param id
     *            role id
     * @return role
     */
    public Optional<Role> findByPrimaryKey(final String id) {
        return Optional.ofNullable(roleDao.selectById(id));
    }

    /**
     * 対象ユーザーの割り当て済みのロールを検索
     * 
     * @param userId
     *            ユーザーID
     * @param nameOptional
     *            ロール名
     * @param pageable
     *            ページャー
     * @param executionUserId
     *            実行ユーザーID
     * @param permissionEnumList
     *            パーミッションリスト
     * @return ロールリスト
     */
    public RoleSearchResultDto findAssignedRoleByUserIdAndLikeName(final String userId,
            final Optional<String> nameOptional, final Pageable pageable, final String executionUserId,
            final List<PermissionEnum> permissionEnumList) {
        final var selectOptions = Pageables.toSelectOptions(pageable).count();
        final boolean hasViewAllRole = permissionEnumList.contains(PermissionEnum.VIEW_ALL_ROLE);
        final var name = nameOptional.orElse(null);
        final var roleList = roleDao.selectAssignedRoleByUserIdAndLikeName(userId, name, hasViewAllRole,
                executionUserId, selectOptions);
        return new RoleSearchResultDto(roleList, pageable.getOffset(), selectOptions.getCount());
    }

    /**
     * 対象ユーザーの未割りてのロール一覧を取得
     * 
     * @param userId
     *            ユーザーID
     * @param name
     *            ロール名
     * @param pageable
     *            ページャー
     * @param executionUserId
     *            実行ユーザーID
     * @param permissionEnumList
     *            パーミッションリスト
     * @return ロールリスト
     */
    public RoleSearchResultDto findUnassignedRoleByUserIdAndLikeName(final String userId, final String name,
            final Pageable pageable, final String executionUserId, final List<PermissionEnum> permissionEnumList) {
        final var selectOptions = Pageables.toSelectOptions(pageable).count();
        final boolean hasViewAllRole = permissionEnumList.contains(PermissionEnum.VIEW_ALL_ROLE);
        final var roleList = roleDao.selectUnassignedRoleByUserIdAndLikeName(userId, name, hasViewAllRole,
                executionUserId, selectOptions);
        return new RoleSearchResultDto(roleList, pageable.getOffset(), selectOptions.getCount());
    }

    /**
     * 対象ユーザーに割り当てられているロールを取得
     * 
     * @param userId
     *            ユーザーID
     * @return ロールリスト
     */
    public List<Role> findByAssignedRoleByUserId(final String userId) {
        return roleDao.selectByAssignedRoleByUserId(userId);
    }
}
