SELECT
  *
FROM
  company
WHERE
  /*%if !hasViewAllCompany */
    company.id IN (
      SELECT
        user.company_id
      FROM
        user
      WHERE
        id = /*userId*/''
        AND user.is_deleted = 0
      UNION ALL
      SELECT
        management_company_user.company_id
      FROM
        management_company_user
      WHERE
        management_company_user.user_id = /*userId*/''
        AND management_company_user.is_deleted = 0
    )
  /*%end */
  /*%if @org.apache.commons.lang3.StringUtils@isNotBlank(name)*/
    AND company.name LIKE /* @infix(name) */''
  /*%end */
  AND company.is_deleted = 0