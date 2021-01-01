/*
 Navicat Premium Data Transfer

 Source Server         : 我的电脑
 Source Server Type    : MySQL
 Source Server Version : 50722
 Source Host           : localhost:3306
 Source Schema         : scholar-system

 Target Server Type    : MySQL
 Target Server Version : 50722
 File Encoding         : 65001

 Date: 01/01/2021 09:35:29
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for depart
-- ----------------------------
DROP TABLE IF EXISTS `depart`;
CREATE TABLE `depart`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '机构名称',
  `type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '机构类型(school/college/class)',
  `parentId` int(255) NULL DEFAULT -1 COMMENT '上级机构',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of depart
-- ----------------------------
INSERT INTO `depart` VALUES (1, '汉东大学', 'school', -1);
INSERT INTO `depart` VALUES (2, '计算机学院', 'college', 1);
INSERT INTO `depart` VALUES (3, '计算机1班', 'school', 2);

-- ----------------------------
-- Table structure for flow
-- ----------------------------
DROP TABLE IF EXISTS `flow`;
CREATE TABLE `flow`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `studentId` int(11) NULL DEFAULT NULL,
  `studentName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `projectId` int(11) NULL DEFAULT NULL,
  `projectName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `classUserId` int(11) NULL DEFAULT NULL,
  `classAdvice` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `collegeUserId` int(11) NULL DEFAULT NULL,
  `collegeAdvice` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `schoolUserId` int(11) NULL DEFAULT NULL,
  `schoolAdvice` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `currentUserId` int(11) NULL DEFAULT NULL,
  `currentNode` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'class/college/shcool/success/fail',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of flow
-- ----------------------------
INSERT INTO `flow` VALUES (3, 3, 'chen', 1, '国家奖学金', '学习成绩优异，道德品质良好，特此申请', 5, '该生表现良好，同意！', 4, '同意', 1, '1', 3, 'success');

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 29 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES (1, '项目管理', 'projectManage.html');
INSERT INTO `menu` VALUES (2, '机构管理', 'departManage.html');
INSERT INTO `menu` VALUES (3, '人员管理', 'userManage.html');
INSERT INTO `menu` VALUES (4, '权限管理', 'powerManage.html');
INSERT INTO `menu` VALUES (5, '问题反馈', 'questionAsk.html');
INSERT INTO `menu` VALUES (6, '问题答复', 'questionReply.html');
INSERT INTO `menu` VALUES (7, '奖助学金申请', 'studentApply.html');
INSERT INTO `menu` VALUES (8, '审核', 'audit.html');

-- ----------------------------
-- Table structure for project
-- ----------------------------
DROP TABLE IF EXISTS `project`;
CREATE TABLE `project`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT 'award/help',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `about` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of project
-- ----------------------------
INSERT INTO `project` VALUES (1, 'award', '国家奖学金', '8000');
INSERT INTO `project` VALUES (2, 'award', '国家励志奖学金', '5000');
INSERT INTO `project` VALUES (3, 'award', '人民助学奖学金', '2000');

-- ----------------------------
-- Table structure for question
-- ----------------------------
DROP TABLE IF EXISTS `question`;
CREATE TABLE `question`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) NULL DEFAULT NULL,
  `userName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `departId` int(11) NULL DEFAULT NULL,
  `departName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `reply` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of question
-- ----------------------------
INSERT INTO `question` VALUES (1, 3, 'chen', 3, 'xxxx', 'ddddd', NULL);
INSERT INTO `question` VALUES (2, 3, 'chen', 3, '计算机1班', '学费贵', NULL);
INSERT INTO `question` VALUES (3, 4, '4', 3, '4', '44', NULL);

-- ----------------------------
-- Table structure for rolemenu
-- ----------------------------
DROP TABLE IF EXISTS `rolemenu`;
CREATE TABLE `rolemenu`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `menuId` int(11) NULL DEFAULT -1,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 62 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of rolemenu
-- ----------------------------
INSERT INTO `rolemenu` VALUES (51, 'schoolmaster', 1);
INSERT INTO `rolemenu` VALUES (52, 'schoolmaster', 2);
INSERT INTO `rolemenu` VALUES (53, 'schoolmaster', 3);
INSERT INTO `rolemenu` VALUES (54, 'schoolmaster', 4);
INSERT INTO `rolemenu` VALUES (55, 'schoolmaster', 6);
INSERT INTO `rolemenu` VALUES (57, 'student', 5);
INSERT INTO `rolemenu` VALUES (58, 'student', 7);
INSERT INTO `rolemenu` VALUES (59, 'schoolmaster', 8);
INSERT INTO `rolemenu` VALUES (60, 'collegemaster', 8);
INSERT INTO `rolemenu` VALUES (61, 'classmaster', 8);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `loginName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `role` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT 'schoolmaster/collegemaster/classmaster/student',
  `departId` int(11) NULL DEFAULT -1,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, '管理员', 'school', '111', 'schoolmaster', 1);
INSERT INTO `user` VALUES (3, 'chen', 'stu', '111', 'student', 3);
INSERT INTO `user` VALUES (4, '院管', 'col', '111', 'collegemaster', 2);
INSERT INTO `user` VALUES (5, '班管', 'cla', '111', 'classmaster', 3);

SET FOREIGN_KEY_CHECKS = 1;
