SELECT
    /*%expand*/*
FROM company
WHERE id = /*id*/'a'
    /*%if !hasViewAllCompany */
  AND id IN (SELECT user.company_id
             FROM user
             WHERE user.id = /*userId*/''
               AND user.is_deleted = 0)
    /*%end*/
  AND is_deleted = 0;