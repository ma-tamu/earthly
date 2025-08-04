SELECT
  *
FROM
  user
WHERE
  user.id IN /*ids*/('1')
  /*%if !hasViewAllCompany */
  AND user.company_id IN (
    SELECT
      company.id
    FROM
      company
      LEFT OUTER JOIN management_company_user on company.id = management_company_user.company_id
    WHERE
      management_company_user.user_id = user.id
      AND company.is_deleted = 0
    UNION ALL
    SELECT
    user_1.company_id
    FROM
      user user_1
    WHERE
      id = /*executionUserId*/'a'
  )
  /*%end*/
  AND user.is_deleted = 0