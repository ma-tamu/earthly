SELECT
   /*%expand "user, company, country" */*
FROM
  user
  LEFT OUTER JOIN company ON user.company_id = company.id
  LEFT OUTER JOIN country ON company.country_id = country.id
WHERE
  user.id IN (
    SELECT
      user_role.user_id
    FROM
      user_role
    WHERE
      user_role.role_id = /*roleId*/'a'
  )
  /*%if !hasViewAllCompany*/
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
  /*%if @isNotBlank(loginId)*/
    AND user.login_id LIKE /*@infix(loginId)*/''
  /*%end*/
  /*%if @isNotBlank(name)*/
    AND user.name LIKE /*@infix(name)*/''
  /*%end*/
  /*%if @isNotBlank(companyName)*/
    AND coompany.name LIKE /*@infix(companyName)*/''
  /*%end*/
  AND user.is_deleted = 0
ORDER BY
  company.name,
  user.name,
  user.id