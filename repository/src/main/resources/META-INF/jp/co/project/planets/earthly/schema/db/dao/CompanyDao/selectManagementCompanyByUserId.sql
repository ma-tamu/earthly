SELECT *
FROM company
WHERE id IN (SELECT 1
             FROM management_company_user
             WHERE company_id = /*userId */'1')
  AND is_deleted = 0