SELECT
  oauth_client_management.id AS id,
  oauth_client.id AS oauth_client_id,
  user.id AS user_id,
  user.name AS user_name,
  company.id AS company_id,
  company.name AS company_name
FROM
  user
  LEFT OUTER JOIN company on user.company_id = company.id
  LEFT OUTER JOIN oauth_client_management on user.id = oauth_client_management.user_id
  LEFT OUTER JOIN oauth_client on oauth_client_management.oauth_client_id = oauth_client.id
WHERE
  oauth_client.id = /*clientId*/'a'
  /*%if !hasViewAllUser*/
    AND user.company_id IN (
      SELECT
        company.id
      FROM
        company
          INNER JOIN management_company_user on company.id = management_company_user.company_id
      WHERE
        management_company_user.user_id = /*operatorUserId*/'1'
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
  AND oauth_client.is_deleted = 0
  AND user.is_deleted = 0