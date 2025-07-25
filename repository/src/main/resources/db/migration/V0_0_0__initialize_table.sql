DROP TABLE IF EXISTS `company`;
CREATE TABLE `company` (
`id` char(32) COLLATE utf8mb4_bin NOT NULL COMMENT 'id',
`name` char(64) COLLATE utf8mb4_bin NOT NULL COMMENT '名前',
`country_id` char(32) COLLATE utf8mb4_bin NOT NULL COMMENT '所属国',
`created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '作成日',
`created_by` char(32) COLLATE utf8mb4_bin NOT NULL COMMENT '作成者',
`updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日',
`updated_by` char(32) COLLATE utf8mb4_bin NOT NULL COMMENT '更新者',
`is_deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '削除フラグ',
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='会社';

DROP TABLE IF EXISTS `country`;
CREATE TABLE `country` (
`id` char(32) COLLATE utf8mb4_bin NOT NULL COMMENT 'id',
`name` char(64) COLLATE utf8mb4_bin NOT NULL COMMENT '名前',
`region_id` char(32) COLLATE utf8mb4_bin NOT NULL COMMENT 'リージョン',
`language_id` char(32) COLLATE utf8mb4_bin NOT NULL COMMENT '言語',
`created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '作成日',
`created_by` char(32) COLLATE utf8mb4_bin NOT NULL COMMENT '作成者',
`updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日',
`updated_by` char(32) COLLATE utf8mb4_bin NOT NULL COMMENT '更新者',
`is_deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '削除フラグ',
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='国';

DROP TABLE IF EXISTS `grant_type`;
CREATE TABLE `grant_type` (
`id` char(32) COLLATE utf8mb4_bin NOT NULL COMMENT 'id',
`type` varchar(255) COLLATE utf8mb4_bin NOT NULL COMMENT '付与タイプ',
`created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '作成日',
`created_by` char(32) COLLATE utf8mb4_bin NOT NULL COMMENT '作成者',
`updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日',
`updated_by` char(32) COLLATE utf8mb4_bin NOT NULL COMMENT '更新者',
`is_deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '削除フラグ',
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='認可タイプ';

DROP TABLE IF EXISTS `language`;
CREATE TABLE `language` (
`id` char(32) COLLATE utf8mb4_bin NOT NULL COMMENT 'id',
`name` char(64) COLLATE utf8mb4_bin NOT NULL COMMENT '名前',
`created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '作成日',
`created_by` char(32) COLLATE utf8mb4_bin NOT NULL COMMENT '作成者',
`updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日',
`updated_by` char(32) COLLATE utf8mb4_bin NOT NULL COMMENT '更新者',
`is_deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '削除フラグ',
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='言語';

DROP TABLE IF EXISTS `logout_redirect_url`;
CREATE TABLE `logout_redirect_url` (
`id` char(32) COLLATE utf8mb4_bin NOT NULL COMMENT 'id',
`oauth_client_id` char(32) COLLATE utf8mb4_bin NOT NULL COMMENT 'OAuthクライアントid',
`redirect_url` char(255) COLLATE utf8mb4_bin NOT NULL COMMENT 'ログアウトリダイレクトURL',
`created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '作成日',
`created_by` char(32) COLLATE utf8mb4_bin NOT NULL COMMENT '作成者',
`updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日',
`updated_by` char(32) COLLATE utf8mb4_bin NOT NULL COMMENT '更新者',
`is_deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '削除フラグ',
PRIMARY KEY (`id`),
KEY `idx_oauth_client_id` (`oauth_client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='OAuthクライアントログアウトリダイレクトURL';

DROP TABLE IF EXISTS `management_company_user`;
CREATE TABLE `management_company_user` (
`id` char(32) COLLATE utf8mb4_bin NOT NULL,
`company_id` char(32) COLLATE utf8mb4_bin NOT NULL COMMENT '会社ID',
`user_id` varchar(32) COLLATE utf8mb4_bin NOT NULL COMMENT 'ユーザーID',
`created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '作成日',
`created_by` char(32) COLLATE utf8mb4_bin NOT NULL COMMENT '作成者',
`updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日',
`updated_by` char(32) COLLATE utf8mb4_bin NOT NULL COMMENT '更新者',
`is_deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '削除フラグ',
PRIMARY KEY (`id`),
UNIQUE KEY `UK_company_id_user_id` (`company_id`,`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='会社管理';

DROP TABLE IF EXISTS `notice`;
CREATE TABLE `notice` (
`id` char(32) NOT NULL COMMENT 'お知らせID',
`title` varchar(45) NOT NULL COMMENT '件名',
`body` text NOT NULL COMMENT '本文',
`start_at` datetime NOT NULL COMMENT '掲載開始日',
`end_at` datetime DEFAULT NULL COMMENT '掲載終了日',
`emphasis` bit(1) NOT NULL DEFAULT b'0' COMMENT '強いお知らせか',
`created_at` datetime NOT NULL COMMENT '作成日時',
`created_by` char(32) NOT NULL COMMENT '作成者',
`updated_at` datetime NOT NULL COMMENT '更新日時',
`updated_by` varchar(45) NOT NULL COMMENT '更新者',
`is_deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '削除フラグ',
PRIMARY KEY (`id`),
KEY `idx_title` (`title`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='お知らせ';

DROP TABLE IF EXISTS `oauth2_authorization`;
CREATE TABLE `oauth2_authorization` (
`id` varchar(100) NOT NULL,
`registered_client_id` varchar(100) NOT NULL,
`principal_name` varchar(200) NOT NULL,
`authorization_grant_type` varchar(100) NOT NULL,
`attributes` text,
`state` varchar(500) DEFAULT NULL,
`authorization_code_value` text,
`authorization_code_issued_at` timestamp NULL DEFAULT NULL,
`authorization_code_expires_at` timestamp NULL DEFAULT NULL,
`authorization_code_metadata` text,
`access_token_value` text,
`access_token_issued_at` timestamp NULL DEFAULT NULL,
`access_token_expires_at` timestamp NULL DEFAULT NULL,
`access_token_metadata` text,
`access_token_type` text,
`access_token_scopes` text,
`oidc_id_token_value` text,
`oidc_id_token_issued_at` timestamp NULL DEFAULT NULL,
`oidc_id_token_expires_at` timestamp NULL DEFAULT NULL,
`oidc_id_token_metadata` text,
`refresh_token_value` text,
`refresh_token_issued_at` timestamp NULL DEFAULT NULL,
`refresh_token_expires_at` timestamp NULL DEFAULT NULL,
`refresh_token_metadata` text,
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `oauth2_authorization_consent`;
CREATE TABLE `oauth2_authorization_consent` (
`id` char(32) NOT NULL,
`registered_client_id` varchar(100) NOT NULL,
`principal_name` varchar(200) NOT NULL,
`authorities` text NOT NULL,
PRIMARY KEY (`id`),
UNIQUE KEY `UK_registered_client_id_principal_name` (`registered_client_id`,`principal_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `oauth_client`;
CREATE TABLE `oauth_client` (
`id` char(32) COLLATE utf8mb4_bin NOT NULL COMMENT 'id',
`name` varchar(255) COLLATE utf8mb4_bin NOT NULL COMMENT 'OAuthクライアント名',
`client_id` varchar(255) COLLATE utf8mb4_bin NOT NULL COMMENT 'クライアントID',
`client_secret` varchar(255) COLLATE utf8mb4_bin NOT NULL COMMENT 'クライアントシークレット',
`created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '作成日',
`created_by` char(32) COLLATE utf8mb4_bin NOT NULL COMMENT '作成者',
`updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日',
`updated_by` char(32) COLLATE utf8mb4_bin NOT NULL COMMENT '更新者',
`is_deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '削除フラグ',
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='OAuthクライアント';

DROP TABLE IF EXISTS `oauth_client_consent`;
CREATE TABLE `oauth_client_consent` (
`id` char(32) COLLATE utf8mb4_bin NOT NULL,
`registered_client_id` varchar(100) COLLATE utf8mb4_bin NOT NULL,
`principal_name` varchar(200) COLLATE utf8mb4_bin NOT NULL,
`authorities` text COLLATE utf8mb4_bin NOT NULL,
PRIMARY KEY (`id`),
UNIQUE KEY `UK_registered_client_id_principal_name` (`registered_client_id`,`principal_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='OAuthクライアント承認';

DROP TABLE IF EXISTS `oauth_client_grant_type`;
CREATE TABLE `oauth_client_grant_type` (
`id` char(32) COLLATE utf8mb4_bin NOT NULL COMMENT 'id',
`oauth_client_id` char(32) COLLATE utf8mb4_bin NOT NULL COMMENT 'OAuthクライアントid',
`grant_type_id` char(32) COLLATE utf8mb4_bin NOT NULL COMMENT '認可タイプid',
PRIMARY KEY (`id`),
UNIQUE KEY `uk_oauth_client_grant` (`oauth_client_id`,`grant_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='OAuthクライアント認可タイプ';

DROP TABLE IF EXISTS `oauth_client_management`;
CREATE TABLE `oauth_client_management` (
`id` char(32) COLLATE utf8mb4_bin NOT NULL COMMENT 'ID',
`oauth_client_id` char(32) COLLATE utf8mb4_bin NOT NULL COMMENT 'OAuthクライアントId',
`user_id` char(32) COLLATE utf8mb4_bin NOT NULL COMMENT 'ユーザーID',
`created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '作成日',
`created_by` char(32) COLLATE utf8mb4_bin NOT NULL COMMENT '作成者',
PRIMARY KEY (`id`),
KEY `idx_oauth_client_id` (`oauth_client_id`),
KEY `idx_user_id` (`user_id`),
KEY `idx_oauth_client_id_user_id` (`oauth_client_id`,`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='OAuthクライアント管理者';

DROP TABLE IF EXISTS `oauth_client_redirect_url`;
CREATE TABLE `oauth_client_redirect_url` (
`id` char(32) COLLATE utf8mb4_bin NOT NULL COMMENT 'id',
`oauth_client_id` char(32) COLLATE utf8mb4_bin NOT NULL COMMENT 'OAuthクライアントid',
`redirect_url` char(255) COLLATE utf8mb4_bin NOT NULL COMMENT 'リダイレクトURL',
`created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '作成日',
`created_by` char(32) COLLATE utf8mb4_bin NOT NULL COMMENT '作成者',
`updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日',
`updated_by` char(32) COLLATE utf8mb4_bin NOT NULL COMMENT '更新者',
`is_deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '削除フラグ',
PRIMARY KEY (`id`),
KEY `idx_oauth_client_id` (`oauth_client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='OAuthクライアントリダイレクトURL';

DROP TABLE IF EXISTS `oauth_client_scope`;
CREATE TABLE `oauth_client_scope` (
`id` char(32) COLLATE utf8mb4_bin NOT NULL COMMENT 'id',
`oauth_client_id` char(32) COLLATE utf8mb4_bin NOT NULL COMMENT 'OAuthクライアントid',
`scope_id` char(32) COLLATE utf8mb4_bin NOT NULL COMMENT 'スコープid',
PRIMARY KEY (`id`),
UNIQUE KEY `uk_oauth_client_scope` (`oauth_client_id`,`scope_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='OAuthクライアントスコープ';

DROP TABLE IF EXISTS `open_notice`;
CREATE TABLE `open_notice` (
`id` char(32) NOT NULL COMMENT '既読管理ID',
`user_id` char(32) NOT NULL COMMENT 'ユーザーID',
`notice_id` char(32) NOT NULL COMMENT 'お知らせID',
`opened_at` datetime NOT NULL COMMENT '既読日時',
PRIMARY KEY (`id`),
UNIQUE KEY `uk_user_id` (`user_id`),
KEY `idx_notice_id` (`notice_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='既読管理';

DROP TABLE IF EXISTS `organization`;
CREATE TABLE `organization` (
`id` char(32) COLLATE utf8mb4_bin NOT NULL COMMENT 'グループID',
`company_id` char(32) COLLATE utf8mb4_bin NOT NULL COMMENT '会社ID',
`name` varchar(50) COLLATE utf8mb4_bin NOT NULL COMMENT 'グループ名',
`created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '作成日',
`created_by` char(32) COLLATE utf8mb4_bin NOT NULL COMMENT '作成者',
`updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日',
`updated_by` char(32) COLLATE utf8mb4_bin NOT NULL COMMENT '更新者',
`is_deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '削除フラグ',
PRIMARY KEY (`id`),
KEY `idx_organization_company_id` (`company_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='組織';

DROP TABLE IF EXISTS `organization_user`;
CREATE TABLE `organization_user` (
`id` char(32) COLLATE utf8mb4_bin NOT NULL COMMENT 'グループユーザーID',
`organization_id` char(32) COLLATE utf8mb4_bin NOT NULL COMMENT 'グループID',
`user_id` varchar(32) COLLATE utf8mb4_bin NOT NULL COMMENT 'ユーザーID',
`created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '作成日',
`created_by` char(32) COLLATE utf8mb4_bin NOT NULL COMMENT '作成者',
PRIMARY KEY (`id`),
UNIQUE KEY `uk_organization_user` (`organization_id`,`user_id`),
KEY `idx_organization_id` (`organization_id`),
KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='グループユーザー';

DROP TABLE IF EXISTS `password_token`;
CREATE TABLE `password_token` (
`id` char(32) COLLATE utf8mb4_bin NOT NULL COMMENT 'id',
`user_id` char(32) COLLATE utf8mb4_bin NOT NULL COMMENT 'ユーザーID',
`token` varchar(255) COLLATE utf8mb4_bin NOT NULL COMMENT 'トークン',
`expire` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '有効期限',
PRIMARY KEY (`id`),
UNIQUE KEY `uk_user_id_token` (`user_id`,`token`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='パスワードトークン';

DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission` (
`id` char(32) COLLATE utf8mb4_bin NOT NULL COMMENT 'id',
`name` char(64) COLLATE utf8mb4_bin NOT NULL COMMENT 'パーミッション名',
`created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '作成日',
`created_by` char(32) COLLATE utf8mb4_bin NOT NULL COMMENT '作成者',
`updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日',
`updated_by` char(32) COLLATE utf8mb4_bin NOT NULL COMMENT '更新者',
`is_deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '削除フラグ',
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='パーミッション';

DROP TABLE IF EXISTS `recovery_code`;
CREATE TABLE `recovery_code` (
`id` char(32) NOT NULL,
`userid` char(32) NOT NULL,
`code` char(19) NOT NULL,
`used` bit(1) NOT NULL DEFAULT b'0',
PRIMARY KEY (`id`),
UNIQUE KEY `user_id_code_idx` (`userid`,`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `region`;
CREATE TABLE `region` (
`id` char(32) COLLATE utf8mb4_bin NOT NULL COMMENT 'id',
`name` char(64) COLLATE utf8mb4_bin NOT NULL COMMENT '名前',
`created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '作成日',
`created_by` char(32) COLLATE utf8mb4_bin NOT NULL COMMENT '作成者',
`updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日',
`updated_by` char(32) COLLATE utf8mb4_bin NOT NULL COMMENT '更新者',
`is_deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '削除フラグ',
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='リージョン';

DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
`id` char(32) COLLATE utf8mb4_bin NOT NULL COMMENT 'id',
`name` char(64) COLLATE utf8mb4_bin NOT NULL COMMENT 'ロール名',
`description` varchar(255) COLLATE utf8mb4_bin NOT NULL COMMENT '概要',
`grantable` bit(1) NOT NULL DEFAULT b'0' COMMENT '不要可能',
`created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '作成日',
`created_by` char(32) COLLATE utf8mb4_bin NOT NULL COMMENT '作成者',
`updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日',
`updated_by` char(32) COLLATE utf8mb4_bin NOT NULL COMMENT '更新者',
`is_deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '削除フラグ',
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='ロール';

DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission` (
`id` char(32) COLLATE utf8mb4_bin NOT NULL COMMENT 'id',
`role_id` char(32) COLLATE utf8mb4_bin NOT NULL COMMENT 'id',
`permission_id` char(32) COLLATE utf8mb4_bin NOT NULL COMMENT 'id',
`created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '作成日',
`created_by` char(32) COLLATE utf8mb4_bin NOT NULL COMMENT '作成者',
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='ロールに紐づくパーミッション';

DROP TABLE IF EXISTS `scope`;
CREATE TABLE `scope` (
`id` char(32) COLLATE utf8mb4_bin NOT NULL COMMENT 'id',
`name` varchar(255) COLLATE utf8mb4_bin NOT NULL COMMENT 'スコープ名',
`created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '作成日',
`created_by` char(32) COLLATE utf8mb4_bin NOT NULL COMMENT '作成者',
`updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日',
`updated_by` char(32) COLLATE utf8mb4_bin NOT NULL COMMENT '更新者',
`is_deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '削除フラグ',
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='スコープ';

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
`id` char(32) COLLATE utf8mb4_bin NOT NULL COMMENT 'id',
`login_id` varchar(32) COLLATE utf8mb4_bin NOT NULL COMMENT 'ログインID',
`name` varchar(255) COLLATE utf8mb4_bin NOT NULL COMMENT 'ユーザー名',
`gender` char(1) COLLATE utf8mb4_bin NOT NULL COMMENT '性別',
`language` varchar(3) COLLATE utf8mb4_bin NOT NULL DEFAULT 'ja',
`timezone` varchar(45) COLLATE utf8mb4_bin NOT NULL DEFAULT 'Asia/Tokyo',
`mail` varchar(255) COLLATE utf8mb4_bin NOT NULL COMMENT 'メールアドレス',
`password` varchar(255) COLLATE utf8mb4_bin NOT NULL COMMENT 'パスワード',
`lockout` bit(1) NOT NULL DEFAULT b'0' COMMENT 'ロックアウト',
`two_factor_authentication` bit(1) NOT NULL DEFAULT b'0',
`secret` varchar(45) COLLATE utf8mb4_bin DEFAULT NULL,
`company_id` char(32) COLLATE utf8mb4_bin NOT NULL COMMENT '所属会社',
`created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '作成日',
`created_by` char(32) COLLATE utf8mb4_bin NOT NULL COMMENT '作成者',
`updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日',
`updated_by` char(32) COLLATE utf8mb4_bin NOT NULL COMMENT '更新者',
`is_deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '削除フラグ',
PRIMARY KEY (`id`),
UNIQUE KEY `uk_employee_no` (`login_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='ユーザー';

DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
`id` char(32) COLLATE utf8mb4_bin NOT NULL COMMENT 'id',
`user_id` char(32) COLLATE utf8mb4_bin NOT NULL COMMENT 'id',
`role_id` char(32) COLLATE utf8mb4_bin NOT NULL COMMENT 'id',
`created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '作成日',
`created_by` char(32) COLLATE utf8mb4_bin NOT NULL COMMENT '作成者',
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='ユーザに紐づくロール';

DROP TABLE IF EXISTS `work_time`;
CREATE TABLE `work_time` (
`id` char(32) COLLATE utf8mb4_bin NOT NULL COMMENT 'id',
`work_date` date NOT NULL COMMENT '年月日',
`start_time` time NOT NULL COMMENT '始業時間',
`end_time` time NOT NULL COMMENT '終業時間',
`official_working_time` time DEFAULT NULL COMMENT '所定内通常勤務',
`official_midnight_working_time` time DEFAULT NULL COMMENT '所定内深夜勤務',
`official_break_time` time DEFAULT NULL COMMENT '所定内休憩勤務',
`created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '作成日',
`created_by` char(32) COLLATE utf8mb4_bin NOT NULL COMMENT '作成者',
`updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日',
`updated_by` char(32) COLLATE utf8mb4_bin NOT NULL COMMENT '更新者',
`is_deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '削除フラグ',
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='作業時間';
