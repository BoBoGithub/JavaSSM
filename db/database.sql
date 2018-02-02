/* 数据表结构： */

/* 创建数据库 */
CREATE DATABASE `mg_db_admin` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `mg_db_admin`;

/*管理用户表*/
CREATE TABLE `mg_admin` (
  `uid` 		bigint(20)  NOT NULL AUTO_INCREMENT COMMENT '主键',
  `username` 	varchar(20) NOT NULL DEFAULT ''  COMMENT '用户名',
  `password` 	varchar(32) NOT NULL DEFAULT ''  COMMENT '密码',
  `roleid` 		smallint(5) NOT NULL DEFAULT '0' COMMENT '所属角色id',
  `encrypt` 	varchar(6)  NOT NULL DEFAULT ''  COMMENT '加盐串',
  `email` 		varchar(40) NOT NULL DEFAULT ''  COMMENT '邮箱',
  `realname` 	varchar(50) NOT NULL DEFAULT ''  COMMENT '真实姓名',
  `ctime` 		datetime 	NOT NULL DEFAULT '0' COMMENT '创建时间',
  `status` 		int(3) 		NOT NULL DEFAULT '0' COMMENT '用户状态{-1已删除 0正常 1禁用}',
  PRIMARY KEY (`uid`),
  KEY `username` (`username`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

/* 初始化超级管理员数据 */
INSERT INTO `mg_admin` (`uid`, `username`, `password`, `roleid`, `encrypt`, `email`, `realname`, `ctime`, `status`) VALUES
(1, 'admin001', '5efb380c733d3d71993c3f00fcfba641', 1, 'migang', 'admin@migang.com', 'MiGang_DB', '2017-08-24 16:46:03', 0);


/*管理员角色表*/
CREATE TABLE `mg_admin_role` (
  `roleid`		int(5) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `rolename`	varchar(50) 	NOT NULL COMMENT '角色名',
  `roledesc`	varchar(300) 	NOT NULL COMMENT '角色描述',
  `status`		tinyint(3)		NOT NULL COMMENT '是否启用{-1已删除 0:启用 1:禁止}' DEFAULT '0',
  PRIMARY KEY (roleid)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

/* 初始化角色数据 */
INSERT INTO `mg_admin_role` (`roleid`, `rolename`, `roledesc`, `status`) VALUES
(1, '超级管理员', '超级管理员', 0);


/*角色权限表*/
CREATE TABLE `mg_admin_role_priv` (
  `roleid`	int(5) unsigned NOT NULL DEFAULT '0' COMMENT '角色表id',
  `url` 	varchar(250) 	NOT NULL DEFAULT '0' COMMENT '拥有的访问url',
  KEY `roleid` (`roleid`,`url`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


/*菜单表*/
CREATE TABLE `mg_admin_menu` (
  `id` 			int(6) 	unsigned 	NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` 		char(40) 			NOT NULL DEFAULT '' 	COMMENT '菜单名称',
  `parentid` 	int(6) 				NOT NULL DEFAULT '0' 	COMMENT '父级菜单id',
  `url` 		varchar(300) 		NOT NULL DEFAULT '' 	COMMENT '访问菜单的url',
  `status`		tinyint(3)			NOT NULL DEFAULT '0'    COMMENT '是否显示菜单{-1已删除 0显示 1不显示}',
  PRIMARY KEY (`id`),
  KEY parentid (`parentid`),
  KEY url (`url`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


/* 前台用户表 */
CREATE TABLE `mg_user`(
  `uid` 			bigint(20)  NOT NULL AUTO_INCREMENT COMMENT '主键',
  `mobile` 			varchar(11) NOT NULL DEFAULT ''  COMMENT '手机号',
  `password` 		varchar(32) NOT NULL DEFAULT ''  COMMENT '密码',
  `encrypt` 		varchar(6)  NOT NULL DEFAULT ''  COMMENT '加盐串',
  `ctime` 			int(10) 	NOT NULL DEFAULT '0' COMMENT '注册时间',
  `status` 			int(3) 		NOT NULL DEFAULT '0' COMMENT '用户状态:{-1:已删除 0:正常}',
  `ip` 				varchar(15) NOT NULL DEFAULT ''  COMMENT '用户注册IP',
  `shenfen_auth` 	int(10) 	NOT NULL DEFAULT '0' COMMENT '身份证认证时间',
  `huoti_auth` 		int(10) 	NOT NULL DEFAULT '0' COMMENT '活体认证时间',
  `person_auth` 	int(10) 	NOT NULL DEFAULT '0' COMMENT '个人信息认证时间',
  `job_auth` 		int(10) 	NOT NULL DEFAULT '0' COMMENT '职业信息认证时间',
  `contacts_auth` 	int(10) 	NOT NULL DEFAULT '0' COMMENT '联系人认证时间',
  `mobile_auth` 	int(10)		NOT NULL DEFAULT '0' COMMENT '手机号认证时间',
  `bandcard_auth` 	int(10) 	NOT NULL DEFAULT '0' COMMENT '绑卡认证时间',
  `province`		smallint(5) NOT NULL DEFAULT '0' COMMENT '所属省份',
  `city`			smallint(5) NOT NULL DEFAULT '0' COMMENT '所属城市',
  `district`		smallint(5) NOT NULL DEFAULT '0' COMMENT '所属区县',
  PRIMARY KEY (`uid`),
  UNIQUE KEY `mobile` (`mobile`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


/* 用户基本信息表 */
CREATE TABLE `mg_user_base`(
	`uid` 			bigint(20)	 NOT NULL DEFAULT '0' COMMENT '主键',
	`degree` 		int(3) 		 NOT NULL DEFAULT '0' COMMENT '学历:{0:初中及以下 1:高中或中专 2:专科 3:本科 4:研究生及以上}',
	`marriage`		int(3)		 NOT NULL DEFAULT '0' COMMENT '婚姻:{0:未婚 1:已婚}',
	`child`			int(3)		 NOT NULL DEFAULT '0' COMMENT '子女个数',
	`province`		smallint(5)  NOT NULL DEFAULT '0' COMMENT '所属省份',
	`city`			smallint(5)  NOT NULL DEFAULT '0' COMMENT '所属城市',
	`district`		smallint(5)  NOT NULL DEFAULT '0' COMMENT '所属区县',
	`address`		varchar(300) NOT NULL DEFAULT ''  COMMENT '具体住址',
	`live_time`		int(3) 		 NOT NULL DEFAULT '0' COMMENT '居住时长:{0:1年以下 1:1~3年 2:3~5年 3:5年以上}',
	`wechat`		varchar(100) NOT NULL DEFAULT ''  COMMENT '微信号',
	`email`			varchar(100) NOT NULL DEFAULT ''  COMMENT '邮箱',
	`realname`		varchar(50)  NOT NULL DEFAULT ''  COMMENT '真实姓名',
	`sex`			tinyint(3)   NOT NULL DEFAULT '0' COMMENT '性别{1男 2女}',
	`card_num`		varchar(20)  NOT NULL DEFAULT ''  COMMENT '身份证号',
	`card_address`	varchar(300) NOT NULL DEFAULT ''  COMMENT '身份上住址',
	·card_nation·	varchar(100) NOT NULL DEFAULT ''  COMMENT '身份上民族',
	`card_office`	varchar(200) NOT NULL DEFAULT ''  COMMENT '身份上办证机构',
	`card_expire`	varchar(50)  NOT NULL DEFAULT ''  COMMENT '身份上有效期',
	`card_pic`		varchar(200) NOT NULL DEFAULT ''  COMMENT '身份证照片地址:{"zm":"/upload/zm.jpg", "fm":"/upload/fm.jpg"}',
	 PRIMARY KEY (`uid`),
	 KEY(`card_num`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


/* 用户职业信息 */
CREATE TABLE `mg_user_job`(
	`job_id`		int(10)		 NOT NULL AUTO_INCREMENT COMMENT '主键',
	`uid` 			bigint(20)	 NOT NULL DEFAULT '0' COMMENT '用户UID',
	`job_name`		varchar(200) NOT NULL DEFAULT ''  COMMENT '职业名称',
	`income`		int(3)		 NOT NULL DEFAULT '0' COMMENT '收入{0:2000元以下 1:2000~5000元 2:5001~8000元 3:8001~12000元 4:12001~18000元 5:18000元以上}',
	`company`		varchar(300) NOT NULL DEFAULT ''  COMMENT '公司名称',
	`province`		smallint(5)  NOT NULL DEFAULT '0' COMMENT '所属省份',
	`city`			smallint(5)  NOT NULL DEFAULT '0' COMMENT '所属城市',
	`district`		smallint(5)  NOT NULL DEFAULT '0' COMMENT '所属区县',
	`address`		varchar(300) NOT NULL DEFAULT ''  COMMENT '具体住址',
	`phone`			varchar(100) NOT NULL DEFAULT ''  COMMENT '单位电话',
	`ctime`			int(10)	 	 NOT NULL DEFAULT '0' COMMENT '创建时间',
	 PRIMARY KEY (`job_id`),
	 KEY(`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


/* 用户联系人信息 */
CREATE TABLE `mg_user_linkman`(
	`linkman_id`	int(10)		 NOT NULL AUTO_INCREMENT COMMENT '主键',
	`uid` 			bigint(20)	 NOT NULL DEFAULT '0' COMMENT '用户UID',
	`name`			varchar(50)	 NOT NULL DEFAULT ''  COMMENT '联系人姓名',
	`phone`			varchar(100) NOT NULL DEFAULT ''  COMMENT '联系人电话',
	`relation`		int(3)		 NOT NULL DEFAULT '0' COMMENT '联系人类型{0:朋友 1:父母 2:兄弟 3:姐妹 4:同事}',
	`ctime`			int(10)		 NOT NULL DEFAULT '0' COMMENT '创建时间',
	`remark`		varchar(500) NOT NULL DEFAULT ''  COMMENT '联系人备注',
	 PRIMARY KEY (`linkman_id`),
	 KEY(`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


/* 用户银行卡信息 */
CREATE TABLE `mg_user_bank`(
	`bank_id`		int(10)		 NOT NULL AUTO_INCREMENT COMMENT '主键',
	`uid` 			bigint(20)	 NOT NULL DEFAULT '0' COMMENT '用户UID',
	`bank_num`		varchar(50)	 NOT NULL DEFAULT ''  COMMENT '银行卡号',
	`mobile`		varchar(20)	 NOT NULL DEFAULT ''  COMMENT '银行预留手机号',
	`bank_name`		varchar(100) NOT NULL DEFAULT ''  COMMENT '银行名称',
	`bank_province`	smallint(5)  NOT NULL DEFAULT '0' COMMENT '所属省份',
	`bank_city`		smallint(5)  NOT NULL DEFAULT '0' COMMENT '所属城市',
	`card_type`		tinyint(3)	 NOT NULL DEFAULT '0' COMMENT '银行卡类型{0:放款卡, 1:还款卡}',
	`status`		tinyint(3)   NOT NULL DEFAULT '0' COMMENT '银行卡状态{-1已删除, 0:正常}',
	`ctime`			int(10)		 NOT NULL DEFAULT '0' COMMENT '创建时间',
	 PRIMARY KEY (`bank_id`),
	 KEY(`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


/* 借款记录表 */
CREATE TABLE `mg_borrow_record`(
	`brecord_id`		int(10)		  NOT NULL AUTO_INCREMENT 	COMMENT '主键',
	`uid` 				bigint(20)	  NOT NULL DEFAULT '0' 		COMMENT '用户UID',	
	`borrow_money`		decimal(9, 2) NOT NULL DEFAULT '0.00'	COMMENT '借款金额',
	`borrow_limit`		int(3)		  NOT NULL DEFAULT '0'		COMMENT '期限类型{1:7天 2:15天 3:30天}',
	`borrow_interest`	decimal(9, 2) NOT NULL DEFAULT '0.00' 	COMMENT '借款利息',
	`service_charge`	decimal(9, 2) NOT NULL DEFAULT '0.00' 	COMMENT '服务费',
	`overdue_charge`	decimal(9, 2) NOT NULL DEFAULT '0.00' 	COMMENT '逾期费',
	`apply_time`		int(10)		  NOT NULL DEFAULT '0'    	COMMENT '申请时间',
	`check_time`		int(10)		  NOT NULL DEFAULT '0'    	COMMENT '审核时间',
	`loan_time`			int(10)		  NOT NULL DEFAULT '0'    	COMMENT '放款时间',
	`repay_time`		int(10)		  NOT NULL DEFAULT '0'    	COMMENT '正常还款时间',
	`real_repay_time`	int(10)		  NOT NULL DEFAULT '0'    	COMMENT '实际还款时间',
	`status`			int(3)		  NOT NULL DEFAULT '0'		COMMENT '借款状态{-3:审核失败[机器] -2:审核失败[信审员] -1:取消订单 0:待审核 1:待人工信审 2:待确认银行卡 3:待签约 4:待放款 5:放款失败(原因更新备注) 6:待还款 7:逾期 8:逾期后还款 9:已还款 10:坏账[催收无果]}',
	`check_type`		tinyint(3)    NOT NULL DEFAULT '0'      COMMENT '审核类型{0:自动 1:人工 2:百融}',
	`operater`			bigint(20)	  NOT NULL DEFAULT '0' 		COMMENT '操作人UID{0自动审核}',
	`score`				float(7)	  NOT NULL DEFAULT '0'		COMMENT '风控分数',
	`remark`			varchar(500)  NOT NULL DEFAULT ''		COMMENT '备注信息，多条用!@##@!隔开',
	 PRIMARY KEY (`brecord_id`),
	 KEY(`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


/* 还款记录表 */
CREATE TABLE `mg_repay_record`(
	`repay_id`			int(10)		  NOT NULL AUTO_INCREMENT 	COMMENT '主键',
	`brecord_id`		int(10)		  NOT NULL DEFAULT '0' 		COMMENT '借款记录ID',
	`uid` 				bigint(20)	  NOT NULL DEFAULT '0' 		COMMENT '用户UID',
	`repay_moeny`		decimal(9, 2) NOT NULL DEFAULT '0.00' 	COMMENT '还款金额',
	`repay_type`		tinyint(3)	  NOT NULL DEFAULT '0'      COMMENT '还款方式{0银行卡自动扣除 1信托代扣}',
	`ctime`				int(10)		  NOT NULL DEFAULT '0'		COMMENT '创建时间',
	`remark`			varchar(500)  NOT NULL DEFAULT ''       COMMENT '备注信息',
	 PRIMARY KEY (`repay_id`),
	 KEY(`uid`),
	 KEY(`brecord_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


/* 风控审核配置表 */
CREATE TABLE `mg_risk_config`(
	`riskc_id`			int(10)		   NOT NULL AUTO_INCREMENT 	COMMENT '主键',
	`start_score`		varchar(50)	   NOT NULL DEFAULT '0'		COMMENT '最低风控分数',
	`end_score`			varchar(50)	   NOT NULL DEFAULT '0'		COMMENT '最高风控分数',
	`day_check_num`		int(5)		   NOT NULL DEFAULT '0'     COMMENT '每日风控审核通过个数',
	`day_loan_money`	decimal(12, 2) NOT NULL DEFAULT '0.00' 	COMMENT '每日放款总金额',
	`start_time`		int(10)		   NOT NULL DEFAULT '0'	    COMMENT '生效开始时间',
	`end_time`			int(10)		   NOT NULL DEFAULT '0'	    COMMENT '生效结束时间',	
	 PRIMARY KEY (`riskc_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/* 问题返回表 */
CREATE TABLE `mg_help_feedback`(
	`feedback_id`		int(10)			NOT NULL AUTO_INCREMENT COMMENT "主键",
	`uid`				bigint(20)		NOT NULL DEFAULT '0' COMMENT '用户UID',
	`udid`				varchar(150)	NOT NULL DEFAULT ''  COMMENT '设备标识',
	`content`			varchar(500)	NOT NULL DEFAULT ''  COMMENT '反馈内容',
	`add_time`			int(10)			NOT NULL DEFAULT '0' COMMENT '反馈时间',
	`reply`				varchar(500)	NOT NULL DEFAULT ''  COMMENT '回复内容',
	PRIMARY KEY (`feedback_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


/* 产品表 */
CREATE TABLE `mg_product` (
	`product_id`		int(10)			NOT NULL AUTO_INCREMENT COMMENT '自增主键',
	`product_name`		varchar(150)	NOT NULL DEFAULT ''		COMMENT '产品名称',
	`borrow_money`		decimal(9, 2) 	NOT NULL DEFAULT '0.00'	COMMENT '借款金额',
	`borrow_limit`		int(3)		  	NOT NULL DEFAULT '0'	COMMENT '期限类型{1:7天 2:15天 3:30天}',
	`day_rate`			decimal(6, 4)	NOT NULL DEFAULT '0.00'	COMMENT '日利率',
	`service_charge`	decimal(9, 4) 	NOT NULL DEFAULT '0.00' COMMENT '服务费',
	`status`			tinyint(3)		NOT NULL DEFAULT '0'	COMMENT '产品状态{-1删除 0停用 1启用}',
	PRIMARY KEY (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/* 对接返回数据表... */