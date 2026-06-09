-- Migration: Add user_id column to power table
-- Created: 2024-06-09
-- Description: Add user_id column to track which user owns each power record

ALTER TABLE `power` ADD COLUMN `user_id` BIGINT NULL AFTER `id`;
ALTER TABLE `power` ADD INDEX `idx_user_id` (`user_id`);
ALTER TABLE `power` ADD INDEX `idx_querytime` (`querytime`);
