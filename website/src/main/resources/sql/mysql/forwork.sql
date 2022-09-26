/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50725
 Source Host           : 127.0.0.1:3306
 Source Schema         : forwork

 Target Server Type    : MySQL
 Target Server Version : 50725
 File Encoding         : 65001

 Date: 07/03/2021 23:39:37
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for article
-- ----------------------------
DROP TABLE IF EXISTS `article`;
CREATE TABLE `article`
(
    `id`          int(11)                                                 NOT NULL AUTO_INCREMENT,
    `author`      varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL,
    `title`       varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `intro`       varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `file_path`   varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `star`        int(11)                                                 NULL DEFAULT 0,
    `visit`       int(11)                                                 NULL DEFAULT 0,
    `comment`     int(11)                                                 NULL DEFAULT 0,
    `create_time` datetime(0)                                             NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time` datetime(0)                                             NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
    `status`      int(1)                                                  NULL DEFAULT 1,
    `tags`        varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 13
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category`
(
    `id`          int(11)                                                 NOT NULL,
    `content`     varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL     DEFAULT NULL,
    `count`       int(11)                                                 NULL     DEFAULT 0,
    `sort`        int(11)                                                 NULL     DEFAULT NULL,
    `visible`     int(11)                                                 NULL     DEFAULT 1,
    `create_user` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL     DEFAULT 'admin',
    `create_time` datetime(0)                                             NULL     DEFAULT CURRENT_TIMESTAMP,
    `update_time` datetime(0)                                             NULL     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
    `used`        tinyint(1)                                              NOT NULL DEFAULT 1,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment`
(
    `id`          int(11)                                                  NOT NULL AUTO_INCREMENT,
    `article_id`  int(11)                                                  NULL DEFAULT NULL,
    `content`     varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `create_user` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci   NULL DEFAULT NULL,
    `create_time` datetime(0)                                              NULL DEFAULT CURRENT_TIMESTAMP,
    `parent_id`   int(11)                                                  NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tag
-- ----------------------------
DROP TABLE IF EXISTS `tag`;
CREATE TABLE `tag`
(
    `id`          int(11)                                                 NOT NULL,
    `content`     varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `count`       int(11)                                                 NULL DEFAULT 0,
    `create_user` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL,
    `create_time` datetime(0)                                             NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time` datetime(0)                                             NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
    `used`        tinyint(1)                                              NULL DEFAULT 1,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`
(
    `id`         int(11)                                                NOT NULL AUTO_INCREMENT,
    `name`       varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `password`   varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `role`       varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `status`     int(1)                                                 NULL DEFAULT NULL,
    `last_login` datetime(0)                                            NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
    `is_login`   int(1)                                                 NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
