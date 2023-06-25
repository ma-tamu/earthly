SELECT role.*
FROM role
         LEFT OUTER JOIN user_role ON role.id = user_role.role_id
WHERE user_role.user_id = /*userId*/'a'
  AND role.is_deleted = 0
/*%if !hasViewAllRole*/
  AND role.id IN (SELECT user_role.role_id
                  FROM user_role
                  WHERE user_role.user_id = /*executionUserId*/'a')
/*%end*/