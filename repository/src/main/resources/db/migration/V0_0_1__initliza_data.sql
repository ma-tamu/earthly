insert into permission (id, name, created_at, created_by, updated_at, updated_by, is_deleted)
values ('f212985ca90d11ec88720242ac120003', 'add_user', now(), 'NULL', now(), 'NULL', 0),
       ('f212996aa90d11ec88720242ac120003', 'add_company', now(), 'NULL', now(), 'NULL', 0),
       ('f2129a10a90d11ec88720242ac120003', 'add_all_branch', now(), 'NULL', now(), 'NULL', 0),
       ('f2129ab3a90d11ec88720242ac120003', 'add_my_company_branch', now(), 'NULL', now(), 'NULL', 0),
       ('f2129b64a90d11ec88720242ac120003', 'add_notice', now(), 'NULL', now(), 'NULL', 0),
       ('f2129c0aa90d11ec88720242ac120003', 'add_oauth_client', now(), 'NULL', now(), 'NULL', 0),
       ('f2129caea90d11ec88720242ac120003', 'add_role', now(), 'NULL', now(), 'NULL', 0),
       ('f2129d52a90d11ec88720242ac120003', 'edit_user', now(), 'NULL', now(), 'NULL', 0),
       ('f2129df8a90d11ec88720242ac120003', 'edit_company', now(), 'NULL', now(), 'NULL', 0),
       ('f2129e9aa90d11ec88720242ac120003', 'edit_all_branch', now(), 'NULL', now(), 'NULL', 0),
       ('f2129f3ea90d11ec88720242ac120003', 'edit_my_company_branch', now(), 'NULL', now(), 'NULL', 0),
       ('f2129fe3a90d11ec88720242ac120003', 'edit_notice', now(), 'NULL', now(), 'NULL', 0),
       ('f212a0ada90d11ec88720242ac120003', 'edit_oauth_client', now(), 'NULL', now(), 'NULL', 0),
       ('f212a157a90d11ec88720242ac120003', 'edit_role', now(), 'NULL', now(), 'NULL', 0),
       ('f212a1fca90d11ec88720242ac120003', 'view_all_user', now(), 'NULL', now(), 'NULL', 0),
       ('f212a2a5a90d11ec88720242ac120003', 'view_all_company', now(), 'NULL', now(), 'NULL', 0),
       ('f212a34aa90d11ec88720242ac120003', 'view_all_branch', now(), 'NULL', now(), 'NULL', 0),
       ('f212a3f3a90d11ec88720242ac120003', 'view_all_role', now(), 'NULL', now(), 'NULL', 0),
       ('f212a499a90d11ec88720242ac120003', 'view_all_permission', now(), 'NULL', now(), 'NULL', 0),
       ('a4f8f0b6a90e11ec88720242ac120003', 'view_all_oauth_client', now(), 'NULL', now(), 'NULL', 0);

insert into role (id, name, created_at, created_by, updated_at, updated_by, is_deleted)
values ('da5ad0c8b28c11ec93590242ac120002', 'System Administrator', now(), 'NULL', now(), 'NULL', 0);

insert into user_role (id, user_id, role_id, created_at, created_by)
values ('d289a0e0b28e11ec93590242ac120002', 'NULL', 'da5ad0c8b28c11ec93590242ac120002', now(), 'NULL');

insert into role_permission (id, role_id, permission_id, created_at, created_by)
values ('0500994fb29011ec93590242ac120002', 'da5ad0c8b28c11ec93590242ac120002', '', now(), 'NULL'),
       ('fee5af86b29011ec93590242ac120002', 'da5ad0c8b28c11ec93590242ac120002', 'f212985ca90d11ec88720242ac120003',
        now(), 'NULL'),
       ('070994bcb29111ec93590242ac120002', 'da5ad0c8b28c11ec93590242ac120002', 'f212996aa90d11ec88720242ac120003',
        now(), 'NULL'),
       ('111730cfb29111ec93590242ac120002', 'da5ad0c8b28c11ec93590242ac120002', 'f2129a10a90d11ec88720242ac120003',
        now(), 'NULL'),
       ('25719e33b29111ec93590242ac120002', 'da5ad0c8b28c11ec93590242ac120002', 'f2129ab3a90d11ec88720242ac120003',
        now(), 'NULL'),
       ('3fe572d3b29111ec93590242ac120002', 'da5ad0c8b28c11ec93590242ac120002', 'f2129b64a90d11ec88720242ac120003',
        now(), 'NULL'),
       ('46f94bcbb29111ec93590242ac120002', 'da5ad0c8b28c11ec93590242ac120002', 'f2129c0aa90d11ec88720242ac120003',
        now(), 'NULL'),
       ('585e5554b29111ec93590242ac120002', 'da5ad0c8b28c11ec93590242ac120002', 'f2129caea90d11ec88720242ac120003',
        now(), 'NULL'),
       ('6814be05b29111ec93590242ac120002', 'da5ad0c8b28c11ec93590242ac120002', 'f2129d52a90d11ec88720242ac120003',
        now(), 'NULL'),
       ('86dd5b1eb29111ec93590242ac120002', 'da5ad0c8b28c11ec93590242ac120002', 'f2129df8a90d11ec88720242ac120003',
        now(), 'NULL'),
       ('92b9cd2db29111ec93590242ac120002', 'da5ad0c8b28c11ec93590242ac120002', 'f2129e9aa90d11ec88720242ac120003',
        now(), 'NULL'),
       ('99b556a3b29111ec93590242ac120002', 'da5ad0c8b28c11ec93590242ac120002', 'f2129f3ea90d11ec88720242ac120003',
        now(), 'NULL'),
       ('9e9798b0b29111ec93590242ac120002', 'da5ad0c8b28c11ec93590242ac120002', 'f2129fe3a90d11ec88720242ac120003',
        now(), 'NULL'),
       ('a4c9a040b29111ec93590242ac120002', 'da5ad0c8b28c11ec93590242ac120002', 'f212a0ada90d11ec88720242ac120003',
        now(), 'NULL'),
       ('b2d38fccb29111ec93590242ac120002', 'da5ad0c8b28c11ec93590242ac120002', 'f212a157a90d11ec88720242ac120003',
        now(), 'NULL');

INSERT INTO `grant_type` (`id`, `type`, `created_at`, `created_by`, `updated_at`, `updated_by`, `is_deleted`)
VALUES ('1b6a0d2a65f911ec8a0b0242ac120003', 'authorization_code', now(), 'NULL', now(), 'NULL', 0),
       ('f35c63dd660c11ec8a0b0242ac120003', 'implicit', now(), 'NULL', now(), 'NULL', 0),
       ('fdc24af6660c11ec8a0b0242ac120003', 'refresh_token', now(), 'NULL', now(), 'NULL', 0),
       ('092519c3660d11ec8a0b0242ac120003', 'client_credentials', now(), 'NULL', now(), 'NULL', 0),
       ('0f4c8f7b660d11ec8a0b0242ac120003', 'password', now(), 'NULL', now(), 'NULL', 0);

INSERT INTO `scope` (`id`, `name`, `created_at`, `created_by`, `updated_at`, `updated_by`, `is_deleted`)
VALUES ('a85c1c6665fc11ec8a0b0242ac120003', 'openid', now(), 'NULL', now(), 'NULL', 0),
       ('15b4a732660d11ec8a0b0242ac120003', 'me', now(), 'NULL', now(), 'NULL', 0);

INSERT INTO `oauth_client` (`id`, `name`, `client_id`, `client_secret`, `created_at`, `created_by`, `updated_at`,
                            `updated_by`, `is_deleted`)
VALUES ('5e7abd95663011ec8a0b0242ac120003', 'mars', '34a10a88cbc2cfab10f4262e09439efec7e76a34e1b28687f0a19d43d3e76f4d',
        'c103fb40ee6d955024b7319b29fc4ad44e8c4c4a3c160b6a6075c00b39bd1991', now(), 'NULL', now(), 'NULL', 0);

INSERT INTO `oauth_client_grant_type` (`id`, `oauth_client_id`, `grant_type_id`)
VALUES (replace(uuid(), '-', ''), '5e7abd95663011ec8a0b0242ac120003', '1b6a0d2a65f911ec8a0b0242ac120003'),
       (replace(uuid(), '-', ''), '5e7abd95663011ec8a0b0242ac120003', 'fdc24af6660c11ec8a0b0242ac120003'),
       (replace(uuid(), '-', ''), '5e7abd95663011ec8a0b0242ac120003', '092519c3660d11ec8a0b0242ac120003');


INSERT INTO `oauth_client_scope` (`id`, `oauth_client_id`, `scope_id`)
VALUES (replace(uuid(), '-', ''), '5e7abd95663011ec8a0b0242ac120003', 'a85c1c6665fc11ec8a0b0242ac120003'),
       (replace(uuid(), '-', ''), '5e7abd95663011ec8a0b0242ac120003', 'a85cc25165fc11ec8a0b0242ac120003')
;

INSERT INTO `oauth_client_redirect_url`(`id`, `oauth_client_id`, `redirect_url`, `created_at`, `created_by`,
                                        `updated_at`, `updated_by`, `is_deleted`)
VALUES ('fabfbc406c4611ecb1b80242ac120002', '5e7abd95663011ec8a0b0242ac120003',
        'http://127.0.0.1:8082/mars/swagger-ui/oauth2-redirect.html', now(), 'NULL', now(), 'NULL', 0);


INSERT INTO `user` (`id`, `login_id`, `name`, `gender`, `mail`, `password`, `lockout`, `office_id`,
                    `created_by`, `updated_by`, `is_deleted`)
VALUES ('NULL', 'admin', 'Administrator', 'M', 'a@a.com',
        '$2a$10$IfIpdWUeKUBFd0pN6dRV/.4IT3Lsln5zuw8bZgiV.nTH/RbVRlxP2', b'0', b'0', 'NULL', 'NULL', 0);