SELECT
  *
FROM
  oauth_client_management
WHERE
  oauth_client_management.oauth_client_id = /*clientId*/''
  /*%if !hasViewAllClient*/
    AND EXISTS(
      SELECT
        1
      FROM
        oauth_client
      WHERE
        oauth_client.id = oauth_client_management.oauth_client_id
        AND EXISTS(
          SELECT
            1
          FROM
            oauth_client_management oauth_client_management_1
          WHERE
            oauth_client_management_1.oauth_client_id = oauth_client.id
            AND oauth_client_management_1.user_id = /*operatorUserId*/''
        )
        AND oauth_client.is_deleted = 0
    )
  /*%end */
  AND oauth_client_management.user_id IN /*userIdList*/('1')
  /*%if !hasViewAllUser*/
  AND EXISTS(
    SELECT
      1
    FROM
      user
    WHERE
      user.id = oauth_client_management.user_id
      AND user.is_deleted = 0
  )
  /*%end*/