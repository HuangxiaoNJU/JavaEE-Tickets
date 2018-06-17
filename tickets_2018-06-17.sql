# ************************************************************
# Sequel Pro SQL dump
# Version 4541
#
# http://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: 127.0.0.1 (MySQL 5.7.16)
# Database: tickets
# Generation Time: 2018-06-17 08:11:50 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table account
# ------------------------------------------------------------

DROP TABLE IF EXISTS `account`;

CREATE TABLE `account` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `account_number` varchar(32) NOT NULL DEFAULT '' COMMENT '账号',
  `balance` double NOT NULL COMMENT '余额',
  `user_id` int(11) unsigned NOT NULL COMMENT '用户id',
  `type` varchar(16) NOT NULL DEFAULT '' COMMENT '账户类型',
  PRIMARY KEY (`id`),
  KEY `user_id_forerign_key` (`user_id`),
  CONSTRAINT `user_id_forerign_key` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `account` WRITE;
/*!40000 ALTER TABLE `account` DISABLE KEYS */;

INSERT INTO `account` (`id`, `account_number`, `balance`, `user_id`, `type`)
VALUES
	(1,'158********',6666,2,'支付宝'),
	(2,'622202*********2397',666,2,'工行卡'),
	(4,'hx0698',902,2,'微信支付'),
	(5,'184********',6902.76,4,'支付宝'),
	(6,'138********',4277.92,8,'支付宝'),
	(7,'151250044@smail.nju.edu.cn',5187.52,9,'支付宝'),
	(8,'151250060@smail.nju.edu.cn',8888,10,'支付宝');

/*!40000 ALTER TABLE `account` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table allocation
# ------------------------------------------------------------

DROP TABLE IF EXISTS `allocation`;

CREATE TABLE `allocation` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `venue_id` int(11) unsigned NOT NULL COMMENT '场馆id',
  `project_id` int(11) unsigned NOT NULL COMMENT '活动id',
  `ratio` int(11) NOT NULL COMMENT '平台分配比例',
  `platform_income` double NOT NULL COMMENT '平台收入',
  `venue_income` double NOT NULL COMMENT '场馆收入',
  `time` datetime NOT NULL COMMENT '分配时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `allocation` WRITE;
/*!40000 ALTER TABLE `allocation` DISABLE KEYS */;

INSERT INTO `allocation` (`id`, `venue_id`, `project_id`, `ratio`, `platform_income`, `venue_income`, `time`)
VALUES
	(1,1,11,25,0,0,'2018-03-21 17:58:08');

/*!40000 ALTER TABLE `allocation` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table coupon
# ------------------------------------------------------------

DROP TABLE IF EXISTS `coupon`;

CREATE TABLE `coupon` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(16) NOT NULL DEFAULT '' COMMENT '名称',
  `money` int(11) NOT NULL COMMENT '优惠金额',
  `require_points` int(11) NOT NULL COMMENT '所需积分',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `coupon` WRITE;
/*!40000 ALTER TABLE `coupon` DISABLE KEYS */;

INSERT INTO `coupon` (`id`, `name`, `money`, `require_points`)
VALUES
	(1,'10元优惠券',10,100),
	(2,'30元优惠券',30,250);

/*!40000 ALTER TABLE `coupon` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table manager
# ------------------------------------------------------------

DROP TABLE IF EXISTS `manager`;

CREATE TABLE `manager` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `manager_name` varchar(16) NOT NULL DEFAULT '',
  `password` varchar(32) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `manager` WRITE;
/*!40000 ALTER TABLE `manager` DISABLE KEYS */;

INSERT INTO `manager` (`id`, `manager_name`, `password`)
VALUES
	(1,'huangxiao','123456');

/*!40000 ALTER TABLE `manager` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table order_form
# ------------------------------------------------------------

DROP TABLE IF EXISTS `order_form`;

CREATE TABLE `order_form` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `purchase_type` int(11) NOT NULL COMMENT '购买类型',
  `project_price_id` int(11) unsigned NOT NULL COMMENT '座位价位',
  `seat_number` int(11) NOT NULL COMMENT '座位数',
  `state` int(11) NOT NULL COMMENT '订单状态',
  `user_id` int(11) unsigned NOT NULL COMMENT '用户id',
  `discount` int(11) NOT NULL COMMENT '会员折扣(0-100)',
  `total_price` double NOT NULL COMMENT '订单总价',
  `create_time` datetime NOT NULL COMMENT '下单时间',
  `coupon_id` int(11) unsigned NOT NULL COMMENT '优惠券id(不使用为0)',
  `check_in` tinyint(1) NOT NULL DEFAULT '0',
  `seat_list` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `order_form` WRITE;
/*!40000 ALTER TABLE `order_form` DISABLE KEYS */;

INSERT INTO `order_form` (`id`, `purchase_type`, `project_price_id`, `seat_number`, `state`, `user_id`, `discount`, `total_price`, `create_time`, `coupon_id`, `check_in`, `seat_list`)
VALUES
	(1,0,7,1,1,2,100,100,'2018-03-09 12:54:25',0,0,'1_1'),
	(2,1,9,2,2,2,98,98,'2018-03-19 00:34:55',0,0,NULL),
	(3,0,9,3,1,2,98,147,'2018-03-20 23:04:24',0,0,'15_7,15_8,15_9'),
	(7,1,10,1,1,4,98,3810.24,'2018-03-21 00:35:31',0,0,NULL),
	(8,1,11,1,2,4,98,1850.24,'2018-03-21 01:19:39',0,0,'1_1'),
	(9,0,9,3,2,4,90,135,'2018-03-21 14:04:11',0,0,'1_12,1_13,1_11'),
	(10,1,12,2,4,8,98,1740.48,'2018-03-21 18:17:21',0,0,'1_1,1_2'),
	(11,0,12,4,4,0,100,3552,'2018-03-22 00:18:05',0,1,'3_5,3_6,4_5,4_6'),
	(12,1,12,2,4,8,85,1509.6,'2018-03-22 00:28:36',0,1,'1_3,1_4'),
	(13,0,9,5,4,0,100,250,'2018-03-22 14:04:41',0,1,'1_6,1_7,1_8,1_9,1_10'),
	(14,0,11,2,4,9,98,3700.48,'2018-03-22 14:40:54',0,0,'1_4,1_5'),
	(15,0,14,2,2,8,85,340,'2018-03-22 15:33:59',0,0,'1_1,1_2'),
	(16,1,13,4,4,8,85,1020,'2018-03-22 15:34:28',0,0,'1_1,1_2,1_3,1_4'),
	(17,0,14,2,1,8,85,340,'2018-03-22 15:35:00',0,0,'1_3,1_4'),
	(18,0,12,2,4,0,100,1776,'2018-03-22 15:37:46',0,1,'3_9,3_10');

/*!40000 ALTER TABLE `order_form` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table pay
# ------------------------------------------------------------

DROP TABLE IF EXISTS `pay`;

CREATE TABLE `pay` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `venue_id` int(11) unsigned NOT NULL,
  `project_id` int(11) unsigned NOT NULL,
  `money` double NOT NULL,
  `time` datetime NOT NULL,
  `is_online` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `pay` WRITE;
/*!40000 ALTER TABLE `pay` DISABLE KEYS */;

INSERT INTO `pay` (`id`, `venue_id`, `project_id`, `money`, `time`, `is_online`)
VALUES
	(1,1,12,98,'2018-03-19 00:38:08',1),
	(2,1,12,-78.4,'2018-03-20 15:44:52',1),
	(3,2,15,1850.24,'2018-03-21 01:20:04',1),
	(4,1,12,135,'2018-03-21 14:04:56',1),
	(5,1,12,-67.5,'2018-03-21 14:05:21',1),
	(6,2,15,-925.12,'2018-03-21 14:05:50',1),
	(7,2,15,1740.48,'2018-03-21 18:18:44',1),
	(8,2,15,3552,'2018-03-22 00:18:05',0),
	(9,2,15,1509.6,'2018-03-22 00:30:52',1),
	(10,1,12,250,'2018-03-22 14:04:41',0),
	(11,2,15,3700.48,'2018-03-22 14:41:07',1),
	(12,3,16,340,'2018-03-22 15:34:10',1),
	(13,3,16,1020,'2018-03-22 15:34:42',1),
	(14,3,16,-170,'2018-03-22 15:35:26',1),
	(15,2,15,1776,'2018-03-22 15:37:46',0);

/*!40000 ALTER TABLE `pay` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table project
# ------------------------------------------------------------

DROP TABLE IF EXISTS `project`;

CREATE TABLE `project` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(16) NOT NULL DEFAULT '' COMMENT '计划名',
  `begin_time` datetime NOT NULL COMMENT '开始时间',
  `end_time` datetime NOT NULL COMMENT '结束时间',
  `type` varchar(16) NOT NULL DEFAULT '' COMMENT '类型',
  `description` varchar(256) NOT NULL DEFAULT '' COMMENT '描述',
  `venue_id` int(11) unsigned NOT NULL COMMENT '场馆id',
  `poster_url` varchar(128) DEFAULT NULL COMMENT '活动海报url',
  PRIMARY KEY (`id`),
  KEY `venue_foreign_key` (`venue_id`),
  CONSTRAINT `venue_foreign_key` FOREIGN KEY (`venue_id`) REFERENCES `venue` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `project` WRITE;
/*!40000 ALTER TABLE `project` DISABLE KEYS */;

INSERT INTO `project` (`id`, `name`, `begin_time`, `end_time`, `type`, `description`, `venue_id`, `poster_url`)
VALUES
	(11,'开学典礼','2018-03-03 13:55:36','2018-03-04 13:55:36','讲座','南京大学信任校长吕建发表新学期开学演讲，请所有同学务必参加。南京大学信任校长吕建发表新学期开学演讲，请所有同学务必参加。南京大学信任校长吕建发表新学期开学演讲，请所有同学务必参加。南京大学信任校长吕建发表新学期开学演讲，请所有同学务必参加。',1,NULL),
	(12,'云计算、大数据和人工智能','2018-04-01 10:00:00','2018-04-02 17:30:00','讲座','人工智能、大数据已成为当下各行业最热门的话题。云计算，大数据，和人工智能，最近火的不行不行的词汇，似乎不相同，但又似乎相互关联，到底是什么样的关系呢？其实他们本没有什么关系，各自活在不同的世界里，然而随着互联网的发展，相互纠葛在了一起。',1,NULL),
	(15,'第八届Dota2国际邀请赛','2018-08-01 00:00:00','2018-08-10 00:00:00','电子竞技','DOTA2国际邀请赛，The International DOTA2 Championships。简称Ti，创立于2011年，是一个全球性的电子竞技赛事，每年一届，由ValveCorporation（V社）主办,奖杯为V社特制冠军盾牌，每一届冠军队伍及人员将记录在游戏泉水的冠军盾中。',2,NULL),
	(16,'J2EE讲座','2018-03-23 00:00:00','2018-03-24 00:00:00','讲座','J2EE',3,NULL),
	(17,'开学典礼','2018-03-03 13:55:36','2018-03-04 13:55:36','讲座','南京大学信任校长吕建发表新学期开学演讲，请所有同学务必参加。南京大学信任校长吕建发表新学期开学演讲，请所有同学务必参加。南京大学信任校长吕建发表新学期开学演讲，请所有同学务必参加。南京大学信任校长吕建发表新学期开学演讲，请所有同学务必参加。',1,'/poster/11/octocat.jpg');

/*!40000 ALTER TABLE `project` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table project_price
# ------------------------------------------------------------

DROP TABLE IF EXISTS `project_price`;

CREATE TABLE `project_price` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `project_id` int(11) unsigned NOT NULL,
  `seat_name` varchar(16) NOT NULL DEFAULT '',
  `seat_number` int(11) NOT NULL,
  `price` double NOT NULL,
  `row_num` int(11) DEFAULT NULL,
  `column_num` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `project_price` WRITE;
/*!40000 ALTER TABLE `project_price` DISABLE KEYS */;

INSERT INTO `project_price` (`id`, `project_id`, `seat_name`, `seat_number`, `price`, `row_num`, `column_num`)
VALUES
	(7,17,'VIP座位',50,100,10,5),
	(8,17,'普通座位',100,50,10,10),
	(9,12,'统一座位',225,50,15,15),
	(10,15,'贵宾座',50,3888,5,10),
	(11,15,'一等座',64,1888,8,8),
	(12,15,'二等座',100,888,10,10),
	(13,16,'一等座',25,300,5,5),
	(14,16,'二等座',100,200,10,10);

/*!40000 ALTER TABLE `project_price` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table user
# ------------------------------------------------------------

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `email` varchar(32) NOT NULL DEFAULT '' COMMENT '电子邮箱',
  `nickname` varchar(16) NOT NULL DEFAULT '' COMMENT '昵称',
  `password` varchar(32) NOT NULL DEFAULT '' COMMENT '密码',
  `level` int(11) NOT NULL COMMENT '会员等级（等于0则被注销）',
  `points` int(11) NOT NULL COMMENT '会员积分',
  `register_time` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;

INSERT INTO `user` (`id`, `email`, `nickname`, `password`, `level`, `points`, `register_time`)
VALUES
	(2,'hx36w35@163.com','Sigma','123456',1,20,'2018-02-28 00:00:00'),
	(4,'151250051@smail.nju.edu.cn','XianXian','123456',2,493,'2018-03-01 00:00:00'),
	(8,'123','huangxiao','123',5,4639,'2018-03-15 19:59:53'),
	(9,'151250044@smail.nju.edu.cn','gkj','123',5,3350,'2018-03-22 14:40:04'),
	(10,'151250060@smail.nju.edu.cn','huangxiao123','123',1,0,'2018-03-22 15:29:28');

/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table user_coupon
# ------------------------------------------------------------

DROP TABLE IF EXISTS `user_coupon`;

CREATE TABLE `user_coupon` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) unsigned NOT NULL,
  `coupon_id` int(11) unsigned NOT NULL,
  `quantity` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `user_coupon` WRITE;
/*!40000 ALTER TABLE `user_coupon` DISABLE KEYS */;

INSERT INTO `user_coupon` (`id`, `user_id`, `coupon_id`, `quantity`)
VALUES
	(1,2,1,2),
	(3,8,1,3),
	(5,8,2,2),
	(6,4,2,2),
	(7,9,2,1),
	(8,9,1,1);

/*!40000 ALTER TABLE `user_coupon` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table venue
# ------------------------------------------------------------

DROP TABLE IF EXISTS `venue`;

CREATE TABLE `venue` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(16) NOT NULL DEFAULT '',
  `email` varchar(32) NOT NULL COMMENT '联系邮箱',
  `identification` varchar(7) DEFAULT '' COMMENT '识别码',
  `location` varchar(64) NOT NULL DEFAULT '' COMMENT '地点',
  `seat_number` int(11) NOT NULL COMMENT '座位数',
  `register_time` datetime NOT NULL COMMENT '申请时间',
  `is_checked` tinyint(1) NOT NULL COMMENT '是否审核',
  `is_pass` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否通过',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `venue` WRITE;
/*!40000 ALTER TABLE `venue` DISABLE KEYS */;

INSERT INTO `venue` (`id`, `name`, `email`, `identification`, `location`, `seat_number`, `register_time`, `is_checked`, `is_pass`)
VALUES
	(1,'南京大学（鼓楼校区）','735913885@qq.com','Q2p2eyd','南京鼓楼区',500,'2018-03-09 12:00:00',1,1),
	(2,'钥匙球馆 KEY Arena','hx36w35@163.com','LGG0wvs','美国华盛顿州西雅图市',20000,'2018-03-20 23:48:47',1,1),
	(3,'南京大学','151250060@smail.nju.edu.cn','1QEZxXO','南京市 鼓楼校区',2000,'2018-03-22 15:30:44',1,1);

/*!40000 ALTER TABLE `venue` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table venue_modify
# ------------------------------------------------------------

DROP TABLE IF EXISTS `venue_modify`;

CREATE TABLE `venue_modify` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `venue_id` int(11) unsigned NOT NULL,
  `new_name` varchar(16) DEFAULT NULL,
  `new_seat_number` int(11) DEFAULT NULL,
  `new_location` varchar(64) DEFAULT NULL,
  `apply_time` datetime NOT NULL,
  `is_checked` tinyint(1) NOT NULL DEFAULT '0',
  `is_pass` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `venue_modify` WRITE;
/*!40000 ALTER TABLE `venue_modify` DISABLE KEYS */;

INSERT INTO `venue_modify` (`id`, `venue_id`, `new_name`, `new_seat_number`, `new_location`, `apply_time`, `is_checked`, `is_pass`)
VALUES
	(1,1,'南京大学',NULL,NULL,'2018-03-19 19:36:13',1,1),
	(2,2,'钥匙球馆 KEY Arena',NULL,'美国华盛顿州西雅图市','2018-03-20 23:53:34',1,1),
	(3,1,'南京生态大学',NULL,NULL,'2018-03-21 14:14:41',1,0),
	(4,3,'东南大学',3000,NULL,'2018-03-22 15:32:02',0,0);

/*!40000 ALTER TABLE `venue_modify` ENABLE KEYS */;
UNLOCK TABLES;



/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
