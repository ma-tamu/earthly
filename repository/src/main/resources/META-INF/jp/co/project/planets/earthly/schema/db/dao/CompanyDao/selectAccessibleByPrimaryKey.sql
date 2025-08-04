SELECT
    /*%expand*/*
FROM
  company
WHERE
  id = /*id*/'a'
  /*%if !hasViewAllCompany */
  AND id IN (
    SELECT
      user.company_id
    FROM
      user
    WHERE
      user.id = /*userId*/''
      AND user.is_deleted = 0
    UNION ALL
    SELECT
      management_company_user.company_id
    FROM
      management_company_user
    WHERE
      management_company_user.user_id = /*userId*/''
    )
    /*%end*/
    AND is_deleted = 0;