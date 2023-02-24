SELECT /*%expand*/*
FROM oauth2_authorization
where refresh_token_value = /*refreshToken*/''