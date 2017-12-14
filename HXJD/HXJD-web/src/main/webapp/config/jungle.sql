/*
Navicat MySQL Data Transfer

Source Server         : d
Source Server Version : 50528
Source Host           : localhost:3306
Source Database       : jungle

Target Server Type    : MYSQL
Target Server Version : 50528
File Encoding         : 65001

Date: 2017-05-11 13:08:48
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for j_button
-- ----------------------------
DROP TABLE IF EXISTS `j_button`;
CREATE TABLE `j_button` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `menuId` int(11) DEFAULT NULL,
  `name` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `code` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `url` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1893 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of j_button
-- ----------------------------
INSERT INTO `j_button` VALUES ('94', '19', '增', 'add', '');
INSERT INTO `j_button` VALUES ('95', '19', '删', 'del', '');
INSERT INTO `j_button` VALUES ('96', '19', '改', 'edit', '');
INSERT INTO `j_button` VALUES ('97', '19', '查', 'sel', '');
INSERT INTO `j_button` VALUES ('98', '13', '增', 'add', '');
INSERT INTO `j_button` VALUES ('99', '13', '删', 'del', '');
INSERT INTO `j_button` VALUES ('100', '13', '改', 'edit', '');
INSERT INTO `j_button` VALUES ('101', '13', '查', 'sel', '');
INSERT INTO `j_button` VALUES ('106', '16', '增', 'add', 'a');
INSERT INTO `j_button` VALUES ('107', '16', '删', 'del', 'b');
INSERT INTO `j_button` VALUES ('108', '16', '改', 'edit', 'c');
INSERT INTO `j_button` VALUES ('109', '16', '查', 'sel', 'd');
INSERT INTO `j_button` VALUES ('122', '1', '增', 'add', '');
INSERT INTO `j_button` VALUES ('123', '1', '删', 'del', '');
INSERT INTO `j_button` VALUES ('124', '1', '改', 'edit', '');
INSERT INTO `j_button` VALUES ('125', '1', '查', 'sel', '');
INSERT INTO `j_button` VALUES ('126', '3', '增', 'add', '');
INSERT INTO `j_button` VALUES ('127', '3', '删', 'del', '');
INSERT INTO `j_button` VALUES ('128', '3', '改', 'edit', '');
INSERT INTO `j_button` VALUES ('129', '3', '查', 'sel', '');
INSERT INTO `j_button` VALUES ('130', '20', '增', 'add', '');
INSERT INTO `j_button` VALUES ('131', '20', '删', 'del', '');
INSERT INTO `j_button` VALUES ('132', '20', '改', 'edit', '');
INSERT INTO `j_button` VALUES ('133', '20', '查', 'sel', '');
INSERT INTO `j_button` VALUES ('134', '21', '增', 'add', '');
INSERT INTO `j_button` VALUES ('135', '21', '删', 'del', '');
INSERT INTO `j_button` VALUES ('136', '21', '改', 'edit', '');
INSERT INTO `j_button` VALUES ('137', '21', '查', 'sel', '');
INSERT INTO `j_button` VALUES ('138', '22', '增', 'add', '');
INSERT INTO `j_button` VALUES ('139', '22', '删', 'del', '');
INSERT INTO `j_button` VALUES ('140', '22', '改', 'edit', '');
INSERT INTO `j_button` VALUES ('141', '22', '查', 'sel', '');
INSERT INTO `j_button` VALUES ('142', '23', '增', 'add', '');
INSERT INTO `j_button` VALUES ('143', '23', '删', 'del', '');
INSERT INTO `j_button` VALUES ('144', '23', '改', 'edit', '');
INSERT INTO `j_button` VALUES ('145', '23', '查', 'sel', '');
INSERT INTO `j_button` VALUES ('146', '24', '增', 'add', '');
INSERT INTO `j_button` VALUES ('147', '24', '删', 'del', '');
INSERT INTO `j_button` VALUES ('148', '24', '改', 'edit', '');
INSERT INTO `j_button` VALUES ('149', '24', '查', 'sel', '');
INSERT INTO `j_button` VALUES ('150', '25', '增', 'add', '');
INSERT INTO `j_button` VALUES ('151', '25', '删', 'del', '');
INSERT INTO `j_button` VALUES ('152', '25', '改', 'edit', '');
INSERT INTO `j_button` VALUES ('153', '25', '查', 'sel', '');
INSERT INTO `j_button` VALUES ('154', '26', '增', 'add', '');
INSERT INTO `j_button` VALUES ('155', '26', '删', 'del', '');
INSERT INTO `j_button` VALUES ('156', '26', '改', 'edit', '');
INSERT INTO `j_button` VALUES ('157', '26', '查', 'sel', '');
INSERT INTO `j_button` VALUES ('158', '27', '增', 'add', '');
INSERT INTO `j_button` VALUES ('159', '27', '删', 'del', '');
INSERT INTO `j_button` VALUES ('160', '27', '改', 'edit', '');
INSERT INTO `j_button` VALUES ('161', '27', '查', 'sel', '');
INSERT INTO `j_button` VALUES ('162', '28', '增', 'add', '');
INSERT INTO `j_button` VALUES ('163', '28', '删', 'del', '');
INSERT INTO `j_button` VALUES ('164', '28', '改', 'edit', '');
INSERT INTO `j_button` VALUES ('165', '28', '查', 'sel', '');
INSERT INTO `j_button` VALUES ('166', '12', '增', 'add', '');
INSERT INTO `j_button` VALUES ('167', '12', '删', 'del', '');
INSERT INTO `j_button` VALUES ('168', '12', '改', 'edit', '');
INSERT INTO `j_button` VALUES ('169', '12', '查', 'sel', '');
INSERT INTO `j_button` VALUES ('182', '32', '增', 'add', null);
INSERT INTO `j_button` VALUES ('183', '32', '删', 'del', null);
INSERT INTO `j_button` VALUES ('184', '32', '改', 'edit', null);
INSERT INTO `j_button` VALUES ('185', '32', '查', 'sel', null);
INSERT INTO `j_button` VALUES ('186', '33', '增', 'add', null);
INSERT INTO `j_button` VALUES ('187', '33', '删', 'del', null);
INSERT INTO `j_button` VALUES ('188', '33', '改', 'edit', null);
INSERT INTO `j_button` VALUES ('189', '33', '查', 'sel', null);
INSERT INTO `j_button` VALUES ('1860', '18', '增', 'add', '/admin/user/add');
INSERT INTO `j_button` VALUES ('1870', '18', '删', 'del', '/admin/user/del');
INSERT INTO `j_button` VALUES ('1880', '18', '改', 'edit', '/admin/user/edit');
INSERT INTO `j_button` VALUES ('1890', '18', '查', 'sel', '/admin/user/sel');
INSERT INTO `j_button` VALUES ('1892', '18', '导出excel', 'excel', '/admin/user/excel');

-- ----------------------------
-- Table structure for j_job
-- ----------------------------
DROP TABLE IF EXISTS `j_job`;
CREATE TABLE `j_job` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `jobName` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '任务名称',
  `jobGroup` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '任务分组',
  `jobStatus` int(1) DEFAULT NULL COMMENT '任务状态 0禁用 1启用 2删除',
  `cronExp` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '任务运行时间表达式 ',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of j_job
-- ----------------------------

-- ----------------------------
-- Table structure for j_menu
-- ----------------------------
DROP TABLE IF EXISTS `j_menu`;
CREATE TABLE `j_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `parent` int(11) DEFAULT NULL,
  `name` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `url` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `tag` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `ico` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `sort` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of j_menu
-- ----------------------------
INSERT INTO `j_menu` VALUES ('1', '0', '项目管理', '', 'userManage', 'fa-flag', '1');
INSERT INTO `j_menu` VALUES ('3', '1', '项目管理', 'admin/safe', 'safe', '', '1');
INSERT INTO `j_menu` VALUES ('12', '0', '系统管理', '', 'settingManage', 'fa-cog', '4');
INSERT INTO `j_menu` VALUES ('13', '12', '菜单管理', 'admin/menu', 'menu', '', '3');
INSERT INTO `j_menu` VALUES ('16', '12', '参数设置', 'admin/option', 'option', '', '5');
INSERT INTO `j_menu` VALUES ('18', '12', '用户管理', 'admin/user', 'user', '', '1');
INSERT INTO `j_menu` VALUES ('19', '12', '角色管理', 'admin/role', 'role', '', '2');
INSERT INTO `j_menu` VALUES ('20', '0', '信息公布', '', 'mess', 'fa-rss', '2');
INSERT INTO `j_menu` VALUES ('21', '20', '总体信息', 'admin/messPublic', 'messPublic', '', '1');
INSERT INTO `j_menu` VALUES ('22', '20', '信息交底', 'admin/disclosure', 'disclosure', '', '2');
INSERT INTO `j_menu` VALUES ('23', '20', '工程进度', 'admin/jobprogress', 'jobprogress', '', '3');
INSERT INTO `j_menu` VALUES ('24', '20', '设备状态', 'admin/DStates', 'DStates', '', '4');
INSERT INTO `j_menu` VALUES ('25', '24', '龙门吊', 'admin/DStates/rtgc', 'rtgc', '', '1');
INSERT INTO `j_menu` VALUES ('26', '24', '盾构机', 'admin/DStates/tbm', 'tbm', '', '2');
INSERT INTO `j_menu` VALUES ('27', '24', '排水系统', 'admin/DStates/drainagesystem', 'drainagesystem', '', '3');
INSERT INTO `j_menu` VALUES ('28', '20', '环境检测', 'admin/envmoni', 'envmoni', '', '5');
INSERT INTO `j_menu` VALUES ('32', '0', '系统设置', '', 'sys', 'fa-puzzle-piece', '3');
INSERT INTO `j_menu` VALUES ('33', '32', '信息类型', 'admin/messType', 'messType', '', '1');

-- ----------------------------
-- Table structure for j_messpublic
-- ----------------------------
DROP TABLE IF EXISTS `j_messpublic`;
CREATE TABLE `j_messpublic` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `code` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `content` text COLLATE utf8mb4_unicode_ci,
  `creatime` datetime DEFAULT NULL,
  `safeId` int(11) DEFAULT NULL,
  `typeId` int(11) DEFAULT NULL,
  `creatUserId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of j_messpublic
-- ----------------------------
INSERT INTO `j_messpublic` VALUES ('1', '2221', '2221', '<p>							</p><p>ffdfdf<br/></p><p>&nbsp;					 			</p><p>&nbsp;					 			</p><p>&nbsp;					 			</p><p>&nbsp;					 			</p>', '2017-05-09 10:47:11', null, null, null);
INSERT INTO `j_messpublic` VALUES ('2', '2017年第一季度考核信息', 'jdkh-001', '<p>						</p><p style=\"text-align: center;\"><strong style=\"font-size: 20px; text-align: center; white-space: normal; background-color: rgb(255, 255, 255);\"><br/></strong></p><p style=\"text-align: center;\"><strong style=\"font-size: 20px; text-align: center; white-space: normal; background-color: rgb(255, 255, 255);\">XX工地XXXX年第X季度考勤信息</strong></p><p><strong style=\"font-size: 20px; text-align: center; white-space: normal; background-color: rgb(255, 255, 255);\"></strong></p><hr/><p><br/><strong style=\"font-size: 20px; text-align: center; white-space: normal; background-color: rgb(255, 255, 255);\"></strong></p><table align=\"center\" width=\"1151\"><tbody><tr class=\"firstRow\"><td valign=\"middle\" style=\"word-break: break-all; border-color: rgb(221, 221, 221);\" align=\"center\" rowspan=\"2\" colspan=\"1\" width=\"106\"><p>XXXX年第X季度</p></td><td valign=\"top\" style=\"word-break: break-all; border-color: rgb(221, 221, 221);\" rowspan=\"1\" colspan=\"2\" align=\"center\" width=\"251\">1月</td><td valign=\"top\" style=\"word-break: break-all; border-color: rgb(221, 221, 221);\" rowspan=\"1\" colspan=\"2\" align=\"center\" width=\"175\">2月</td><td valign=\"top\" style=\"word-break: break-all; border-color: rgb(221, 221, 221);\" rowspan=\"1\" colspan=\"2\" align=\"center\" width=\"175\">3月<br/></td></tr><tr><td valign=\"top\" style=\"word-break: break-all; border-color: rgb(221, 221, 221);\" align=\"center\" width=\"125\">上班</td><td valign=\"top\" style=\"word-break: break-all; border-color: rgb(221, 221, 221);\" align=\"center\" width=\"125\">下班</td><td valign=\"top\" align=\"center\" style=\"word-break: break-all; border-color: rgb(221, 221, 221);\" width=\"87\"><span style=\"text-align: -webkit-center;\">上班</span></td><td valign=\"top\" align=\"center\" style=\"word-break: break-all; border-color: rgb(221, 221, 221);\" width=\"87\"><span style=\"text-align: -webkit-center;\">下班</span></td><td valign=\"top\" align=\"center\" style=\"word-break: break-all; border-color: rgb(221, 221, 221);\" width=\"87\"><span style=\"text-align: -webkit-center;\">上班</span></td><td valign=\"top\" align=\"center\" style=\"word-break: break-all; border-color: rgb(221, 221, 221);\" width=\"87\"><span style=\"text-align: -webkit-center;\">下班</span></td></tr><tr><td valign=\"top\" style=\"border-color: rgb(221, 221, 221);\" width=\"106\"><br/></td><td valign=\"top\" style=\"border-color: rgb(221, 221, 221);\" width=\"125\"><br/></td><td valign=\"top\" style=\"border-color: rgb(221, 221, 221);\" width=\"125\"><br/></td><td valign=\"top\" style=\"border-color: rgb(221, 221, 221);\" width=\"87\"><br/></td><td valign=\"top\" style=\"border-color: rgb(221, 221, 221);\" width=\"87\"><br/></td><td valign=\"top\" style=\"border-color: rgb(221, 221, 221);\" width=\"87\"><br/></td><td valign=\"top\" style=\"border-color: rgb(221, 221, 221);\" width=\"87\"><br/></td></tr><tr><td valign=\"top\" style=\"border-color: rgb(221, 221, 221);\" width=\"106\"><br/></td><td valign=\"top\" style=\"border-color: rgb(221, 221, 221);\" width=\"125\"><br/></td><td valign=\"top\" style=\"border-color: rgb(221, 221, 221);\" width=\"125\"><br/></td><td valign=\"top\" style=\"border-color: rgb(221, 221, 221);\" width=\"87\"><br/></td><td valign=\"top\" style=\"border-color: rgb(221, 221, 221);\" width=\"87\"><br/></td><td valign=\"top\" style=\"border-color: rgb(221, 221, 221);\" width=\"87\"><br/></td><td valign=\"top\" style=\"border-color: rgb(221, 221, 221);\" width=\"87\"><br/></td></tr><tr><td valign=\"top\" colspan=\"1\" rowspan=\"1\" style=\"border-color: rgb(221, 221, 221);\" width=\"106\"><br/></td><td valign=\"top\" colspan=\"1\" rowspan=\"1\" style=\"border-color: rgb(221, 221, 221);\" width=\"125\"><br/></td><td valign=\"top\" colspan=\"1\" rowspan=\"1\" style=\"border-color: rgb(221, 221, 221);\" width=\"125\"><br/></td><td valign=\"top\" colspan=\"1\" rowspan=\"1\" style=\"border-color: rgb(221, 221, 221);\" width=\"87\"><br/></td><td valign=\"top\" colspan=\"1\" rowspan=\"1\" style=\"border-color: rgb(221, 221, 221);\" width=\"87\"><br/></td><td valign=\"top\" colspan=\"1\" rowspan=\"1\" style=\"border-color: rgb(221, 221, 221);\" width=\"87\"><br/></td><td valign=\"top\" colspan=\"1\" rowspan=\"1\" style=\"border-color: rgb(221, 221, 221);\" width=\"87\"><br/></td></tr></tbody></table><p><br/></p><p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;					 		</p><p>&nbsp;					 		</p><p>&nbsp;					 		</p>', '2017-05-10 17:16:08', '1', '2', '1');
INSERT INTO `j_messpublic` VALUES ('10', '2017年第二季度考核信息', 'jdkh-002', '<p>						</p><p style=\"text-align: center;\"><strong style=\"font-size: 20px; text-align: center; white-space: normal; background-color: rgb(255, 255, 255);\"><br/></strong></p><p style=\"text-align: center;\"><strong style=\"font-size: 20px; text-align: center; white-space: normal; background-color: rgb(255, 255, 255);\">XX工地XXXX年第X季度考勤信息</strong></p><p><strong style=\"font-size: 20px; text-align: center; white-space: normal; background-color: rgb(255, 255, 255);\"></strong></p><hr/><p><br/><strong style=\"font-size: 20px; text-align: center; white-space: normal; background-color: rgb(255, 255, 255);\"></strong></p><table align=\"center\" width=\"1151\"><tbody><tr class=\"firstRow\"><td valign=\"middle\" style=\"word-break: break-all; border-color: rgb(221, 221, 221);\" align=\"center\" rowspan=\"2\" colspan=\"1\" width=\"106\"><p>XXXX年第X季度</p></td><td valign=\"top\" style=\"word-break: break-all; border-color: rgb(221, 221, 221);\" rowspan=\"1\" colspan=\"2\" align=\"center\" width=\"251\">1月</td><td valign=\"top\" style=\"word-break: break-all; border-color: rgb(221, 221, 221);\" rowspan=\"1\" colspan=\"2\" align=\"center\" width=\"175\">2月</td><td valign=\"top\" style=\"word-break: break-all; border-color: rgb(221, 221, 221);\" rowspan=\"1\" colspan=\"2\" align=\"center\" width=\"175\">3月<br/></td></tr><tr><td valign=\"top\" style=\"word-break: break-all; border-color: rgb(221, 221, 221);\" align=\"center\" width=\"125\">上班</td><td valign=\"top\" style=\"word-break: break-all; border-color: rgb(221, 221, 221);\" align=\"center\" width=\"125\">下班</td><td valign=\"top\" align=\"center\" style=\"word-break: break-all; border-color: rgb(221, 221, 221);\" width=\"87\"><span style=\"text-align: -webkit-center;\">上班</span></td><td valign=\"top\" align=\"center\" style=\"word-break: break-all; border-color: rgb(221, 221, 221);\" width=\"87\"><span style=\"text-align: -webkit-center;\">下班</span></td><td valign=\"top\" align=\"center\" style=\"word-break: break-all; border-color: rgb(221, 221, 221);\" width=\"87\"><span style=\"text-align: -webkit-center;\">上班</span></td><td valign=\"top\" align=\"center\" style=\"word-break: break-all; border-color: rgb(221, 221, 221);\" width=\"87\"><span style=\"text-align: -webkit-center;\">下班</span></td></tr><tr><td valign=\"top\" style=\"border-color: rgb(221, 221, 221);\" width=\"106\"><br/></td><td valign=\"top\" style=\"border-color: rgb(221, 221, 221);\" width=\"125\"><br/></td><td valign=\"top\" style=\"border-color: rgb(221, 221, 221);\" width=\"125\"><br/></td><td valign=\"top\" style=\"border-color: rgb(221, 221, 221);\" width=\"87\"><br/></td><td valign=\"top\" style=\"border-color: rgb(221, 221, 221);\" width=\"87\"><br/></td><td valign=\"top\" style=\"border-color: rgb(221, 221, 221);\" width=\"87\"><br/></td><td valign=\"top\" style=\"border-color: rgb(221, 221, 221);\" width=\"87\"><br/></td></tr><tr><td valign=\"top\" style=\"border-color: rgb(221, 221, 221);\" width=\"106\"><br/></td><td valign=\"top\" style=\"border-color: rgb(221, 221, 221);\" width=\"125\"><br/></td><td valign=\"top\" style=\"border-color: rgb(221, 221, 221);\" width=\"125\"><br/></td><td valign=\"top\" style=\"border-color: rgb(221, 221, 221);\" width=\"87\"><br/></td><td valign=\"top\" style=\"border-color: rgb(221, 221, 221);\" width=\"87\"><br/></td><td valign=\"top\" style=\"border-color: rgb(221, 221, 221);\" width=\"87\"><br/></td><td valign=\"top\" style=\"border-color: rgb(221, 221, 221);\" width=\"87\"><br/></td></tr><tr><td valign=\"top\" colspan=\"1\" rowspan=\"1\" style=\"border-color: rgb(221, 221, 221);\" width=\"106\"><br/></td><td valign=\"top\" colspan=\"1\" rowspan=\"1\" style=\"border-color: rgb(221, 221, 221);\" width=\"125\"><br/></td><td valign=\"top\" colspan=\"1\" rowspan=\"1\" style=\"border-color: rgb(221, 221, 221);\" width=\"125\"><br/></td><td valign=\"top\" colspan=\"1\" rowspan=\"1\" style=\"border-color: rgb(221, 221, 221);\" width=\"87\"><br/></td><td valign=\"top\" colspan=\"1\" rowspan=\"1\" style=\"border-color: rgb(221, 221, 221);\" width=\"87\"><br/></td><td valign=\"top\" colspan=\"1\" rowspan=\"1\" style=\"border-color: rgb(221, 221, 221);\" width=\"87\"><br/></td><td valign=\"top\" colspan=\"1\" rowspan=\"1\" style=\"border-color: rgb(221, 221, 221);\" width=\"87\"><br/></td></tr></tbody></table><p><br/></p><p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;					 		</p><p>&nbsp;					 		</p><p>&nbsp;					 		</p>', '2017-05-11 10:00:52', '3', '2', '1');
INSERT INTO `j_messpublic` VALUES ('11', '2017年五月考勤信息', 'ydkq-001', '<p>						</p><p style=\"text-align: center;\"><strong style=\"font-size: 20px; text-align: center; white-space: normal; background-color: rgb(255, 255, 255);\"><br/></strong></p><p style=\"text-align: center;\"><strong style=\"font-size: 20px; text-align: center; white-space: normal; background-color: rgb(255, 255, 255);\">XX工地XXXX年X月考勤信息</strong></p><p><strong style=\"font-size: 20px; text-align: center; white-space: normal; background-color: rgb(255, 255, 255);\"></strong></p><hr/><p><strong style=\"font-size: 20px; text-align: center; white-space: normal; background-color: rgb(255, 255, 255);\"></strong><br/></p><p><br/></p><p><span style=\"font-size: 20px;\"></span></p><p><br/></p><table align=\"center\"><tbody><tr class=\"firstRow\"><td valign=\"middle\" style=\"word-break: break-all; border-color: rgb(221, 221, 221);\" align=\"center\" rowspan=\"2\" colspan=\"1\" width=\"265\"><p>XXXX年X月</p></td><td valign=\"top\" style=\"word-break: break-all; border-color: rgb(221, 221, 221);\" rowspan=\"1\" colspan=\"2\" align=\"center\">1号</td><td valign=\"top\" style=\"word-break: break-all; border-color: rgb(221, 221, 221);\" rowspan=\"1\" colspan=\"2\" align=\"center\">2号</td><td valign=\"top\" style=\"word-break: break-all; border-color: rgb(221, 221, 221);\" rowspan=\"1\" colspan=\"2\" align=\"center\" width=\"237\">3号<br/></td><td valign=\"top\" align=\"center\" rowspan=\"1\" colspan=\"2\" style=\"word-break: break-all; border-color: rgb(221, 221, 221);\" width=\"342\">4号</td><td valign=\"top\" align=\"center\" rowspan=\"1\" colspan=\"2\" style=\"border-color: rgb(221, 221, 221);\">5号</td><td valign=\"top\" align=\"center\" rowspan=\"1\" colspan=\"2\" style=\"border-color: rgb(221, 221, 221);\">6号<br/></td></tr><tr><td valign=\"top\" style=\"word-break: break-all; border-color: rgb(221, 221, 221);\" align=\"center\" width=\"134\">上班</td><td valign=\"top\" style=\"word-break: break-all; border-color: rgb(221, 221, 221);\" align=\"center\" width=\"134\">下班</td><td valign=\"top\" align=\"center\" style=\"word-break: break-all; border-color: rgb(221, 221, 221);\" width=\"134\"><span style=\"text-align: -webkit-center;\">上班</span></td><td valign=\"top\" align=\"center\" style=\"word-break: break-all; border-color: rgb(221, 221, 221);\" width=\"134\"><span style=\"text-align: -webkit-center;\">下班</span></td><td valign=\"top\" align=\"center\" style=\"word-break: break-all; border-color: rgb(221, 221, 221);\" width=\"134\"><span style=\"text-align: -webkit-center;\">上班</span></td><td valign=\"top\" align=\"center\" style=\"word-break: break-all; border-color: rgb(221, 221, 221);\" width=\"134\"><span style=\"text-align: -webkit-center;\">下班</span></td><td valign=\"top\" align=\"center\" style=\"word-break: break-all; border-color: rgb(221, 221, 221);\" width=\"212\"><span style=\"text-align: -webkit-center;\">上班</span></td><td valign=\"top\" align=\"center\" style=\"word-break: break-all; border-color: rgb(221, 221, 221);\" width=\"83\"><span style=\"text-align: -webkit-center;\">下班</span></td><td valign=\"top\" align=\"center\" rowspan=\"1\" colspan=\"1\" style=\"border-color: rgb(221, 221, 221);\" width=\"216\"><span style=\"text-align: -webkit-center;\">上班</span></td><td valign=\"top\" align=\"center\" rowspan=\"1\" colspan=\"1\" style=\"border-color: rgb(221, 221, 221);\" width=\"134\"><span style=\"text-align: -webkit-center;\">下班</span></td><td valign=\"top\" align=\"center\" rowspan=\"1\" colspan=\"1\" style=\"border-color: rgb(221, 221, 221);\" width=\"134\"><span style=\"text-align: -webkit-center;\">上班</span></td><td valign=\"top\" align=\"center\" rowspan=\"1\" colspan=\"1\" style=\"border-color: rgb(221, 221, 221);\" width=\"134\"><span style=\"text-align: -webkit-center;\">下班</span></td></tr><tr><td valign=\"top\" style=\"border-color: rgb(221, 221, 221); word-break: break-all;\" width=\"134\" align=\"center\">张三</td><td valign=\"top\" style=\"border-color: rgb(221, 221, 221); word-break: break-all;\" width=\"134\" align=\"center\">√</td><td valign=\"top\" style=\"border-color: rgb(221, 221, 221); background-color: rgb(255, 0, 0);\" width=\"134\" align=\"center\">×</td><td valign=\"top\" style=\"border-color: rgb(221, 221, 221); word-break: break-all;\" width=\"134\" align=\"center\">√</td><td valign=\"top\" style=\"border-color: rgb(221, 221, 221); word-break: break-all;\" width=\"134\" align=\"center\">√</td><td valign=\"top\" style=\"border-color: rgb(221, 221, 221); word-break: break-all;\" width=\"134\" align=\"center\">√</td><td valign=\"top\" style=\"border-color: rgb(221, 221, 221); word-break: break-all;\" width=\"107\" align=\"center\">√</td><td valign=\"top\" style=\"border-color: rgb(221, 221, 221); word-break: break-all;\" width=\"119\" align=\"center\">√</td><td valign=\"top\" style=\"border-color: rgb(221, 221, 221); word-break: break-all;\" width=\"216\" align=\"center\">√</td><td valign=\"top\" align=\"center\" rowspan=\"1\" colspan=\"1\" style=\"border-color: rgb(221, 221, 221); word-break: break-all;\" width=\"133\">√</td><td valign=\"top\" align=\"center\" rowspan=\"1\" colspan=\"1\" style=\"border-color: rgb(221, 221, 221); word-break: break-all;\" width=\"134\">√</td><td valign=\"top\" align=\"center\" rowspan=\"1\" colspan=\"1\" style=\"border-color: rgb(221, 221, 221); word-break: break-all;\" width=\"134\">√</td><td valign=\"top\" align=\"center\" rowspan=\"1\" colspan=\"1\" style=\"border-color: rgb(221, 221, 221); word-break: break-all;\" width=\"134\">√</td></tr><tr><td valign=\"top\" style=\"border-color: rgb(221, 221, 221); word-break: break-all;\" width=\"134\" align=\"center\">李四</td><td valign=\"top\" style=\"border-color: rgb(221, 221, 221); word-break: break-all;\" width=\"134\" align=\"center\">√</td><td valign=\"top\" style=\"border-color: rgb(221, 221, 221); word-break: break-all;\" width=\"134\" align=\"center\">√</td><td valign=\"top\" style=\"border-color: rgb(221, 221, 221); word-break: break-all;\" width=\"134\" align=\"center\">√</td><td valign=\"top\" style=\"border-color: rgb(221, 221, 221); word-break: break-all;\" width=\"134\" align=\"center\">√</td><td valign=\"top\" style=\"border-color: rgb(221, 221, 221); word-break: break-all;\" width=\"134\" align=\"center\">√</td><td valign=\"top\" style=\"border-color: rgb(221, 221, 221); word-break: break-all;\" width=\"107\" align=\"center\">√</td><td valign=\"top\" style=\"border-color: rgb(221, 221, 221); word-break: break-all;\" width=\"119\" align=\"center\">√</td><td valign=\"top\" style=\"border-color: rgb(221, 221, 221); word-break: break-all;\" width=\"216\" align=\"center\">√</td><td valign=\"top\" align=\"center\" rowspan=\"1\" colspan=\"1\" style=\"border-color: rgb(221, 221, 221); word-break: break-all;\" width=\"133\">√</td><td valign=\"top\" align=\"center\" rowspan=\"1\" colspan=\"1\" style=\"border-color: rgb(221, 221, 221); word-break: break-all;\" width=\"134\">√</td><td valign=\"top\" align=\"center\" rowspan=\"1\" colspan=\"1\" style=\"border-color: rgb(221, 221, 221); word-break: break-all;\" width=\"134\">√</td><td valign=\"top\" align=\"center\" rowspan=\"1\" colspan=\"1\" style=\"border-color: rgb(221, 221, 221); word-break: break-all;\" width=\"134\">√</td></tr><tr><td valign=\"top\" colspan=\"1\" rowspan=\"1\" style=\"border-color: rgb(221, 221, 221); word-break: break-all;\" width=\"134\" align=\"center\">王五</td><td valign=\"top\" colspan=\"1\" rowspan=\"1\" style=\"border-color: rgb(221, 221, 221); word-break: break-all;\" width=\"134\" align=\"center\">√</td><td valign=\"top\" colspan=\"1\" rowspan=\"1\" style=\"border-color: rgb(221, 221, 221); word-break: break-all;\" width=\"134\" align=\"center\">√</td><td valign=\"top\" colspan=\"1\" rowspan=\"1\" style=\"border-color: rgb(221, 221, 221); word-break: break-all;\" width=\"134\" align=\"center\">√</td><td valign=\"top\" colspan=\"1\" rowspan=\"1\" style=\"border-color: rgb(221, 221, 221); word-break: break-all;\" width=\"134\" align=\"center\">√</td><td valign=\"top\" colspan=\"1\" rowspan=\"1\" style=\"border-color: rgb(221, 221, 221); word-break: break-all;\" width=\"134\" align=\"center\">√</td><td valign=\"top\" colspan=\"1\" rowspan=\"1\" style=\"border-color: rgb(221, 221, 221); word-break: break-all;\" width=\"107\" align=\"center\">√</td><td valign=\"top\" colspan=\"1\" rowspan=\"1\" style=\"border-color: rgb(221, 221, 221); word-break: break-all;\" width=\"119\" align=\"center\">√</td><td valign=\"top\" colspan=\"1\" rowspan=\"1\" style=\"border-color: rgb(221, 221, 221); word-break: break-all;\" width=\"NaN\" align=\"center\">√</td><td valign=\"top\" align=\"center\" rowspan=\"1\" colspan=\"1\" style=\"border-color: rgb(221, 221, 221); word-break: break-all;\" width=\"undefined\">√</td><td valign=\"top\" align=\"center\" rowspan=\"1\" colspan=\"1\" style=\"border-color: rgb(221, 221, 221); word-break: break-all;\" width=\"undefined\">√</td><td valign=\"top\" align=\"center\" rowspan=\"1\" colspan=\"1\" style=\"border-color: rgb(221, 221, 221); word-break: break-all;\" width=\"undefined\">√</td><td valign=\"top\" align=\"center\" rowspan=\"1\" colspan=\"1\" style=\"border-color: rgb(221, 221, 221); word-break: break-all;\" width=\"undefined\">√</td></tr></tbody></table><p><br/></p><p>&nbsp;					 		</p><p>&nbsp;					 		</p><p>&nbsp;					 		</p>', '2017-05-11 10:07:47', '2', '1', '1');

-- ----------------------------
-- Table structure for j_messtype
-- ----------------------------
DROP TABLE IF EXISTS `j_messtype`;
CREATE TABLE `j_messtype` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `name` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `template` text COLLATE utf8mb4_unicode_ci,
  `status` int(1) DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of j_messtype
-- ----------------------------
INSERT INTO `j_messtype` VALUES ('1', 'day', '月度考勤信息', '<p>						</p><p style=\"text-align: center;\"><strong style=\"font-size: 20px; text-align: center; white-space: normal; background-color: rgb(255, 255, 255);\"><br/></strong></p><p style=\"text-align: center;\"><strong style=\"font-size: 20px; text-align: center; white-space: normal; background-color: rgb(255, 255, 255);\">XX工地XXXX年X月考勤信息</strong></p><p><strong style=\"font-size: 20px; text-align: center; white-space: normal; background-color: rgb(255, 255, 255);\"></strong></p><hr/><p><strong style=\"font-size: 20px; text-align: center; white-space: normal; background-color: rgb(255, 255, 255);\"></strong><br/></p><p><br/></p><p><span style=\"font-size: 20px;\"></span></p><p><br/></p><table align=\"center\"><tbody><tr class=\"firstRow\"><td valign=\"middle\" style=\"word-break: break-all; border-color: rgb(221, 221, 221);\" align=\"center\" rowspan=\"2\" colspan=\"1\" width=\"265\"><p>XXXX年X月</p></td><td valign=\"top\" style=\"word-break: break-all; border-color: rgb(221, 221, 221);\" rowspan=\"1\" colspan=\"2\" align=\"center\">1号</td><td valign=\"top\" style=\"word-break: break-all; border-color: rgb(221, 221, 221);\" rowspan=\"1\" colspan=\"2\" align=\"center\">2号</td><td valign=\"top\" style=\"word-break: break-all; border-color: rgb(221, 221, 221);\" rowspan=\"1\" colspan=\"2\" align=\"center\" width=\"237\">3号<br/></td><td valign=\"top\" align=\"center\" rowspan=\"1\" colspan=\"2\" style=\"word-break: break-all; border-color: rgb(221, 221, 221);\" width=\"342\">4号</td><td valign=\"top\" align=\"center\" rowspan=\"1\" colspan=\"2\" style=\"border-color: rgb(221, 221, 221);\">5号</td><td valign=\"top\" align=\"center\" rowspan=\"1\" colspan=\"2\" style=\"border-color: rgb(221, 221, 221);\">6号<br/></td></tr><tr><td valign=\"top\" style=\"word-break: break-all; border-color: rgb(221, 221, 221);\" align=\"center\" width=\"134\">上班</td><td valign=\"top\" style=\"word-break: break-all; border-color: rgb(221, 221, 221);\" align=\"center\" width=\"134\">下班</td><td valign=\"top\" align=\"center\" style=\"word-break: break-all; border-color: rgb(221, 221, 221);\" width=\"134\"><span style=\"text-align: -webkit-center;\">上班</span></td><td valign=\"top\" align=\"center\" style=\"word-break: break-all; border-color: rgb(221, 221, 221);\" width=\"134\"><span style=\"text-align: -webkit-center;\">下班</span></td><td valign=\"top\" align=\"center\" style=\"word-break: break-all; border-color: rgb(221, 221, 221);\" width=\"134\"><span style=\"text-align: -webkit-center;\">上班</span></td><td valign=\"top\" align=\"center\" style=\"word-break: break-all; border-color: rgb(221, 221, 221);\" width=\"134\"><span style=\"text-align: -webkit-center;\">下班</span></td><td valign=\"top\" align=\"center\" style=\"word-break: break-all; border-color: rgb(221, 221, 221);\" width=\"212\"><span style=\"text-align: -webkit-center;\">上班</span></td><td valign=\"top\" align=\"center\" style=\"word-break: break-all; border-color: rgb(221, 221, 221);\" width=\"83\"><span style=\"text-align: -webkit-center;\">下班</span></td><td valign=\"top\" align=\"center\" rowspan=\"1\" colspan=\"1\" style=\"border-color: rgb(221, 221, 221);\" width=\"216\"><span style=\"text-align: -webkit-center;\">上班</span></td><td valign=\"top\" align=\"center\" rowspan=\"1\" colspan=\"1\" style=\"border-color: rgb(221, 221, 221);\" width=\"134\"><span style=\"text-align: -webkit-center;\">下班</span></td><td valign=\"top\" align=\"center\" rowspan=\"1\" colspan=\"1\" style=\"border-color: rgb(221, 221, 221);\" width=\"134\"><span style=\"text-align: -webkit-center;\">上班</span></td><td valign=\"top\" align=\"center\" rowspan=\"1\" colspan=\"1\" style=\"border-color: rgb(221, 221, 221);\" width=\"134\"><span style=\"text-align: -webkit-center;\">下班</span></td></tr><tr><td valign=\"top\" style=\"border-color: rgb(221, 221, 221);\" width=\"134\"><br/></td><td valign=\"top\" style=\"border-color: rgb(221, 221, 221);\" width=\"134\"><br/></td><td valign=\"top\" style=\"border-color: rgb(221, 221, 221);\" width=\"134\"><br/></td><td valign=\"top\" style=\"border-color: rgb(221, 221, 221);\" width=\"134\"><br/></td><td valign=\"top\" style=\"border-color: rgb(221, 221, 221);\" width=\"134\"><br/></td><td valign=\"top\" style=\"border-color: rgb(221, 221, 221);\" width=\"134\"><br/></td><td valign=\"top\" style=\"border-color: rgb(221, 221, 221);\" width=\"107\"><br/></td><td valign=\"top\" style=\"border-color: rgb(221, 221, 221);\" width=\"119\"><br/></td><td valign=\"top\" style=\"border-color: rgb(221, 221, 221);\" width=\"216\"><br/></td><td valign=\"top\" align=\"center\" rowspan=\"1\" colspan=\"1\" style=\"border-color: rgb(221, 221, 221);\" width=\"133\"><br/></td><td valign=\"top\" align=\"center\" rowspan=\"1\" colspan=\"1\" style=\"border-color: rgb(221, 221, 221);\" width=\"134\"><br/></td><td valign=\"top\" align=\"center\" rowspan=\"1\" colspan=\"1\" style=\"border-color: rgb(221, 221, 221);\" width=\"134\"><br/></td><td valign=\"top\" align=\"center\" rowspan=\"1\" colspan=\"1\" style=\"border-color: rgb(221, 221, 221);\" width=\"134\"><br/></td></tr><tr><td valign=\"top\" style=\"border-color: rgb(221, 221, 221);\" width=\"134\"><br/></td><td valign=\"top\" style=\"border-color: rgb(221, 221, 221);\" width=\"134\"><br/></td><td valign=\"top\" style=\"border-color: rgb(221, 221, 221);\" width=\"134\"><br/></td><td valign=\"top\" style=\"border-color: rgb(221, 221, 221);\" width=\"134\"><br/></td><td valign=\"top\" style=\"border-color: rgb(221, 221, 221);\" width=\"134\"><br/></td><td valign=\"top\" style=\"border-color: rgb(221, 221, 221);\" width=\"134\"><br/></td><td valign=\"top\" style=\"border-color: rgb(221, 221, 221);\" width=\"107\"><br/></td><td valign=\"top\" style=\"border-color: rgb(221, 221, 221);\" width=\"119\"><br/></td><td valign=\"top\" style=\"border-color: rgb(221, 221, 221);\" width=\"216\"><br/></td><td valign=\"top\" align=\"center\" rowspan=\"1\" colspan=\"1\" style=\"border-color: rgb(221, 221, 221);\" width=\"133\"><br/></td><td valign=\"top\" align=\"center\" rowspan=\"1\" colspan=\"1\" style=\"border-color: rgb(221, 221, 221);\" width=\"134\"><br/></td><td valign=\"top\" align=\"center\" rowspan=\"1\" colspan=\"1\" style=\"border-color: rgb(221, 221, 221);\" width=\"134\"><br/></td><td valign=\"top\" align=\"center\" rowspan=\"1\" colspan=\"1\" style=\"border-color: rgb(221, 221, 221);\" width=\"134\"><br/></td></tr><tr><td valign=\"top\" colspan=\"1\" rowspan=\"1\" style=\"border-color: rgb(221, 221, 221);\" width=\"134\"><br/></td><td valign=\"top\" colspan=\"1\" rowspan=\"1\" style=\"border-color: rgb(221, 221, 221);\" width=\"134\"><br/></td><td valign=\"top\" colspan=\"1\" rowspan=\"1\" style=\"border-color: rgb(221, 221, 221);\" width=\"134\"><br/></td><td valign=\"top\" colspan=\"1\" rowspan=\"1\" style=\"border-color: rgb(221, 221, 221);\" width=\"134\"><br/></td><td valign=\"top\" colspan=\"1\" rowspan=\"1\" style=\"border-color: rgb(221, 221, 221);\" width=\"134\"><br/></td><td valign=\"top\" colspan=\"1\" rowspan=\"1\" style=\"border-color: rgb(221, 221, 221);\" width=\"134\"><br/></td><td valign=\"top\" colspan=\"1\" rowspan=\"1\" style=\"border-color: rgb(221, 221, 221);\" width=\"107\"><br/></td><td valign=\"top\" colspan=\"1\" rowspan=\"1\" style=\"border-color: rgb(221, 221, 221);\" width=\"119\"><br/></td><td valign=\"top\" colspan=\"1\" rowspan=\"1\" style=\"border-color: rgb(221, 221, 221);\" width=\"NaN\"><br/></td><td valign=\"top\" align=\"center\" rowspan=\"1\" colspan=\"1\" style=\"border-color: rgb(221, 221, 221);\" width=\"undefined\"><br/></td><td valign=\"top\" align=\"center\" rowspan=\"1\" colspan=\"1\" style=\"border-color: rgb(221, 221, 221);\" width=\"undefined\"><br/></td><td valign=\"top\" align=\"center\" rowspan=\"1\" colspan=\"1\" style=\"border-color: rgb(221, 221, 221);\" width=\"undefined\"><br/></td><td valign=\"top\" align=\"center\" rowspan=\"1\" colspan=\"1\" style=\"border-color: rgb(221, 221, 221);\" width=\"undefined\"><br/></td></tr></tbody></table><p><br/></p><p>&nbsp;					 		</p><p>&nbsp;					 		</p><p>&nbsp;					 		</p>', '1');
INSERT INTO `j_messtype` VALUES ('2', 'quarter', '季度考勤信息', '<p>						</p><p style=\"text-align: center;\"><strong style=\"font-size: 20px; text-align: center; white-space: normal; background-color: rgb(255, 255, 255);\"><br/></strong></p><p style=\"text-align: center;\"><strong style=\"font-size: 20px; text-align: center; white-space: normal; background-color: rgb(255, 255, 255);\">XX工地XXXX年第X季度考勤信息</strong></p><p><strong style=\"font-size: 20px; text-align: center; white-space: normal; background-color: rgb(255, 255, 255);\"></strong></p><hr/><p><br/><strong style=\"font-size: 20px; text-align: center; white-space: normal; background-color: rgb(255, 255, 255);\"></strong></p><table align=\"center\" width=\"1151\"><tbody><tr class=\"firstRow\"><td valign=\"middle\" style=\"word-break: break-all; border-color: rgb(221, 221, 221);\" align=\"center\" rowspan=\"2\" colspan=\"1\" width=\"106\"><p>XXXX年第X季度</p></td><td valign=\"top\" style=\"word-break: break-all; border-color: rgb(221, 221, 221);\" rowspan=\"1\" colspan=\"2\" align=\"center\" width=\"251\">1月</td><td valign=\"top\" style=\"word-break: break-all; border-color: rgb(221, 221, 221);\" rowspan=\"1\" colspan=\"2\" align=\"center\" width=\"175\">2月</td><td valign=\"top\" style=\"word-break: break-all; border-color: rgb(221, 221, 221);\" rowspan=\"1\" colspan=\"2\" align=\"center\" width=\"175\">3月<br/></td></tr><tr><td valign=\"top\" style=\"word-break: break-all; border-color: rgb(221, 221, 221);\" align=\"center\" width=\"125\">上班</td><td valign=\"top\" style=\"word-break: break-all; border-color: rgb(221, 221, 221);\" align=\"center\" width=\"125\">下班</td><td valign=\"top\" align=\"center\" style=\"word-break: break-all; border-color: rgb(221, 221, 221);\" width=\"87\"><span style=\"text-align: -webkit-center;\">上班</span></td><td valign=\"top\" align=\"center\" style=\"word-break: break-all; border-color: rgb(221, 221, 221);\" width=\"87\"><span style=\"text-align: -webkit-center;\">下班</span></td><td valign=\"top\" align=\"center\" style=\"word-break: break-all; border-color: rgb(221, 221, 221);\" width=\"87\"><span style=\"text-align: -webkit-center;\">上班</span></td><td valign=\"top\" align=\"center\" style=\"word-break: break-all; border-color: rgb(221, 221, 221);\" width=\"87\"><span style=\"text-align: -webkit-center;\">下班</span></td></tr><tr><td valign=\"top\" style=\"border-color: rgb(221, 221, 221);\" width=\"106\"><br/></td><td valign=\"top\" style=\"border-color: rgb(221, 221, 221);\" width=\"125\"><br/></td><td valign=\"top\" style=\"border-color: rgb(221, 221, 221);\" width=\"125\"><br/></td><td valign=\"top\" style=\"border-color: rgb(221, 221, 221);\" width=\"87\"><br/></td><td valign=\"top\" style=\"border-color: rgb(221, 221, 221);\" width=\"87\"><br/></td><td valign=\"top\" style=\"border-color: rgb(221, 221, 221);\" width=\"87\"><br/></td><td valign=\"top\" style=\"border-color: rgb(221, 221, 221);\" width=\"87\"><br/></td></tr><tr><td valign=\"top\" style=\"border-color: rgb(221, 221, 221);\" width=\"106\"><br/></td><td valign=\"top\" style=\"border-color: rgb(221, 221, 221);\" width=\"125\"><br/></td><td valign=\"top\" style=\"border-color: rgb(221, 221, 221);\" width=\"125\"><br/></td><td valign=\"top\" style=\"border-color: rgb(221, 221, 221);\" width=\"87\"><br/></td><td valign=\"top\" style=\"border-color: rgb(221, 221, 221);\" width=\"87\"><br/></td><td valign=\"top\" style=\"border-color: rgb(221, 221, 221);\" width=\"87\"><br/></td><td valign=\"top\" style=\"border-color: rgb(221, 221, 221);\" width=\"87\"><br/></td></tr><tr><td valign=\"top\" colspan=\"1\" rowspan=\"1\" style=\"border-color: rgb(221, 221, 221);\" width=\"106\"><br/></td><td valign=\"top\" colspan=\"1\" rowspan=\"1\" style=\"border-color: rgb(221, 221, 221);\" width=\"125\"><br/></td><td valign=\"top\" colspan=\"1\" rowspan=\"1\" style=\"border-color: rgb(221, 221, 221);\" width=\"125\"><br/></td><td valign=\"top\" colspan=\"1\" rowspan=\"1\" style=\"border-color: rgb(221, 221, 221);\" width=\"87\"><br/></td><td valign=\"top\" colspan=\"1\" rowspan=\"1\" style=\"border-color: rgb(221, 221, 221);\" width=\"87\"><br/></td><td valign=\"top\" colspan=\"1\" rowspan=\"1\" style=\"border-color: rgb(221, 221, 221);\" width=\"87\"><br/></td><td valign=\"top\" colspan=\"1\" rowspan=\"1\" style=\"border-color: rgb(221, 221, 221);\" width=\"87\"><br/></td></tr></tbody></table><p><br/></p><p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;					 		</p><p>&nbsp;					 		</p><p>&nbsp;					 		</p>', '0');
INSERT INTO `j_messtype` VALUES ('4', 'year', '年度考勤信息', '<p>						</p><p style=\"text-align: center;\"><strong style=\"font-size: 20px; text-align: center; white-space: normal; background-color: rgb(255, 255, 255);\"><br/></strong></p><p style=\"text-align: center;\"><strong style=\"font-size: 20px; text-align: center; white-space: normal; background-color: rgb(255, 255, 255);\">XX工地XXXX年考勤信息</strong></p><p><strong style=\"font-size: 20px; text-align: center; white-space: normal; background-color: rgb(255, 255, 255);\"></strong></p><hr/><p><strong style=\"font-size: 20px; text-align: center; white-space: normal; background-color: rgb(255, 255, 255);\"></strong><br/></p><p><br/></p><p><span style=\"font-size: 20px;\"></span></p><p><br/></p><table align=\"center\" width=\"1151\"><tbody><tr class=\"firstRow\"><td valign=\"middle\" style=\"word-break: break-all; border-color: rgb(221, 221, 221);\" align=\"center\" rowspan=\"2\" colspan=\"1\" width=\"161\"><p>XXXX年X月</p></td><td valign=\"top\" style=\"word-break: break-all; border-color: rgb(221, 221, 221);\" rowspan=\"1\" colspan=\"1\" align=\"center\" width=\"81\">1月</td><td rowspan=\"1\" valign=\"top\" align=\"center\" style=\"word-break: break-all; border-color: rgb(221, 221, 221);\" width=\"81\">2月</td><td valign=\"top\" style=\"word-break: break-all; border-color: rgb(221, 221, 221);\" rowspan=\"1\" colspan=\"1\" align=\"center\" width=\"81\">3月</td><td rowspan=\"1\" valign=\"top\" align=\"center\" style=\"word-break: break-all; border-color: rgb(221, 221, 221);\" width=\"81\">4月</td><td valign=\"top\" style=\"word-break: break-all; border-color: rgb(221, 221, 221);\" rowspan=\"1\" colspan=\"1\" align=\"center\" width=\"81\">5月<br/></td><td rowspan=\"1\" valign=\"top\" align=\"center\" style=\"word-break: break-all; border-color: rgb(221, 221, 221);\" width=\"81\">6月</td><td valign=\"top\" align=\"center\" rowspan=\"1\" colspan=\"1\" style=\"word-break: break-all; border-color: rgb(221, 221, 221);\" width=\"81\">7月</td><td rowspan=\"1\" valign=\"top\" align=\"center\" style=\"word-break: break-all; border-color: rgb(221, 221, 221);\" width=\"81\">8月</td><td valign=\"top\" align=\"center\" rowspan=\"1\" colspan=\"1\" style=\"border-color: rgb(221, 221, 221); word-break: break-all;\" width=\"81\">9月</td><td rowspan=\"1\" valign=\"top\" align=\"center\" style=\"border-color: rgb(221, 221, 221); word-break: break-all;\" width=\"86\">10月</td><td valign=\"top\" align=\"center\" rowspan=\"1\" colspan=\"1\" style=\"border-color: rgb(221, 221, 221); word-break: break-all;\" width=\"87\">11月<br/></td><td rowspan=\"1\" valign=\"top\" align=\"center\" style=\"border-color: rgb(221, 221, 221); word-break: break-all;\" width=\"87\">12月</td></tr><tr><td valign=\"top\" style=\"word-break: break-all; border-color: rgb(221, 221, 221);\" align=\"center\" width=\"81\">上班</td><td valign=\"top\" style=\"word-break: break-all; border-color: rgb(221, 221, 221);\" align=\"center\" width=\"81\">下班</td><td valign=\"top\" align=\"center\" style=\"word-break: break-all; border-color: rgb(221, 221, 221);\" width=\"81\"><span style=\"text-align: -webkit-center;\">上班</span></td><td valign=\"top\" align=\"center\" style=\"word-break: break-all; border-color: rgb(221, 221, 221);\" width=\"81\"><span style=\"text-align: -webkit-center;\">下班</span></td><td valign=\"top\" align=\"center\" style=\"word-break: break-all; border-color: rgb(221, 221, 221);\" width=\"81\"><span style=\"text-align: -webkit-center;\">上班</span></td><td valign=\"top\" align=\"center\" style=\"word-break: break-all; border-color: rgb(221, 221, 221);\" width=\"81\"><span style=\"text-align: -webkit-center;\">下班</span></td><td valign=\"top\" align=\"center\" style=\"word-break: break-all; border-color: rgb(221, 221, 221);\" width=\"81\"><span style=\"text-align: -webkit-center;\">上班</span></td><td valign=\"top\" align=\"center\" style=\"word-break: break-all; border-color: rgb(221, 221, 221);\" width=\"81\"><span style=\"text-align: -webkit-center;\">下班</span></td><td valign=\"top\" align=\"center\" rowspan=\"1\" colspan=\"1\" style=\"border-color: rgb(221, 221, 221);\" width=\"81\"><span style=\"text-align: -webkit-center;\">上班</span></td><td valign=\"top\" align=\"center\" rowspan=\"1\" colspan=\"1\" style=\"border-color: rgb(221, 221, 221);\" width=\"86\"><span style=\"text-align: -webkit-center;\">下班</span></td><td valign=\"top\" align=\"center\" rowspan=\"1\" colspan=\"1\" style=\"border-color: rgb(221, 221, 221); word-break: break-all;\" width=\"87\"><span style=\"text-align: -webkit-center;\">上班</span></td><td valign=\"top\" align=\"center\" rowspan=\"1\" colspan=\"1\" style=\"border-color: rgb(221, 221, 221); word-break: break-all;\" width=\"87\"><span style=\"text-align: -webkit-center;\">下班</span></td></tr><tr><td valign=\"top\" style=\"border-color: rgb(221, 221, 221);\" width=\"161\"><br/></td><td valign=\"top\" style=\"border-color: rgb(221, 221, 221);\" width=\"81\"><br/></td><td valign=\"top\" style=\"border-color: rgb(221, 221, 221);\" width=\"81\"><br/></td><td valign=\"top\" style=\"border-color: rgb(221, 221, 221);\" width=\"81\"><br/></td><td valign=\"top\" style=\"border-color: rgb(221, 221, 221);\" width=\"81\"><br/></td><td valign=\"top\" style=\"border-color: rgb(221, 221, 221);\" width=\"81\"><br/></td><td valign=\"top\" style=\"border-color: rgb(221, 221, 221);\" width=\"81\"><br/></td><td valign=\"top\" style=\"border-color: rgb(221, 221, 221);\" width=\"81\"><br/></td><td valign=\"top\" style=\"border-color: rgb(221, 221, 221);\" width=\"81\"><br/></td><td valign=\"top\" align=\"center\" rowspan=\"1\" colspan=\"1\" style=\"border-color: rgb(221, 221, 221);\" width=\"81\"><br/></td><td valign=\"top\" align=\"center\" rowspan=\"1\" colspan=\"1\" style=\"border-color: rgb(221, 221, 221);\" width=\"86\"><br/></td><td valign=\"top\" align=\"center\" rowspan=\"1\" colspan=\"1\" style=\"border-color: rgb(221, 221, 221);\" width=\"87\"><br/></td><td valign=\"top\" align=\"center\" rowspan=\"1\" colspan=\"1\" style=\"border-color: rgb(221, 221, 221);\" width=\"87\"><br/></td></tr><tr><td valign=\"top\" style=\"border-color: rgb(221, 221, 221);\" width=\"161\"><br/></td><td valign=\"top\" style=\"border-color: rgb(221, 221, 221);\" width=\"81\"><br/></td><td valign=\"top\" style=\"border-color: rgb(221, 221, 221);\" width=\"81\"><br/></td><td valign=\"top\" style=\"border-color: rgb(221, 221, 221);\" width=\"81\"><br/></td><td valign=\"top\" style=\"border-color: rgb(221, 221, 221);\" width=\"81\"><br/></td><td valign=\"top\" style=\"border-color: rgb(221, 221, 221);\" width=\"81\"><br/></td><td valign=\"top\" style=\"border-color: rgb(221, 221, 221);\" width=\"81\"><br/></td><td valign=\"top\" style=\"border-color: rgb(221, 221, 221);\" width=\"81\"><br/></td><td valign=\"top\" style=\"border-color: rgb(221, 221, 221);\" width=\"81\"><br/></td><td valign=\"top\" align=\"center\" rowspan=\"1\" colspan=\"1\" style=\"border-color: rgb(221, 221, 221);\" width=\"81\"><br/></td><td valign=\"top\" align=\"center\" rowspan=\"1\" colspan=\"1\" style=\"border-color: rgb(221, 221, 221);\" width=\"86\"><br/></td><td valign=\"top\" align=\"center\" rowspan=\"1\" colspan=\"1\" style=\"border-color: rgb(221, 221, 221);\" width=\"87\"><br/></td><td valign=\"top\" align=\"center\" rowspan=\"1\" colspan=\"1\" style=\"border-color: rgb(221, 221, 221);\" width=\"87\"><br/></td></tr><tr><td valign=\"top\" colspan=\"1\" rowspan=\"1\" style=\"border-color: rgb(221, 221, 221);\" width=\"161\"><br/></td><td valign=\"top\" colspan=\"1\" rowspan=\"1\" style=\"border-color: rgb(221, 221, 221);\" width=\"81\"><br/></td><td valign=\"top\" colspan=\"1\" rowspan=\"1\" style=\"border-color: rgb(221, 221, 221);\" width=\"81\"><br/></td><td valign=\"top\" colspan=\"1\" rowspan=\"1\" style=\"border-color: rgb(221, 221, 221);\" width=\"81\"><br/></td><td valign=\"top\" colspan=\"1\" rowspan=\"1\" style=\"border-color: rgb(221, 221, 221);\" width=\"81\"><br/></td><td valign=\"top\" colspan=\"1\" rowspan=\"1\" style=\"border-color: rgb(221, 221, 221);\" width=\"81\"><br/></td><td valign=\"top\" colspan=\"1\" rowspan=\"1\" style=\"border-color: rgb(221, 221, 221);\" width=\"81\"><br/></td><td valign=\"top\" colspan=\"1\" rowspan=\"1\" style=\"border-color: rgb(221, 221, 221);\" width=\"81\"><br/></td><td valign=\"top\" colspan=\"1\" rowspan=\"1\" style=\"border-color: rgb(221, 221, 221);\" width=\"81\"><br/></td><td valign=\"top\" align=\"center\" rowspan=\"1\" colspan=\"1\" style=\"border-color: rgb(221, 221, 221);\" width=\"81\"><br/></td><td valign=\"top\" align=\"center\" rowspan=\"1\" colspan=\"1\" style=\"border-color: rgb(221, 221, 221);\" width=\"86\"><br/></td><td valign=\"top\" align=\"center\" rowspan=\"1\" colspan=\"1\" style=\"border-color: rgb(221, 221, 221);\" width=\"87\"><br/></td><td valign=\"top\" align=\"center\" rowspan=\"1\" colspan=\"1\" style=\"border-color: rgb(221, 221, 221);\" width=\"87\"><br/></td></tr></tbody></table><p><br/></p><p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;					 		</p><p>&nbsp;					 		</p><p>&nbsp;					 		</p>', '1');

-- ----------------------------
-- Table structure for j_option
-- ----------------------------
DROP TABLE IF EXISTS `j_option`;
CREATE TABLE `j_option` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `option_name` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `option_key` varchar(128) CHARACTER SET utf8mb4 DEFAULT NULL,
  `option_value` text CHARACTER SET utf8mb4,
  `remark` text COLLATE utf8mb4_unicode_ci,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of j_option
-- ----------------------------
INSERT INTO `j_option` VALUES ('1', '基础服务', 'base_service', 'base', '基础服务');
INSERT INTO `j_option` VALUES ('21', '版权信息', 'copyright', '平安工地安全管理系统 By <a href=\"https://colorlib.com\">华夏九鼎</a>', '底部统一版权信息');
INSERT INTO `j_option` VALUES ('43', '后台页面顶部', 'admin_top_title', '华夏九鼎', null);
INSERT INTO `j_option` VALUES ('44', '图片路径', 'img_url', 'd:\\', null);

-- ----------------------------
-- Table structure for j_role
-- ----------------------------
DROP TABLE IF EXISTS `j_role`;
CREATE TABLE `j_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `describe` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of j_role
-- ----------------------------
INSERT INTO `j_role` VALUES ('1', '测试角色一', '测试角色一');
INSERT INTO `j_role` VALUES ('2', '测试角色二', '测试角色二');
INSERT INTO `j_role` VALUES ('5', '测试角色三', '测试角色三');
INSERT INTO `j_role` VALUES ('6', '测试角色四', '测试角色四');
INSERT INTO `j_role` VALUES ('7', '项目管理员', '项目管理员');
INSERT INTO `j_role` VALUES ('10', '测试按钮', '测试按钮');
INSERT INTO `j_role` VALUES ('11', 'fgdfgfd', '规范的施工方都是高分多少');

-- ----------------------------
-- Table structure for j_rolemenubutton
-- ----------------------------
DROP TABLE IF EXISTS `j_rolemenubutton`;
CREATE TABLE `j_rolemenubutton` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `roleId` int(11) DEFAULT NULL,
  `menuId` int(11) DEFAULT NULL,
  `buttons` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of j_rolemenubutton
-- ----------------------------
INSERT INTO `j_rolemenubutton` VALUES ('24', '7', '1', null);
INSERT INTO `j_rolemenubutton` VALUES ('25', '7', '3', null);
INSERT INTO `j_rolemenubutton` VALUES ('26', '5', '1', null);
INSERT INTO `j_rolemenubutton` VALUES ('27', '5', '3', null);
INSERT INTO `j_rolemenubutton` VALUES ('28', '5', '20', null);
INSERT INTO `j_rolemenubutton` VALUES ('29', '5', '21', null);
INSERT INTO `j_rolemenubutton` VALUES ('30', '5', '22', null);
INSERT INTO `j_rolemenubutton` VALUES ('31', '5', '28', null);
INSERT INTO `j_rolemenubutton` VALUES ('32', '6', '20', null);
INSERT INTO `j_rolemenubutton` VALUES ('33', '6', '21', null);
INSERT INTO `j_rolemenubutton` VALUES ('34', '6', '22', null);
INSERT INTO `j_rolemenubutton` VALUES ('35', '10', '1', null);
INSERT INTO `j_rolemenubutton` VALUES ('36', '10', '3', 'add');
INSERT INTO `j_rolemenubutton` VALUES ('37', '10', '20', null);
INSERT INTO `j_rolemenubutton` VALUES ('38', '10', '21', 'del|edit');
INSERT INTO `j_rolemenubutton` VALUES ('39', '11', '20', null);
INSERT INTO `j_rolemenubutton` VALUES ('40', '11', '21', 'add|del|edit|sel');

-- ----------------------------
-- Table structure for j_safe
-- ----------------------------
DROP TABLE IF EXISTS `j_safe`;
CREATE TABLE `j_safe` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `dwip` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `spip` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `mjip` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `add` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `x` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `y` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of j_safe
-- ----------------------------
INSERT INTO `j_safe` VALUES ('1', '一号项目', '127.0.0.1:8180/cloud/', '192.168.3.5', '192.168.3.6', '一号项目(修改测试1）', '102.683874', '24.977951');
INSERT INTO `j_safe` VALUES ('2', '二号项目', '192.168.3.51', '192.168.3.6', '192.168.3.7', '二号项目（修改测试）', '102.669426', '24.971461');
INSERT INTO `j_safe` VALUES ('3', '三号项目', '192.168.0.1', '192.168.0.2', '192.168.0.4', '三号项目', '102.749738', '24.975429');
INSERT INTO `j_safe` VALUES ('4', '四号项目', '192.168.0.1', '192.168.0.2', '192.168.0.3', '四号项目', '102.694546', '24.984862');
INSERT INTO `j_safe` VALUES ('5', '五号项目', '192.168.0.1', '192.168.0.2', '192.168.0.3', '五号项目', '102.718692', '24.975429');
INSERT INTO `j_safe` VALUES ('6', '六号项目', '192.168.1.1', '192.168.1.2', '192.168.1.3', '曹家坝', '102.710931', '25.001368');
INSERT INTO `j_safe` VALUES ('8', '七号项目', '192.168.2.1', '192.168.2.2', '192.168.8.3', '海贝中英文学校', '102.676436', '24.994425');
INSERT INTO `j_safe` VALUES ('9', '八号项目', '192.168.2.1', '192.168.2.2', '192.168.8.3', '海贝中英文学校', '102.672466', '24.996425');

-- ----------------------------
-- Table structure for j_user
-- ----------------------------
DROP TABLE IF EXISTS `j_user`;
CREATE TABLE `j_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户名',
  `nickname` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '昵称',
  `relname` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '真名',
  `password` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '密码',
  `salt` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '校验',
  `email` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `email_status` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `mobile` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `mobile_status` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `gender` varchar(16) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `avatar` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '头像',
  `role` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created` datetime DEFAULT NULL COMMENT '创建时间',
  `logined` datetime DEFAULT NULL COMMENT '最后登录时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of j_user
-- ----------------------------
INSERT INTO `j_user` VALUES ('1', 'admin', 'jungle', '张洋', 'c83dcd0907f4514de98c02c3c6d712e0148d4f0c1d835adc6ff6ce6092fafe77', 'cb1144ac47595', 'zipoqiy@163.com', null, '18623090141', null, '1', '\\attachment\\avatar\\20170420\\5440a327bc9447d788c0e61725b7487d.png', 'administrator', '1900-01-01 01:01:01', '2017-05-11 11:43:06');
INSERT INTO `j_user` VALUES ('4', 'zy', 'zy1', '张洋', 'f7bd1e5fc11085052561362bfe35177f296fbc8bf91396f5260501636a6a1bca', '2e099ca833b4f672340', 'zipoqiy@163.com', null, '18623090141', null, '0', null, null, '2017-04-20 09:40:10', null);
INSERT INTO `j_user` VALUES ('5', 'hxjd', 'hxjd', '华夏九鼎', 'f423d82306fd79ab0bc11ae3204ad32d2efc2ae53653c17c3a22fc658851539f', '4921c5fd9a28a1aa9', '123@qq.com', null, '18623090141', null, '0', null, null, '2017-05-03 15:48:06', '2017-05-03 15:48:53');

-- ----------------------------
-- Table structure for j_userrole
-- ----------------------------
DROP TABLE IF EXISTS `j_userrole`;
CREATE TABLE `j_userrole` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) DEFAULT NULL,
  `roleId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of j_userrole
-- ----------------------------
INSERT INTO `j_userrole` VALUES ('5', '4', '7');
INSERT INTO `j_userrole` VALUES ('8', '5', '5');

-- ----------------------------
-- Table structure for j_usersafe
-- ----------------------------
DROP TABLE IF EXISTS `j_usersafe`;
CREATE TABLE `j_usersafe` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userid` int(11) DEFAULT NULL,
  `safeid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of j_usersafe
-- ----------------------------
