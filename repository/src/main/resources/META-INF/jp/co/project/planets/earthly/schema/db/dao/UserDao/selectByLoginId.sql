SELECT
  /*%expand "user, company, country" */*
FROM
  user
  LEFT OUTER JOIN company ON user.company_id = company.id
  LEFT OUTER JOIN country ON company.country_id = country.id
WHERE
  user.login_id = /* loginId */'a'
  AND user.is_deleted = 0