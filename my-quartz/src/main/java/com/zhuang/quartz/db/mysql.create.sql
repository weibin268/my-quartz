DROP TABLE IF EXISTS sys_job_log;
CREATE TABLE sys_job_log
(
    id          VARCHAR(50) PRIMARY KEY,
    job_group   VARCHAR(50),
    job_name    VARCHAR(50),
    job_class   VARCHAR(500),
    result      INT,
    message     VARCHAR(2000),
    create_time DATETIME,
    create_by   VARCHAR(50)
);
ALTER TABLE sys_job_log
    COMMENT = '定时任务执行记录表';

