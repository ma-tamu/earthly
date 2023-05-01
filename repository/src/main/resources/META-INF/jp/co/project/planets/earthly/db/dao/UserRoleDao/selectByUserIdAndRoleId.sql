SELECT /*%expand */*
FROM user_role
WHERE user_id = /*userId*/''
  AND role_id IN /*roleIds*/('A', 'B')