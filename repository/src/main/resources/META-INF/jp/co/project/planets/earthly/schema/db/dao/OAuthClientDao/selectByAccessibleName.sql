SELECT
  /*%expand*/*
FROM oauth_client
WHERE oauth_client.name = /*name*/''
  AND oauth_client.is_deleted = 0
  /*%if !hasViewAllOAuthClient */
  AND (
      oauth_client.created_by = /* operatorUserId */'a'
    OR oauth_client.id IN (SELECT oauth_client_id
                           FROM oauth_client_management
                           WHERE user_id = /* operatorUserId */'a')
  )
/*%end */