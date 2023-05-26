DROP TABLE IF EXISTS logout_redirect_url;
CREATE TABLE logout_redirect_url
(
  `id`              char(32)  NOT NULL COMMENT 'id',
  `oauth_client_id` char(32)  NOT NULL COMMENT 'OAuthクライアントid',
  `redirect_url`    char(255) NOT NULL COMMENT 'ログアウトリダイレクトURL',
  `created_at`      datetime  NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '作成日',
  `created_by`      char(32)  NOT NULL COMMENT '作成者',
  `updated_at`      datetime  NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日',
  `updated_by`      char(32)  NOT NULL COMMENT '更新者',
  `is_deleted`      bit(1)    NOT NULL DEFAULT b'0' COMMENT '削除フラグ',
  PRIMARY KEY (`id`),
  INDEX             idx_oauth_client_id (`oauth_client_id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT 'OAuthクライアントログアウトリダイレクトURL';