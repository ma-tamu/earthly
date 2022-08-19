DROP TABLE IF EXISTS user;
CREATE TABLE user
(
  `id`         char(32)     NOT NULL COMMENT 'id',
  `login_id`   varchar(32)  NOT NULL COMMENT 'ログインID',
  `name`       varchar(255) NOT NULL COMMENT 'ユーザー名',
  `gender`     char(1)      NOT NULL COMMENT '性別',
  `mail`       varchar(255) NOT NULL COMMENT 'メールアドレス',
  `password`   varchar(255) NOT NULL COMMENT 'パスワード',
  `lockout`    bit(1)       NOT NULL DEFAULT 0 COMMENT 'ロックアウト',
  `office_id`  char(32)     NOT NULL COMMENT '所属オフィス',
  `created_at` datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '作成日',
  `created_by` char(32)     NOT NULL COMMENT '作成者',
  `updated_at` datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE current_timestamp COMMENT '更新日',
  `updated_by` char(32)     NOT NULL COMMENT '更新者',
  `is_deleted` bit(1)       NOT NULL DEFAULT b'0' COMMENT '削除フラグ',
  PRIMARY KEY (`id`),
  UNIQUE uk_employee_no(login_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT 'ユーザー';

DROP TABLE IF EXISTS office;
CREATE TABLE office
(
  `id`         char(32) NOT NULL COMMENT 'id',
  `name`       char(64) NOT NULL COMMENT '名前',
  `kind`       char(1)  NOT NULL COMMENT '種別',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '作成日',
  `created_by` char(32) NOT NULL COMMENT '作成者',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE current_timestamp COMMENT '更新日',
  `updated_by` char(32) NOT NULL COMMENT '更新者',
  `is_deleted` bit(1)   NOT NULL DEFAULT b'0' COMMENT '削除フラグ',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT 'オフィス';

DROP TABLE IF EXISTS permission;
CREATE TABLE permission
(
  `id`         char(32) NOT NULL COMMENT 'id',
  `name`       char(64) NOT NULL COMMENT 'パーミッション名',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '作成日',
  `created_by` char(32) NOT NULL COMMENT '作成者',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE current_timestamp COMMENT '更新日',
  `updated_by` char(32) NOT NULL COMMENT '更新者',
  `is_deleted` bit(1)   NOT NULL DEFAULT b'0' COMMENT '削除フラグ',
  PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT 'パーミッション';

DROP TABLE IF EXISTS role;
CREATE TABLE role
(
  `id`         char(32) NOT NULL COMMENT 'id',
  `name`       char(64) NOT NULL COMMENT 'ロール名',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '作成日',
  `created_by` char(32) NOT NULL COMMENT '作成者',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE current_timestamp COMMENT '更新日',
  `updated_by` char(32) NOT NULL COMMENT '更新者',
  `is_deleted` bit(1)   NOT NULL DEFAULT b'0' COMMENT '削除フラグ',
  PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT 'ロール';

DROP TABLE IF EXISTS role_permission;
CREATE TABLE role_permission
(
  `id`            char(32) NOT NULL COMMENT 'id',
  `role_id`       char(32) NOT NULL COMMENT 'id',
  `permission_id` char(32) NOT NULL COMMENT 'id',
  `created_at`    datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '作成日',
  `created_by`    char(32) NOT NULL COMMENT '作成者',
  PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT 'ロールに紐づくパーミッション';

DROP TABLE IF EXISTS user_role;
CREATE TABLE user_role
(
  `id`         char(32) NOT NULL COMMENT 'id',
  `user_id`    char(32) NOT NULL COMMENT 'id',
  `role_id`    char(32) NOT NULL COMMENT 'id',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '作成日',
  `created_by` char(32) NOT NULL COMMENT '作成者',
  PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT 'ユーザに紐づくロール';

DROP TABLE IF EXISTS sub_office;
CREATE TABLE sub_office
(
  `id`              char(32) NOT NULL COMMENT 'id',
  `name`            char(64) NOT NULL COMMENT '名前',
  `office_id`       char(32) NOT NULL COMMENT 'オフィス',
  `department_code` char(3)  NOT NULL COMMENT '部門コード',
  `belong_code`     char(3)  NOT NULL COMMENT '所属コード',
  `created_at`      datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '作成日',
  `created_by`      char(32) NOT NULL COMMENT '作成者',
  `updated_at`      datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE current_timestamp COMMENT '更新日',
  `updated_by`      char(32) NOT NULL COMMENT '更新者',
  `is_deleted`      bit(1)   NOT NULL DEFAULT b'0' COMMENT '削除フラグ',
  PRIMARY KEY (`id`),
  UNIQUE uk_department_belong(department_code, belong_code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT '分室';

DROP TABLE IF EXISTS work_time;
CREATE TABLE work_time
(
  `id`                             char(32) NOT NULL COMMENT 'id',
  `work_date`                      date     NOT NULL COMMENT '年月日',
  `start_time`                     time     NOT NULL COMMENT '始業時間',
  `end_time`                       time     NOT NULL COMMENT '終業時間',
  `official_working_time`          time NULL COMMENT '所定内通常勤務',
  `official_midnight_working_time` time NULL COMMENT '所定内深夜勤務',
  `official_break_time`            time NULL COMMENT '所定内休憩勤務',
  `created_at`                     datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '作成日',
  `created_by`                     char(32) NOT NULL COMMENT '作成者',
  `updated_at`                     datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE current_timestamp COMMENT '更新日',
  `updated_by`                     char(32) NOT NULL COMMENT '更新者',
  `is_deleted`                     bit(1)   NOT NULL DEFAULT b'0' COMMENT '削除フラグ',
  PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT '作業時間';

DROP TABLE IF EXISTS oauth_client;
CREATE TABLE oauth_client
(
  `id`            char(32)     NOT NULL COMMENT 'id',
  `name`          varchar(255) NOT NULL COMMENT 'OAuthクライアント名',
  `client_id`     varchar(255) NOT NULL COMMENT 'クライアントID',
  `client_secret` varchar(255) NOT NULL COMMENT 'クライアントシークレット',
  `created_at`    datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '作成日',
  `created_by`    char(32)     NOT NULL COMMENT '作成者',
  `updated_at`    datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE current_timestamp COMMENT '更新日',
  `updated_by`    char(32)     NOT NULL COMMENT '更新者',
  `is_deleted`    bit(1)       NOT NULL DEFAULT b'0' COMMENT '削除フラグ',
  PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT 'OAuthクライアント';

DROP TABLE IF EXISTS oauth_client_redirect_url;
CREATE TABLE oauth_client_redirect_url
(
  `id`              char(32)  NOT NULL COMMENT 'id',
  `oauth_client_id` char(32)  NOT NULL COMMENT 'OAuthクライアントid',
  `redirect_url`    char(255) NOT NULL COMMENT 'リダイレクトURL',
  `created_at`      datetime  NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '作成日',
  `created_by`      char(32)  NOT NULL COMMENT '作成者',
  `updated_at`      datetime  NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE current_timestamp COMMENT '更新日',
  `updated_by`      char(32)  NOT NULL COMMENT '更新者',
  `is_deleted`      bit(1)    NOT NULL DEFAULT b'0' COMMENT '削除フラグ',
  PRIMARY KEY (`id`),
  INDEX             idx_oauth_client_id (`oauth_client_id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT 'OAuthクライアントリダイレクトURL';

DROP TABLE IF EXISTS grant_type;
CREATE TABLE grant_type
(
  `id`         char(32)     NOT NULL COMMENT 'id',
  `type`       varchar(255) NOT NULL COMMENT '付与タイプ',
  `created_at` datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '作成日',
  `created_by` char(32)     NOT NULL COMMENT '作成者',
  `updated_at` datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE current_timestamp COMMENT '更新日',
  `updated_by` char(32)     NOT NULL COMMENT '更新者',
  `is_deleted` bit(1)       NOT NULL DEFAULT b'0' COMMENT '削除フラグ',
  PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT '認可タイプ';

DROP TABLE IF EXISTS oauth_client_grant_type;
CREATE TABLE oauth_client_grant_type
(
  `id`              char(32) NOT NULL COMMENT 'id',
  `oauth_client_id` char(32) NOT NULL COMMENT 'OAuthクライアントid',
  `grant_type_id`   char(32) NOT NULL COMMENT '認可タイプid',
  PRIMARY KEY (`id`),
  UNIQUE uk_oauth_client_grant(oauth_client_id, grant_type_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT 'OAuthクライアント認可タイプ';

DROP TABLE IF EXISTS scope;
CREATE TABLE scope
(
  `id`         char(32)     NOT NULL COMMENT 'id',
  `name`       varchar(255) NOT NULL COMMENT 'スコープ名',
  `created_at` datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '作成日',
  `created_by` char(32)     NOT NULL COMMENT '作成者',
  `updated_at` datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE current_timestamp COMMENT '更新日',
  `updated_by` char(32)     NOT NULL COMMENT '更新者',
  `is_deleted` bit(1)       NOT NULL DEFAULT b'0' COMMENT '削除フラグ',
  PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT 'スコープ';

DROP TABLE IF EXISTS oauth_client_scope;
CREATE TABLE oauth_client_scope
(
  `id`              char(32) NOT NULL COMMENT 'id',
  `oauth_client_id` char(32) NOT NULL COMMENT 'OAuthクライアントid',
  `scope_id`        char(32) NOT NULL COMMENT 'スコープid',
  PRIMARY KEY (`id`),
  UNIQUE uk_oauth_client_scope(oauth_client_id, scope_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT 'OAuthクライアントスコープ';

DROP TABLE IF EXISTS oauth_client_consent;
CREATE TABLE oauth_client_consent
(
  registered_client_id varchar(100) NOT NULL,
  principal_name       varchar(200) NOT NULL,
  authorities          text         NOT NULL,
  PRIMARY KEY (registered_client_id, principal_name)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT 'OAuthクライアント承認';

DROP TABLE IF EXISTS oauth2_authorization;
CREATE TABLE oauth2_authorization
(
  id                            varchar(100) NOT NULL,
  registered_client_id          varchar(100) NOT NULL,
  principal_name                varchar(200) NOT NULL,
  authorization_grant_type      varchar(100) NOT NULL,
  attributes                    text         DEFAULT NULL,
  state                         varchar(500) DEFAULT NULL,
  authorization_code_value      text         DEFAULT NULL,
  authorization_code_issued_at  timestamp    DEFAULT NULL,
  authorization_code_expires_at timestamp    DEFAULT NULL,
  authorization_code_metadata   text         DEFAULT NULL,
  access_token_value            text         DEFAULT NULL,
  access_token_issued_at        timestamp    DEFAULT NULL,
  access_token_expires_at       timestamp    DEFAULT NULL,
  access_token_metadata         text         DEFAULT NULL,
  access_token_type             text         DEFAULT NULL,
  access_token_scopes           text         DEFAULT NULL,
  oidc_id_token_value           text         DEFAULT NULL,
  oidc_id_token_issued_at       timestamp    DEFAULT NULL,
  oidc_id_token_expires_at      timestamp    DEFAULT NULL,
  oidc_id_token_metadata        text         DEFAULT NULL,
  refresh_token_value           text         DEFAULT NULL,
  refresh_token_issued_at       timestamp    DEFAULT NULL,
  refresh_token_expires_at      timestamp    DEFAULT NULL,
  refresh_token_metadata        text         DEFAULT NULL,
  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS oauth2_authorization_consent;
CREATE TABLE oauth2_authorization_consent
(
  registered_client_id varchar(100) NOT NULL,
  principal_name       varchar(200) NOT NULL,
  authorities          text         NOT NULL,
  PRIMARY KEY (registered_client_id, principal_name)
);