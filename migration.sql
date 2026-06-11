-- 数据迁移脚本
-- 为 Building 表添加 main_area 和 sub_area 字段
ALTER TABLE building ADD COLUMN main_area INT DEFAULT 2;
ALTER TABLE building ADD COLUMN sub_area INT DEFAULT 1;

-- 为 Room 表添加 room_number 字段
ALTER TABLE room ADD COLUMN room_number VARCHAR(10);

-- 更新房间号（只存储房间号，不包含楼层）
-- 例如：1室 → 01，12室 → 12
UPDATE room
SET room_number = LPAD(SUBSTRING(name, 1, CHAR_LENGTH(name) - 1), 2, '0');
