DROP TABLE IF EXISTS oauth_client_management;
CREATE TABLE oauth_client_management
(
  `id`              char(32) NOT NULL COMMENT 'ID',
  `oauth_client_id` char(32) NOT NULL COMMENT 'OAuthクライアントId',
  `user_id`         char(32) NOT NULL COMMENT 'ユーザーID',
  `created_at`      datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '作成日',
  `created_by`      char(32) NOT NULL COMMENT '作成者',
  PRIMARY KEY (`id`),
  INDEX idx_oauth_client_id (`oauth_client_id`),
  INDEX idx_user_id (`user_id`),
  INDEX idx_oauth_client_id_user_id (`oauth_client_id`, `user_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_bin COMMENT 'OAuthクライアント管理者';