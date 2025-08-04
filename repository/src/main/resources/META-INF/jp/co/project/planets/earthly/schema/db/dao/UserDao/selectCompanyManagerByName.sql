SELECT
  /*%expand "user, company, country" */*
FROM
  user
  LEFT OUTER JOIN company ON user.company_id = company.id
  LEFT OUTER JOIN country ON company.country_id = country.id
WHERE
  user.id IN (
    SELECT
      management_company_user.user_id
    FROM
      management_company_user
    WHERE
      management_company_user.company_id = /*companyId*/'a'
  )
  /*%if !hasViewAllUser*/
  AND user.company_id IN (
    SELECT
      management_company_user.company_id
    FROM
      management_company_user
    WHERE
      management_company_user.user_id = /*executionUserId*/'a'
    UNION ALL
    SELECT
      user_1.company_id
    FROM
      user user_1
    WHERE
      user_1.id = /*executionUserId*/'a'
      AND user_1.is_deleted = 0
  )
  /*%end */
  /*%if @isNotBlank(loginId)*/
  AND user.name LIKE /*@infix(loginId)*/'a'
  /*%end*/
  /*%if @isNotBlank(name)*/
  AND user.name LIKE /*@infix(name)*/'a'
  /*%end*/
  /*%if @isNotBlank(companyName)*/
    AND user.company_id IN(
      SELECT
        company.id
      FROM
        company
      WHERE
        company.name LIKE /*@infix(companyName)*/'a'
        AND company.is_deleted = 0
    )
  /*%end*/
  AND user.is_deleted = 0
ORDER BY
  user.name,
  company.name,
  user.id