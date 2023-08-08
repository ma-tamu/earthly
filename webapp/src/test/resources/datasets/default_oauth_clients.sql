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
DELETE
FROM `oauth_client_management`;


INSERT INTO `oauth_client`(`id`, `name`, `client_id`, `client_secret`, `created_at`, `created_by`, `updated_at`,
                           `updated_by`, `is_deleted`)
VALUES ('001', 'OAuthクライアント1', 'client_1', 'secret_1', '2001-04-01 17:07:38', 'NULL', '2001-04-01 17:07:38',
        'NULL', b'0'),
       ('002', 'OIDCクライアント2', 'client_2', 'secret_2', '2001-04-01 17:08:38', 'NULL', '2001-04-01 17:08:38',
        'NULL', b'0'),
       ('003', 'duel', 'client_3', 'secret_3', '2001-04-01 17:09:38', 'NULL', '2001-04-01 17:09:38', 'NULL', b'0'),
       ('004', 'buster', 'client_4', 'secret_4', '2001-04-01 17:10:38', 'NULL', '2001-04-01 17:10:38', 'NULL', b'0'),
       ('005', 'blitz', 'client_5', 'secret_5', '2001-04-01 17:11:38', 'NULL', '2001-04-01 17:11:38', 'NULL', b'0'),
       ('006', 'aegis', 'client_6', 'secret_6', '2001-04-01 17:12:38', 'NULL', '2001-04-01 17:12:38', 'NULL', b'0'),
       ('007', 'strike', 'client_7', 'secret_7', '2001-04-01 17:13:38', 'NULL', '2001-04-01 17:13:38', 'NULL', b'0'),
       ('008', 'calamity', 'client_8', 'secret_8', '2001-04-01 17:14:38', 'NULL', '2001-04-01 17:14:38', 'NULL', b'0'),
       ('009', 'forbidden', 'client_9', 'secret_9', '2001-04-01 17:15:38', 'NULL', '2001-04-01 17:15:38', 'NULL', b'0'),
       ('010', 'raider', 'client_10', 'secret_10', '2001-04-01 17:16:38', 'NULL', '2001-04-01 17:16:38', 'NULL', b'0'),
       ('011', 'strike-noir', 'client_11', 'secret_11', '2001-04-01 17:17:38', 'NULL', '2001-04-01 17:17:38', 'NULL',
        b'0'),
       ('012', 'astray', 'client_12', 'secret_12', '2001-04-01 17:18:38', 'NULL', '2001-04-01 17:18:38', 'NULL', b'0'),
       ('013', 'hyperion', 'client_13', 'secret_13', '2001-04-01 17:19:38', 'NULL', '2001-04-01 17:19:38', 'NULL',
        b'0'),
       ('014', 'testament', 'client_14', 'secret_14', '2001-04-01 17:20:38', 'NULL', '2001-04-01 17:20:38', 'NULL',
        b'0'),
       ('015', 'providence', 'client_15', 'secret_15', '2001-04-01 17:21:38', 'NULL', '2001-04-01 17:21:38', 'NULL',
        b'0'),
       ('016', 'freedom', 'client_16', 'secret_16', '2001-04-01 17:22:38', 'NULL', '2001-04-01 17:22:38', 'NULL', b'0'),
       ('017', 'justice', 'client_17', 'secret_17', '2001-04-01 17:23:38', 'NULL', '2001-04-01 17:23:38', 'NULL', b'0'),
       ('018', 'dretnote', 'client_18', 'secret_18', '2001-04-01 17:24:38', 'NULL', '2001-04-01 17:24:38', 'NULL',
        b'0'),
       ('019', 'strike-rouge', 'client_19', 'secret_19', '2001-04-01 17:25:38', 'NULL', '2001-04-01 17:25:38', 'NULL',
        b'0'),
       ('020', 'regenerate', 'client_20', 'secret_20', '2001-04-01 17:26:38', 'NULL', '2001-04-01 17:26:38', 'NULL',
        b'0');

INSERT INTO `oauth_client_redirect_url` (`id`, `oauth_client_id`, `redirect_url`, `created_at`, `created_by`,
                                         `updated_at`, `updated_by`, `is_deleted`)
VALUES ('001', '001', 'http://127.0.0.1/dummy/login/oauth2/code/earthly', NOW(), 'NULL', NOW(), 'NULL', b'0'),
       ('002', '001', 'http://127.0.0.1/dummy/swagger-ui/oauth2-redirect.html', NOW(), 'NULL', NOW(), 'NULL', b'0'),
       ('003', '002', 'http://127.0.0.1/dummy-oidc/login/oauth2/code/earthly', NOW(), 'NULL', NOW(), 'NULL', b'0'),
       ('004', '002', 'http://127.0.0.1/dummy-oidc/swagger-ui/oauth2-redirect.html', NOW(), 'NULL', NOW(), 'NULL',
        b'0'),
       ('005', '003', 'http://127.0.0.1/duel/login/oauth2/code/earthly', NOW(), 'NULL', NOW(), 'NULL', b'0'),
       ('006', '004', 'http://127.0.0.1/buster/login/oauth2/code/earthly', NOW(), 'NULL', NOW(), 'NULL', b'0'),
       ('007', '005', 'http://127.0.0.1/blitz/login/oauth2/code/earthly', NOW(), 'NULL', NOW(), 'NULL', b'0'),
       ('008', '006', 'http://127.0.0.1/aegis/login/oauth2/code/earthly', NOW(), 'NULL', NOW(), 'NULL', b'0'),
       ('009', '007', 'http://127.0.0.1/strike/login/oauth2/code/earthly', NOW(), 'NULL', NOW(), 'NULL', b'0'),
       ('010', '008', 'http://127.0.0.1/calamity/login/oauth2/code/earthly', NOW(), 'NULL', NOW(), 'NULL', b'0'),
       ('011', '009', 'http://127.0.0.1/forbidden/login/oauth2/code/earthly', NOW(), 'NULL', NOW(), 'NULL', b'0'),
       ('012', '010', 'http://127.0.0.1/raider/login/oauth2/code/earthly', NOW(), 'NULL', NOW(), 'NULL', b'0'),
       ('013', '011', 'http://127.0.0.1/strike-noir/login/oauth2/code/earthly', NOW(), 'NULL', NOW(), 'NULL', b'0'),
       ('014', '012', 'http://127.0.0.1/astray/login/oauth2/code/earthly', NOW(), 'NULL', NOW(), 'NULL', b'0'),
       ('015', '013', 'http://127.0.0.1/hyperion/login/oauth2/code/earthly', NOW(), 'NULL', NOW(), 'NULL', b'0'),
       ('016', '014', 'http://127.0.0.1/testament/login/oauth2/code/earthly', NOW(), 'NULL', NOW(), 'NULL', b'0'),
       ('017', '015', 'http://127.0.0.1/providence/login/oauth2/code/earthly', NOW(), 'NULL', NOW(), 'NULL', b'0'),
       ('018', '016', 'http://127.0.0.1/freedom/login/oauth2/code/earthly', NOW(), 'NULL', NOW(), 'NULL', b'0'),
       ('019', '017', 'http://127.0.0.1/justice/login/oauth2/code/earthly', NOW(), 'NULL', NOW(), 'NULL', b'0'),
       ('020', '018', 'http://127.0.0.1/dretnote/login/oauth2/code/earthly', NOW(), 'NULL', NOW(), 'NULL', b'0'),
       ('021', '019', 'http://127.0.0.1/strike-rouge/login/oauth2/code/earthly', NOW(), 'NULL', NOW(), 'NULL', b'0'),
       ('022', '020', 'http://127.0.0.1/regenerate/login/oauth2/code/earthly', NOW(), 'NULL', NOW(), 'NULL', b'0');

INSERT INTO `oauth_client_scope` (`id`, `oauth_client_id`, `scope_id`)
VALUES ('001', '001', '15b4a732660d11ec8a0b0242ac120003'),
       ('002', '002', '15b4a732660d11ec8a0b0242ac120003'),
       ('003', '002', 'a85c1c6665fc11ec8a0b0242ac120003'),
       ('004', '003', '15b4a732660d11ec8a0b0242ac120003'),
       ('005', '004', '15b4a732660d11ec8a0b0242ac120003'),
       ('006', '005', '15b4a732660d11ec8a0b0242ac120003'),
       ('007', '006', '15b4a732660d11ec8a0b0242ac120003'),
       ('008', '007', '15b4a732660d11ec8a0b0242ac120003'),
       ('009', '008', '15b4a732660d11ec8a0b0242ac120003'),
       ('010', '009', '15b4a732660d11ec8a0b0242ac120003'),
       ('011', '010', '15b4a732660d11ec8a0b0242ac120003'),
       ('012', '011', '15b4a732660d11ec8a0b0242ac120003'),
       ('013', '012', '15b4a732660d11ec8a0b0242ac120003'),
       ('014', '013', '15b4a732660d11ec8a0b0242ac120003'),
       ('015', '014', '15b4a732660d11ec8a0b0242ac120003'),
       ('016', '015', '15b4a732660d11ec8a0b0242ac120003'),
       ('017', '016', '15b4a732660d11ec8a0b0242ac120003'),
       ('018', '017', '15b4a732660d11ec8a0b0242ac120003'),
       ('019', '018', '15b4a732660d11ec8a0b0242ac120003'),
       ('020', '019', '15b4a732660d11ec8a0b0242ac120003'),
       ('021', '020', '15b4a732660d11ec8a0b0242ac120003');

INSERT INTO `oauth_client_grant_type` (`id`, `oauth_client_id`, `grant_type_id`)
VALUES ('001', '001', '092519c3660d11ec8a0b0242ac120003'),
       ('002', '001', '0f4c8f7b660d11ec8a0b0242ac120003'),
       ('003', '001', '1b6a0d2a65f911ec8a0b0242ac120003'),
       ('004', '001', 'f35c63dd660c11ec8a0b0242ac120003'),
       ('005', '001', 'fdc24af6660c11ec8a0b0242ac120003'),
       ('006', '002', '092519c3660d11ec8a0b0242ac120003'),
       ('007', '002', '0f4c8f7b660d11ec8a0b0242ac120003'),
       ('008', '002', '1b6a0d2a65f911ec8a0b0242ac120003'),
       ('009', '002', 'f35c63dd660c11ec8a0b0242ac120003'),
       ('010', '002', 'fdc24af6660c11ec8a0b0242ac120003'),
       ('011', '003', '092519c3660d11ec8a0b0242ac120003'),
       ('012', '003', '0f4c8f7b660d11ec8a0b0242ac120003'),
       ('013', '003', '1b6a0d2a65f911ec8a0b0242ac120003'),
       ('014', '003', 'f35c63dd660c11ec8a0b0242ac120003'),
       ('015', '003', 'fdc24af6660c11ec8a0b0242ac120003'),
       ('016', '004', '092519c3660d11ec8a0b0242ac120003'),
       ('017', '004', '0f4c8f7b660d11ec8a0b0242ac120003'),
       ('018', '004', '1b6a0d2a65f911ec8a0b0242ac120003'),
       ('019', '004', 'f35c63dd660c11ec8a0b0242ac120003'),
       ('020', '004', 'fdc24af6660c11ec8a0b0242ac120003'),
       ('021', '005', '092519c3660d11ec8a0b0242ac120003'),
       ('022', '005', '0f4c8f7b660d11ec8a0b0242ac120003'),
       ('023', '005', '1b6a0d2a65f911ec8a0b0242ac120003'),
       ('024', '005', 'f35c63dd660c11ec8a0b0242ac120003'),
       ('025', '005', 'fdc24af6660c11ec8a0b0242ac120003'),
       ('026', '006', '092519c3660d11ec8a0b0242ac120003'),
       ('027', '006', '0f4c8f7b660d11ec8a0b0242ac120003'),
       ('028', '006', '1b6a0d2a65f911ec8a0b0242ac120003'),
       ('029', '006', 'f35c63dd660c11ec8a0b0242ac120003'),
       ('030', '006', 'fdc24af6660c11ec8a0b0242ac120003'),
       ('031', '007', '092519c3660d11ec8a0b0242ac120003'),
       ('032', '007', '0f4c8f7b660d11ec8a0b0242ac120003'),
       ('033', '007', '1b6a0d2a65f911ec8a0b0242ac120003'),
       ('034', '007', 'f35c63dd660c11ec8a0b0242ac120003'),
       ('035', '007', 'fdc24af6660c11ec8a0b0242ac120003'),
       ('036', '008', '092519c3660d11ec8a0b0242ac120003'),
       ('037', '008', '0f4c8f7b660d11ec8a0b0242ac120003'),
       ('038', '008', '1b6a0d2a65f911ec8a0b0242ac120003'),
       ('039', '008', 'f35c63dd660c11ec8a0b0242ac120003'),
       ('040', '008', 'fdc24af6660c11ec8a0b0242ac120003'),
       ('041', '009', '092519c3660d11ec8a0b0242ac120003'),
       ('042', '009', '0f4c8f7b660d11ec8a0b0242ac120003'),
       ('043', '009', '1b6a0d2a65f911ec8a0b0242ac120003'),
       ('044', '009', 'f35c63dd660c11ec8a0b0242ac120003'),
       ('045', '009', 'fdc24af6660c11ec8a0b0242ac120003'),
       ('046', '010', '092519c3660d11ec8a0b0242ac120003'),
       ('047', '010', '0f4c8f7b660d11ec8a0b0242ac120003'),
       ('048', '010', '1b6a0d2a65f911ec8a0b0242ac120003'),
       ('049', '010', 'f35c63dd660c11ec8a0b0242ac120003'),
       ('050', '010', 'fdc24af6660c11ec8a0b0242ac120003'),
       ('051', '011', '092519c3660d11ec8a0b0242ac120003'),
       ('052', '011', '0f4c8f7b660d11ec8a0b0242ac120003'),
       ('053', '011', '1b6a0d2a65f911ec8a0b0242ac120003'),
       ('054', '011', 'f35c63dd660c11ec8a0b0242ac120003'),
       ('055', '011', 'fdc24af6660c11ec8a0b0242ac120003'),
       ('056', '012', '092519c3660d11ec8a0b0242ac120003'),
       ('057', '012', '0f4c8f7b660d11ec8a0b0242ac120003'),
       ('058', '012', '1b6a0d2a65f911ec8a0b0242ac120003'),
       ('059', '012', 'f35c63dd660c11ec8a0b0242ac120003'),
       ('060', '012', 'fdc24af6660c11ec8a0b0242ac120003'),
       ('061', '013', '092519c3660d11ec8a0b0242ac120003'),
       ('062', '013', '0f4c8f7b660d11ec8a0b0242ac120003'),
       ('063', '013', '1b6a0d2a65f911ec8a0b0242ac120003'),
       ('064', '013', 'f35c63dd660c11ec8a0b0242ac120003'),
       ('065', '013', 'fdc24af6660c11ec8a0b0242ac120003'),
       ('066', '014', '092519c3660d11ec8a0b0242ac120003'),
       ('067', '014', '0f4c8f7b660d11ec8a0b0242ac120003'),
       ('068', '014', '1b6a0d2a65f911ec8a0b0242ac120003'),
       ('069', '014', 'f35c63dd660c11ec8a0b0242ac120003'),
       ('070', '014', 'fdc24af6660c11ec8a0b0242ac120003'),
       ('071', '015', '092519c3660d11ec8a0b0242ac120003'),
       ('072', '015', '0f4c8f7b660d11ec8a0b0242ac120003'),
       ('073', '015', '1b6a0d2a65f911ec8a0b0242ac120003'),
       ('074', '015', 'f35c63dd660c11ec8a0b0242ac120003'),
       ('075', '015', 'fdc24af6660c11ec8a0b0242ac120003'),
       ('076', '016', '092519c3660d11ec8a0b0242ac120003'),
       ('077', '016', '0f4c8f7b660d11ec8a0b0242ac120003'),
       ('078', '016', '1b6a0d2a65f911ec8a0b0242ac120003'),
       ('079', '016', 'f35c63dd660c11ec8a0b0242ac120003'),
       ('080', '016', 'fdc24af6660c11ec8a0b0242ac120003'),
       ('081', '017', '092519c3660d11ec8a0b0242ac120003'),
       ('082', '017', '0f4c8f7b660d11ec8a0b0242ac120003'),
       ('083', '017', '1b6a0d2a65f911ec8a0b0242ac120003'),
       ('084', '017', 'f35c63dd660c11ec8a0b0242ac120003'),
       ('085', '017', 'fdc24af6660c11ec8a0b0242ac120003'),
       ('086', '018', '092519c3660d11ec8a0b0242ac120003'),
       ('087', '018', '0f4c8f7b660d11ec8a0b0242ac120003'),
       ('088', '018', '1b6a0d2a65f911ec8a0b0242ac120003'),
       ('089', '018', 'f35c63dd660c11ec8a0b0242ac120003'),
       ('090', '018', 'fdc24af6660c11ec8a0b0242ac120003'),
       ('091', '019', '092519c3660d11ec8a0b0242ac120003'),
       ('092', '019', '0f4c8f7b660d11ec8a0b0242ac120003'),
       ('093', '019', '1b6a0d2a65f911ec8a0b0242ac120003'),
       ('094', '019', 'f35c63dd660c11ec8a0b0242ac120003'),
       ('095', '019', 'fdc24af6660c11ec8a0b0242ac120003'),
       ('096', '020', '092519c3660d11ec8a0b0242ac120003'),
       ('097', '020', '0f4c8f7b660d11ec8a0b0242ac120003'),
       ('098', '020', '1b6a0d2a65f911ec8a0b0242ac120003'),
       ('099', '020', 'f35c63dd660c11ec8a0b0242ac120003'),
       ('100', '020', 'fdc24af6660c11ec8a0b0242ac120003');

INSERT INTO `logout_redirect_url` (`id`, `oauth_client_id`, `redirect_url`, `created_at`, `created_by`, `updated_at`,
                                   `updated_by`, `is_deleted`)
VALUES ('001', '001', 'http://127.0.0.1/dummy/welcome', NOW(), 'NULL', NOW(), 'NULL', b'0'),
       ('002', '002', 'http://127.0.0.1/dummy-oidc/welcome', NOW(), 'NULL', NOW(), 'NULL', b'0'),
       ('003', '003', 'http://127.0.0.1/duel/welcome', NOW(), 'NULL', NOW(), 'NULL', b'0'),
       ('004', '004', 'http://127.0.0.1/buster/welcome', NOW(), 'NULL', NOW(), 'NULL', b'0'),
       ('005', '005', 'http://127.0.0.1/blitz/welcome', NOW(), 'NULL', NOW(), 'NULL', b'0'),
       ('006', '006', 'http://127.0.0.1/aegis/welcome', NOW(), 'NULL', NOW(), 'NULL', b'0'),
       ('007', '007', 'http://127.0.0.1/strike/welcome', NOW(), 'NULL', NOW(), 'NULL', b'0'),
       ('008', '008', 'http://127.0.0.1/calamity/welcome', NOW(), 'NULL', NOW(), 'NULL', b'0'),
       ('009', '009', 'http://127.0.0.1/forbidden/welcome', NOW(), 'NULL', NOW(), 'NULL', b'0'),
       ('010', '010', 'http://127.0.0.1/raider/welcome', NOW(), 'NULL', NOW(), 'NULL', b'0'),
       ('011', '011', 'http://127.0.0.1/strike-noir/welcome', NOW(), 'NULL', NOW(), 'NULL', b'0'),
       ('012', '012', 'http://127.0.0.1/astray/welcome', NOW(), 'NULL', NOW(), 'NULL', b'0'),
       ('013', '013', 'http://127.0.0.1/hyperion/welcome', NOW(), 'NULL', NOW(), 'NULL', b'0'),
       ('014', '014', 'http://127.0.0.1/testament/welcome', NOW(), 'NULL', NOW(), 'NULL', b'0'),
       ('015', '015', 'http://127.0.0.1/providence/welcome', NOW(), 'NULL', NOW(), 'NULL', b'0'),
       ('016', '016', 'http://127.0.0.1/freedom/welcome', NOW(), 'NULL', NOW(), 'NULL', b'0'),
       ('017', '017', 'http://127.0.0.1/justice/welcome', NOW(), 'NULL', NOW(), 'NULL', b'0'),
       ('018', '018', 'http://127.0.0.1/dretnote/welcome', NOW(), 'NULL', NOW(), 'NULL', b'0'),
       ('019', '019', 'http://127.0.0.1/strike-rouge/welcome', NOW(), 'NULL', NOW(), 'NULL', b'0'),
       ('020', '020', 'http://127.0.0.1/regenerate/welcome', NOW(), 'NULL', NOW(), 'NULL', b'0');

INSERT INTO `oauth_client_management` (`id`, `oauth_client_id`, `user_id`, `created_at`, `created_by`)
VALUES ('001', '001', 'USER_ID_02', NOW(), 'NULL'),
       ('002', '002', 'USER_ID_02', NOW(), 'NULL'),
       ('003', '003', 'USER_ID_02', NOW(), 'NULL'),
       ('004', '004', 'USER_ID_02', NOW(), 'NULL'),
       ('005', '005', 'USER_ID_02', NOW(), 'NULL'),
       ('006', '006', 'USER_ID_02', NOW(), 'NULL'),
       ('007', '007', 'USER_ID_02', NOW(), 'NULL'),
       ('008', '008', 'USER_ID_02', NOW(), 'NULL'),
       ('009', '009', 'USER_ID_02', NOW(), 'NULL'),
       ('010', '010', 'USER_ID_02', NOW(), 'NULL'),
       ('011', '011', 'USER_ID_02', NOW(), 'NULL'),
       ('012', '012', 'USER_ID_02', NOW(), 'NULL'),
       ('013', '013', 'USER_ID_02', NOW(), 'NULL'),
       ('014', '014', 'USER_ID_03', NOW(), 'NULL'),
       ('015', '015', 'USER_ID_03', NOW(), 'NULL'),
       ('016', '016', 'USER_ID_03', NOW(), 'NULL'),
       ('017', '017', 'USER_ID_03', NOW(), 'NULL'),
       ('018', '018', 'USER_ID_03', NOW(), 'NULL'),
       ('019', '019', 'USER_ID_02', NOW(), 'NULL'),
       ('020', '020', 'USER_ID_03', NOW(), 'NULL');
