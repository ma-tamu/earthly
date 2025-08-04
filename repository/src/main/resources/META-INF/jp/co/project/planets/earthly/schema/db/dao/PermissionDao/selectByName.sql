SELECT
  /*%expand*/*
FROM
  permission
WHERE
  /*%if @isNotBlank(name)*/
  permission.name LIKE /*@infix(name)*/''
  /*%end*/
  AND permission.is_deleted = 0
ORDER BY
  permission.name,
  permission.id