SELECT
  user.*
FROM
  user
  LEFT OUTER JOIN oauth_client_management on user.id = oauth_client_management.user_id
WHERE
  oauth_client_management.oauth_client_id = /*clientId*/'1'
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
  AND user.is_deleted = 0;