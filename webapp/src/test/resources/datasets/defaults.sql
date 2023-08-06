DELETE
FROM company;
INSERT INTO company (`id`, `name`, `country_id`, `created_at`, `created_by`, `updated_at`, `updated_by`, `is_deleted`)
VALUES ('NULL', 'Management Company', 'd92bf311652a77227d2725c204a5396b', '1985-10-02 06:13:58', 'NULL',
        '1985-10-02 06:13:58', 'NULL', 0),
       ('COMPANY_ID_01', 'COMPANY_NAME_01', 'd92bf311652a77227d2725c204a5396b', '1985-10-02 06:13:58', 'NULL',
        '1985-10-02 06:13:58', 'NULL', 0),
       ('COMPANY_ID_02', 'COMPANY_NAME_02', 'd92bf311652a77227d2725c204a5396b', '1985-05-07 06:13:58', 'NULL',
        '1985-10-02 06:13:58', 'NULL', 0);

DELETE
FROM user;
INSERT INTO `user`
(`id`, `login_id`, `name`, `gender`, `mail`, `password`, `lockout`, `two_factor_authentication`, `secret`, `company_id`,
 `created_at`, `created_by`,`updated_at`, `updated_by`, `is_deleted`)
VALUES ('NULL', 'admin', 'Administrator', '-', 'shakeem_brasebbyk@roommates.mh', 'U0FnFypudNL', 0, 0, 'NULL', 'NULL',
        '1985-10-02 06:13:58', 'NULL', '1985-10-02 06:13:58', 'NULL', 0),
       ('USER_ID_01', 'LOGIN_ID_01', 'USER_NAME_01', 'M', 'orren_hannana7co@conservation.fm', 'T4gXZYL6qQK84', 0, 0,
        'NULL', 'COMPANY_ID_01', '2018-07-13 15:59:03', 'NULL', '2018-07-13 15:59:03', 'NULL', 0),
       ('USER_ID_02', 'LOGIN_ID_02', 'USER_NAME_02', 'F', 'thai_patillojroy@enhancing.yhc', 'f01Z3zYUFY', 0, 0,
        'NULL', 'COMPANY_ID_01', '2018-07-31 15:59:03', 'NULL', '2018-07-31 15:59:03', 'NULL', 0),
       ('USER_ID_03', 'LOGIN_ID_03', 'USER_NAME_03', 'M', 'jeanene_helmj@telecommunications.at', 'f01Z3zYUFY', 0, 0,
        'NULL', 'COMPANY_ID_02', '2008-06-25 15:59:03', 'NULL', '2008-06-25 15:59:03', 'NULL', 0),
       ('USER_ID_04', 'LOGIN_ID_04', 'USER_NAME_04', 'M', 'dontavis_paquetteea@tale.th', 'Tc4NUOcdm2V34', 0, 0,
        'NULL', 'COMPANY_ID_02', '2010-02-13 07:07:35', 'NULL', '2010-02-13 07:07:35', 'NULL', 0);

