/*
Navicat MySQL Data Transfer
Source Host     : localhost:3306
Source Database : nzlive
Target Host     : localhost:3306
Target Database : nzlive
Date: 2018-10-12 18:07:21
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student` (
  `userid` varchar(10) NOT NULL,
  `userpwd` varchar(16) DEFAULT NULL,
  `dormroom` varchar(10) DEFAULT NULL,
  `username` longtext CHARACTER SET utf8 COLLATE utf8_swedish_ci,
  PRIMARY KEY (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES ('2016041111', '123456', '2#504', '张三');
INSERT INTO `student` VALUES ('2016041112', '123456', '2#505', '李四');
INSERT INTO `student` VALUES ('2016041113', '123456', '2#506', '王五');

-- ----------------------------
-- Table structure for teacher
-- ----------------------------
DROP TABLE IF EXISTS `teacher`;
CREATE TABLE `teacher` (
  `userid` varchar(10) NOT NULL,
  `userpwd` varchar(20) DEFAULT NULL,
  `username` longtext CHARACTER SET utf8 COLLATE utf8_swedish_ci,
  `system` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of teacher
-- ----------------------------
INSERT INTO `teacher` VALUES ('111111', '123456', '张三老师', '04');
