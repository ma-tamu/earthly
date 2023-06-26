SELECT
    /*%expand*/*
FROM oauth_client
WHERE oauth_client.client_id = /* clientId */'a'
  AND oauth_client.is_deleted = 0