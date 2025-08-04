SELECT
   /*%expand "user, company, country" */*
FROM
  user
  LEFT OUTER JOIN company ON user.company_id = company.id
  LEFT OUTER JOIN country ON company.country_id = country.id
WHERE
  user.id = /* id */'a'
  AND user.is_deleted = 0
  /*%if !hasViewAllCompany */
  AND company_id = (
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
  /*%end */