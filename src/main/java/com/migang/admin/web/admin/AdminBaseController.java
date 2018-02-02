package com.migang.admin.web.admin;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.migang.admin.common.CommonConstant;
import com.migang.admin.entity.AdminUser;
import com.migang.admin.exception.BusinessException;
import com.migang.admin.service.AdminUserService;
import com.migang.admin.web.BaseController;
import com.migang.admin.web.app.AppBaseController;

/**
 * 后台请求控制器基类
 *
 *＠param addTime 2017-10-09
 *@param author   ChengBo
 */
public class AdminBaseController extends BaseController {
	//引入日志操作
	protected static final Logger logger = LoggerFactory.getLogger(AppBaseController.class);
	
	@Autowired
	private AdminUserService adminUserService;

	/**
	 * 解析字符串为Json对象
	 * 
	 * @param jsonStr
	 * @return
	 * 
	 * @param addTime 2017年9月21日
	 * @param author     ChengBo
	 * @throws BusinessException 
	 */
	public  JSONObject parseJsonStrToObj(String jsonStr) throws BusinessException{
		try{			
			//解析请求Body中的JSON数据
			JSONObject jsonObject = JSONObject.parseObject(jsonStr);
			
			//字符串过滤　TODO
//			Iterator iterator = jsonObject.keys();
//			while(iterator.hasNext()){
//			        key = (String) iterator.next();
//			        value = jsonObject.getString(key);
//					if(!ValidateUtils.checkSecurity(value)){
//						//抛出异常
//						throw new BusinessException(CommonConstant.COMMON_PARAM_ERROR, "请求参数格式错误");
//					}
//			}
			
			//清空现有的baseMap
			BaseController.baseMap.clear();
			
			//返回解析后的Json对象
			return jsonObject;
		}catch(Exception ex){
			//记录日志
			logger.error("AdminBaseController parseJsonStrToObj requestBody: "+jsonStr+" exception: "+ex.getMessage());
			
			//抛出异常
			throw new BusinessException(CommonConstant.COMMON_PARAM_ERROR, "请求参数格式错误");
		}
	}
	
	/**
	 * 
	 * 
	 * @return
	 * 
	 * @param addTime 2017年10月31日
	 * @param author     ChengBo
	 * @throws BusinessException 
	 */
	public AdminUser getAdminUser(HttpServletRequest request) throws BusinessException{
		//获取用户信息
		long adminUid =  Long.parseLong(request.getSession().getAttribute("adminUid").toString());
		
		//根据用户uid获取管理用户信息
		AdminUser userInfo = adminUserService.getAdminUserInfoByUid(adminUid);
		
		//返回登陆用户信息
		return userInfo;
	}
}
