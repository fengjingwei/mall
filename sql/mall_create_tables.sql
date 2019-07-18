/*
SQLyog Ultimate v12.2.6 (64 bit)
MySQL - 5.7.19-0ubuntu0.16.04.1 : Database - account
*********************************************************************
*/
CREATE DATABASE /*!32312 IF NOT EXISTS */`mall_account` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_bin */;
USE `mall_account`;
DROP TABLE IF EXISTS `t_account`;
CREATE TABLE `t_account` (
  `id`            BIGINT(20)      NOT NULL AUTO_INCREMENT,
  `user_id`       VARCHAR(128)   NOT NULL,
  `balance`       DECIMAL(10, 0) NOT NULL COMMENT '用户余额',
  `freeze_amount` DECIMAL(10, 0) NOT NULL COMMENT '冻结金额，扣款暂存余额',
  `create_time`   DATETIME        NOT NULL,
  `update_time`   DATETIME        DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_bin;
INSERT INTO `t_account` (`id`, `user_id`, `balance`, `freeze_amount`, `create_time`, `update_time`) VALUES
(1, '10000', 10000, 0, now(), NULL),
(2, '10001', 20000, 0, now(), NULL),
(3, '10002', 30000, 0, now(), NULL);

CREATE DATABASE /*!32312 IF NOT EXISTS */`mall_inventory` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;
USE `mall_inventory`;
DROP TABLE IF EXISTS `t_inventory`;
CREATE TABLE `t_inventory` (
  `id`              BIGINT(20)    NOT NULL AUTO_INCREMENT,
  `product_id`      VARCHAR(128) NOT NULL,
  `total_inventory` INT(10)      NOT NULL COMMENT '总库存',
  `lock_inventory`  INT(10)      NOT NULL COMMENT '锁定库存',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_bin;
INSERT INTO `t_inventory` (`id`, `product_id`, `total_inventory`, `lock_inventory`) VALUES (1, '1', 10000, 0);

CREATE DATABASE /*!32312 IF NOT EXISTS */`mall_order` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;
USE `mall_order`;
DROP TABLE IF EXISTS `t_order_0`;
CREATE TABLE `t_order_0` (
  `id`           BIGINT(20)                      NOT NULL AUTO_INCREMENT,
  `order_no`       VARCHAR(20) COLLATE utf8mb4_bin NOT NULL,
  `user_id`      VARCHAR(128)                    NOT NULL,
  `status`       TINYINT(4)                      NOT NULL,
  `product_id`   VARCHAR(128)                    NOT NULL,
  `count`        INT(4)                          NOT NULL,
  `total_amount` DECIMAL(10, 0)                  NOT NULL,
  `create_time`  DATETIME                        NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_bin;

DROP TABLE IF EXISTS `t_order_1`;
CREATE TABLE `t_order_1` (
  `id`           BIGINT(20)                      NOT NULL AUTO_INCREMENT,
  `order_no`       VARCHAR(20) COLLATE utf8mb4_bin NOT NULL,
  `user_id`      VARCHAR(128)                    NOT NULL,
  `status`       TINYINT(4)                      NOT NULL,
  `product_id`   VARCHAR(128)                    NOT NULL,
  `count`        INT(4)                          NOT NULL,
  `total_amount` DECIMAL(10, 0)                  NOT NULL,
  `create_time`  DATETIME                        NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_bin;

DROP TABLE IF EXISTS `t_order_2`;
CREATE TABLE `t_order_2` (
  `id`           BIGINT(20)                      NOT NULL AUTO_INCREMENT,
  `order_no`       VARCHAR(20) COLLATE utf8mb4_bin NOT NULL,
  `user_id`      VARCHAR(128)                    NOT NULL,
  `status`       TINYINT(4)                      NOT NULL,
  `product_id`   VARCHAR(128)                    NOT NULL,
  `count`        INT(4)                          NOT NULL,
  `total_amount` DECIMAL(10, 0)                  NOT NULL,
  `create_time`  DATETIME                        NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_bin;