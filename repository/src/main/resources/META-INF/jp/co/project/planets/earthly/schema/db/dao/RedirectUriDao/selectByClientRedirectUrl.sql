SELECT
  /*%expand*/*
FROM
  oauth_client_redirect_url
WHERE
  oauth_client_redirect_url.oauth_client_id IN (
    SELECT
      id
    FROM
      oauth_client
    WHERE
      oauth_client.id = /*clientId*/'a'
      /*%if !hasViewAllOAuthClient */
      AND (oauth_client.created_by = /* operatorUserId */'a'
        OR oauth_client.id IN (SELECT oauth_client_id
                               FROM oauth_client_management
                               WHERE user_id = /* operatorUserId */'a'))
      /*%end */
      AND oauth_client.is_deleted = 0
  )
  AND oauth_client_redirect_url.redirect_url LIKE /* @infix(redirectUrl) */''
  AND oauth_client_redirect_url.is_deleted = 0
ORDER BY
  oauth_client_redirect_url.id