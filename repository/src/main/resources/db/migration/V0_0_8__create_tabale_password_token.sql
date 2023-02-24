DROP TABLE IF EXISTS password_token;
CREATE TABLE password_token
(
  `id`      char(32)     NOT NULL COMMENT 'id',
  `user_id` char(32)     NOT NULL COMMENT 'ユーザーID',
  `token`   varchar(255) NOT NULL COMMENT 'トークン',
  `expire`  datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '有効期限',
  PRIMARY KEY (`id`),
  UNIQUE uk_user_id_token (`user_id`, `token`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_bin COMMENT 'パスワードトークン';