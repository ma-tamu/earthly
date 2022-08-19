SELECT user.id         AS id,
       user.login_id   AS login_id,
       user.name       AS name,
       user.gender     AS gender,
       user.mail       AS mail,
       user.lockout    AS lockout,
       user.company_id AS company_id,
       company.name    AS company_name
FROM user
       LEFT OUTER JOIN company on user.company_id = company.id
WHERE
  /*%if hasViewAllCompany */
    company.id IN (SELECT user_01.company_id
                   FROM user user_01
                   WHERE user_01.id = /*executionUserId*/'')
  /*%end*/
  /*%if @isNotBlank(loginId) */
  AND user.login_id LIKE /*@infix(loginId)*/'a'
  /*%end*/
  /*%if @isNotBlank(name)*/
  AND user.name LIKE /*@infix(name)*/'a'
  /*%end*/
  /*%if @isNotBlank(company) */
  AND company.name LIKE /*@infix(company)*/''
  /*%end*/
  AND user.is_deleted = 0
  AND company.is_deleted = 0
ORDER BY user.id