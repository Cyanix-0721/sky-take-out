-- --------------------------------------------------------
-- 主机:                           localhost
-- 服务器版本:                        8.4.0 - MySQL Community Server - GPL
-- 服务器操作系统:                      Linux
-- HeidiSQL 版本:                  12.6.0.6765
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- 导出 sky_take_out 的数据库结构
DROP DATABASE IF EXISTS `sky_take_out`;
CREATE DATABASE IF NOT EXISTS `sky_take_out` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `sky_take_out`;

-- 导出  表 sky_take_out.address_book 结构
DROP TABLE IF EXISTS `address_book`;
CREATE TABLE IF NOT EXISTS `address_book` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint NOT NULL COMMENT '用户id',
  `consignee` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '收货人',
  `sex` varchar(2) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '性别',
  `phone` varchar(11) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '手机号',
  `province_code` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '省级区划编号',
  `province_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '省级名称',
  `city_code` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '市级区划编号',
  `city_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '市级名称',
  `district_code` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '区级区划编号',
  `district_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '区级名称',
  `detail` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '详细地址',
  `label` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '标签',
  `is_default` tinyint(1) NOT NULL DEFAULT '0' COMMENT '默认 0 否 1是',
  `version` int unsigned DEFAULT '1' COMMENT '乐观锁版本号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='地址簿';

-- 正在导出表  sky_take_out.address_book 的数据：~0 rows (大约)
DELETE FROM `address_book`;

-- 导出  表 sky_take_out.category 结构
DROP TABLE IF EXISTS `category`;
CREATE TABLE IF NOT EXISTS `category` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `type` int DEFAULT NULL COMMENT '类型   1 菜品分类 2 套餐分类',
  `name` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '分类名称',
  `sort` int NOT NULL DEFAULT '0' COMMENT '顺序',
  `status` int DEFAULT NULL COMMENT '分类状态 0:禁用，1:启用',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `create_user` bigint DEFAULT NULL COMMENT '创建人',
  `update_user` bigint DEFAULT NULL COMMENT '修改人',
  `version` int unsigned DEFAULT '1' COMMENT '乐观锁版本号',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_category_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='菜品及套餐分类';

-- 正在导出表  sky_take_out.category 的数据：~10 rows (大约)
DELETE FROM `category`;
INSERT INTO `category` (`id`, `type`, `name`, `sort`, `status`, `create_time`, `update_time`, `create_user`, `update_user`, `version`) VALUES
	(11, 1, '酒水饮料', 10, 1, '2022-06-09 22:09:18', '2022-06-09 22:09:18', 1, 1, 1),
	(12, 1, '传统主食', 9, 1, '2022-06-09 22:09:32', '2022-06-09 22:18:53', 1, 1, 1),
	(13, 2, '人气套餐', 12, 1, '2022-06-09 22:11:38', '2022-06-10 11:04:40', 1, 1, 1),
	(15, 2, '商务套餐', 13, 1, '2022-06-09 22:14:10', '2022-06-10 11:04:48', 1, 1, 1),
	(16, 1, '蜀味烤鱼', 4, 1, '2022-06-09 22:15:37', '2022-08-31 14:27:25', 1, 1, 1),
	(17, 1, '蜀味牛蛙', 5, 1, '2022-06-09 22:16:14', '2022-08-31 14:39:44', 1, 1, 1),
	(18, 1, '特色蒸菜', 6, 1, '2022-06-09 22:17:42', '2022-06-09 22:17:42', 1, 1, 1),
	(19, 1, '新鲜时蔬', 7, 1, '2022-06-09 22:18:12', '2022-06-09 22:18:28', 1, 1, 1),
	(20, 1, '水煮鱼', 8, 1, '2022-06-09 22:22:29', '2022-06-09 22:23:45', 1, 1, 1),
	(21, 1, '汤类', 11, 1, '2022-06-10 10:51:47', '2022-06-10 10:51:47', 1, 1, 1);

-- 导出  表 sky_take_out.dish 结构
DROP TABLE IF EXISTS `dish`;
CREATE TABLE IF NOT EXISTS `dish` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '菜品名称',
  `category_id` bigint NOT NULL COMMENT '菜品分类id',
  `price` decimal(10,2) DEFAULT NULL COMMENT '菜品价格',
  `image` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '图片',
  `description` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '描述信息',
  `status` int DEFAULT '1' COMMENT '0 停售 1 起售',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `create_user` bigint DEFAULT NULL COMMENT '创建人',
  `update_user` bigint DEFAULT NULL COMMENT '修改人',
  `version` int unsigned DEFAULT '1' COMMENT '乐观锁版本号',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_dish_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=70 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='菜品';

-- 正在导出表  sky_take_out.dish 的数据：~24 rows (大约)
DELETE FROM `dish`;
INSERT INTO `dish` (`id`, `name`, `category_id`, `price`, `image`, `description`, `status`, `create_time`, `update_time`, `create_user`, `update_user`, `version`) VALUES
	(46, '王老吉', 11, 6.00, 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/41bfcacf-7ad4-4927-8b26-df366553a94c.png', '', 1, '2022-06-09 22:40:47', '2022-06-09 22:40:47', 1, 1, 1),
	(47, '北冰洋', 11, 4.00, 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/4451d4be-89a2-4939-9c69-3a87151cb979.png', '还是小时候的味道', 1, '2022-06-10 09:18:49', '2022-06-10 09:18:49', 1, 1, 1),
	(48, '雪花啤酒', 11, 4.00, 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/bf8cbfc1-04d2-40e8-9826-061ee41ab87c.png', '', 1, '2022-06-10 09:22:54', '2022-06-10 09:22:54', 1, 1, 1),
	(49, '米饭', 12, 2.00, 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/76752350-2121-44d2-b477-10791c23a8ec.png', '精选五常大米', 1, '2022-06-10 09:30:17', '2022-06-10 09:30:17', 1, 1, 1),
	(50, '馒头', 12, 1.00, 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/475cc599-8661-4899-8f9e-121dd8ef7d02.png', '优质面粉', 1, '2022-06-10 09:34:28', '2022-06-10 09:34:28', 1, 1, 1),
	(51, '老坛酸菜鱼', 20, 56.00, 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/4a9cefba-6a74-467e-9fde-6e687ea725d7.png', '原料：汤，草鱼，酸菜', 1, '2022-06-10 09:40:51', '2022-06-10 09:40:51', 1, 1, 1),
	(52, '经典酸菜鮰鱼', 20, 66.00, 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/5260ff39-986c-4a97-8850-2ec8c7583efc.png', '原料：酸菜，江团，鮰鱼', 1, '2022-06-10 09:46:02', '2022-06-10 09:46:02', 1, 1, 1),
	(53, '蜀味水煮草鱼', 20, 38.00, 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/a6953d5a-4c18-4b30-9319-4926ee77261f.png', '原料：草鱼，汤', 1, '2022-06-10 09:48:37', '2022-06-10 09:48:37', 1, 1, 1),
	(54, '清炒小油菜', 19, 18.00, 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/3613d38e-5614-41c2-90ed-ff175bf50716.png', '原料：小油菜', 1, '2022-06-10 09:51:46', '2022-06-10 09:51:46', 1, 1, 1),
	(55, '蒜蓉娃娃菜', 19, 18.00, 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/4879ed66-3860-4b28-ba14-306ac025fdec.png', '原料：蒜，娃娃菜', 1, '2022-06-10 09:53:37', '2022-06-10 09:53:37', 1, 1, 1),
	(56, '清炒西兰花', 19, 18.00, 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/e9ec4ba4-4b22-4fc8-9be0-4946e6aeb937.png', '原料：西兰花', 1, '2022-06-10 09:55:44', '2022-06-10 09:55:44', 1, 1, 1),
	(57, '炝炒圆白菜', 19, 18.00, 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/22f59feb-0d44-430e-a6cd-6a49f27453ca.png', '原料：圆白菜', 1, '2022-06-10 09:58:35', '2022-06-10 09:58:35', 1, 1, 1),
	(58, '清蒸鲈鱼', 18, 98.00, 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/c18b5c67-3b71-466c-a75a-e63c6449f21c.png', '原料：鲈鱼', 1, '2022-06-10 10:12:28', '2022-06-10 10:12:28', 1, 1, 1),
	(59, '东坡肘子', 18, 138.00, 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/a80a4b8c-c93e-4f43-ac8a-856b0d5cc451.png', '原料：猪肘棒', 1, '2022-06-10 10:24:03', '2022-06-10 10:24:03', 1, 1, 1),
	(60, '梅菜扣肉', 18, 58.00, 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/6080b118-e30a-4577-aab4-45042e3f88be.png', '原料：猪肉，梅菜', 1, '2022-06-10 10:26:03', '2022-06-10 10:26:03', 1, 1, 1),
	(61, '剁椒鱼头', 18, 66.00, 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/13da832f-ef2c-484d-8370-5934a1045a06.png', '原料：鲢鱼，剁椒', 1, '2022-06-10 10:28:54', '2022-06-10 10:28:54', 1, 1, 1),
	(62, '金汤酸菜牛蛙', 17, 88.00, 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/7694a5d8-7938-4e9d-8b9e-2075983a2e38.png', '原料：鲜活牛蛙，酸菜', 1, '2022-06-10 10:33:05', '2022-06-10 10:33:05', 1, 1, 1),
	(63, '香锅牛蛙', 17, 88.00, 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/f5ac8455-4793-450c-97ba-173795c34626.png', '配料：鲜活牛蛙，莲藕，青笋', 1, '2022-06-10 10:35:40', '2022-06-10 10:35:40', 1, 1, 1),
	(64, '馋嘴牛蛙', 17, 88.00, 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/7a55b845-1f2b-41fa-9486-76d187ee9ee1.png', '配料：鲜活牛蛙，丝瓜，黄豆芽', 1, '2022-06-10 10:37:52', '2022-06-10 10:37:52', 1, 1, 1),
	(65, '草鱼2斤', 16, 68.00, 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/b544d3ba-a1ae-4d20-a860-81cb5dec9e03.png', '原料：草鱼，黄豆芽，莲藕', 1, '2022-06-10 10:41:08', '2022-06-10 10:41:08', 1, 1, 1),
	(66, '江团鱼2斤', 16, 119.00, 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/a101a1e9-8f8b-47b2-afa4-1abd47ea0a87.png', '配料：江团鱼，黄豆芽，莲藕', 1, '2022-06-10 10:42:42', '2022-06-10 10:42:42', 1, 1, 1),
	(67, '鮰鱼2斤', 16, 72.00, 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/8cfcc576-4b66-4a09-ac68-ad5b273c2590.png', '原料：鮰鱼，黄豆芽，莲藕', 1, '2022-06-10 10:43:56', '2022-06-10 10:43:56', 1, 1, 1),
	(68, '鸡蛋汤', 21, 4.00, 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/c09a0ee8-9d19-428d-81b9-746221824113.png', '配料：鸡蛋，紫菜', 1, '2022-06-10 10:54:25', '2022-06-10 10:54:25', 1, 1, 1),
	(69, '平菇豆腐汤', 21, 6.00, 'https://sky-itcast.oss-cn-beijing.aliyuncs.com/16d0a3d6-2253-4cfc-9b49-bf7bd9eb2ad2.png', '配料：豆腐，平菇', 1, '2022-06-10 10:55:02', '2022-06-10 10:55:02', 1, 1, 1);

-- 导出  表 sky_take_out.dish_flavor 结构
DROP TABLE IF EXISTS `dish_flavor`;
CREATE TABLE IF NOT EXISTS `dish_flavor` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `dish_id` bigint NOT NULL COMMENT '菜品',
  `name` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '口味名称',
  `value` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '口味数据list',
  `version` int unsigned DEFAULT '1' COMMENT '乐观锁版本号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=104 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='菜品口味关系表';

-- 正在导出表  sky_take_out.dish_flavor 的数据：~24 rows (大约)
DELETE FROM `dish_flavor`;
INSERT INTO `dish_flavor` (`id`, `dish_id`, `name`, `value`, `version`) VALUES
	(40, 10, '甜味', '["无糖","少糖","半糖","多糖","全糖"]', 1),
	(41, 7, '忌口', '["不要葱","不要蒜","不要香菜","不要辣"]', 1),
	(42, 7, '温度', '["热饮","常温","去冰","少冰","多冰"]', 1),
	(45, 6, '忌口', '["不要葱","不要蒜","不要香菜","不要辣"]', 1),
	(46, 6, '辣度', '["不辣","微辣","中辣","重辣"]', 1),
	(47, 5, '辣度', '["不辣","微辣","中辣","重辣"]', 1),
	(48, 5, '甜味', '["无糖","少糖","半糖","多糖","全糖"]', 1),
	(49, 2, '甜味', '["无糖","少糖","半糖","多糖","全糖"]', 1),
	(50, 4, '甜味', '["无糖","少糖","半糖","多糖","全糖"]', 1),
	(51, 3, '甜味', '["无糖","少糖","半糖","多糖","全糖"]', 1),
	(52, 3, '忌口', '["不要葱","不要蒜","不要香菜","不要辣"]', 1),
	(86, 52, '忌口', '["不要葱","不要蒜","不要香菜","不要辣"]', 1),
	(87, 52, '辣度', '["不辣","微辣","中辣","重辣"]', 1),
	(88, 51, '忌口', '["不要葱","不要蒜","不要香菜","不要辣"]', 1),
	(89, 51, '辣度', '["不辣","微辣","中辣","重辣"]', 1),
	(92, 53, '忌口', '["不要葱","不要蒜","不要香菜","不要辣"]', 1),
	(93, 53, '辣度', '["不辣","微辣","中辣","重辣"]', 1),
	(94, 54, '忌口', '["不要葱","不要蒜","不要香菜"]', 1),
	(95, 56, '忌口', '["不要葱","不要蒜","不要香菜","不要辣"]', 1),
	(96, 57, '忌口', '["不要葱","不要蒜","不要香菜","不要辣"]', 1),
	(97, 60, '忌口', '["不要葱","不要蒜","不要香菜","不要辣"]', 1),
	(101, 66, '辣度', '["不辣","微辣","中辣","重辣"]', 1),
	(102, 67, '辣度', '["不辣","微辣","中辣","重辣"]', 1),
	(103, 65, '辣度', '["不辣","微辣","中辣","重辣"]', 1);

-- 导出  表 sky_take_out.employee 结构
DROP TABLE IF EXISTS `employee`;
CREATE TABLE IF NOT EXISTS `employee` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '姓名',
  `username` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '用户名',
  `password` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '密码',
  `phone` varchar(11) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '手机号',
  `sex` varchar(2) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '性别',
  `id_number` varchar(18) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '身份证号',
  `status` int NOT NULL DEFAULT '1' COMMENT '状态 0:禁用，1:启用',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `create_user` bigint DEFAULT NULL COMMENT '创建人',
  `update_user` bigint DEFAULT NULL COMMENT '修改人',
  `version` int unsigned DEFAULT '1' COMMENT '乐观锁版本号',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='员工信息';

-- 正在导出表  sky_take_out.employee 的数据：~1 rows (大约)
DELETE FROM `employee`;
INSERT INTO `employee` (`id`, `name`, `username`, `password`, `phone`, `sex`, `id_number`, `status`, `create_time`, `update_time`, `create_user`, `update_user`, `version`) VALUES
	(1, '管理员', 'admin', 'e10adc3949ba59abbe56e057f20f883e', '13812312312', '1', '110101199001010047', 1, '2022-02-15 15:51:20', '2022-02-17 09:16:20', 10, 1, 1);

-- 导出  表 sky_take_out.order 结构
DROP TABLE IF EXISTS `order`;
CREATE TABLE IF NOT EXISTS `order` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `number` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '订单号',
  `status` int NOT NULL DEFAULT '1' COMMENT '订单状态 1待付款 2待接单 3已接单 4派送中 5已完成 6已取消 7退款',
  `user_id` bigint NOT NULL COMMENT '下单用户',
  `address_book_id` bigint NOT NULL COMMENT '地址id',
  `order_time` datetime NOT NULL COMMENT '下单时间',
  `checkout_time` datetime DEFAULT NULL COMMENT '结账时间',
  `pay_method` int NOT NULL DEFAULT '1' COMMENT '支付方式 1微信,2支付宝',
  `pay_status` tinyint NOT NULL DEFAULT '0' COMMENT '支付状态 0未支付 1已支付 2退款',
  `amount` decimal(10,2) NOT NULL COMMENT '实收金额',
  `remark` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '备注',
  `phone` varchar(11) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '手机号',
  `address` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '地址',
  `user_name` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '用户名称',
  `consignee` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '收货人',
  `cancel_reason` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '订单取消原因',
  `rejection_reason` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '订单拒绝原因',
  `cancel_time` datetime DEFAULT NULL COMMENT '订单取消时间',
  `estimated_delivery_time` datetime DEFAULT NULL COMMENT '预计送达时间',
  `delivery_status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '配送状态  1立即送出  0选择具体时间',
  `delivery_time` datetime DEFAULT NULL COMMENT '送达时间',
  `pack_amount` int DEFAULT NULL COMMENT '打包费',
  `tableware_number` int DEFAULT NULL COMMENT '餐具数量',
  `tableware_status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '餐具数量状态  1按餐量提供  0选择具体数量',
  `version` int unsigned DEFAULT '1' COMMENT '乐观锁版本号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='订单表';

-- 正在导出表  sky_take_out.order 的数据：~0 rows (大约)
DELETE FROM `order`;

-- 导出  表 sky_take_out.order_detail 结构
DROP TABLE IF EXISTS `order_detail`;
CREATE TABLE IF NOT EXISTS `order_detail` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '名字',
  `image` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '图片',
  `order_id` bigint NOT NULL COMMENT '订单id',
  `dish_id` bigint DEFAULT NULL COMMENT '菜品id',
  `setmeal_id` bigint DEFAULT NULL COMMENT '套餐id',
  `dish_flavor` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '口味',
  `number` int NOT NULL DEFAULT '1' COMMENT '数量',
  `amount` decimal(10,2) NOT NULL COMMENT '金额',
  `version` int unsigned DEFAULT '1' COMMENT '乐观锁版本号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='订单明细表';

-- 正在导出表  sky_take_out.order_detail 的数据：~0 rows (大约)
DELETE FROM `order_detail`;

-- 导出  表 sky_take_out.setmeal 结构
DROP TABLE IF EXISTS `setmeal`;
CREATE TABLE IF NOT EXISTS `setmeal` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `category_id` bigint NOT NULL COMMENT '菜品分类id',
  `name` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '套餐名称',
  `price` decimal(10,2) NOT NULL COMMENT '套餐价格',
  `status` int DEFAULT '1' COMMENT '售卖状态 0:停售 1:起售',
  `description` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '描述信息',
  `image` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '图片',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `create_user` bigint DEFAULT NULL COMMENT '创建人',
  `update_user` bigint DEFAULT NULL COMMENT '修改人',
  `version` int unsigned DEFAULT '1' COMMENT '乐观锁版本号',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_setmeal_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='套餐';

-- 正在导出表  sky_take_out.setmeal 的数据：~0 rows (大约)
DELETE FROM `setmeal`;

-- 导出  表 sky_take_out.setmeal_dish 结构
DROP TABLE IF EXISTS `setmeal_dish`;
CREATE TABLE IF NOT EXISTS `setmeal_dish` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `setmeal_id` bigint DEFAULT NULL COMMENT '套餐id',
  `dish_id` bigint DEFAULT NULL COMMENT '菜品id',
  `name` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '菜品名称 （冗余字段）',
  `price` decimal(10,2) DEFAULT NULL COMMENT '菜品单价（冗余字段）',
  `copies` int DEFAULT NULL COMMENT '菜品份数',
  `version` int unsigned DEFAULT '1' COMMENT '乐观锁版本号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='套餐菜品关系';

-- 正在导出表  sky_take_out.setmeal_dish 的数据：~0 rows (大约)
DELETE FROM `setmeal_dish`;

-- 导出  表 sky_take_out.shopping_cart 结构
DROP TABLE IF EXISTS `shopping_cart`;
CREATE TABLE IF NOT EXISTS `shopping_cart` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '商品名称',
  `image` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '图片',
  `user_id` bigint NOT NULL COMMENT '主键',
  `dish_id` bigint DEFAULT NULL COMMENT '菜品id',
  `setmeal_id` bigint DEFAULT NULL COMMENT '套餐id',
  `dish_flavor` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '口味',
  `number` int NOT NULL DEFAULT '1' COMMENT '数量',
  `amount` decimal(10,2) NOT NULL COMMENT '金额',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `version` int unsigned DEFAULT '1' COMMENT '乐观锁版本号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='购物车';

-- 正在导出表  sky_take_out.shopping_cart 的数据：~0 rows (大约)
DELETE FROM `shopping_cart`;

-- 导出  表 sky_take_out.user 结构
DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `openid` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '微信用户唯一标识',
  `status` int NOT NULL DEFAULT '1' COMMENT '状态 0:禁用，1:启用',
  `name` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '姓名',
  `phone` varchar(11) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '手机号',
  `sex` varchar(2) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '性别',
  `id_number` varchar(18) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '身份证号',
  `avatar` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '头像',
  `create_time` datetime DEFAULT NULL,
  `version` int unsigned DEFAULT '1' COMMENT '乐观锁版本号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin COMMENT='用户信息';

-- 正在导出表  sky_take_out.user 的数据：~0 rows (大约)
DELETE FROM `user`;

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
