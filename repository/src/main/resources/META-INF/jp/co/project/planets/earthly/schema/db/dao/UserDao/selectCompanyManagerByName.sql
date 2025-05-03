SELECT
  *
FROM
  user
WHERE
  user.id IN (
    SELECT
      management_company_user.user_id
    FROM
      management_company_user
    WHERE
      management_company_user.company_id = /*companyId*/'a'
  )
  /*%if !hasViewAllUser*/
  AND user.company_id IN (
    SELECT
      management_company_user.company_id
    FROM
      management_company_user
    WHERE
      management_company_user.user_id = /*executionUserId*/'a'
    UNION ALL
    SELECT
      user_1.company_id
    FROM
      user user_1
    WHERE
      user_1.id = /*executionUserId*/'a'
      AND user_1.is_deleted = 0
  )
  /*%end */
  /*%if @isNotBlank(name)*/
  AND user.name LIKE /*@infix(name)*/'a'
  /*%end*/
  AND user.is_deleted = 0
