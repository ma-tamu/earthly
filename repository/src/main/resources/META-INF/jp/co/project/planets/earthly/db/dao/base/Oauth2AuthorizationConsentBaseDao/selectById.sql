select
/*%expand*/*
from
oauth2_authorization_consent
where
    registered_client_id = /* registeredClientId */'a'
    and
    principal_name = /* principalName */'a'
    
