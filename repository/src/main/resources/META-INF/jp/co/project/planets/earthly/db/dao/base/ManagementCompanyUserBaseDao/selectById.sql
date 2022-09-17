select
/*%expand*/*
from
management_company_user
where
    company_id = /* companyId */'a'
    and
    user_id = /* userId */'a'
    
      AND is_deleted = 0
