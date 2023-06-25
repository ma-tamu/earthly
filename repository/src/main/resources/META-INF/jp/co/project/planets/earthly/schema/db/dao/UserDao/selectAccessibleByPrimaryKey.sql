SELECT /*%expand*/*
FROM user
WHERE id = /* id */'a'
  AND is_deleted = 0
    /*%if !hasViewAllCompany */
  AND company_id = (SELECT user_01.company_id
                    FROM user user_01
                    WHERE user_01.id = /* executionUserId */'a')
/*%end */