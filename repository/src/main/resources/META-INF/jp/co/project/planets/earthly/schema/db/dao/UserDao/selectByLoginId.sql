SELECT
    /*%expand*/*
FROM user
WHERE login_id = /* loginId */'a'
  AND is_deleted = 0