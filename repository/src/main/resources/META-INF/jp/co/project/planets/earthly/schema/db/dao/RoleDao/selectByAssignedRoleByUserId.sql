SELECT /*%expand*/*
FROM role
WHERE id IN (SELECT role_id
             FROM user_role
             WHERE user_id = /*userId*/'')
  AND is_deleted = 0