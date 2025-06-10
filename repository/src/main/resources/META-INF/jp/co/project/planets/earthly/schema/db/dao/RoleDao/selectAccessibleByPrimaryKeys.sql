SELECT
  /*%expand*/*
FROM
  role
WHERE
  role.id IN /*ids*/('a')
  /*%if !hasViewAllRole*/
    AND role.id IN (
      SELECT
        user_role.role_id
      FROM
        uesr_role
      WHERE
        user_role.user_id = /*account.id*/'a'
    )
  /*%end*/
  AND role.is_deleted = 0
