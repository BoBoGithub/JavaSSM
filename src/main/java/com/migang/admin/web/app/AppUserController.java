package com.migang.admin.web.app;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.migang.admin.entity.UserBank;
import com.migang.admin.exception.BusinessException;
import com.migang.admin.service.AuthService;
import com.migang.admin.service.MobileMessageService;
import com.migang.admin.service.UserService;

/**
 * App的用户Controller
 *
 *@param addTime 2017-09-19
 *@param author    ChengBo
 */
@Controller
public class AppUserController extends AppBaseController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthService authService;
	
	@Autowired
	private MobileMessageService mobileMsgService;
	
	/**
	 * 用户注册
	 * 
	 * @param requestBody
	 * @return
	 * 
	 * @param addTime 2017年9月19日
	 * @param author     ChengBo
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value = "/app/user/reg", method = RequestMethod.POST)
	private Object userReg(HttpServletRequest request, @RequestBody String requestBody) throws BusinessException {
		//解析json字符串
		JSONObject jsonObject = this.parseRequestBody(request, requestBody);

		//提取手机号
		String mobile			= jsonObject.getString("mobile");
		String password	= jsonObject.getString("password");
		String captcha		= jsonObject.getString("captcha");
		String regIp				= request.getRemoteAddr();

		//用户注册
		long uid = userService.userReg(mobile, password, captcha, regIp);
		
		//设置返回结果
		this.set("uid",(uid > 0 ? uid : 0));
		
		//设置Token
		String token = this.getUserToken(uid);

		//设置返回用户唯一标示Token
		this.set("token", token);
		
		//返回结果
		return this.getAppRet();
	}
	
	/**
	 * 获取用户个人信息
	 * 
	 * @param addTime 2017年9月21日
	 * @param author     ChengBo
	 * @return 
	 * 
	 * @throws BusinessException 
	 */
	@RequestMapping(value = "/app/get/user/info", method = RequestMethod.POST)
	@ResponseBody
	private Object getUserInfo(HttpServletRequest request, @RequestBody String requestBody) throws BusinessException{		
		//解析json字符串
		this.parseRequestBody(request, requestBody);
		
		//调取用户信息
		Map<String, Object> userInfo = userService.getUserByUid(this.getUid());
		
		//设置返回结果
		this.set("userInfo", userInfo);
		
		//返回结果
		return this.getAppRet();
	}

	/**
	 * 用户使用密码登陆操作
	 * 
	 * @param addTime 2017年9月21日
	 * @param author     ChengBo
	 * @return 
	 * @throws BusinessException 
	 */
	@RequestMapping(value = "/app/user/login", method = RequestMethod.POST)
	@ResponseBody
	private Object userLoginByPwd(HttpServletRequest request, @RequestBody String requestBody) throws BusinessException{	
		//解析json字符串
		JSONObject jsonObject = this.parseRequestBody(request, requestBody);
		
		//提取手机号和密码
		String mobile			= jsonObject.getString("mobile");
		String password	= jsonObject.getString("password");
		
		//用户登陆
		long uid = userService.doUserLogin(mobile, password);
		
		//获取用户Token
		String token = this.getUserToken(uid);
		
		//设置返回结果
		this.set("token", token);
		
		//返回结果
		return this.getAppRet();
	}
	
	/**
	 * 用户使用短信验证码登陆操作
	 * 
	 * @param request
	 * @param requestBody
	 * @return
	 * 
	 * @param addTime 2017年10月26日
	 * @param author     ChengBo
	 * @throws BusinessException 
	 */
	@RequestMapping(value = "/app/user/code/login", method = RequestMethod.POST)
	@ResponseBody
	private Object userLoginByCaptcha(HttpServletRequest request, @RequestBody String requestBody) throws BusinessException{
		//解析json字符串
		JSONObject jsonObject = this.parseRequestBody(request, requestBody);
		
		//提取手机号和密码
		String mobile			= jsonObject.getString("mobile");
		String code				= jsonObject.getString("captcha");
		
		//用户登陆
		long uid = userService.doUserLoginByCaptcha(mobile, code);
		
		//获取用户Token
		String token = this.getUserToken(uid);
		
		//设置返回结果
		this.set("token", token);
		
		//返回结果
		return this.getAppRet();
	}
	
	/**
	 * 用户退出操作
	 * 
	 * @param requestBody
	 * 
	 * @param addTime 2017年9月22日
	 * @param author     ChengBo
	 * @return 
	 * @throws BusinessException 
	 */
	@RequestMapping(value = "/app/user/logout",  method = RequestMethod.POST)
	@ResponseBody
	private Object userLogout(HttpServletRequest request, @RequestBody String requestBody) throws BusinessException{
		//解析json字符串
		this.parseRequestBody(request, requestBody);
		
		//退出登陆
		Boolean ret = userService.doUserLogout(this.getUid());
		
		//设置返回结果
		this.set("isLogin", (ret?1:0));
		
		//返回结果
		return this.getAppRet();
	}
	
	/**
	 * 设置用户基本信息数据
	 * 
	 * @param requestBody
	 * 
	 * @param addTime 2017年9月25日
	 * @param author     ChengBo
	 * @return 
	 * @throws BusinessException 
	 */
	@RequestMapping(value = "/app/user/base/add", method = RequestMethod.POST)
	@ResponseBody
	private Object addUserBaseInfo(HttpServletRequest request, @RequestBody String requestBody) throws BusinessException{
		//解析json字符串
		JSONObject jsonObject = this.parseRequestBody(request, requestBody);
		
		//实例化存储Map
		Map<String, Object> map = new HashMap<String, Object>();
		
		//设置接收参数
		map.put("uid", 				this.getUid());
		map.put("degree", 		jsonObject.getString("degree"));
		map.put("marriage", 	jsonObject.getString("marriage"));
		map.put("child", 			jsonObject.getString("child"));
		map.put("province", 	jsonObject.getString("province"));
		map.put("city", 				jsonObject.getString("city"));
		map.put("district", 		jsonObject.getString("district"));
		map.put("address", 		jsonObject.getString("address"));
		map.put("liveTime", 	jsonObject.getString("liveTime"));
		map.put("wechat", 		jsonObject.getString("wechat"));
		
		//个人信息入库
		 Boolean ret = userService.insertUserBaseInfo(map);
		 
		 //设置返回结果
		 this.set("ret", ret?1:0);
		 
		 //返回结果
		 return this.getAppRet();
	}
	
	/**
	 * 新增用户的职业信息
	 * 
	 * @param request
	 * @param requestBody
	 * 
	 * @param addTime 2017年9月25日
	 * @param author     ChengBo
	 * @return 
	 * @throws BusinessException 
	 */
	@RequestMapping(value = "/app/user/job/add", method = RequestMethod.POST)
	@ResponseBody
	private Object addUserJobInfo(HttpServletRequest request, @RequestBody String requestBody) throws BusinessException{
		//解析json字符串
		JSONObject jsonObject = this.parseRequestBody(request, requestBody);
		
		//实例化存储Map
		Map<String, Object> map = new HashMap<String, Object>();
		
		//设置接收参数
		map.put("uid", 				this.getUid());
		map.put("jobName",	jsonObject.getString("jobName"));
		map.put("company",	jsonObject.getString("company"));
		map.put("income", 		jsonObject.getString("income"));
		map.put("province", 	jsonObject.getString("province"));
		map.put("city", 				jsonObject.getString("city"));
		map.put("district", 		jsonObject.getString("district"));
		map.put("address", 		jsonObject.getString("address"));
		map.put("phone", 		jsonObject.getString("phone"));
		
		//个人职业入库
		 Boolean ret = userService.insertUserJobInfo(map);
		 
		 //设置返回结果
		 this.set("ret", ret?1:0);
		 
		 //返回结果
		 return this.getAppRet();
	}
	
	/**
	 * 新增用户联系人信息
	 * 
	 * @param request
	 * @param requestBody
	 * 
	 * @param addTime 2017年9月26日
	 * @param author     ChengBo
	 * @return 
	 * @throws BusinessException 
	 */
	@RequestMapping(value = "/app/user/link/man/add", method = RequestMethod.POST)
	@ResponseBody
	private Object addUserLinkManInfo(HttpServletRequest request, @RequestBody String requestBody) throws BusinessException{
		//解析json字符串
		JSONObject jsonObject = this.parseRequestBody(request, requestBody);

		//实例化存储Map
		Map<String, Object> map = new HashMap<String, Object>();

		//设置接收参数
		map.put("uid", 				this.getUid());
		map.put("name",			jsonObject.getString("name"));
		map.put("phone",		jsonObject.getString("phone"));
		map.put("relation", 		jsonObject.getString("relation"));
		map.put("remark", 		jsonObject.getString("remark"));

		//个人联系人信息入库
		 Boolean ret = userService.insertUserLinkManInfo(map);

		 //设置返回结果
		 this.set("ret", ret?1:0);

		//返回结果
		return this.getAppRet();
	}
	
	/**
	 * 手机号服务密码验证
	 * 
	 * @param request
	 * @param requestBody
	 * 
	 * @param addTime 2017年9月26日
	 * @param author     ChengBo
	 * @return 
	 * @throws BusinessException 
	 */
	@RequestMapping(value = "/app/auth/mobile/code", method = RequestMethod.POST)
	@ResponseBody
	private Object authMobileServiceCode(HttpServletRequest request, @RequestBody String requestBody) throws BusinessException{
		//解析json字符串
		JSONObject jsonObject = this.parseRequestBody(request, requestBody);
		
		//获取用户信息
		Map<String, Object> userInfo = userService.getUserByUid(this.getUid());
		
		//设置验证参数
		String mobile	= userInfo.get("mobile").toString();
		String code		= jsonObject.getString("serviceCode");
		
		//认证手机号服务密码
		Boolean ret = authService.authMobileServiceCode(mobile, code);
		
		//设置认证结果
		this.set("auth", (ret?1:0));
		
		//返回结果
		return this.getAppRet();
	}
	
	/**
	 * 验证 手机授权验证码
	 * 
	 * @param request
	 * @param requestBody
	 * 
	 * @param addTime 2017年9月26日
	 * @param author     ChengBo
	 * @return 
	 * @throws BusinessException 
	 */
	@RequestMapping(value = "/app/auth/mobile/info", method = RequestMethod.POST)
	@ResponseBody
	private Object authMobileAuthCode(HttpServletRequest request, @RequestBody String requestBody) throws BusinessException{
		//解析json字符串
		JSONObject jsonObject = this.parseRequestBody(request, requestBody);

		//设置验证参数
		String mobileCode	= jsonObject.getString("mobileCode");
		String picCode			= jsonObject.getString("picCode");
		
		//验证用户的手机授权
		Boolean ret = authService.authUserMobile(this.getUid(), mobileCode, picCode);
		
		//设置认证结果
		this.set("auth", (ret ? 1 : 0));
		
		//返回结果
		return this.getAppRet();
	}
	
	/**
	 * 提交银行卡验证
	 * 
	 * @param request
	 * @param requestBody
	 * 
	 * @throws BusinessException
	 * 
	 * @param addTime 2017年9月27日
	 * @param author     ChengBo
	 * @return 
	 */
	@RequestMapping(value = "/app/auth/user/bank", method = RequestMethod.POST)
	@ResponseBody
	private Object authBankCard(HttpServletRequest request, @RequestBody String requestBody) throws BusinessException{
		//解析json字符串
		JSONObject jsonObject = this.parseRequestBody(request, requestBody);
		
		//实例化Map
		Map<String, String> bankMap = new HashMap<String, String>();
		
		//提取银行卡参数
		bankMap.put("uid", 						this.getUid()+"");
		bankMap.put("bankNum", 			jsonObject.getString("bankNum"));
		bankMap.put("mobile", 					jsonObject.getString("mobile"));
		bankMap.put("bankName",			jsonObject.getString("bankName"));
		bankMap.put("bankProvince", 	jsonObject.getString("bankProvince"));
		bankMap.put("bankCity", 				jsonObject.getString("bankCity"));
		
		//银行卡验证入库
		Boolean ret = userService.authAddUserBank(bankMap);
		
		//设置验证结果
		this.set("authRet", ret?1:0);
		
		//返回结果
		return this.getAppRet();
	}
	
	/**
	 * 获取用户银行卡信息
	 * 
	 * @param request
	 * @param requestBody
	 * 
	 * @param addTime 2017年9月27日
	 * @param author     ChengBo
	 * @return 
	 * @throws BusinessException 
	 */
	@RequestMapping(value = "/app/get/user/baink/info", method = RequestMethod.POST)
	@ResponseBody
	private Object getUserBankInfo(HttpServletRequest request, @RequestBody String requestBody) throws BusinessException{
		//解析json字符串
		this.parseRequestBody(request, requestBody);
		
		//查询用户银行卡信息
		UserBank userBank = userService.getUserBankByUid(this.getUid());
		
		//设置返回数据
		this.set("userBankInfo", userBank);
		
		//返回结果
		return this.getAppRet();
	}
	
	/**
	 * 发送短信验证码
	 * 
	 * @param request
	 * @param requestBody
	 * @return
	 * 
	 * @param addTime 2017年10月25日
	 * @param author     ChengBo
	 * @throws BusinessException 
	 */
	@RequestMapping(value = "/app/send/mobile/code", method = RequestMethod.POST)
	@ResponseBody
	private Object sendMobileCode(HttpServletRequest request, @RequestBody String requestBody) throws BusinessException{
		//解析json字符串
		JSONObject jsonObject = this.parseRequestBody(request, requestBody);
		
		//提取请求参数
		String mobile = jsonObject.getString("mobile");
		int msgType	= jsonObject.getInteger("type");
		
		//发送短信
		Boolean ret = mobileMsgService.sendMobileMsgByType(mobile, msgType);
		
		//设置返回结果
		this.set("ret", (ret?1:0));
		
		//返回结果
		return this.getAppRet();
	}
	
	/**
	 * 找回密码
	 * 
	 * @param request
	 * @param requestBody
	 * @return
	 * 
	 * @param addTime 2017年10月26日
	 * @param author     ChengBo
	 * @throws BusinessException 
	 */
	@RequestMapping(value = "/app/find/pwd", method = RequestMethod.POST)
	@ResponseBody
	private Object findLoginPwd(HttpServletRequest request, @RequestBody String requestBody) throws BusinessException{
		//解析json字符串
		JSONObject jsonObject = this.parseRequestBody(request, requestBody);
		
		//提取请求参数
		String mobile		= jsonObject.getString("mobile");
		String captcha	= jsonObject.getString("captcha");
		String pwd			= jsonObject.getString("password");
	
		//设置新密码
		Boolean ret = userService.setUserNewPwdByCaptcha(mobile, captcha, pwd);
		
		//设置返回结果
		this.set("ret", (ret?"1":"0"));
		
		//返回数据
		return this.getAppRet();
	}
}