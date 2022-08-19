DROP TABLE IF EXISTS office;

CREATE TABLE region
(
  `id`         char(32) NOT NULL COMMENT 'id',
  `name`       char(64) NOT NULL COMMENT '名前',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '作成日',
  `created_by` char(32) NOT NULL COMMENT '作成者',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE current_timestamp COMMENT '更新日',
  `updated_by` char(32) NOT NULL COMMENT '更新者',
  `is_deleted` bit(1)   NOT NULL DEFAULT b'0' COMMENT '削除フラグ',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_bin COMMENT 'リージョン';

CREATE TABLE language
(
  `id`         char(32) NOT NULL COMMENT 'id',
  `name`       char(64) NOT NULL COMMENT '名前',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '作成日',
  `created_by` char(32) NOT NULL COMMENT '作成者',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE current_timestamp COMMENT '更新日',
  `updated_by` char(32) NOT NULL COMMENT '更新者',
  `is_deleted` bit(1)   NOT NULL DEFAULT b'0' COMMENT '削除フラグ',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_bin COMMENT '言語';

CREATE TABLE country
(
  `id`          char(32) NOT NULL COMMENT 'id',
  `name`        char(64) NOT NULL COMMENT '名前',
  `region_id`   char(32) NOT NULL COMMENT 'リージョン',
  `language_id` char(32) NOT NULL COMMENT '言語',
  `created_at`  datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '作成日',
  `created_by`  char(32) NOT NULL COMMENT '作成者',
  `updated_at`  datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE current_timestamp COMMENT '更新日',
  `updated_by`  char(32) NOT NULL COMMENT '更新者',
  `is_deleted`  bit(1)   NOT NULL DEFAULT b'0' COMMENT '削除フラグ',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_bin COMMENT '国';

CREATE TABLE company
(
  `id`         char(32) NOT NULL COMMENT 'id',
  `name`       char(64) NOT NULL COMMENT '名前',
  `country_id` char(32) NOT NULL COMMENT '所属国',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '作成日',
  `created_by` char(32) NOT NULL COMMENT '作成者',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE current_timestamp COMMENT '更新日',
  `updated_by` char(32) NOT NULL COMMENT '更新者',
  `is_deleted` bit(1)   NOT NULL DEFAULT b'0' COMMENT '削除フラグ',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_bin COMMENT '会社';

insert into region (id, name, created_at, created_by, updated_at, updated_by, is_deleted)
values ('45bd917836a599faf2a30c54d677d9a6', 'Asia', now(), 'NULL', now(), 'NULL', 0),
       ('24a24a199ca19e6853bb23aefd44ad8d', 'Middle East', now(), 'NULL', now(), 'NULL', 0),
       ('42fd3737326d5e0115a56ea186b9ad92', 'Africa', now(), 'NULL', now(), 'NULL', 0),
       ('b55aca0e646064b3f5e0f016ae76b079', 'Oceania', now(), 'NULL', now(), 'NULL', 0),
       ('8f00678baab677f3a359207e5f5a9970', 'North America', now(), 'NULL', now(), 'NULL', 0),
       ('2f098f78f1b15a57af2e8c3f209776f8', 'Latin America', now(), 'NULL', now(), 'NULL', 0),
       ('3292eb011aef251d6d6a1f72e7be04a2', 'Europe', now(), 'NULL', now(), 'NULL', 0);

insert into language(id, name, created_at, created_by, updated_at, updated_by, is_deleted)
VALUES ('19154223c579d44a0fe8c9e5476d8f5e', 'japanese', now(), 'NULL', now(), 'NULL', 0);

insert into country (id, name, region_id, language_id, created_at, created_by, updated_at, updated_by, is_deleted)
VALUES ('d92bf311652a77227d2725c204a5396b', 'Japan', '45bd917836a599faf2a30c54d677d9a6',
        '19154223c579d44a0fe8c9e5476d8f5e', now(), 'NULL', now(), 'NULL', 0);

insert into company (id, name, country_id, created_at, created_by, updated_at, updated_by, is_deleted)
VALUES ('6fcc6579e42b58845ea7cbdd0752d861', 'Planet', 'd92bf311652a77227d2725c204a5396b', now(), 'NULL', now(), 'NULL',
        0);

ALTER TABLE user
  CHANGE COLUMN office_id company_id CHAR(32) NOT NULL COMMENT '所属会社';

update user
set company_id = '6fcc6579e42b58845ea7cbdd0752d861'
where id = 'NULL'



