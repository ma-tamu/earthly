SELECT
  /*%expand*/*
FROM
  scope
WHERE
    scope.id IN (
    SELECT
      oauth_client_scope.scope_id
    FROM
      oauth_client_scope
    WHERE
        oauth_client_scope.oauth_client_id = /*clientId*/'a'
  )
  AND scope.is_deleted = 0