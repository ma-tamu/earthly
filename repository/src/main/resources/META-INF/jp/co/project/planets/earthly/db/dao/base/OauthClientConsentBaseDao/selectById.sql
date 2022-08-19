select
/*%expand*/*
from
oauth_client_consent
where
    registered_client_id = /* registeredClientId */'a'
    and
    principal_name = /* principalName */'a'
    
