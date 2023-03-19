SELECT
  /*%expand*/*
FROM role
WHERE id NOT IN (SELECT role_id
                 FROM user_role
                 WHERE user_id = /*userId*/'a')
  /*%if hasViewAllRole*/
  AND id IN (SELECT role_id
             FROM user_role
             WHERE user_id = /*executionUserId*/'a')
  /*%end*/
  /*%if @isNotBlank(name) */
  AND name LIKE /*@infix(name)*/'a'
  /*%end*/
  AND is_deleted = 0