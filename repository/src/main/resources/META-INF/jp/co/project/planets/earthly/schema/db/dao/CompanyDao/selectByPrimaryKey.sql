SELECT company.id         AS id,
       company.name       as name,
       country.id         AS country_id,
       country.name       AS country_name,
       language.id        AS language_id,
       language.name      AS language_name,
       region.id          AS region_id,
       region.name        AS region_name,
       company.created_by AS created_by,
       company.created_at AS created_at,
       company.updated_by AS updated_by,
       company.updated_at AS updated_at,
       company.is_deleted AS is_deleted
FROM company
         LEFT OUTER JOIN country ON company.country_id = country.id
         LEFT OUTER JOIN language ON language.id = country.language_id
         LEFT OUTER JOIN region ON region.id = country.region_id
WHERE company.id = /*id */''
  AND company.is_deleted = 0