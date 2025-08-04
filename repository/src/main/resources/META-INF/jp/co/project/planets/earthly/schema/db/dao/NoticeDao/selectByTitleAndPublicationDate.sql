SELECT
  /*%expand*/*
FROM
  notice
WHERE
  /*%if @isNotBlank(title)*/
    title = /*title*/''
  /*%end*/
  /*%if startDate != null*/
    AND start_at >= /*startDate*/''
  /*%end*/
  /*%if endDate != null*/
    AND end_at <= /*endDate*/''
  /*%end*/
  AND is_deleted = 0
ORDER BY
  title,
  id