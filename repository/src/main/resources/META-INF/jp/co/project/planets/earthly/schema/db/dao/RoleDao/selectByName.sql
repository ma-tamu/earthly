SELECT
  *
FROM
  role
WHERE
  /*%if !hasViewAllRole*/
    role.id IN (
      SELECT
        role_1.id
      FROM
        role role_1
        INNER JOIN user_role ON role_1.id = user_role.role_id
      WHERE
        user_role.user_id = /*executionUserId*/'a'
        AND role_1.grantable = 1
        AND role_1.is_deleted = 0
    )
  /*%end*/
  /*%if @isNotBlank(name)*/
    AND role.name LIKE /*@infix(name)*/''
  /*%end*/
  AND role.is_deleted = 0
ORDER BY
  role.name,
  role.id
