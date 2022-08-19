SELECT
  /*%expand */*
FROM permission
WHERE id IN (SELECT role_permission.permission_id
             FROM user
                    LEFT OUTER JOIN user_role ON user.id = user_role.user_id
                    LEFT OUTER JOIN role ON role.id = user_role.role_id
                    LEFT OUTER JOIN role_permission ON role.id = role_permission.role_id
             WHERE user.id = /* userId */'a'
               AND user.is_deleted = 0
               AND role.is_deleted = 0)
  AND is_deleted = 0
ORDER BY id