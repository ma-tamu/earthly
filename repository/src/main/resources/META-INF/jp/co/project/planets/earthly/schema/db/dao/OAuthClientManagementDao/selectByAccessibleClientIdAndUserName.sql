SELECT
  oauth_client_management.id AS id,
  oauth_client_management.oauth_client_id AS oauth_client_id,
  oauth_client_management.user_id AS user_id,
  user.name AS user_name,
  user.company_id AS company_id,
  company.name AS company_name
FROM
  user
  LEFT OUTER JOIN company ON user.company_id = company.id
  LEFT OUTER JOIN oauth_client_management on user.id = oauth_client_management.user_id
WHERE
  oauth_client_management.oauth_client_id IN (
  SELECT
    oauth_client.id
  FROM
    oauth_client
  WHERE
    oauth_client.id = /*clientId*/'1'
    /*%if !hasViewAllClient */
    AND (
      oauth_client.created_by = /* operatorUserId */'a'
      OR oauth_client.id IN (
        SELECT
          oauth_client_id
        FROM
          oauth_client_management
        WHERE
          user_id = /* operatorUserId */'a'
      )
    )
    /*%end */
  )
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
  AND user.name LIKE /* @infix(userName) */''
  AND user.is_deleted = 0