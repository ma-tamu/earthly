DROP TABLE IF EXISTS `recovery_code`;
CREATE TABLE IF NOT EXISTS `recovery_code`
(
  `id`     CHAR(32) NOT NULL,
  `userid` CHAR(32) NOT NULL,
  `code`   CHAR(19) NOT NULL,
  `used`   BIT      NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `user_id_code_idx` (`userid` ASC, `code` ASC) VISIBLE
);
