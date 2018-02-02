package com.migang.admin.web.app;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.migang.admin.common.CommonConstant;
import com.migang.admin.common.utils.CommonUtils;
import com.migang.admin.common.utils.CryptoUtils;
import com.migang.admin.dto.ResultData;
import com.migang.admin.exception.BusinessException;
import com.migang.admin.service.UserService;
import com.migang.admin.web.BaseController;

/**
 * App请求控制器基类
 *
 *＠param addTime 2017-09-21
 *@param author   ChengBo
 */
public class AppBaseController extends BaseController {
	//引入日志操作
	protected static final Logger logger = LoggerFactory.getLogger(AppBaseController.class);
	
	@Autowired
	private UserService userService;

	//当前登陆用户的UID
	private long uid;
	
	//获取用户UID
	public long getUid() {
		return uid;
	}

	//设置用户UID
	public void setUid(long uid) {
		this.uid = uid;
	}
	
	/**
	 * 解析Json字符串为Json对象
	 * 
	 * @param requestBody
	 * @return
	 * 
	 * @param addTime 2017年9月20日
	 * @param author     ChengBo
	 * 
	 * @throws BusinessException 
	 */
	public JSONObject parseRequestBody(HttpServletRequest request, String requestBody) throws BusinessException{
		//记录接收请求参数
		logger.debug("AppBaseController  requestBody:"+ requestBody);

		//解析请求Body中的JSON数据
		JSONObject jsonObject = parseJsonStrToObj(requestBody);
		
		//检查是否存在参数内容 {appid区别渠道, data真正的参数}
		if(jsonObject == null || jsonObject.get("appid") == null || jsonObject.get("data") == null){
			//记录日志
			logger.error("AppBaseController parseJsonStrToObj requestBody: "+requestBody+" jsonObject: "+jsonObject);
			
			//抛出异常
			throw new BusinessException(CommonConstant.COMMON_PARAM_ERROR, "请求参数错误");
		}
		
		//解密加密参数
		String requestData = decodeRequestBody(jsonObject.get("data").toString());
		
		//记录接收业务参数
		logger.debug("AppBaseController  requestParam:"+ requestData);
		
		//解析接收请求参数
		JSONObject jsonData = parseJsonStrToObj(requestData);
		
		//清空现有的baseMap
		BaseController.baseMap.clear();
		
		//处理Token
		if(request.getHeader("Security-Authorization-MG") != null){
        //if(jsonData != null && jsonData.containsKey("token")){
				logger.debug("==== {}", request.getHeader("Security-Authorization-MG"));
                //验证Token
			    this.checkToken(request.getHeader("Security-Authorization-MG"));
                //this.checkToken(jsonData.getString("token"));

                //去除Token
                //jsonData.remove("token");
        }else{
        	//未登录状态
        	this.setUid(0);
        }
		
		//返回解析后的Json对象
		return jsonData;
	}
	
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
			
			//返回解析后的Json对象
			return jsonObject;
		}catch(Exception ex){
			//记录日志
			logger.error("AppBaseController parseJsonStrToObj requestBody: "+jsonStr+" exception: "+ex.getMessage());
			
			//抛出异常
			throw new BusinessException(CommonConstant.COMMON_PARAM_ERROR, "请求参数格式错误");
		}
	}
	
	/**
	 * 解密字符串
	 * 
	 * @param encodeString
	 * @return
	 * 
	 * @param addTime 2017年9月20日
	 * @param author     ChengBo
	 * @throws BusinessException 
	 */
	public String decodeRequestBody(String encodeString) throws BusinessException{
			//解密字符串
			String deString = CryptoUtils.AESDecode(encodeString, CommonConstant.AESKey);

			//返回解密后的字符串
			return deString;
	}
	
	/**
	 * 根据用户uid生成用户唯一标示token
	 * 
	 * @param str
	 * @return
	 * 
	 * @param addTime 2017年9月20日
	 * @param author     ChengBo
	 * @throws Exception 
	 */
	public String getUserToken(long uid) throws BusinessException{	
		//检查参数
		if(uid == 0){
			throw new BusinessException(CommonConstant.USER_SET_CACHE_PARAM_ERROR, "用户相关信息错误");
		}
		
		//加密当前用户的uid
		String md5Uid = CryptoUtils.encodeMD5(CommonConstant.TokenKey+":"+uid+":"+CommonUtils.getCurrTimeStamp());
		
		//AES加密生成Token
		String token = CryptoUtils.AESEncode(md5Uid, CommonConstant.TokenKey);
		
		//设置用户登陆缓存数据
		userService.setUserSession(uid, md5Uid, token);
		
		//返回token
		return token;
	}
	
	/**
	 * 验证请求token
	 * 
	 * @param token
	 * 
	 * @param addTime 2017年9月21日
	 * @param author     ChengBo
	 * 
	 * @throws BusinessException 
	 */
	public void checkToken(String token) throws BusinessException{
		//检查参数
		if(token.isEmpty()){
			throw new BusinessException(CommonConstant.COMMON_TOKEN_VALIDATE_ERROR, "Token参数不能为空");
		}

		//设置解密后的Token
		String deToken = CryptoUtils.AESDecode(token, CommonConstant.TokenKey);
		
		//检查请求Token 获取uid
		long uid = userService.checkUserToken(deToken, token);
		
		//设置当前用户的uid
		this.setUid(uid);
	}

	/**
	 * 加密返回结果
	 * 
	 * @return
	 * @throws BusinessException
	 * 
	 * @param addTime 2017年10月24日
	 * @param author     ChengBo
	 * @throws JsonProcessingException 
	 */
	protected Object getAppRet() throws BusinessException{
		try{
			//map数据转Token
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(BaseController.baseMap);
			
			//设置返回加密结果
	//		String retStr = CryptoUtils.AESEncode(BaseController.baseMap.toString(), CommonConstant.AESKey);
			String retStr = CryptoUtils.AESEncode(json, CommonConstant.AESKey);
			logger.debug("==== {}, {}", json, retStr);
			
			//返回数据
//			return new ResultData<String>(retStr);
			return new ResultData<Object>(BaseController.baseMap);
		}catch(Exception ex){
			logger.debug("getAppRet Exception : {}", ex.getMessage());
			throw new BusinessException(CommonConstant.COMMON_PARAM_ERROR, "json解析错误");
		}
	}

}
