SELECT
  /*%expand "user, company, country" */*
FROM
  user
  LEFT OUTER JOIN company ON user.company_id = company.id
  LEFT OUTER JOIN country ON company.country_id = country.id
WHERE
  user.id NOT IN (
    SELECT
      management_company_user1.user_id
    FROM
      management_company_user management_company_user1
    WHERE
      management_company_user1.company_id = /*companyId*/''
  )
  /*%if !hasViewAllUser*/
  AND user.company_id IN (
    SELECT
      management_company_user2.company_id
    FROM
      management_company_user management_company_user2
    WHERE
      management_company_user2.user_id = /* executionUserId */'a'
    UNION ALL
    SELECT
      user1.company_id
    FROM
      user user1
    WHERE
      user1.id = /* executionUserId */'a'
  )
  /*%end */
  /*%if @isNotBlank(loginId)*/
  AND user.login_id LIKE /*@infix(loginId)*/'a'
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