SELECT
  /*%expand*/*
FROM
  company
WHERE
  id IN (
    SELECT
      management_company_user.company_id
    FROM
      management_company_user
    WHERE
      management_company_user.user_id = /*userId*/'a'
  )
  /*%if !hasViewAllCompany*/
  AND id IN (
    SELECT
      management_company_user.company_id
    FROM
      management_company_user
    WHERE
      management_company_user.user_id = /*executionUserId*/'a'
  )
  /*%end*/
  AND is_deleted = 0
ORDER BY
  name,
  id