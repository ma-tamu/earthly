SELECT
  user.id AS user_id,
  user.name AS user_name,
  company.id AS company_id,
  company.name AS company_name
FROM
  user
  LEFT OUTER JOIN company on user.company_id = company.id
WHERE
  /*%if !hasViewAllUser*/
 user.company_id IN (
    SELECT
      company.id
    FROM
      company
      INNER JOIN management_company_user on company.id = management_company_user.company_id
    WHERE
      management_company_user.user_id = /*operatorUserId*/'1'
      /*%if @isNotBlank(companyName) */
      AND company.name LIKE /*@infix(companyName) */'a'
      /*%end */
      AND company.is_deleted = 0
    UNION ALL
    SELECT
      company_id
    FROM
      user
    WHERE
      id = /*operatorUserId*/'1'
      AND is_deleted = 0
  )
  /*%end*/
  AND user.id NOT IN (
    SELECT
      oauth_client_management.user_id
    FROM
      oauth_client_management
    WHERE
      oauth_client_management.oauth_client_id = /*clientId*/'a'
  )
  /*%if @isNotBlank(loginId) */
  AND user.login_id LIKE /*@infix(loginId) */'a'
  /*%end */
  /*%if @isNotBlank(userName) */
  AND user.name LIKE /*@infix(userName) */'a'
  /*%end */
  AND user.is_deleted = 0
ORDER BY
  company.name,
  user.name,
  user.id