ALTER TABLE `user` ADD COLUMN `two_factor_authentication` BIT(1) NOT NULL DEFAULT 0 AFTER `lockout`;
