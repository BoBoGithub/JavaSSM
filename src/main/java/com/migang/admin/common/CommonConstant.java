package com.migang.admin.common;

/**
 * 全局常量信息配置
 *
 * ＠param addTime 2017-08-28
 * ＠param author     ChengBo
 */
public class CommonConstant {
	
	//设置App用户登陆数据缓存时间
	public static final Integer APP_USER_DATA_CACHE_EXPIRE_TIME			= 864000;
	
	//设置App用户登陆会话时长 半小时 30*60*60s
	public static final Integer APP_USER_SESSION_EXPIRE_TIME 				= 108000;
	
	//设置用户缓存前缀
	public static final String APP_USER_INFO_CACHE_PREFIX 						= "app_user_info_cache:";
	
	//设置用户登陆Token前缀
	public static final String APP_USER_lOGIN_TOKNE_CACHE_PREFIX		= "app_user_login_token:";
	
	//设置后台管理菜单缓存key
	public static final String ADMIN_MENU_CACHE_KEY										= "admin_menu_cache_key";
	
	//设置后台角色权限缓存key
	public static final String ADMIN_ROLE_PRIV_CACHE_KEY							= "admin_role_priv_cache_key:";
	
	//设置验证码缓存key前缀
	public static final String CAPTCHA_CACHE_PREFIX_KEY								= "captcha_cache_key:";
	
	//短信服务商请求地址
	public static final String MOBILE_MESSAGE_SERVICE_URL						= "https://www-dev00.migang.com/zengxin/shot_message_api";

	//请求成功
	public static final Integer SUCCESSS	=	0;
	public static final String SUCCESS		=	"0";
	
	//没有权限
	public static final String COMMON_PERMISSION_PARAM_ERROR		= "10000";
	public static final String COMMON_NO_PERMISSION 								= "10001";
	
	//参数错误
	public static final String COMMON_PARAM_ERROR										= "10002";
	
	//Token验证错误
	public static final String COMMON_TOKEN_VALIDATE_ERROR				= "10003";
	
	//系统内部错误
	public static final String COMMON_SYS_INNER_ERROR								= "10004";//系统内部错误
	
	//解密失败错误
	public static final String COMMON_DECODE_STRING_ERROR					= "10005";//解密失败
	public static final String COMMON_ENCODE_STRING_ERROR				= "10006";//加密失败
	
	//用户登陆相关错误码
	public static final String USER_NOT_EXIST															= "20001";//用户不存在
	public static final String USER_PWD_ERROR														= "20002";//用户登陆密码错误
	public static final String USER_IS_LOCKED															= "20003";//用户被锁定
	public static final String USER_LOGIN_NUM_MORE_THAN_LIMIT		= "20004";//用户登陆超限
	public static final String USER_OR_PWD_ERROR												= "20005";//用户名或密码错误
	public static final String USER_LOGIN_FAILURE												= "20006";//用户登陆失败
	public static final String USER_REG_FAILURE														= "20007";//用户注册失败
	public static final String USER_HAS_EXISTS														= "20008";//用户已存在
	public static final String USER_SET_CACHE_PARAM_ERROR						= "20009";//用户设置缓存参数错误
	public static final String USER_SET_CACHE_FAILURE										= "20010";//设置用户缓存失败
	public static final String USER_NOT_LOGIN														= "20011";//用户未登录
	public static final String USER_UPDATE_FAILURE											= "20012";//更新用户信息失败
	public static final String USER_MOBILE_ERROR												= "20013";//用户手机号错误
	public static final String USER_MOBILE_NOT_EXISTS									= "20014";//用户手机号不存在
	public static final String USER_UID_ERROR														= "20015";//用户uid错误
	public static final String USER_UDID_ERROR														= "20016";//用户udid错误
	public static final String USER_REAL_NAME_ERROR										= "20017";//用户真实姓名错误
	public static final String USER_EMAIL_ERROR													= "20018";//用户邮箱地址错误
	
	//用户个人信息相关错误码
	public static final String USER_BASE_INFO_PARAM_ERROR						= "20101";//用户基本信息参数错误
	public static final String USER_BASE_INFO_DEGREE_ERROR					= "20102";//用户基本信息－学历错误
	public static final String USER_BASE_INFO_MARRIAGE_ERROR				= "20103";//用户基本信息－婚姻错误
	public static final String USER_BASE_INFO_CHILD_ERRO							= "20104";//用户基本信息－子女个数错误
	public static final String USER_BASE_INFO_PROVINCE_ERROR				= "20105";//用户基本信息－所在省份错误
	public static final String USER_BASE_INFO_CITY_ERROR							= "20106";//用户基本信息－所在城市错误
	public static final String USER_BASE_INFO_DISTRICT_ERROR					= "20107";//用户基本信息－所在区县错误
	public static final String USER_BASE_INFO_ADDRESS_ERROR					= "20108";//用户基本信息－所在地址错误
	public static final String USER_BASE_INFO_WECHAT_ERROR					= "20109";//用户基本信息－微信号错误
	public static final String USER_BASE_INFO_INSERT_FAILURE					= "20110";//用户基本信息－入库失败
	public static final String USER_BASE_INFO_SELECT_FAILURE					= "20111";//用户基本信息－查询失败
	
	//用户职业信息相关错误码
	public static final String USER_JOB_INFO_PARAM_ERROR							= "20201";//用户职业信息参数错误
	public static final String USER_JOB_INFO_INSERT_FAILURE						= "20202";//用户职业信息－入库失败
	public static final String USER_JOB_INFO_JOBNAME_ERROR					= "20203";//用户职业信息－职业名称错误
	public static final String USER_JOB_INFO_COMPANY_NAME_ERROR	= "20204";//用户职业信息－公司名称错误
	public static final String USER_JOB_INFO_PHONE_ERROR						= "20205";//用户职业信息－单位电话错误
	
	//用户联系人信息相关错误码
	public static final String USER_LINK_MAN_INFO_PARAM_ERROR			= "20301";//用户联系人信息参数错误
	public static final String USER_LINK_MAN_INFO_INSERT_FAILURE		= "20302";//用户联系人信息－入库失败
	public static final String USER_LINK_MAN_INFO_NAME_ERROR			= "20303";//用户联系人信息－联系人姓名错误
	public static final String USER_LINK_MAN_INFO_PHONE_ERROR		= "20304";//用户联系人信息－联系电话错误
	public static final String USER_LINK_MAN_INFO_RELATION_ERROR	= "20305";//用户联系人信息－联系人类型错误
	
	//用户银行卡信息相关错误码
	public static final String USER_BANK_INFO_PARAM_ERROR					= "20401";//用户银行卡信息错误
	public static final String USER_BANK_INFO_INSERT_FAILURE					= "20402";//用户银行卡信息入库失败
	public static final String USER_BANK_INFO_BANK_NUM_ERROR			= "20403";//用户银行卡信息－银行卡号错误
	public static final String USER_BANK_INFO_MOBILE_ERROR					= "20404";//用户银行卡信息－预留手机号错误
	public static final String USER_BANK_INFO_CARDTYPE_ERROR				= "20405";//用户银行卡信息－银行卡类型错误
	public static final String USER_BANK_INFO_STATUS_ERROR					= "20406";//用户银行卡信息－银行卡状态错误
	public static final String USER_BANK_INFO_BANK_NAME_ERROR		= "20407";//用户银行卡信息－银行名称错误
	public static final String USER_BANK_INFO_SELECT_FAILURE					= "20408";//用户银行卡信息查询失败
	
	//借款记录相关错误码
	public static final String BORROW_RECORD_PARAM_ERROR					= "20501";//借款记录参数错误
	public static final String BORROW_RECORD_INSRET_FAILURE				= "20502";//借款记录入库失败
	public static final String BORROW_RECORD_ITEM_NOT_EXISTS				= "20503";//借款项目不存在
	public static final String BORROW_RECORD_LIMIT_NOT_EXISTS			= "20504";//借款项目类型不存在
	public static final String BORROW_RECORD_SELECT_FAILURE				= "20505";//借款记录查询失败
	public static final String BORROW_RECORD_ORDER_ID_ERROR				= "20506";//借款记录－订单id－参数错误
	public static final String BORROW_RECORD_MONEY_ERROR					= "20507";//借款记录－参数错误－借款金额错误
	public static final String BORROW_RECORD_STATUS_ERROR					= "20508";//借款记录－参数错误－订单状态错误
	public static final String BORROW_RECORD_APPLY_TIME_ERROR		= "20509";//借款记录－参数错误－申请时间错误
	public static final String BORROW_RECORD_CHECK_TYPE_ERROR		= "20510";//借款记录－参数错误－审核类型错误
	public static final String BORROW_RECORD_OPERATER_ERROR				= "20511";//借款记录－操作人参数错误
	public static final String BORROW_RECORD_SCORE_ERROR						= "20512";//借款记录－数据评分错误
	public static final String BORROW_RECORD_NOT_EXISTS							= "20513";//借款记录不存在
	
	
	//用户基本信息相关错误码
	public static final String USER_IDENTITY_AUTH_PARAM_ERROR			= "20601";//用户认证信息－身份认证参数错误
	public static final String USER_BODY_AUTH_PARAM_ERROR						= "20602";//用户认证信息－活体参数错误
	public static final String USER_MOBILE_AUTH_PARAM_ERROR				= "20603";//用户认证信息－手机认证参数错误
	public static final String USER_INFO_UID_PARAM_ERROR						= "20604";//用户信息uid参数错误
	
	//后台用户角色相关错误码
	public static final String ADMIN_USER_ROLE_PARAM_ERROR					= "20701";//后台用户角色参数错误
	public static final String ADMIN_USER_ROLE_NAME_ERROR					= "20702";//后台用户角色－角色名称错误
	public static final String ADMIN_USER_ROLE_DESC_ERROR						= "20703";//后台用户角色－角色描述错误
	public static final String ADMIN_USER_ROLE_STATUS_ERROR				= "20704";//后台用户角色－角色状态错误
	public static final String ADMIN_USER_ROLE_INSERT_FAILURE				= "20705";//后台用户角色－入库失败
	public static final String ADMIN_USER_ROLE_SELECT_FAILURE				= "20706";//后台用户角色－查询失败
	public static final String ADMIN_USER_ROLE_EDIT_FAILURE					= "20707";//后台用户角色－编辑失败
	public static final String ADMIN_USER_ROLE_ID_PARAM							= "20708";//后台用户角色－角色id错误
	
	//后台－菜单管理相关错误码
	public static final String ADMIN_MENU_PARAM_ERROR							= "20801";//菜单相关参数错误
	public static final String ADMIN_MENU_STATUS_ERROR							= "20802";//菜单状态参数错误
	public static final String ADMIN_MENU_PARENTID_ERROR						= "20803";//菜单父级菜单参数错误
	public static final String ADMIN_MENU_NAME_ERROR								= "20804";//菜单名称参数错误
	public static final String ADMIN_MENU_URL_ERROR									= "20805";//菜单URL参数错误
	public static final String ADMIN_MENU_INSERT_FAILURE							= "20806";//管理菜单入库失败
	public static final String ADMIN_MENU_ID_PARAM_ERROR						= "20807";//管理菜单菜单id参数错误
	
	//后台－权限管理相关错误码
	public static final String ADMIN_ROLE_PRIV_SELECT_FAILURE				= "20901";//角色权限查询失败
	public static final String ADMIN_ROLE_PRIV_PARAM_ERROR					= "20902";//角色权限－参数错误
	public static final String ADMIN_ROLE_PRIV_INSERT_FAILURE				= "20903";//角色权限－插入数据失败

	//验证码相关错误码
	public static final String MOBILE_CAPTCHA_ERROR										= "21001";//短信验证码错误
	public static final String MOBILE_CODE_ERROR												= "21002";//手机号服务密码错误
	public static final String PICTURE_CODE_ERROR												= "21003";//图片验证码错误
	public static final String MOBILE_MSG_TYPE_ERROR									= "21004";//短信类型错误
	public static final String CAPTCHA_PARAM_ERROR										= "21005";//验证码参数错误
	public static final String MOBILE_CODE_HAS_SENDED								= "21006";//短信验证码已发送
	public static final String MOBILE_CODE_HAS_INVALID								= "21007";//短信验证码已失效
	
	//管理用户相关错误码
	public static final String ADMIN_USER_PARAM_ERROR								= "21101";//管理用户参数错误
	public static final String ADMIN_USER_UID_PARAM_ERROR					= "21102";//管理用户参数错误－用户uid错误
	public static final String ADMIN_USER_STATUS_PARAM_ERROR			= "21103";//管理用户参数错误－用户状态错误
	public static final String ADMIN_USER_NAME_PARAM_ERROR				= "21104";//管理用户参数错误－用户名错误
	public static final String ADMIN_REAL_NAME_PARAM_ERROR				= "21105";//管理用户参数错误－用户真实姓名错误
	
	//帮助中心相关错误码
	public static final String HELP_FEED_BACK_PARAM_ERROR						= "21201";//帮助中心－问题反馈参数错误
	public static final String HELP_FEED_BACK_CONTENT_ERROR				= "21202";//帮助中心－问题反馈内容错误
	public static final String HELP_REGION_PID_ERROR										= "21203";//地区数据－上级地区参数错误
	
	//产品相关错误码
	public static final String PRODUCT_PARAM_ERROR										= "21301";//产品－参数错误
	public static final String PRODUCT_NAME_PARAM_ERROR						= "21302";//产品名称－参数错误
	public static final String PRODUCT_BORROW_MONEY_PARAM_ERROR = "21303";//产品借款金额－参数错误
	public static final String PRODUCT_BORROW_LIMIT_PARAM_ERROR = "21304";//产品借款期限－参数错误
	public static final String PRODUCT_DAY_RATE_PARAM_ERROR				= "21305";//产品日利率－参数错误
	public static final String PRODUCT_SERVICE_PARAM_ERROR					= "21306";//产品服务费－参数错误
	public static final String PRODUCT_INSERT_FAILURE									= "21307";//产品入库失败
	public static final String PRODUCT_ID_PARAM_ERROR								= "21208";//产品id参数错误
	
	//设置加解密Key
	public static final String AESKey		= "a6ad82fe158f02ef";//bf11a97cddcc2b50";
	
	//设置Token加密key
	public static final String TokenKey	= "86bb9a278901596e";//9a03473ebbc18006";
	
	//短信类型配置
	public static final Integer MOBILE_MSG_TYPE_REG													= 1;//注册短信
	public static final Integer MOBILE_MSG_TYPE_LOGIN											= 2;//登陆短信
	public static final Integer MOBILE_MSG_TYPE_FIND_PWD									= 3;//找回密码

	//消息队列类型
	public static final Integer QUEUE_MESSAGE_TYPE_APPLY_LOAN						= 1;//申请借款
}
