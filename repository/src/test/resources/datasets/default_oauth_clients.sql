DELETE
FROM `oauth_client`;
DELETE
FROM `oauth_client_redirect_url`;
DELETE
FROM `oauth_client_scope`;
DELETE
FROM `oauth_client_grant_type`;
DELETE
FROM `logout_redirect_url`;


INSERT INTO `oauth_client`(`id`, `name`, `client_id`, `client_secret`, `created_at`, `created_by`, `updated_at`,
                           `updated_by`, `is_deleted`)
VALUES ('1', 'OAuthクライアント1', 'client_1', 'secret_1', NOW(), 'NULL', NOW(), 'NULL', b'0'),
       ('2', 'OIDCクライアント2', 'client_2', 'secret_2', NOW(), 'NULL', NOW(), 'NULL', b'0');

INSERT INTO `oauth_client_redirect_url` (`id`, `oauth_client_id`, `redirect_url`, `created_at`, `created_by`,
                                         `updated_at`, `updated_by`, `is_deleted`)
VALUES ('1', '1', 'http://127.0.0.1/dummy/login/oauth2/code/earthly', NOW(), 'NULL', NOW(), 'NULL', b'0'),
       ('2', '1', 'http://127.0.0.1/dummy/swagger-ui/oauth2-redirect.html', NOW(), 'NULL', NOW(), 'NULL', b'0'),
       ('3', '2', 'http://127.0.0.1/dummy-oidc/login/oauth2/code/earthly', NOW(), 'NULL', NOW(), 'NULL', b'0'),
       ('4', '2', 'http://127.0.0.1/dummy-oidc/swagger-ui/oauth2-redirect.html', NOW(), 'NULL', NOW(), 'NULL', b'0');

INSERT INTO `oauth_client_scope` (`id`, `oauth_client_id`, `scope_id`)
VALUES ('1', '1', '15b4a732660d11ec8a0b0242ac120003'),
       ('2', '2', '15b4a732660d11ec8a0b0242ac120003'),
       ('3', '2', 'a85c1c6665fc11ec8a0b0242ac120003');

INSERT INTO `oauth_client_grant_type` (`id`, `oauth_client_id`, `grant_type_id`)
VALUES ('1', '1', '092519c3660d11ec8a0b0242ac120003'),
       ('2', '1', '0f4c8f7b660d11ec8a0b0242ac120003'),
       ('3', '1', '1b6a0d2a65f911ec8a0b0242ac120003'),
       ('4', '1', 'f35c63dd660c11ec8a0b0242ac120003'),
       ('5', '1', 'fdc24af6660c11ec8a0b0242ac120003'),
       ('6', '2', '092519c3660d11ec8a0b0242ac120003'),
       ('7', '2', '0f4c8f7b660d11ec8a0b0242ac120003'),
       ('8', '2', '1b6a0d2a65f911ec8a0b0242ac120003'),
       ('9', '2', 'f35c63dd660c11ec8a0b0242ac120003'),
       ('10', '2', 'fdc24af6660c11ec8a0b0242ac120003');

INSERT INTO `logout_redirect_url` (`id`, `oauth_client_id`, `redirect_url`, `created_at`, `created_by`, `updated_at`,
                                   `updated_by`, `is_deleted`)
VALUES ('1', '1', 'http://127.0.0.1/dummy/welcome', NOW(), 'NULL', NOW(), 'NULL', b'0'),
       ('2', '2', 'http://127.0.0.1/dummy-oidc/welcome', NOW(), 'NULL', NOW(), 'NULL', b'0');

