SELECT
  /*%expand "user, company, country" */*
FROM
  user
  LEFT OUTER JOIN company ON user.company_id = company.id
  LEFT OUTER JOIN country ON company.country_id = country.id
WHERE
  /*%if @isNotBlank(name)*/
  user.name LIKE /*@infix(name)*/'a'
  /*%end*/
  /*%if @isNotBlank(loginId)*/
  user.login_id LIKE /*@infix(loginId)*/'a'
  /*%end*/
  AND company.id = /*companyId*/''
  AND user.id NOT IN (
    SELECT
      organization_user.user_id
    FROM
      organization_user
    WHERE
      organization_user.organization_id = /*organizationId*/'a'
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
    )
  /*%end*/
  AND user.is_deleted = 0
ORDER BY
  user.name,
  user.id