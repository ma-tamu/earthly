SELECT /*%expand*/*
FROM oauth2_authorization
where access_token_value = /*token*/''
   OR refresh_token_value = /*token*/''
   OR authorization_code_value = /*token*/''