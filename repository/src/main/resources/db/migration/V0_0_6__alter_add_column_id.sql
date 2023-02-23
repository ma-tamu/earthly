ALTER TABLE `management_company_user`
  ADD COLUMN `id` CHAR(32) NOT NULL AFTER `is_deleted`,
  DROP PRIMARY KEY,
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE INDEX `UK_company_id_user_id` (`company_id` ASC, `user_id` ASC) VISIBLE;
;
ALTER TABLE `oauth2_authorization_consent`
  ADD COLUMN `id` CHAR(32) NOT NULL FIRST,
  DROP PRIMARY KEY,
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE INDEX `UK_registered_client_id_principal_name` (`registered_client_id` ASC, `principal_name` ASC) VISIBLE;
;
ALTER TABLE `oauth_client_consent`
  ADD COLUMN `id` CHAR(32) NOT NULL FIRST,
  DROP PRIMARY KEY,
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE INDEX `UK_registered_client_id_principal_name` (`registered_client_id` ASC, `principal_name` ASC) VISIBLE;
;
