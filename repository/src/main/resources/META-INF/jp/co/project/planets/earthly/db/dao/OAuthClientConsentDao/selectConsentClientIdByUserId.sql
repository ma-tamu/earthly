SELECT
  /*%expand*/*
FROM oauth_client_consent
WHERE registered_client_id = /* registeredClientId */'a'
  AND principal_name = /* principalName */'a'