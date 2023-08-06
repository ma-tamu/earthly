SELECT
    /*%expand*/*
FROM oauth_client
WHERE
  /*%if @isNotBlank(name) */
    oauth_client.name = /* name */'a'
  /*%end */
  /*%if !hasViewAllOAuthClient */
  AND (oauth_client.created_by = /* operatorUserId */'a'
  OR oauth_client.id IN (SELECT oauth_client_id
                         FROM oauth_client_management
                         WHERE user_id = /* operatorUserId */'a'))
  /*%end */
  AND oauth_client.is_deleted = 0
ORDER BY oauth_client.id
