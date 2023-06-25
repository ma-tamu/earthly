SELECT
    /*%expand*/*
FROM company
WHERE
    /*%if !hasViewAllCompany*/
        id IN (SELECT user.company_id
               FROM user
               WHERE user.id = /*userId*/''
                 AND user.is_deleted = 0)
   OR id IN (SELECT management_company_user.company_id
             FROM management_company_user
             WHERE management_company_user.user_id = /*userId*/'')
    /*%end*/
    /*%if keywordOptional != null */
    AND company.name LIKE /*@infix(keywordOptional)*/''
    /*%end*/
    AND is_deleted = 0