SELECT
  company.id as id,
  company.name AS name,
  country.id AS country_id,
  country.name AS country_name,
  company.created_at AS created_at,
  company.created_by AS created_by,
  company.updated_at AS updated_at,
  company.is_deleted AS is_deleted
FROM
  company
  LEFT OUTER JOIN country ON company.country_id = country.id
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