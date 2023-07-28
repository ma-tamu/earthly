SELECT
    /*%expand*/*
FROM oauth_client
WHERE oauth_client.is_deleted = 0
    /*%if !hasViewAllOAuthClient */
  AND oauth_client.created_by = /* operatorUserId */'a'
/*%end */
ORDER BY oauth_client.updated_by DESC