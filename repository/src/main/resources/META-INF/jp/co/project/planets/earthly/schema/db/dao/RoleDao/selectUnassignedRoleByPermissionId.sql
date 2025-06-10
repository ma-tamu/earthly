SELECT
  /*%expand*/*
FROM
  role
WHERE
  /*%if !hasViewAllRole*/
    role.id IN (
      SELECT
        user_role.role_id
      FROM
        user_role
      WHERE
        user_role.user_id = /*account.id*/'a'
    )
  /*%end*/
  AND role.id NOT IN (
    SELECT
      role_permission.role_id
    FROM
      role_permission
    WHERE
      role_permission.permission_id = /*permissionId*/'a'
  )
  /*%if @isNotBlank(name)*/
    AND role.name LIKE /*@infix(name)*/'a'
  /*%end*/
  AND role.is_deleted = 0
ORDER BY
  role.name,
  role.id