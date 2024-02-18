DROP TABLE IF EXISTS organization;
CREATE TABLE organization
(
  `id`         char(32)    NOT NULL COMMENT 'ID',
  `company_id` char(32)    NOT NULL COMMENT '会社Id',
  `name`       varchar(50) NOT NULL COMMENT '組織名',
  `created_at` datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '作成日',
  `created_by` char(32)    NOT NULL COMMENT '作成者',
  `updated_at` datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE current_timestamp COMMENT '更新日',
  `updated_by` char(32)    NOT NULL COMMENT '更新者',
  `is_deleted` bit(1)      NOT NULL DEFAULT b'0' COMMENT '削除フラグ',
  PRIMARY KEY (`id`),
  INDEX idx_company_id (`company_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_bin COMMENT '会社組織';