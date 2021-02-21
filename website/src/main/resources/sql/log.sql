CREATE DATABASE IF NOT EXISTS forwork DEFAULT CHARSET utf8 COLLATE utf8_general_ci;
USE forwork;
CREATE TABLE IF NOT EXISTS SYS_LOG
(
    UNIQUE_ID   int(10) primary key auto_increment,
    LOG_LEVEL   varchar(30),
    TEXT        varchar(50),
    TS          TIMESTAMP not null,
    USER_TYPE   varchar(30),
    USER_ID     varchar(30),
    USER_NAME   varchar(30),
    REMOTE_HOST varchar(50),
    NM_SPACE    varchar(50),
    AGENT       varchar(50)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;