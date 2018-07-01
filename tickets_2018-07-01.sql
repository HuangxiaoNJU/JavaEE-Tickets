# ************************************************************
# Sequel Pro SQL dump
# Version 4541
#
# http://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: 127.0.0.1 (MySQL 5.7.17)
# Database: tickets
# Generation Time: 2018-07-01 14:52:06 +0000
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
	(1,'158********',3327.1200000000003,2,'支付宝'),
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
	(1,1,11,25,0,0,'2018-03-21 17:58:08'),
	(2,3,16,25,297.5,892.5,'2018-05-26 20:21:11'),
	(3,1,12,25,84.25,252.75,'2018-06-28 21:11:39');

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
	(1,'hehuixian','123456');

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
  `score` int(11) DEFAULT NULL COMMENT '订单评分',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `order_form` WRITE;
/*!40000 ALTER TABLE `order_form` DISABLE KEYS */;

INSERT INTO `order_form` (`id`, `purchase_type`, `project_price_id`, `seat_number`, `state`, `user_id`, `discount`, `total_price`, `create_time`, `coupon_id`, `check_in`, `seat_list`, `score`)
VALUES
	(1,0,7,1,1,2,100,100,'2018-03-09 12:54:25',0,0,'1_1',-1),
	(2,1,9,2,2,2,98,98,'2018-03-19 00:34:55',0,0,NULL,-1),
	(3,0,9,3,1,2,98,147,'2018-03-20 23:04:24',0,0,'15_7,15_8,15_9',-1),
	(7,1,10,1,1,4,98,3810.24,'2018-03-21 00:35:31',0,0,NULL,-1),
	(8,1,11,1,2,4,98,1850.24,'2018-03-21 01:19:39',0,0,'1_1',-1),
	(9,0,9,3,2,4,90,135,'2018-03-21 14:04:11',0,0,'1_12,1_13,1_11',-1),
	(10,1,12,2,4,8,98,1740.48,'2018-03-21 18:17:21',0,0,'1_1,1_2',-1),
	(11,0,12,4,4,0,100,3552,'2018-03-22 00:18:05',0,1,'3_5,3_6,4_5,4_6',-1),
	(12,1,12,2,4,8,85,1509.6,'2018-03-22 00:28:36',0,1,'1_3,1_4',-1),
	(13,0,9,5,4,0,100,250,'2018-03-22 14:04:41',0,1,'1_6,1_7,1_8,1_9,1_10',-1),
	(14,0,11,2,4,9,98,3700.48,'2018-03-22 14:40:54',0,0,'1_4,1_5',-1),
	(15,0,14,2,2,8,85,340,'2018-03-22 15:33:59',0,0,'1_1,1_2',-1),
	(16,1,13,4,4,8,85,1020,'2018-03-22 15:34:28',0,0,'1_1,1_2,1_3,1_4',-1),
	(17,0,14,2,1,8,85,340,'2018-03-22 15:35:00',0,0,'1_3,1_4',-1),
	(18,0,12,2,4,0,100,1776,'2018-03-22 15:37:46',0,1,'3_9,3_10',-1),
	(19,0,10,2,1,2,98,7620.48,'2018-06-29 00:09:06',0,0,'2_8,2_7',-1),
	(20,0,12,2,4,2,98,1740.48,'2018-06-29 00:09:27',0,0,'1_7,1_8',5),
	(21,0,12,2,4,2,90,1598.4,'2018-06-29 15:50:14',0,0,'1_9,1_10',5);

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
	(15,2,15,1776,'2018-03-22 15:37:46',0),
	(16,2,15,1740.48,'2018-06-29 00:09:34',1),
	(17,2,15,1598.4,'2018-06-29 15:50:21',1);

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
	(11,'开学典礼','2018-03-03 13:55:36','2018-03-04 13:55:36','知识讲座','南京大学信任校长吕建发表新学期开学演讲，请所有同学务必参加。南京大学信任校长吕建发表新学期开学演讲，请所有同学务必参加。南京大学信任校长吕建发表新学期开学演讲，请所有同学务必参加。南京大学信任校长吕建发表新学期开学演讲，请所有同学务必参加。',1,NULL),
	(12,'云计算、大数据和人工智能','2018-04-01 10:00:00','2018-04-02 17:30:00','知识讲座','人工智能、大数据已成为当下各行业最热门的话题。云计算，大数据，和人工智能，最近火的不行不行的词汇，似乎不相同，但又似乎相互关联，到底是什么样的关系呢？其实他们本没有什么关系，各自活在不同的世界里，然而随着互联网的发展，相互纠葛在了一起。',1,NULL),
	(15,'第八届Dota2国际邀请赛','2018-08-01 00:00:00','2018-08-10 00:00:00','电子竞技','DOTA2国际邀请赛，The International DOTA2 Championships。简称Ti，创立于2011年，是一个全球性的电子竞技赛事，每年一届，由ValveCorporation（V社）主办,奖杯为V社特制冠军盾牌，每一届冠军队伍及人员将记录在游戏泉水的冠军盾中。',2,'/poster/pic/15.jpg'),
	(16,'J2EE讲座','2018-03-23 00:00:00','2018-03-24 00:00:00','知识讲座','J2EE',3,NULL),
	(18,'麦克白','2018-06-19 14:00:00','2018-06-19 16:00:00','话剧歌剧','麦克白将军（迈克尔·法斯宾德 Michael Fassbender 饰）是苏格兰国王邓肯（大卫·休里斯 David Thewlis 饰）的表弟，在外抵御外敌平反谋乱，战功赫赫，某日归国途中，遇见了三个女巫，女巫预言麦克白将会加官进爵，此言令麦克白心中蠢蠢欲动。然而，最终将要继承王位的，却是班柯将军（帕迪·康斯戴恩 Paddy Considine 饰）的儿子。',4,'/poster/pic/18.jpg'),
	(19,'李尔王','2018-03-28 14:00:00','2018-03-28 16:00:00','话剧歌剧','李尔王》是莎士比亚创作的戏剧，是其四大悲剧之一。故事来源于英国的一个古老传说，故事本身大约发生在8世纪左右。后在英国编成了许多戏剧，现存的戏剧除莎士比亚外，还有一个更早的无名氏作品，一般认为莎士比亚的李尔王是改编此剧而创作的。故事讲述了年事已高的国王李尔王退位后，被大女儿和二女儿赶到荒郊野外，成为法兰西皇后的三女儿率军救父，却被杀死，李尔王伤心地死在她身旁。',4,'/poster/pic/19.jpeg'),
	(20,'罗密欧与朱丽叶','2018-05-28 16:00:00','2018-05-28 18:43:40','话剧歌剧','《罗密欧与朱丽叶》（Romeo and Juliet）是英国剧作家威廉·莎士比亚创作的戏剧，该剧讲述意大利贵族凯普莱特女儿朱丽叶与蒙太古的儿子罗密欧诚挚相爱，誓言相依，但因两家世代为仇而受到阻挠。《罗密欧与朱丽叶》虽是一出悲剧，但两个青年男、女主人公的爱情本身却不可悲。',4,'/poster/pic/20.jpg'),
	(21,'时光旅行局意外事件','2018-06-24 14:45:51','2018-06-24 16:46:02','话剧歌剧','时光旅行局意外事件',4,'/poster/pic/21.jpg'),
	(22,'猫','2018-06-30 18:30:12','2018-06-30 16:00:02','话剧歌剧','原版音乐剧《猫》由作曲大师安德鲁.劳埃德.韦伯创作的音乐剧，根据英国著名诗人、剧作家T.S.艾略特的诗集改编。自1981年5月11日，于伦敦西区新伦敦剧院（New London Theatre）首演后，《猫》不仅屡次刷新驻演场次记录，更是横扫托尼奖、奥利弗奖和格莱美奖等世界奖项。音乐剧中的不朽名曲《回忆(Memory)》更是以委婉深情的词曲家喻户晓，“月光女神”莎拉•布莱曼、“西区音乐剧女王”伊莲•佩姬等名家唱将都曾演绎过。',4,'/poster/pic/22.jpg'),
	(23,'谋杀启事','2018-07-22 10:00:00','2018-07-22 12:00:00','话剧歌剧','在这个特别的星期五，一则启事，一则谋杀的启事，预告着将在小派达克斯家将有一场神秘的谋杀。连女主人莉蒂小姐也被这一则意外的启事困惑，她忠实的女伴，调皮的侄子、尖刻的侄女、文静的女房客和神经兮兮的厨娘没有一个承认与这则惊人的启事有关。那么是谁发布这惊人的消息？又为什么要发布呢？为了有趣？为了刺激？还是……暗涌着的危险？傍晚时分，好奇的人们聚集在小派达克斯家。六点半钟声响起，灯忽然熄灭。人们在黑暗中尖叫着，颤抖着，兴奋地等待着一场刺激游戏的开始……',4,'/poster/pic/23.jpg'),
	(24,'潜伏','2018-07-13 13:00:54','2018-07-13 15:49:08','话剧歌剧',' 本场演出为南京市文化消费政府补贴剧目，所有观众购票均可享受30%政府补贴，单张票最 高补贴150元。洪剑涛、包文婧主演•明星版话剧《潜伏》《潜伏》是当代著名作家龙一先生的代表作。',4,'/poster/pic/24.jpg'),
	(25,'原告证人','2018-07-03 20:00:33','2018-07-03 22:00:00','话剧歌剧','本剧由阿加莎·克里斯蒂亲自执笔，改编自她的同名短篇小说，并再一次将不同与小说的全新结尾带上了舞台。\r\n1953年在伦敦连续上演468场后，继而被搬上百老汇舞台上演645场并获得纽约剧评家协会最 佳外国戏剧奖。',4,'/poster/pic/25.jpg'),
	(26,'长腿叔叔','2018-07-08 17:00:00','2018-07-08 19:00:00','话剧歌剧','乔若莎是孤儿院里年纪最 大的孩子，她总是怀有强烈的好奇，内心深处一直渴望孤儿院以外世界。突然有一天，她迎来了人生的巨大转折。一位神秘男士愿意资助她上大学，这是简直就是自己梦寐以求的机会!但这位男士一切信息都是严格对外保密的，他们唯一的联结就是乔若莎每月要给他写封信。对神秘男士一无所知的乔若莎给他起了\"长腿叔叔\"的昵称，每个月的一封信也见证着乔若莎的成长，从不谙世事的女孩成为性格爽朗坚毅的独立女性。',4,'/poster/pic/26.jpg'),
	(27,'月亮和六便士','2018-05-19 14:00:35','2018-05-19 16:30:47','话剧歌剧','大约一个世纪以前，英国作家毛姆写下《月亮和六便士》。为什么要叫做月亮和六便士呢？当年泰晤士报的一篇书评曾写道：“人们在仰望月亮时常常忘了脚下的六便士”',4,'/poster/pic/27.jpg'),
	(28,'Kinky Boots长靴皇后','2018-07-10 09:00:00','2018-07-10 11:30:06','话剧歌剧','百老汇当红原版音乐剧《长靴皇后Kinky Boots》取材于真实发生的故事。男主角查理·普莱斯（Charlie）是英国一家老字号鞋店Price & Sons的第四代传人。从父亲手上接过鞋厂事业后一直举步维艰的经营着家族产业。正在查理一筹莫展之际，他邂逅了才华横溢的酒吧歌手“变装皇后”劳拉 (Lola) 并发现这类人群中蕴藏着的巨大商机。《长靴皇后》题材新颖，以轻松欢快的旋律诠释友谊和信念的力量。剧中华美艳丽的服饰将观众从位于北安普顿的一间小小皮鞋作坊，一步步带至米兰星光熠熠的T台。',4,'/poster/pic/28.jpg'),
	(29,'湖人VS凯尔特人','2018-06-28 19:00:00','2018-06-28 21:30:30','体育竞赛','NBA湖人队对阵凯尔特人队',4,'/poster/pic/29.jpg'),
	(30,'湖人VS骑士','2018-07-14 19:00:00','2018-07-19 21:00:00','体育竞赛','NBA湖人队对阵骑士队',4,''),
	(31,'湖人VS勇士','2018-05-25 19:30:30','2018-05-25 22:00:41','体育竞赛','NBA湖人队对阵勇士队',4,'/poster/pic/31.jpg'),
	(32,'湖人VS火箭','2018-07-23 16:30:32','2018-07-23 19:00:19','体育竞赛','NBA湖人队对阵火箭队',4,'/poster/pic/32.jpg'),
	(33,'RNG VS SKT','2018-07-17 13:00:07','2018-07-17 15:00:21','电子竞技','RNG对阵SKT',4,'/poster/pic/33.jpg'),
	(34,'格莱美颁奖典礼','2018-05-20 14:00:06','2018-05-20 18:00:16','典礼','格莱美颁奖贷典礼',4,'/poster/pic/34.jpeg'),
	(35,'奥斯卡颁奖典礼','2018-07-15 14:00:46','2018-07-15 18:05:34','典礼','奥斯卡颁奖典礼',4,'/poster/pic/35.jpg'),
	(36,'Dota总决赛颁奖典礼','2018-06-01 18:00:27','2018-06-01 22:06:43','典礼','Dota总决赛颁奖典礼',4,''),
	(37,'中国龙 VS 世界虫','2018-07-11 16:00:49','2018-07-11 18:00:03','电子竞技','LOL中国对对阵世界队',4,''),
	(38,'汪峰演唱会','2018-06-16 20:00:12','2018-06-16 22:09:33','演唱会','汪峰演唱会',4,'/poster/pic/38.jpg'),
	(39,'吴亦凡演唱会','2018-06-13 18:00:07','2018-06-13 20:10:22','演唱会','吴亦凡演唱会',4,'/poster/pic/39.jpg'),
	(40,'王菲演唱会','2018-07-20 19:11:09','2018-07-20 21:00:22','演唱会','王菲演唱会',4,'/poster/pic/40.jpg'),
	(41,'鹿晗演唱会','2018-07-18 19:12:03','2018-07-18 21:12:11','演唱会','鹿晗演唱会',4,'/poster/pic/41.jpg'),
	(42,'萧敬腾演唱会','2018-07-07 19:12:54','2018-07-07 21:13:06','演唱会','萧敬腾演唱会',4,'/poster/pic/42.jpg'),
	(43,'周杰伦演唱会','2018-07-24 20:14:15','2018-07-24 22:14:20','演唱会','周杰伦演唱会',4,'/poster/pic/43.jpg'),
	(44,'五月天演唱会','2018-06-18 17:15:19','2018-06-18 19:15:01','演唱会','五月天演唱会',4,'/poster/pic/44.jpg'),
	(45,'许嵩演唱会','2018-07-17 18:15:54','2018-07-17 20:16:08','演唱会','许嵩演唱会',4,'/poster/pic/45.jpg'),
	(46,'lady gaga演唱会','2018-06-12 14:17:06','2018-06-12 16:17:19','演唱会','lady gaga演唱会',4,'/poster/pic/46.jpg'),
	(47,'王者荣耀年度总决赛','2018-07-27 19:00:02','2018-07-27 21:19:16','电子竞技','王者荣耀年度总决赛',4,'/poster/pic/47.jpg'),
	(48,'南京大学毕业典礼','2018-06-21 14:00:46','2018-06-21 16:20:08','典礼','南京大学毕业典礼',4,''),
	(49,'森林狼VS奇才','2018-06-06 18:21:14','2018-06-06 21:21:26','体育竞赛','NBA森林狼队对阵奇才队',4,'/poster/pic/49.jpg'),
	(50,'NBA全明星赛','2018-07-20 09:22:15','2018-07-20 14:22:01','体育竞赛','NBA全明星赛，一年一度不容错过',4,'/poster/pic/50.jpg'),
	(51,'LOL全明星赛','2018-05-25 19:00:29','2018-05-25 21:23:41','电子竞技','LOL全明星赛，精彩对决不容错过',4,'/poster/pic/51.jpg'),
	(52,'EDG.M VS QG','2018-07-24 16:25:49','2018-07-24 18:25:59','电子竞技','王者荣耀EDG对阵QG',4,''),
	(53,'东南大学毕业典礼','2018-06-22 18:00:13','2018-06-22 20:27:23','典礼','东南大学毕业典礼',4,'/poster/pic/53.jpg'),
	(54,'UCLA毕业典礼','2018-07-01 16:00:50','2018-07-01 18:00:01','典礼','UCLA毕业典礼',4,'/poster/pic/54.jpg'),
	(55,'UCB毕业典礼','2018-07-05 18:30:28','2018-07-05 20:28:39','典礼','UCB毕业典礼',4,'/poster/pic/55.jpg'),
	(56,'【上海站】暗恋桃花源','2018-06-30 00:00:00','2018-07-02 00:00:00','话剧歌剧','“暗恋”和“桃花源”是两个不相干的剧组，他们都与剧场签定了当晚彩排的合约，双方争执不下，谁也不肯相让。由于演出在即，他们不得不同时在剧场中彩排，遂成就了一出古今悲喜交错的舞台奇观。',5,'/poster/56/56.jpg');

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
	(7,11,'VIP座位',50,100,10,5),
	(8,11,'普通座位',100,50,10,10),
	(9,12,'统一座位',225,50,15,15),
	(10,15,'贵宾座',50,3888,5,10),
	(11,15,'一等座',64,1888,8,8),
	(12,15,'二等座',100,888,10,10),
	(13,16,'一等座',25,300,5,5),
	(14,16,'二等座',100,200,10,10),
	(471,18,'VIP座位',345,4956,15,23),
	(472,18,'一等座',760,4513,19,40),
	(473,18,'二等座',2262,3535,39,58),
	(474,18,'普通座',6633,3035,40,165),
	(475,19,'VIP座位',594,4118,18,33),
	(476,19,'一等座',1421,4067,29,49),
	(477,19,'二等座',3080,3418,40,77),
	(478,19,'普通座',4905,2918,40,122),
	(479,20,'VIP座位',448,3927,14,32),
	(480,20,'一等座',792,3122,22,36),
	(481,20,'二等座',1302,3093,31,42),
	(482,20,'普通座',7458,2593,40,186),
	(483,21,'VIP座位',130,3023,5,26),
	(484,21,'一等座',1219,1994,23,53),
	(485,21,'二等座',1740,1540,30,58),
	(486,21,'普通座',6911,1040,40,172),
	(487,22,'VIP座位',54,4504,3,18),
	(488,22,'一等座',63,3691,3,21),
	(489,22,'二等座',414,2920,9,46),
	(490,22,'普通座',9469,2420,40,236),
	(491,23,'VIP座位',252,4227,14,18),
	(492,23,'一等座',722,3141,38,19),
	(493,23,'二等座',1794,2145,46,39),
	(494,23,'普通座',7232,1645,40,180),
	(495,24,'VIP座位',527,4204,17,31),
	(496,24,'一等座',1792,4184,32,56),
	(497,24,'二等座',4100,3385,50,82),
	(498,24,'普通座',3581,2885,40,89),
	(499,25,'VIP座位',294,4018,14,21),
	(500,25,'一等座',798,2527,19,42),
	(501,25,'二等座',3525,1619,47,75),
	(502,25,'普通座',5383,1119,40,134),
	(503,26,'VIP座位',136,3279,8,17),
	(504,26,'一等座',504,1838,28,18),
	(505,26,'二等座',2236,867,52,43),
	(506,26,'普通座',7124,367,40,178),
	(507,27,'VIP座位',480,3817,15,32),
	(508,27,'一等座',660,2621,15,44),
	(509,27,'二等座',1541,1787,23,67),
	(510,27,'普通座',7319,1287,40,182),
	(511,28,'VIP座位',16,4443,1,16),
	(512,28,'一等座',1000,3759,25,40),
	(513,28,'二等座',2352,3262,49,48),
	(514,28,'普通座',6632,2762,40,165),
	(515,29,'VIP座位',432,4566,18,24),
	(516,29,'一等座',675,3620,25,27),
	(517,29,'二等座',1680,2749,48,35),
	(518,29,'普通座',7213,2249,40,180),
	(519,30,'VIP座位',270,4408,18,15),
	(520,30,'一等座',595,3222,35,17),
	(521,30,'二等座',1260,3038,35,36),
	(522,30,'普通座',7875,2538,40,196),
	(523,31,'VIP座位',133,3735,7,19),
	(524,31,'一等座',253,2801,11,23),
	(525,31,'二等座',602,2006,14,43),
	(526,31,'普通座',9012,1506,40,225),
	(527,32,'VIP座位',225,4043,9,25),
	(528,32,'一等座',1410,3322,30,47),
	(529,32,'二等座',3498,2872,53,66),
	(530,32,'普通座',4867,2372,40,121),
	(531,33,'VIP座位',352,3020,11,32),
	(532,33,'一等座',624,3005,13,48),
	(533,33,'二等座',2310,2755,30,77),
	(534,33,'普通座',6714,2255,40,167),
	(535,34,'VIP座位',285,4910,19,15),
	(536,34,'一等座',304,3643,19,16),
	(537,34,'二等座',667,3242,23,29),
	(538,34,'普通座',8744,2742,40,218),
	(539,35,'VIP座位',224,3453,8,28),
	(540,35,'一等座',1036,2997,28,37),
	(541,35,'二等座',3618,2618,54,67),
	(542,35,'普通座',5122,2118,40,128),
	(543,36,'VIP座位',230,4766,10,23),
	(544,36,'一等座',612,3345,17,36),
	(545,36,'二等座',2709,3322,43,63),
	(546,36,'普通座',6449,2822,40,161),
	(547,37,'VIP座位',228,4409,12,19),
	(548,37,'一等座',825,3315,25,33),
	(549,37,'二等座',2484,2837,46,54),
	(550,37,'普通座',6463,2337,40,161),
	(551,38,'VIP座位',34,4997,1,34),
	(552,38,'一等座',340,4739,10,34),
	(553,38,'二等座',646,4531,17,38),
	(554,38,'普通座',8980,4031,40,224),
	(555,39,'VIP座位',189,4668,9,21),
	(556,39,'一等座',598,3830,26,23),
	(557,39,'二等座',2014,3170,53,38),
	(558,39,'普通座',7199,2670,40,179),
	(559,40,'VIP座位',140,4898,5,28),
	(560,40,'一等座',448,4522,14,32),
	(561,40,'二等座',1081,3589,23,47),
	(562,40,'普通座',8331,3089,40,208),
	(563,41,'VIP座位',20,3717,1,20),
	(564,41,'一等座',612,3504,18,34),
	(565,41,'二等座',1363,3297,29,47),
	(566,41,'普通座',8005,2797,40,200),
	(567,42,'VIP座位',594,4320,18,33),
	(568,42,'一等座',2142,3805,42,51),
	(569,42,'二等座',2948,3140,44,67),
	(570,42,'普通座',4316,2640,40,107),
	(571,43,'VIP座位',130,4794,5,26),
	(572,43,'一等座',371,3333,7,53),
	(573,43,'二等座',1079,3040,13,83),
	(574,43,'普通座',8420,2540,40,210),
	(575,44,'VIP座位',510,4182,15,34),
	(576,44,'一等座',1419,3419,33,43),
	(577,44,'二等座',3074,2737,58,53),
	(578,44,'普通座',4997,2237,40,124),
	(579,45,'VIP座位',168,3281,8,21),
	(580,45,'一等座',850,2633,17,50),
	(581,45,'二等座',2812,2510,37,76),
	(582,45,'普通座',6170,2010,40,154),
	(583,46,'VIP座位',434,4316,14,31),
	(584,46,'一等座',2124,3959,36,59),
	(585,46,'二等座',2560,3924,40,64),
	(586,46,'普通座',4882,3424,40,122),
	(587,47,'VIP座位',646,3827,19,34),
	(588,47,'一等座',975,2678,25,39),
	(589,47,'二等座',1892,1933,43,44),
	(590,47,'普通座',6487,1433,40,162),
	(591,48,'VIP座位',90,3267,3,30),
	(592,48,'一等座',450,2582,9,50),
	(593,48,'二等座',708,1746,12,59),
	(594,48,'普通座',8752,1246,40,218),
	(595,49,'VIP座位',336,3805,12,28),
	(596,49,'一等座',1160,3388,29,40),
	(597,49,'二等座',2214,3364,41,54),
	(598,49,'普通座',6290,2864,40,157),
	(599,50,'VIP座位',186,3339,6,31),
	(600,50,'一等座',833,2312,17,49),
	(601,50,'二等座',1890,2116,30,63),
	(602,50,'普通座',7091,1616,40,177),
	(603,51,'VIP座位',225,4105,9,25),
	(604,51,'一等座',275,4023,11,25),
	(605,51,'二等座',2183,3551,37,59),
	(606,51,'普通座',7317,3051,40,182),
	(607,52,'VIP座位',15,3975,1,15),
	(608,52,'一等座',44,2921,1,44),
	(609,52,'二等座',378,2088,6,63),
	(610,52,'普通座',9563,1588,40,239),
	(611,53,'VIP座位',270,4784,10,27),
	(612,53,'一等座',663,4626,17,39),
	(613,53,'二等座',1681,3641,41,41),
	(614,53,'普通座',7386,3141,40,184),
	(615,54,'VIP座位',243,3430,9,27),
	(616,54,'一等座',1150,2220,23,50),
	(617,54,'二等座',3050,2100,50,61),
	(618,54,'普通座',5557,1600,40,138),
	(619,55,'VIP座位',108,3571,6,18),
	(620,55,'一等座',1140,2326,30,38),
	(621,55,'二等座',2880,1683,40,72),
	(622,55,'普通座',5872,1183,40,146),
	(623,56,'vip座',200,418,10,20),
	(624,56,'普通座',200,218,10,20);

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
	(2,'hx36w35@163.com','Sigma','123456',5,3358,'2018-02-28 00:00:00'),
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
	(1,'东南大学','735913885@qq.com','Q2p2eyd','南京 鼓楼区',500,'2018-03-09 12:00:00',1,1),
	(2,'钥匙球馆','hx36w35@163.com','LGG0wvs','南通 如皋',20000,'2018-03-20 23:48:47',1,1),
	(3,'南京大学','151250060@smail.nju.edu.cn','1QEZxXO','南京 鼓楼校区',2000,'2018-03-22 15:30:44',1,1),
	(4,'斯台普斯中心','151250002@smail.nju.edu.cn','1QEZxXO','杭州 西湖旁边',20000,'2018-03-28 02:40:01',1,1),
	(5,'保利大剧院','151250051@smail.nju.edu.cn','123AjBN','上海 嘉定',1200,'2018-04-03 00:00:00',1,1);

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
