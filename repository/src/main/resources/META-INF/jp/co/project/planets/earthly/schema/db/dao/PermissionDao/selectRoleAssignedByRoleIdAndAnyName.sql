SELECT
  /*%expand*/*
FROM
  permission
WHERE
  permission.id IN (
    SELECT
      role_permission.permission_id
    FROM
      role_permission
      LEFT OUTER JOIN role ON role.id = role_permission.role_id
    WHERE
      role_permission.role_id = /*roleId*/'a'
      AND role.grantable = 1
  )
  /*%if @isNotBlank(name)*/
    AND permission.name LIKE /*@infix(name)*/'a'
  /*%end*/
ORDER BY
  permission.name,
  permission.id