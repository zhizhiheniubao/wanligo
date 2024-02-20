/*
 Navicat Premium Data Transfer

 Source Server         : 123
 Source Server Type    : MySQL
 Source Server Version : 80031
 Source Host           : localhost:3306
 Source Schema         : elm_db

 Target Server Type    : MySQL
 Target Server Version : 80031
 File Encoding         : 65001

 Date: 29/12/2023 11:12:22
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_account
-- ----------------------------
DROP TABLE IF EXISTS `sys_account`;
CREATE TABLE `sys_account`  (
  `account_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `account_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `account_sex` int(0) NULL DEFAULT NULL,
  `account_img` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `statu` int(0) NULL DEFAULT NULL,
  `del_tag` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`account_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_account
-- ----------------------------
INSERT INTO `sys_account` VALUES ('123', '4f218e52e7160752b9456e7958a7cd6c', '汁汁和牛堡', 1, 'https://qyfffff.oss-cn-hangzhou.aliyuncs.com/2023/07/25eca02883749446a6ba389fdc0c1d7f3220220123222213_2899a.jpeg', 1, 1);
INSERT INTO `sys_account` VALUES ('1234', '4f218e52e7160752b9456e7958a7cd6c', '张三', 1, 'https://qyfffff.oss-cn-hangzhou.aliyuncs.com/2023/08/21adf1735bbb144b1ebbdf18c71611981a20220325225853_b4674.jpg', 1, 1);
INSERT INTO `sys_account` VALUES ('12345', '4f218e52e7160752b9456e7958a7cd6c', '王五', 1, 'https://wanligo07.oss-cn-hangzhou.aliyuncs.com/2023/12/23108f6dff92104a4099326c05a056b97312.jpg', 1, 1);
INSERT INTO `sys_account` VALUES ('15669979332', '67a93f1cc072efb34694e700e58fb3d1', '博', 0, 'https://wanligo07.oss-cn-hangzhou.aliyuncs.com/2023/12/238ba4c74a5d8e4dc7a59cd294b4d5d03713.jpg', 1, 1);
INSERT INTO `sys_account` VALUES ('18072865627', '4f218e52e7160752b9456e7958a7cd6c', '谢克', 1, 'https://wanligo07.oss-cn-hangzhou.aliyuncs.com/2023/12/28dee836e312144e7bae370f451a93a96atrue (1).jpg', 1, 1);
INSERT INTO `sys_account` VALUES ('18777886655', 'a83c2848b4d4433f975d631bb126d61e', '大大大大', 1, 'https://qyfffff.oss-cn-hangzhou.aliyuncs.com/2023/08/23fca8a8fea797460e8b124c39b6d84ce520220123222213_2899a.jpeg', 1, 1);
INSERT INTO `sys_account` VALUES ('32131231', '4f218e52e7160752b9456e7958a7cd6c', 'adadasd', 1, NULL, 1, 0);

-- ----------------------------
-- Table structure for sys_business
-- ----------------------------
DROP TABLE IF EXISTS `sys_business`;
CREATE TABLE `sys_business`  (
  `business_id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '商家编号',
  `business_name` varchar(40) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '商家名称',
  `business_address` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '商家地址',
  `business_explain` varchar(40) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '商家介绍',
  `business_img` mediumtext CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '商家图片（base64）',
  `category_id` int(0) NULL DEFAULT NULL COMMENT '点餐分类',
  `grade` decimal(10, 1) NULL DEFAULT NULL,
  `star_price` decimal(5, 2) NULL DEFAULT 0.00 COMMENT '起送费',
  `delivery_price` decimal(5, 2) NULL DEFAULT 0.00 COMMENT '配送费',
  `remarks` varchar(40) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '备注',
  `del_tag` int(0) NOT NULL DEFAULT 1 COMMENT '逻辑删除',
  PRIMARY KEY (`business_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10047 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_business
-- ----------------------------
INSERT INTO `sys_business` VALUES (10001, '万家饺子（软件园E18店）', '沈阳浑南区软件园E18楼', '各种饺子', 'https://wanligo07.oss-cn-hangzhou.aliyuncs.com/sj01.png', 1, 3.3, 15.00, 3.00, NULL, 1);
INSERT INTO `sys_business` VALUES (10002, '小锅饭豆腐馆（全运店）', '沈阳市全运路126号', '小锅套餐', '\r\nhttps://qyfffff.oss-cn-hangzhou.aliyuncs.com/business/sj02.png', 1, 2.0, 16.00, 2.50, NULL, 1);
INSERT INTO `sys_business` VALUES (10003, '麦当劳麦乐送（全运路店）', '沈阳市全运路53号麦当劳', '汉堡薯条', 'https://qyfffff.oss-cn-hangzhou.aliyuncs.com/business/sj03.png', 4, NULL, 20.00, 4.00, NULL, 1);
INSERT INTO `sys_business` VALUES (10004, '米村拌饭（浑南店）', '沈阳市浑南区彩霞街26号', '拌饭套餐', '\r\nhttps://qyfffff.oss-cn-hangzhou.aliyuncs.com/business/sj04.png', 1, NULL, 15.00, 3.00, NULL, 1);
INSERT INTO `sys_business` VALUES (10005, '申记串道（中海康城店）', '沈阳市中海康城社区48号', '烤串炸串', 'https://qyfffff.oss-cn-hangzhou.aliyuncs.com/business/sj05.png', 1, NULL, 18.00, 3.00, NULL, 1);
INSERT INTO `sys_business` VALUES (10006, '半亩良田排骨米饭', '沈阳市和平区文萃路58号', '排骨米饭', 'https://qyfffff.oss-cn-hangzhou.aliyuncs.com/business/sj06.png', 1, NULL, 15.00, 3.50, NULL, 1);
INSERT INTO `sys_business` VALUES (10007, '茶兮鲜果饮品（国际软件园店）', '沈阳市国际软件园6号楼', '甜品饮品', '\r\nhttps://qyfffff.oss-cn-hangzhou.aliyuncs.com/business/sj07.png', 10, NULL, 20.00, 4.00, NULL, 1);
INSERT INTO `sys_business` VALUES (10008, '唯一水果捞（软件园E18店）', '沈阳市软件园E18楼', '新鲜水果', 'https://qyfffff.oss-cn-hangzhou.aliyuncs.com/business/sj08.png', 1, NULL, 15.00, 3.50, NULL, 1);
INSERT INTO `sys_business` VALUES (10009, '满园春饼（全运路店）', '沈阳市全运路99号', '春饼炒菜', 'https://qyfffff.oss-cn-hangzhou.aliyuncs.com/business/sj09.png', 1, NULL, 15.00, 2.00, NULL, 1);
INSERT INTO `sys_business` VALUES (10010, '肯德基(织里店)', '湖州市吴兴区织里镇', '炸鸡汉堡', 'https://qyfffff.oss-cn-hangzhou.aliyuncs.com/business/R-C.png', 1, NULL, 25.00, 9.00, NULL, 1);
INSERT INTO `sys_business` VALUES (10044, '巴比屋', '浙江万里学院', '便餐', '', 4, NULL, 12.00, 1.00, '', 0);
INSERT INTO `sys_business` VALUES (10045, '古茗', '浙江万里学院', '奶茶', '', 10, NULL, 18.00, 0.00, '', 0);
INSERT INTO `sys_business` VALUES (10046, '古茗', '浙江万里学院24楼', '奶茶', 'https://wanligo07.oss-cn-hangzhou.aliyuncs.com/2023/12/281ecd63d41fc144bb80ef8a5b8bb072ad13.jpg', 10, NULL, 15.00, 2.00, NULL, 0);

-- ----------------------------
-- Table structure for sys_cart
-- ----------------------------
DROP TABLE IF EXISTS `sys_cart`;
CREATE TABLE `sys_cart`  (
  `cart_id` int(0) NOT NULL AUTO_INCREMENT COMMENT '无意义编号',
  `food_id` int(0) NOT NULL COMMENT '食品编号',
  `business_id` bigint(0) NOT NULL COMMENT '所属商家编号',
  `account_id` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '所属用户编号',
  `quantity` int(0) NOT NULL COMMENT '同一类型食品的购买数量',
  `del_tag` int(0) NOT NULL DEFAULT 1,
  PRIMARY KEY (`cart_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 204 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_cart
-- ----------------------------
INSERT INTO `sys_cart` VALUES (37, 12, 10002, '12345', 2, 1);
INSERT INTO `sys_cart` VALUES (127, 11, 10002, '1234', 1, 1);
INSERT INTO `sys_cart` VALUES (133, 2, 10001, '1234', 1, 1);
INSERT INTO `sys_cart` VALUES (134, 1, 10001, '1234', 1, 1);
INSERT INTO `sys_cart` VALUES (135, 10, 10002, '1234', 1, 1);
INSERT INTO `sys_cart` VALUES (152, 1, 10001, '18072865627', 1, 1);
INSERT INTO `sys_cart` VALUES (153, 2, 10001, '18072865627', 1, 1);
INSERT INTO `sys_cart` VALUES (200, 10, 10002, '18777886655', 1, 1);
INSERT INTO `sys_cart` VALUES (201, 11, 10002, '18777886655', 2, 1);

-- ----------------------------
-- Table structure for sys_category
-- ----------------------------
DROP TABLE IF EXISTS `sys_category`;
CREATE TABLE `sys_category`  (
  `category_id` int(0) NOT NULL AUTO_INCREMENT,
  `category_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `category_cover` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `del_tag` int(0) NOT NULL DEFAULT 1,
  PRIMARY KEY (`category_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_category
-- ----------------------------
INSERT INTO `sys_category` VALUES (1, '美食', 'https://wanligo07.oss-cn-hangzhou.aliyuncs.com/dcfl01.png', 1);
INSERT INTO `sys_category` VALUES (2, '早餐', 'https://wanligo07.oss-cn-hangzhou.aliyuncs.com/dcfl02.png', 1);
INSERT INTO `sys_category` VALUES (3, '跑腿代购', 'https://wanligo07.oss-cn-hangzhou.aliyuncs.com/dcfl03.png', 1);
INSERT INTO `sys_category` VALUES (4, '汉堡披萨', 'https://wanligo07.oss-cn-hangzhou.aliyuncs.com/dcfl04.png', 1);
INSERT INTO `sys_category` VALUES (5, '速食简餐', 'https://wanligo07.oss-cn-hangzhou.aliyuncs.com/dcfl05.png', 1);
INSERT INTO `sys_category` VALUES (6, '地方小吃', 'https://wanligo07.oss-cn-hangzhou.aliyuncs.com/dcfl06.png', 1);
INSERT INTO `sys_category` VALUES (7, '米粉面馆', 'https://wanligo07.oss-cn-hangzhou.aliyuncs.com/dcfl07.png', 1);
INSERT INTO `sys_category` VALUES (8, '包子粥铺', 'https://wanligo07.oss-cn-hangzhou.aliyuncs.com/dcfl08.png', 1);
INSERT INTO `sys_category` VALUES (9, '炸鸡炸串', 'https://wanligo07.oss-cn-hangzhou.aliyuncs.com/dcfl09.png', 1);
INSERT INTO `sys_category` VALUES (10, '甜品饮品', 'https://wanligo07.oss-cn-hangzhou.aliyuncs.com/dcfl10.png', 1);

-- ----------------------------
-- Table structure for sys_comment
-- ----------------------------
DROP TABLE IF EXISTS `sys_comment`;
CREATE TABLE `sys_comment`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `comment` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `comment_img` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `business_id` bigint(0) NOT NULL,
  `account_id` bigint(0) NOT NULL,
  `pid` int(0) NULL DEFAULT NULL,
  `origin_id` int(0) NULL DEFAULT NULL,
  `create_time` datetime(0) NOT NULL,
  `grade` decimal(10, 0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10019 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_comment
-- ----------------------------
INSERT INTO `sys_comment` VALUES (2, '9月16日晚央视财经频道播出的《对话》栏目，以“打开江苏制造的密码”为主题。在节目中，江苏省委书记信长星对此回应：“‘苏大强’我们一般江苏人不太用。我认为这其中透露着对江苏的喜爱，同时更多的是期待。经济上要强、百姓要富、环境要美、社会文明程度要高，‘强富美高’，这是总书记给（江苏）擘画的蓝图，强是摆在首位的。”', 'https://qyfffff.oss-cn-hangzhou.aliyuncs.com/2023/08/21adf1735bbb144b1ebbdf18c71611981a20220325225853_b4674.jpg,https://qyfffff.oss-cn-hangzhou.aliyuncs.com/2023/08/21adf1735bbb144b1ebbdf18c71611981a20220325225853_b4674.jpg', 10001, 18777886655, NULL, NULL, '2023-09-20 09:39:58', 2);
INSERT INTO `sys_comment` VALUES (3, '你要不要看看你在说什么？', '', 10001, 123123123, 2, 2, '2023-09-20 10:27:27', 1);
INSERT INTO `sys_comment` VALUES (6, 'top 1', 'https://qyfffff.oss-cn-hangzhou.aliyuncs.com/2023/08/21adf1735bbb144b1ebbdf18c71611981a20220325225853_b4674.jpg', 10001, 18777886655, NULL, NULL, '2023-09-20 11:24:12', 4);
INSERT INTO `sys_comment` VALUES (21, '我不敢苟同。 我个人认为这个意大利面就应该拌42号混凝土。因为这个螺丝钉的长度，它很容易会直接影响到挖掘机的扭距，你往里砸的时候，一瞬间它就会产生大量的高能蛋白，俗称UFO。会严重影响经济的发展。 照你这么说，炸鸡块要用92#汽油，毕竟我们无法用光学透镜探测苏格拉底，如果二氧化氢持续侵蚀这个机床组件，那么我们早晚要在斐波那契曲线上安装一个胶原蛋白，否则我们将无法改变蜜雪冰城与阿尔别克的叠加状态，因为众所周知爱吃鸡摩人在捕鲲的时候往往需要用氢的同位素当做诱饵，但是原子弹的新鲜程度又会直接影响到我国东南部的季风和洋流，所以说在西伯利亚地区开设农学院显然是不合理的。\n\n\n我知道你一定会反驳我，告诉我农业的底层思维是什么，就是不用化肥农药和种子，还包括生命之源氮气，使甲烷分子直接转化成能够捕获放射性元素释放的β射线的单质，并且使伽马射线在常温下就能用老虎钳折弯成78°，否则在用望远镜观察细胞结构时，根本发现不了时空重叠时到底要叠几层才能使潼关肉夹馍更酥脆的原因。', 'https://qyfffff.oss-cn-hangzhou.aliyuncs.com/2023/08/21adf1735bbb144b1ebbdf18c71611981a20220325225853_b4674.jpg', 10001, 18777886655, NULL, NULL, '2023-09-20 16:16:58', 5);
INSERT INTO `sys_comment` VALUES (23, '人才', '', 10001, 1234, 21, 21, '2023-09-20 16:30:03', 3);
INSERT INTO `sys_comment` VALUES (28, '你讲话真好听', '', 10001, 18777886655, 23, 21, '2023-09-21 09:38:20', 4);
INSERT INTO `sys_comment` VALUES (101, '什么top1?年度排行吗?', '', 10001, 1234, 6, 6, '2023-09-21 15:29:25', 4);
INSERT INTO `sys_comment` VALUES (112, '√', '', 10001, 18777886655, 101, 6, '2023-09-21 15:37:41', 2);
INSERT INTO `sys_comment` VALUES (10002, 'test', NULL, 10001, 18777886655, NULL, NULL, '2023-12-24 17:55:20', 3);
INSERT INTO `sys_comment` VALUES (10003, '6', NULL, 10001, 18777886655, 3, 2, '2023-12-27 20:13:28', NULL);
INSERT INTO `sys_comment` VALUES (10004, '6', NULL, 10001, 18777886655, 3, 2, '2023-12-27 20:58:14', NULL);
INSERT INTO `sys_comment` VALUES (10005, '6', NULL, 10001, 18777886655, 3, 2, '2023-12-27 20:58:52', NULL);
INSERT INTO `sys_comment` VALUES (10006, '好吃再来', 'https://wanligo07.oss-cn-hangzhou.aliyuncs.com/2023/12/273dbdbc6a22644a19aed0b4ea3066659612.jpg', 10001, 18777886655, NULL, NULL, '2023-12-27 21:06:26', 4);
INSERT INTO `sys_comment` VALUES (10007, '好', 'https://wanligo07.oss-cn-hangzhou.aliyuncs.com/2023/12/275c381a4a3de14c63bc01f99e2055ee5f13.jpg', 10001, 18777886655, NULL, NULL, '2023-12-27 21:37:12', 4);
INSERT INTO `sys_comment` VALUES (10008, '？？？', NULL, 10001, 18777886655, 10007, 10007, '2023-12-27 21:37:29', NULL);
INSERT INTO `sys_comment` VALUES (10009, '搞错了', NULL, 10001, 18777886655, 10008, 10007, '2023-12-27 21:37:40', NULL);
INSERT INTO `sys_comment` VALUES (10010, '还行吧', 'https://wanligo07.oss-cn-hangzhou.aliyuncs.com/2023/12/273b87d3a325d8463d92b66aeda5e3aec912.jpg', 10001, 18777886655, NULL, NULL, '2023-12-27 22:58:15', 3);
INSERT INTO `sys_comment` VALUES (10011, '还行', 'https://wanligo07.oss-cn-hangzhou.aliyuncs.com/2023/12/28fc8c90a050d54e3a814611bc84d6104113.jpg', 10002, 18777886655, NULL, NULL, '2023-12-28 08:11:40', 3);
INSERT INTO `sys_comment` VALUES (10012, '？', NULL, 10002, 18777886655, 10011, 10011, '2023-12-28 08:12:06', NULL);
INSERT INTO `sys_comment` VALUES (10013, '不好吃', 'https://wanligo07.oss-cn-hangzhou.aliyuncs.com/2023/12/282185ddbd21f14bd6a22d2d7850004bf114.jpeg', 10002, 18777886655, NULL, NULL, '2023-12-28 09:35:40', 1);
INSERT INTO `sys_comment` VALUES (10014, '还行把', NULL, 10002, 18777886655, 10012, 10011, '2023-12-28 09:35:58', NULL);
INSERT INTO `sys_comment` VALUES (10015, '6', NULL, 10001, 15669979332, 112, 6, '2023-12-28 10:52:48', NULL);
INSERT INTO `sys_comment` VALUES (10016, '非常好吃', 'https://wanligo07.oss-cn-hangzhou.aliyuncs.com/2023/12/28639b0cba024644cdb9caac96aac0f5d413.jpg', 10002, 18777886655, NULL, NULL, '2023-12-28 15:46:44', 5);
INSERT INTO `sys_comment` VALUES (10017, '好吃个屁', NULL, 10002, 18777886655, 10016, 10016, '2023-12-28 15:47:04', NULL);
INSERT INTO `sys_comment` VALUES (10018, '你懂个屁', NULL, 10002, 18777886655, 10017, 10016, '2023-12-28 15:47:18', NULL);

-- ----------------------------
-- Table structure for sys_deliveryaddress
-- ----------------------------
DROP TABLE IF EXISTS `sys_deliveryaddress`;
CREATE TABLE `sys_deliveryaddress`  (
  `da_id` int(0) NOT NULL AUTO_INCREMENT COMMENT '送货地址编号',
  `contact_name` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '联系人姓名',
  `contact_sex` int(0) NOT NULL COMMENT '联系人性别',
  `contact_tel` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '联系人电话',
  `address` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '送货地址',
  `account_id` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '所属用户编号',
  PRIMARY KEY (`da_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_deliveryaddress
-- ----------------------------
INSERT INTO `sys_deliveryaddress` VALUES (2, '张爱玲', 1, '12345671111', '上海市静安区静安寺街道常德路195号', '18777886655');
INSERT INTO `sys_deliveryaddress` VALUES (4, '乔宇飞', 1, '18335999155', '山西', '18777886655');
INSERT INTO `sys_deliveryaddress` VALUES (7, '乔宇飞', 1, '18335999155', 'dqwd qw', '1234');
INSERT INTO `sys_deliveryaddress` VALUES (8, '阿华田', 0, '15669979332', '浙江万里学院', '18777886655');
INSERT INTO `sys_deliveryaddress` VALUES (13, 'fjf', 1, '122323431231', '浙江万里学院25', '15669979332');
INSERT INTO `sys_deliveryaddress` VALUES (14, 'fff', 1, '123123124212', '浙江万里学院', '18777886655');
INSERT INTO `sys_deliveryaddress` VALUES (15, 'fjf', 1, '15669979332', '浙江万里学院', '18777886655');

-- ----------------------------
-- Table structure for sys_food
-- ----------------------------
DROP TABLE IF EXISTS `sys_food`;
CREATE TABLE `sys_food`  (
  `food_id` int(0) NOT NULL AUTO_INCREMENT COMMENT '食品编号',
  `food_name` varchar(30) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '食品名称',
  `food_explain` varchar(30) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '食品介绍',
  `food_img` mediumtext CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '食品图片',
  `food_price` decimal(5, 2) NOT NULL COMMENT '食品价格',
  `business_id` bigint(0) NOT NULL COMMENT '所属商家编号',
  `remarks` varchar(40) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '备注',
  `del_tag` int(0) NOT NULL DEFAULT 1,
  PRIMARY KEY (`food_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_food
-- ----------------------------
INSERT INTO `sys_food` VALUES (1, '纯肉鲜肉（水饺）', '纯肉馅饺子', 'https://wanligo07.oss-cn-hangzhou.aliyuncs.com/sp01.png', 17.00, 10001, NULL, 1);
INSERT INTO `sys_food` VALUES (2, '玉米鲜肉（水饺）', '玉米清香', 'https://wanligo07.oss-cn-hangzhou.aliyuncs.com/sp02.png', 15.00, 10001, NULL, 1);
INSERT INTO `sys_food` VALUES (3, '虾仁三鲜（蒸饺）', '三鲜馅饺子', 'https://wanligo07.oss-cn-hangzhou.aliyuncs.com/sp03.png', 20.00, 10001, NULL, 1);
INSERT INTO `sys_food` VALUES (4, '素三鲜（蒸饺）', '韭菜鸡蛋虾仁', 'https://wanligo07.oss-cn-hangzhou.aliyuncs.com/sp04.png', 15.00, 10001, NULL, 1);
INSERT INTO `sys_food` VALUES (7, '芹菜牛肉（水饺）', '芹菜牛肉', 'https://wanligo07.oss-cn-hangzhou.aliyuncs.com/sp07.png', 22.00, 10001, NULL, 1);
INSERT INTO `sys_food` VALUES (8, '虾腰鲜肉（蒸饺）', '虾仁腰子', 'https://wanligo07.oss-cn-hangzhou.aliyuncs.com/sp08.png', 25.00, 10001, NULL, 1);
INSERT INTO `sys_food` VALUES (9, '青椒鲜肉（蒸饺）', '青椒鲜肉', 'https://wanligo07.oss-cn-hangzhou.aliyuncs.com/sp09.png', 18.00, 10001, NULL, 1);
INSERT INTO `sys_food` VALUES (10, '蛋黄焗豆花', '蛋黄豆花', 'https://wanligo07.oss-cn-hangzhou.aliyuncs.com/sp10.png', 8.00, 10002, NULL, 1);
INSERT INTO `sys_food` VALUES (11, '麻婆豆腐', '麻辣味', 'https://wanligo07.oss-cn-hangzhou.aliyuncs.com/sp11.png', 14.00, 10002, NULL, 1);
INSERT INTO `sys_food` VALUES (12, '广式小豆腐', '广东风味', 'https://wanligo07.oss-cn-hangzhou.aliyuncs.com/sp12.png', 12.00, 10002, NULL, 1);

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(0) NULL DEFAULT NULL COMMENT '父菜单ID，一级菜单为0',
  `name` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `path` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '菜单URL',
  `perms` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '授权(多个用逗号分隔，如：user:list,user:create)',
  `component` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `type` int(0) NOT NULL COMMENT '类型   0：目录  1：菜单   2：按钮',
  `icon` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '菜单图标',
  `orderNum` int(0) NULL DEFAULT NULL COMMENT '排序',
  `created` datetime(0) NOT NULL,
  `updated` datetime(0) NULL DEFAULT NULL,
  `statu` int(0) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 507 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, 0, '系统管理', '', 'sys:manage', '', 0, 'el-icon-s-operation', 1, '2022-11-03 18:58:18', '2023-12-15 10:09:34', 1);
INSERT INTO `sys_menu` VALUES (2, 1, '用户管理', '/system/user', 'sys:user:list', 'system/User', 1, 'el-icon-s-custom', 1, '2022-11-03 18:58:18', '2023-12-15 10:09:22', 1);
INSERT INTO `sys_menu` VALUES (3, 1, '角色管理', '/system/role', 'sys:role:list', 'system/Role', 1, 'el-icon-rank', 2, '2022-11-03 18:58:18', '2022-11-03 18:58:18', 1);
INSERT INTO `sys_menu` VALUES (4, 1, '菜单管理', '/system/menu', 'sys:menu:list', 'system/Menu', 1, 'el-icon-menu', 3, '2022-11-03 18:58:18', '2022-11-03 18:58:18', 1);
INSERT INTO `sys_menu` VALUES (7, 3, '添加角色', '', 'sys:role:save', '', 2, '', 1, '2022-11-03 18:58:18', '2022-11-03 18:58:18', 0);
INSERT INTO `sys_menu` VALUES (9, 2, '添加用户', NULL, 'sys:user:save', NULL, 2, NULL, 1, '2022-11-03 18:58:18', '2023-12-15 10:08:46', 1);
INSERT INTO `sys_menu` VALUES (10, 2, '修改用户', NULL, 'sys:user:update', NULL, 2, NULL, 2, '2022-11-03 18:58:18', '2022-11-03 18:58:18', 1);
INSERT INTO `sys_menu` VALUES (11, 2, '删除用户', NULL, 'sys:user:delete', NULL, 2, NULL, 3, '2022-11-03 18:58:18', NULL, 1);
INSERT INTO `sys_menu` VALUES (12, 2, '分配角色', NULL, 'sys:user:role', NULL, 2, NULL, 4, '2022-11-03 18:58:18', NULL, 1);
INSERT INTO `sys_menu` VALUES (13, 2, '重置密码', NULL, 'sys:user:repass', NULL, 2, NULL, 5, '2022-11-03 18:58:18', NULL, 1);
INSERT INTO `sys_menu` VALUES (14, 3, '修改角色', NULL, 'sys:role:update', NULL, 2, NULL, 2, '2022-11-03 18:58:18', NULL, 1);
INSERT INTO `sys_menu` VALUES (15, 3, '删除角色', NULL, 'sys:role:delete', NULL, 2, NULL, 3, '2022-11-03 18:58:18', NULL, 1);
INSERT INTO `sys_menu` VALUES (16, 3, '分配权限', NULL, 'sys:role:perm', NULL, 2, NULL, 5, '2022-11-03 18:58:18', NULL, 1);
INSERT INTO `sys_menu` VALUES (17, 4, '添加菜单', NULL, 'sys:menu:save', NULL, 2, NULL, 1, '2022-11-03 18:58:18', '2022-11-03 18:58:18', 1);
INSERT INTO `sys_menu` VALUES (18, 4, '修改菜单', NULL, 'sys:menu:update', NULL, 2, NULL, 2, '2022-11-03 18:58:18', NULL, 1);
INSERT INTO `sys_menu` VALUES (19, 4, '删除菜单', NULL, 'sys:menu:delete', NULL, 2, NULL, 3, '2022-11-03 18:58:18', NULL, 1);
INSERT INTO `sys_menu` VALUES (24, 2, '清空用户', '', 'sys:mess:clear', '', 2, '11111', NULL, '2023-08-15 18:07:43', '2023-08-15 18:11:15', 1);
INSERT INTO `sys_menu` VALUES (25, 42, '商家管理', '/data/business', 'sys:business:list', 'data/Business', 0, 'el-icon-s-goods', NULL, '2023-08-18 14:52:10', '2023-08-18 14:53:43', 1);
INSERT INTO `sys_menu` VALUES (28, 42, '餐品管理', '/data/food', 'sys:food:list', 'data/Food', 0, 'el-icon-chicken', NULL, '2023-08-24 15:54:54', '2023-12-18 13:41:10', 1);
INSERT INTO `sys_menu` VALUES (29, 42, '地址管理', '/data/deliveryaddress', 'sys:deliveryaddress', 'data/Deliveryaddress', 0, 'el-icon-house', NULL, '2023-08-25 00:17:18', '2023-08-25 00:17:18', 1);
INSERT INTO `sys_menu` VALUES (30, 42, '订单管理', '/data/orders', 'sys:orders:list', 'data/Orders', 0, 'el-icon-date', NULL, '2023-08-29 11:48:48', '2023-08-29 17:27:04', 1);
INSERT INTO `sys_menu` VALUES (42, 0, '数据管理', NULL, 'sys:data', NULL, 0, 'el-icon-s-data', NULL, '2023-08-08 12:18:46', '2023-08-08 12:19:01', 1);
INSERT INTO `sys_menu` VALUES (44, 42, '类别管理', '/data/category', 'sys:category:list', 'data/Category', 1, 'el-icon-collection-tag', NULL, '2023-05-22 17:41:29', '2023-05-22 17:43:08', 1);
INSERT INTO `sys_menu` VALUES (45, 2, '修改密码', NULL, 'sys:user:setpass', NULL, 2, NULL, NULL, '2023-05-23 11:01:55', NULL, 1);
INSERT INTO `sys_menu` VALUES (46, 44, '类别更新', NULL, 'sys:category:update', NULL, 2, NULL, NULL, '2023-05-23 14:05:21', NULL, 1);
INSERT INTO `sys_menu` VALUES (47, 44, '新建类别', NULL, 'sys:category:save', NULL, 2, NULL, NULL, '2023-05-23 14:05:49', NULL, 1);
INSERT INTO `sys_menu` VALUES (48, 44, '类别删除', NULL, 'sys:category:delete', NULL, 2, NULL, NULL, '2023-05-23 14:06:08', NULL, 1);
INSERT INTO `sys_menu` VALUES (49, 2, '短信服务', NULL, 'sys:mess:mgr', NULL, 2, NULL, NULL, '2023-08-11 15:32:29', '2023-08-11 15:32:36', 1);
INSERT INTO `sys_menu` VALUES (506, 42, '客户管理', '/data/account', 'sys:account:list', 'data/Account', 1, 'el-icon-user-solid', NULL, '2023-12-18 13:45:19', '2023-12-28 08:13:19', 1);

-- ----------------------------
-- Table structure for sys_orderdetailet
-- ----------------------------
DROP TABLE IF EXISTS `sys_orderdetailet`;
CREATE TABLE `sys_orderdetailet`  (
  `od_id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '订单明细编号',
  `order_id` bigint(0) NOT NULL COMMENT '所属订单编号',
  `food_id` int(0) NOT NULL COMMENT '食品编号',
  `quantity` int(0) NOT NULL COMMENT '数量',
  `del_tag` int(0) NULL DEFAULT 1,
  PRIMARY KEY (`od_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 129 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_orderdetailet
-- ----------------------------
INSERT INTO `sys_orderdetailet` VALUES (5, 4, 8, 2, 1);
INSERT INTO `sys_orderdetailet` VALUES (6, 4, 9, 2, 1);
INSERT INTO `sys_orderdetailet` VALUES (7, 5, 1, 1, 1);
INSERT INTO `sys_orderdetailet` VALUES (8, 5, 2, 1, 1);
INSERT INTO `sys_orderdetailet` VALUES (9, 6, 10, 2, 1);
INSERT INTO `sys_orderdetailet` VALUES (10, 7, 7, 1, 1);
INSERT INTO `sys_orderdetailet` VALUES (11, 7, 6, 1, 1);
INSERT INTO `sys_orderdetailet` VALUES (12, 8, 1, 2, 1);
INSERT INTO `sys_orderdetailet` VALUES (13, 9, 2, 2, 1);
INSERT INTO `sys_orderdetailet` VALUES (14, 10, 1, 2, 1);
INSERT INTO `sys_orderdetailet` VALUES (15, 11, 1, 2, 1);
INSERT INTO `sys_orderdetailet` VALUES (16, 12, 1, 1, 1);
INSERT INTO `sys_orderdetailet` VALUES (54, 49, 1, 2, 1);
INSERT INTO `sys_orderdetailet` VALUES (55, 50, 10, 2, 1);
INSERT INTO `sys_orderdetailet` VALUES (56, 51, 8, 2, 1);
INSERT INTO `sys_orderdetailet` VALUES (57, 52, 10, 2, 1);
INSERT INTO `sys_orderdetailet` VALUES (58, 52, 11, 2, 1);
INSERT INTO `sys_orderdetailet` VALUES (59, 53, 10, 3, 1);
INSERT INTO `sys_orderdetailet` VALUES (60, 54, 1, 3, 1);
INSERT INTO `sys_orderdetailet` VALUES (61, 54, 2, 4, 1);
INSERT INTO `sys_orderdetailet` VALUES (62, 54, 3, 2, 1);
INSERT INTO `sys_orderdetailet` VALUES (63, 55, 10, 2, 1);
INSERT INTO `sys_orderdetailet` VALUES (64, 56, 1, 1, 1);
INSERT INTO `sys_orderdetailet` VALUES (65, 56, 2, 1, 1);
INSERT INTO `sys_orderdetailet` VALUES (66, 57, 1, 2, 1);
INSERT INTO `sys_orderdetailet` VALUES (67, 58, 1, 3, 1);
INSERT INTO `sys_orderdetailet` VALUES (68, 58, 2, 3, 1);
INSERT INTO `sys_orderdetailet` VALUES (69, 59, 1, 2, 1);
INSERT INTO `sys_orderdetailet` VALUES (70, 60, 1, 3, 1);
INSERT INTO `sys_orderdetailet` VALUES (71, 61, 1, 2, 1);
INSERT INTO `sys_orderdetailet` VALUES (77, 67, 2, 1, 1);
INSERT INTO `sys_orderdetailet` VALUES (78, 67, 3, 1, 1);
INSERT INTO `sys_orderdetailet` VALUES (79, 68, 1, 1, 1);
INSERT INTO `sys_orderdetailet` VALUES (80, 68, 2, 1, 1);
INSERT INTO `sys_orderdetailet` VALUES (81, 69, 1, 2, 1);
INSERT INTO `sys_orderdetailet` VALUES (82, 70, 1, 2, 1);
INSERT INTO `sys_orderdetailet` VALUES (83, 71, 1, 3, 1);
INSERT INTO `sys_orderdetailet` VALUES (87, 76, 1, 1, 1);
INSERT INTO `sys_orderdetailet` VALUES (88, 77, 1, 1, 1);
INSERT INTO `sys_orderdetailet` VALUES (89, 77, 2, 1, 1);
INSERT INTO `sys_orderdetailet` VALUES (90, 77, 3, 1, 1);
INSERT INTO `sys_orderdetailet` VALUES (91, 77, 4, 2, 1);
INSERT INTO `sys_orderdetailet` VALUES (92, 78, 1, 1, 1);
INSERT INTO `sys_orderdetailet` VALUES (93, 78, 2, 1, 1);
INSERT INTO `sys_orderdetailet` VALUES (94, 78, 3, 1, 1);
INSERT INTO `sys_orderdetailet` VALUES (95, 78, 4, 2, 1);
INSERT INTO `sys_orderdetailet` VALUES (96, 79, 4, 1, 1);
INSERT INTO `sys_orderdetailet` VALUES (97, 79, 5, 1, 1);
INSERT INTO `sys_orderdetailet` VALUES (98, 80, 1, 1, 1);
INSERT INTO `sys_orderdetailet` VALUES (99, 80, 2, 1, 1);
INSERT INTO `sys_orderdetailet` VALUES (100, 81, 2, 3, 1);
INSERT INTO `sys_orderdetailet` VALUES (101, 82, 1, 2, 1);
INSERT INTO `sys_orderdetailet` VALUES (102, 82, 2, 1, 1);
INSERT INTO `sys_orderdetailet` VALUES (103, 83, 1, 1, 1);
INSERT INTO `sys_orderdetailet` VALUES (104, 83, 2, 1, 1);
INSERT INTO `sys_orderdetailet` VALUES (105, 84, 1, 1, 1);
INSERT INTO `sys_orderdetailet` VALUES (106, 84, 2, 1, 1);
INSERT INTO `sys_orderdetailet` VALUES (107, 85, 5, 2, 1);
INSERT INTO `sys_orderdetailet` VALUES (108, 86, 3, 3, 1);
INSERT INTO `sys_orderdetailet` VALUES (109, 86, 1, 2, 1);
INSERT INTO `sys_orderdetailet` VALUES (110, 86, 1, 2, 1);
INSERT INTO `sys_orderdetailet` VALUES (111, 87, 12, 2, 1);
INSERT INTO `sys_orderdetailet` VALUES (112, 87, 11, 1, 1);
INSERT INTO `sys_orderdetailet` VALUES (113, 88, 12, 1, 1);
INSERT INTO `sys_orderdetailet` VALUES (114, 88, 11, 1, 1);
INSERT INTO `sys_orderdetailet` VALUES (115, 88, 10, 1, 1);
INSERT INTO `sys_orderdetailet` VALUES (116, 89, 11, 1, 1);
INSERT INTO `sys_orderdetailet` VALUES (117, 89, 10, 2, 1);
INSERT INTO `sys_orderdetailet` VALUES (118, 90, 10, 1, 1);
INSERT INTO `sys_orderdetailet` VALUES (119, 90, 11, 1, 1);
INSERT INTO `sys_orderdetailet` VALUES (120, 91, 1, 1, 1);
INSERT INTO `sys_orderdetailet` VALUES (121, 92, 10, 1, 1);
INSERT INTO `sys_orderdetailet` VALUES (122, 92, 11, 1, 1);
INSERT INTO `sys_orderdetailet` VALUES (123, 93, 11, 1, 1);
INSERT INTO `sys_orderdetailet` VALUES (124, 93, 12, 1, 1);
INSERT INTO `sys_orderdetailet` VALUES (125, 94, 1, 3, 1);
INSERT INTO `sys_orderdetailet` VALUES (126, 95, 1, 2, 1);
INSERT INTO `sys_orderdetailet` VALUES (127, 95, 2, 1, 1);
INSERT INTO `sys_orderdetailet` VALUES (128, 96, 1, 1, 1);

-- ----------------------------
-- Table structure for sys_orders
-- ----------------------------
DROP TABLE IF EXISTS `sys_orders`;
CREATE TABLE `sys_orders`  (
  `order_id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '订单编号',
  `account_id` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '用户编号',
  `business_id` bigint(0) NOT NULL COMMENT '商家编号',
  `order_date` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '订购日期',
  `order_total` decimal(7, 2) NOT NULL DEFAULT 0.00 COMMENT '订单总价',
  `da_id` int(0) NOT NULL COMMENT '送货地址编号',
  `order_state` int(0) NOT NULL DEFAULT 0 COMMENT '订单状态（0：未支付； 1：已支付）',
  `del_tag` int(0) NULL DEFAULT 1,
  PRIMARY KEY (`order_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 97 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_orders
-- ----------------------------
INSERT INTO `sys_orders` VALUES (11, '18777886655', 10001, '2023-08-01', 35.00, 2, 1, 0);
INSERT INTO `sys_orders` VALUES (12, '18777886655', 10001, '2023-08-01', 19.00, 2, 1, 1);
INSERT INTO `sys_orders` VALUES (67, '1234', 10001, '2023-08-28', 38.00, 7, 1, 1);
INSERT INTO `sys_orders` VALUES (68, '1234', 10001, '2023-08-28', 34.00, 7, 1, 1);
INSERT INTO `sys_orders` VALUES (69, '1234', 10001, '2023-08-28', 35.00, 7, 0, 1);
INSERT INTO `sys_orders` VALUES (70, '1234', 10001, '2023-08-29', 35.00, 7, 1, 1);
INSERT INTO `sys_orders` VALUES (71, '1234', 10001, '2023-08-29', 51.00, 7, 0, 1);
INSERT INTO `sys_orders` VALUES (76, '18777886655', 10001, '2023-12-26 10:56:15', 19.00, 2, 1, 1);
INSERT INTO `sys_orders` VALUES (77, '15669979332', 10001, '2023-12-26 09:51:16', 84.00, 13, 1, 1);
INSERT INTO `sys_orders` VALUES (78, '15669979332', 10001, '2023-12-26 09:51:17', 84.00, 13, 0, 1);
INSERT INTO `sys_orders` VALUES (79, '12345', 10001, '2023-12-27 03:42:44', 33.00, 14, 1, 1);
INSERT INTO `sys_orders` VALUES (80, '18777886655', 10001, '2023-12-27 09:10:57', 34.00, 2, 1, 1);
INSERT INTO `sys_orders` VALUES (81, '18777886655', 10001, '2023-12-27 09:53:57', 48.00, 2, 1, 1);
INSERT INTO `sys_orders` VALUES (83, '18777886655', 10001, '2023-12-27 10:22:43', 34.00, 2, 1, 1);
INSERT INTO `sys_orders` VALUES (84, '18777886655', 10001, '2023-12-27 10:56:25', 34.00, 2, 1, 1);
INSERT INTO `sys_orders` VALUES (85, '18777886655', 10001, '2023-12-28 12:48:55', 33.00, 6, 1, 0);
INSERT INTO `sys_orders` VALUES (86, '15669979332', 10001, '2023-12-28 02:08:01', 127.00, 13, 1, 1);
INSERT INTO `sys_orders` VALUES (87, '15669979332', 10002, '2023-12-28 02:08:11', 40.50, 13, 1, 1);
INSERT INTO `sys_orders` VALUES (88, '15669979332', 10002, '2023-12-28 02:08:33', 36.50, 13, 1, 1);
INSERT INTO `sys_orders` VALUES (89, '15669979332', 10002, '2023-12-28 02:08:45', 32.50, 13, 1, 1);
INSERT INTO `sys_orders` VALUES (90, '18777886655', 10002, '2023-12-28 08:11:21', 24.50, 6, 1, 1);
INSERT INTO `sys_orders` VALUES (91, '18777886655', 10001, '2023-12-28 09:07:43', 19.00, 6, 1, 1);
INSERT INTO `sys_orders` VALUES (92, '18777886655', 10002, '2023-12-28 09:08:05', 24.50, 6, 0, 0);
INSERT INTO `sys_orders` VALUES (93, '18777886655', 10002, '2023-12-28 09:32:19', 28.50, 4, 1, 1);
INSERT INTO `sys_orders` VALUES (94, '18777886655', 10001, '2023-12-28 02:05:20', 54.00, 4, 0, 0);
INSERT INTO `sys_orders` VALUES (95, '18777886655', 10001, '2023-12-28 03:39:04', 52.00, 15, 1, 1);
INSERT INTO `sys_orders` VALUES (96, '18777886655', 10001, '2023-12-28 03:40:15', 20.00, 15, 1, 1);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `code` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `remark` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `created` datetime(0) NULL DEFAULT NULL,
  `updated` datetime(0) NULL DEFAULT NULL,
  `statu` int(0) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (3, '普通用户', 'normal', '只有基本查看功能', '2022-11-04 10:09:14', '2023-12-15 10:06:37', 1);
INSERT INTO `sys_role` VALUES (6, '超级管理员', 'admin', '系统默认最高权限，可\r\n以编辑和任意修改', '2022-11-02 13:29:03', '2023-12-14 11:11:03', 1);
INSERT INTO `sys_role` VALUES (7, '测试用户', 'test', '用于测试', '2023-08-16 16:19:01', '2023-08-16 16:19:01', 1);
INSERT INTO `sys_role` VALUES (11, '商家管理员', 'business', '管理商家角色', '2023-08-17 11:17:01', '2023-08-17 11:17:01', 1);

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(0) NOT NULL,
  `menu_id` bigint(0) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7904 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES (567, 3, 1);
INSERT INTO `sys_role_menu` VALUES (568, 3, 2);
INSERT INTO `sys_role_menu` VALUES (569, 3, 9);
INSERT INTO `sys_role_menu` VALUES (570, 3, 10);
INSERT INTO `sys_role_menu` VALUES (571, 3, 11);
INSERT INTO `sys_role_menu` VALUES (572, 3, 12);
INSERT INTO `sys_role_menu` VALUES (573, 3, 13);
INSERT INTO `sys_role_menu` VALUES (574, 3, 24);
INSERT INTO `sys_role_menu` VALUES (575, 3, 45);
INSERT INTO `sys_role_menu` VALUES (576, 3, 49);
INSERT INTO `sys_role_menu` VALUES (577, 3, 3);
INSERT INTO `sys_role_menu` VALUES (578, 3, 7);
INSERT INTO `sys_role_menu` VALUES (579, 3, 14);
INSERT INTO `sys_role_menu` VALUES (580, 3, 15);
INSERT INTO `sys_role_menu` VALUES (581, 3, 16);
INSERT INTO `sys_role_menu` VALUES (582, 3, 4);
INSERT INTO `sys_role_menu` VALUES (583, 3, 17);
INSERT INTO `sys_role_menu` VALUES (584, 3, 18);
INSERT INTO `sys_role_menu` VALUES (585, 3, 19);
INSERT INTO `sys_role_menu` VALUES (7845, 6, 1);
INSERT INTO `sys_role_menu` VALUES (7846, 6, 2);
INSERT INTO `sys_role_menu` VALUES (7847, 6, 9);
INSERT INTO `sys_role_menu` VALUES (7848, 6, 10);
INSERT INTO `sys_role_menu` VALUES (7849, 6, 11);
INSERT INTO `sys_role_menu` VALUES (7850, 6, 12);
INSERT INTO `sys_role_menu` VALUES (7851, 6, 13);
INSERT INTO `sys_role_menu` VALUES (7852, 6, 24);
INSERT INTO `sys_role_menu` VALUES (7853, 6, 45);
INSERT INTO `sys_role_menu` VALUES (7854, 6, 49);
INSERT INTO `sys_role_menu` VALUES (7855, 6, 3);
INSERT INTO `sys_role_menu` VALUES (7856, 6, 7);
INSERT INTO `sys_role_menu` VALUES (7857, 6, 14);
INSERT INTO `sys_role_menu` VALUES (7858, 6, 15);
INSERT INTO `sys_role_menu` VALUES (7859, 6, 16);
INSERT INTO `sys_role_menu` VALUES (7860, 6, 4);
INSERT INTO `sys_role_menu` VALUES (7861, 6, 17);
INSERT INTO `sys_role_menu` VALUES (7862, 6, 18);
INSERT INTO `sys_role_menu` VALUES (7863, 6, 19);
INSERT INTO `sys_role_menu` VALUES (7864, 6, 42);
INSERT INTO `sys_role_menu` VALUES (7865, 6, 25);
INSERT INTO `sys_role_menu` VALUES (7866, 6, 28);
INSERT INTO `sys_role_menu` VALUES (7867, 6, 29);
INSERT INTO `sys_role_menu` VALUES (7868, 6, 30);
INSERT INTO `sys_role_menu` VALUES (7869, 6, 44);
INSERT INTO `sys_role_menu` VALUES (7870, 6, 46);
INSERT INTO `sys_role_menu` VALUES (7871, 6, 47);
INSERT INTO `sys_role_menu` VALUES (7872, 6, 48);
INSERT INTO `sys_role_menu` VALUES (7873, 6, 506);
INSERT INTO `sys_role_menu` VALUES (7875, 7, 1);
INSERT INTO `sys_role_menu` VALUES (7876, 7, 2);
INSERT INTO `sys_role_menu` VALUES (7877, 7, 9);
INSERT INTO `sys_role_menu` VALUES (7878, 7, 10);
INSERT INTO `sys_role_menu` VALUES (7879, 7, 11);
INSERT INTO `sys_role_menu` VALUES (7880, 7, 12);
INSERT INTO `sys_role_menu` VALUES (7881, 7, 13);
INSERT INTO `sys_role_menu` VALUES (7882, 7, 24);
INSERT INTO `sys_role_menu` VALUES (7883, 7, 45);
INSERT INTO `sys_role_menu` VALUES (7884, 7, 49);
INSERT INTO `sys_role_menu` VALUES (7885, 7, 3);
INSERT INTO `sys_role_menu` VALUES (7886, 7, 7);
INSERT INTO `sys_role_menu` VALUES (7887, 7, 14);
INSERT INTO `sys_role_menu` VALUES (7888, 7, 15);
INSERT INTO `sys_role_menu` VALUES (7889, 7, 16);
INSERT INTO `sys_role_menu` VALUES (7890, 7, 4);
INSERT INTO `sys_role_menu` VALUES (7891, 7, 17);
INSERT INTO `sys_role_menu` VALUES (7892, 7, 18);
INSERT INTO `sys_role_menu` VALUES (7893, 7, 19);
INSERT INTO `sys_role_menu` VALUES (7894, 7, 42);
INSERT INTO `sys_role_menu` VALUES (7895, 7, 25);
INSERT INTO `sys_role_menu` VALUES (7896, 7, 28);
INSERT INTO `sys_role_menu` VALUES (7897, 7, 29);
INSERT INTO `sys_role_menu` VALUES (7898, 7, 30);
INSERT INTO `sys_role_menu` VALUES (7899, 7, 44);
INSERT INTO `sys_role_menu` VALUES (7900, 7, 46);
INSERT INTO `sys_role_menu` VALUES (7901, 7, 47);
INSERT INTO `sys_role_menu` VALUES (7902, 7, 48);
INSERT INTO `sys_role_menu` VALUES (7903, 7, 506);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `username` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `password` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `avatar` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `email` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `tel` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `created` datetime(0) NULL DEFAULT NULL,
  `updated` datetime(0) NULL DEFAULT NULL,
  `last_login` datetime(0) NULL DEFAULT NULL,
  `statu` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'admin', '$2a$10$qkqd17trzanxdyE64fEmGu1m/owbPrILMUvovyPBqtjDdvFZpLCg6', 'https://wanligo07.oss-cn-hangzhou.aliyuncs.com/2023/12/205d1f630bda1b407b84f3cd4d8dc9769414.jpeg', '123@qq.com', '13353336866', '2022-11-04 22:13:53', '2023-12-24 22:56:36', '2022-11-22 08:38:37', 1);
INSERT INTO `sys_user` VALUES (2, 'test', '$2a$10$b8a9rpVHyLxvxiNPP./9x.iamP5dRHNfnOW8Q5kj1kpYnfMvUp.y2', 'https://qyfffff.oss-cn-hangzhou.aliyuncs.com/2023/08/280a21a71e2653469fadb397e5709f272e20220623150809_33dc4.jpg', 'test@qq.com', '13353336866', '2022-11-05 08:20:22', '2023-12-15 13:25:20', NULL, 1);
INSERT INTO `sys_user` VALUES (6, 'qyf', '$2a$10$b8a9rpVHyLxvxiNPP./9x.iamP5dRHNfnOW8Q5kj1kpYnfMvUp.y2', 'https://qyfffff.oss-cn-hangzhou.aliyuncs.com/2023/08/1703f353c7b3d3443abbb256d7f745c31720220123222213_2899a.jpeg', '1397740193@qq.com', '18335999155', '2023-08-17 16:11:07', '2023-08-17 16:11:07', NULL, 1);
INSERT INTO `sys_user` VALUES (8, 'qqq', '$2a$10$b8a9rpVHyLxvxiNPP./9x.iamP5dRHNfnOW8Q5kj1kpYnfMvUp.y2', 'https://qyfffff.oss-cn-hangzhou.aliyuncs.com/2023/08/17b8ae602891f5494d9a33f8609e65931c20220325225853_b4674.jpg', '2123131312@qq.com', '2112423432', '2023-08-17 16:17:21', '2023-08-17 16:17:21', NULL, 1);
INSERT INTO `sys_user` VALUES (18, 'abcd', '$2a$10$TXuh.l4vZNgySjJkdqPMUeb3XobJeO8ToVrQCLqCm9vGaAoUd3ZLO', 'https://wanligo07.oss-cn-hangzhou.aliyuncs.com/2023/12/18614303400d2d469a983414e0f9bb8f36OIP-C.jpg', '3123', '42131231', '2023-12-18 17:27:19', NULL, NULL, 1);

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(0) NOT NULL,
  `role_id` bigint(0) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 47 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (13, 2, 3);
INSERT INTO `sys_user_role` VALUES (35, 6, 3);
INSERT INTO `sys_user_role` VALUES (36, 6, 6);
INSERT INTO `sys_user_role` VALUES (37, 6, 7);
INSERT INTO `sys_user_role` VALUES (40, 8, 11);
INSERT INTO `sys_user_role` VALUES (46, 1, 3);
INSERT INTO `sys_user_role` VALUES (47, 1, 6);

SET FOREIGN_KEY_CHECKS = 1;
