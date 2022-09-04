DROP TABLE IF EXISTS sys_fileupload;
CREATE TABLE sys_fileupload
(
    id          VARCHAR(50) PRIMARY KEY,
    biz_table   VARCHAR(50),
    biz_field   VARCHAR(30),
    biz_id      VARCHAR(36),
    file_path   VARCHAR(1000) NOT NULL,
    file_name   VARCHAR(500),
    file_size   int,
    status      INT,
    create_time DATETIME,
    modify_time DATETIME,
    create_by   VARCHAR(50),
    modify_by   VARCHAR(50)
);
ALTER TABLE sys_fileupload
    COMMENT = '文件上传记录表';
ALTER TABLE sys_fileupload
    ADD INDEX IDX_created_time (create_time);
