SELECT
    /*%expand*/*
FROM grant_type
WHERE grant_type.id IN (SELECT oauth_client_grant_type.grant_type_id
                        FROM oauth_client_grant_type
                        WHERE oauth_client_grant_type.oauth_client_id = /*clientId*/'a')
  AND grant_type.is_deleted = 0