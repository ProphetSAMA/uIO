-- 数据迁移脚本
-- 为 Building 表添加 main_area 和 sub_area 字段
ALTER TABLE building ADD COLUMN main_area INT DEFAULT 2;
ALTER TABLE building ADD COLUMN sub_area INT DEFAULT 1;

-- 为 Room 表添加 room_number 字段
ALTER TABLE room ADD COLUMN room_number VARCHAR(10);

-- 更新房间号（根据楼层和房间名计算）
-- 例如：3层1室 → 301
-- 注意：使用 CHAR_LENGTH 而不是 LENGTH，因为中文字符是多字节的
UPDATE room r
JOIN floor f ON r.floor_id = f.id
SET r.room_number = CONCAT(
    SUBSTRING(f.name, 1, CHAR_LENGTH(f.name) - 1),
    LPAD(SUBSTRING(r.name, 1, CHAR_LENGTH(r.name) - 1), 2, '0')
);
