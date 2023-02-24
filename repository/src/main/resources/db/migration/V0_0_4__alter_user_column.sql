ALTER TABLE `user` ADD COLUMN `language` VARCHAR(3) NOT NULL DEFAULT 'ja' AFTER `gender`;
ALTER TABLE `user` ADD COLUMN `timezone` VARCHAR(45) NOT NULL DEFAULT 'Asia/Tokyo' AFTER `language`;
