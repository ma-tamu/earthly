SELECT
  /*%expand*/*
FROM
  logout_redirect_url
WHERE
  logout_redirect_url.oauth_client_id IN (
    SELECT
      id
    FROM
      oauth_client
    WHERE
      oauth_client.id = /*clientId*/'a'
      /*%if !hasViewAllOAuthClient */
        AND (
          oauth_client.created_by = /* operatorUserId */'a'
          OR oauth_client.id IN (
            SELECT
              oauth_client_id
            FROM
              oauth_client_management
              LEFT OUTER JOIN user ON oauth_client_management.user_id = user.id
            WHERE
              user.id = /* operatorUserId */'a'
              AND user.is_deleted = 0
          )
        )
      /*%end */
      AND oauth_client.is_deleted = 0
  )
  /*%if @isNotBlank(logoutRedirectUrl)*/
  AND logout_redirect_url.redirect_url LIKE /* @infix(logoutRedirectUrl) */''
  /*%end */
  AND logout_redirect_url.is_deleted = 0
ORDER BY
  logout_redirect_url.created_at DESC,
  logout_redirect_url.id