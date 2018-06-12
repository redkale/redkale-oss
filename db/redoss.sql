/*
SQLyog Ultimate v11.24 (32 bit)
MySQL - 5.7.11-log : Database - redoss
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`redoss` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `redoss`;


DROP TABLE IF EXISTS `sys_actioninfo`;

CREATE TABLE `sys_actioninfo` (
  `actionid` int(11) NOT NULL COMMENT '[操作ID] 值的范围必须是2001-9999,1xxx预留给框架',
  `actionname` varchar(64) NOT NULL DEFAULT '' COMMENT '[操作名称] 系统已经存在的有查询、新增、修改、删除、登录',
  PRIMARY KEY (`actionid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='操作信息表';





DROP TABLE IF EXISTS `sys_moduleinfo`;

CREATE TABLE `sys_moduleinfo` (
  `moduleid` int(11) NOT NULL COMMENT '[模块ID] 值范围必须是2001-9999，1xxx预留给框架',
  `modulename` varchar(64) NOT NULL DEFAULT '' COMMENT '[模块名称]',
  PRIMARY KEY (`moduleid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='模块信息表';




DROP TABLE IF EXISTS `sys_roleinfo`;

CREATE TABLE `sys_roleinfo` (
  `roleid` int(11) NOT NULL COMMENT '[角色ID] 值范围必须是2001-9999，1xxx预留给框架',
  `rolename` varchar(64) NOT NULL DEFAULT '' COMMENT '[角色名称]',
  `description` varchar(255) NOT NULL DEFAULT '' COMMENT '[角色描述]',
  `createtime` bigint(20) NOT NULL DEFAULT '0' COMMENT '[创建时间]',
  `creator` varchar(255) NOT NULL DEFAULT '' COMMENT '[创建人]',
  PRIMARY KEY (`roleid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色信息表';




DROP TABLE IF EXISTS `sys_roletooption`;

CREATE TABLE `sys_roletooption` (
  `seqid` bigint(20) NOT NULL COMMENT '[记录ID] 值=当前时间豪秒数(一般不会并发操作)',
  `roleid` int(11) NOT NULL DEFAULT '0' COMMENT '[角色ID]',
  `optionid` int(11) NOT NULL DEFAULT '0' COMMENT '[模块操作ID] optionid = moduleid * 10000 + actionid',
  `createtime` bigint(20) NOT NULL DEFAULT '0' COMMENT '[创建时间]',
  `creator` varchar(255) NOT NULL DEFAULT '' COMMENT '[创建人]',
  PRIMARY KEY (`seqid`),
  UNIQUE KEY `unique` (`roleid`,`optionid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8  COMMENT='角色操作关联表';




DROP TABLE IF EXISTS `sys_usertorole`;

CREATE TABLE `sys_usertorole` (
  `seqid` int(11) NOT NULL COMMENT '[记录ID] 值=当前时间秒数(一般不会并发操作)',
  `roleid` int(11) NOT NULL DEFAULT '0' COMMENT '[角色ID]',
  `memberid` int(11) NOT NULL DEFAULT '0' COMMENT '[用户ID]',
  `createtime` bigint(20) NOT NULL DEFAULT '0' COMMENT '[创建时间]',
  `creator` varchar(255) NOT NULL DEFAULT '' COMMENT '[创建人]',
  PRIMARY KEY (`seqid`),
  UNIQUE KEY `unique` (`roleid`,`memberid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8  COMMENT='员工角色关联表';




DROP TABLE IF EXISTS `sys_usermember`;

CREATE TABLE `sys_usermember` (
  `memberid` int(10) NOT NULL COMMENT '[用户ID] 值从1000001开始',
  `account` varchar(64) NOT NULL DEFAULT '' COMMENT '[用户账号]',
  `membername` varchar(255) NOT NULL DEFAULT '' COMMENT '[用户昵称]，通常为员工姓名',
  `password` varchar(64) NOT NULL DEFAULT '' COMMENT '密码',
  `type` smallint(5) NOT NULL DEFAULT '0' COMMENT '[类型]；8192为管理员；1为普通员工；其他类型值需要按位移值来定义:2/4/8/16/32',
  `status` smallint(5) NOT NULL DEFAULT '0' COMMENT '[状态]: 10:正常;20:待审批;40:冻结;50:隐藏;60:关闭;70:过期;80:删除;',
  `mobile` varchar(32) NOT NULL DEFAULT '' COMMENT '[手机号码]',
  `email` varchar(128) NOT NULL DEFAULT '' COMMENT '[邮箱地址]',
  `weixin` varchar(128) NOT NULL DEFAULT '' COMMENT '[微信账号]',
  `remark` varchar(255) NOT NULL DEFAULT '' COMMENT '[备注]',
  `createtime` bigint(20) NOT NULL DEFAULT '0' COMMENT '[创建时间]',
  `updatetime` bigint(20) NOT NULL DEFAULT '0' COMMENT '[更新时间]',
  PRIMARY KEY (`memberid`),
  UNIQUE KEY `unique` (`account`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8  COMMENT='员工信息表';

insert  into `sys_usermember`(`memberid`,`account`,`membername`,`password`,`type`,`status`,`mobile`,`email`,`remark`,`createtime`,`updatetime`) values (1000001,'admin','管理员','81dc9bdb52d04dc20036dbd8313ed055',8192,10,'','','',1450000000000,1450000000000),(1000002,'redkale','Redkale','81dc9bdb52d04dc20036dbd8313ed055',8192,10,'','redkale@redkale.org','',1450000000000,0);



/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
