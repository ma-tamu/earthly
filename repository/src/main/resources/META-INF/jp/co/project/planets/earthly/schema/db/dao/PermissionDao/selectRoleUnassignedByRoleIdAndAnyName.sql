SELECT
  /*%expand*/*
FROM
  permission
WHERE
  permission.id NOT IN (
    SELECT
      role_permission.permission_id
    FROM
      role_permission
    WHERE
      role_permission.role_id = /*roleId*/'a'
  )
  /*%if @isNotBlank(name)*/
    AND permission.name LIKE /*@infix(name)*/'a'
  /*%end*/
ORDER BY
  permission.name,
  permission.id