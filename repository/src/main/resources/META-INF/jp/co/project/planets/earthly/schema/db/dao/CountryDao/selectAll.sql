SELECT
  /*%expand*/*
FROM
  country
WHERE
  is_deleted = 0
ORDER BY
  name