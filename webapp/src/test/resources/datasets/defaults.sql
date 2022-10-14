delete
from company;
INSERT INTO company (`id`, `name`, `country_id`, `created_at`, `created_by`, `updated_at`, `updated_by`, `is_deleted`)
VALUES ('NULL', 'Management Company', 'd92bf311652a77227d2725c204a5396b', '1985-10-02 06:13:58', 'NULL',
        '1985-10-02 06:13:58', 'NULL', 0),
       ('COMPANY_ID_01', 'COMPANY_NAME_01', 'd92bf311652a77227d2725c204a5396b', '1985-10-02 06:13:58', 'NULL',
        '1985-10-02 06:13:58', 'NULL', 0);
delete
from user;
INSERT INTO `user`
(`id`, `login_id`, `name`, `gender`, `mail`, `password`, `lockout`, `company_id`, `created_at`, `created_by`,
 `updated_at`, `updated_by`, `is_deleted`)
VALUES ('NULL', 'admin', 'Administrator', '-', 'shakeem_brasebbyk@roommates.mh', 'U0FnFypudNL', 0, 'NULL',
        '1985-10-02 06:13:58', 'NULL', '1985-10-02 06:13:58', 'NULL', 0),
       ('USER_ID_01', 'LOGIN_ID_01', 'USER_NAME_01', 'M', 'orren_hannana7co@conservation.fm', 'T4gXZYL6qQK84', 0,
        'COMPANY_ID_01', '2018-07-13 15:59:03', 'NULL', '2018-07-13 15:59:03', 'NULL', 0);

