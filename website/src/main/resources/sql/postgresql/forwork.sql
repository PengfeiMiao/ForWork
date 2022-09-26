/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : POSTGRESQL
 Source Server Version : 50725
 Source Host           : 127.0.0.1:5432
 Source Schema         : forwork

 Target Server Type    : POSTGRESQL
 Target Server Version : 50725
 File Encoding         : 65001

 Date: 07/03/2021 23:39:37
*/

-- ----------------------------
-- Table structure for article
-- ----------------------------
DROP TABLE IF EXISTS "article";
CREATE TABLE "article"
(
    "id"          SERIAL          PRIMARY KEY,
    "author"      VARCHAR(50)     NULL DEFAULT NULL,
    "title"       VARCHAR(100)    NULL DEFAULT NULL,
    "intro"       VARCHAR(500)    NULL DEFAULT NULL,
    "file_path"   VARCHAR(255)    NULL DEFAULT NULL,
    "star"        INT             NULL DEFAULT 0,
    "visit"       INT             NULL DEFAULT 0,
    "comment"     INT             NULL DEFAULT 0,
    "create_time" TIMESTAMP       DEFAULT CURRENT_TIMESTAMP,
    "update_time" TIMESTAMP       DEFAULT CURRENT_TIMESTAMP,
    "status"      SMALLINT        NULL DEFAULT 1,
    "tags"        VARCHAR(500)    NULL DEFAULT NULL
);

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS "category";
CREATE TABLE "category"
(
    "id"          INT           NOT NULL,
    "content"     VARCHAR(255)  NULL DEFAULT NULL,
    "count"       INT           NULL DEFAULT 0,
    "sort"        INT           NULL DEFAULT NULL,
    "visible"     INT           NULL DEFAULT 1,
    "create_user" VARCHAR(50)   NULL DEFAULT 'admin',
    "create_time" TIMESTAMP     DEFAULT CURRENT_TIMESTAMP,
    "update_time" TIMESTAMP     DEFAULT CURRENT_TIMESTAMP,
    "used"        SMALLINT      NOT NULL DEFAULT 1,
    PRIMARY KEY ("id")
);

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS "comment";
CREATE TABLE "comment"
(
    "id"          SERIAL        PRIMARY KEY,
    "article_id"  INT           NULL DEFAULT NULL,
    "content"     VARCHAR(2000) NULL DEFAULT NULL,
    "create_user" VARCHAR(50)   NULL DEFAULT NULL,
    "create_time" TIMESTAMP     DEFAULT CURRENT_TIMESTAMP,
    "parent_id"   INT           NULL DEFAULT NULL
);

-- ----------------------------
-- Table structure for tag
-- ----------------------------
DROP TABLE IF EXISTS "tag";
CREATE TABLE "tag"
(
    "id"          INT           NOT NULL,
    "content"     VARCHAR(255)  NULL DEFAULT NULL,
    "count"       INT           NULL DEFAULT 0,
    "create_user" VARCHAR(50)   NULL DEFAULT NULL,
    "create_time" TIMESTAMP     DEFAULT CURRENT_TIMESTAMP,
    "update_time" TIMESTAMP     DEFAULT CURRENT_TIMESTAMP,
    "used"        SMALLINT      NULL DEFAULT 1,
    PRIMARY KEY ("id")
);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS "user";
CREATE TABLE "user"
(
    "id"         SERIAL         PRIMARY KEY,
    "name"       VARCHAR(50)    NULL DEFAULT NULL,
    "password"   VARCHAR(50)    NULL DEFAULT NULL,
    "role"       VARCHAR(50)    NULL DEFAULT NULL,
    "status"     SMALLINT       NULL DEFAULT NULL,
    "last_login" TIMESTAMP      DEFAULT CURRENT_TIMESTAMP,
    "is_login"   SMALLINT       NULL DEFAULT NULL
);
