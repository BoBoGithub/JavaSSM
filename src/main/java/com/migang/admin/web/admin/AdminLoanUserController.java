package com.migang.admin.web.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.migang.admin.common.utils.CommonUtils;
import com.migang.admin.entity.Region;
import com.migang.admin.entity.User;
import com.migang.admin.exception.BusinessException;
import com.migang.admin.service.HelpService;
import com.migang.admin.service.LoanService;
import com.migang.admin.service.UserService;

/**
 * 后台 借款用户管理
 *
 *@param addTime 2017-10-09
 *@param author     ChengBo
 */
@Controller
public class AdminLoanUserController extends AdminBaseController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private LoanService loanService;
	
	@Autowired
	private HelpService helpService;
	
	/**
	 * 借款用户列表页面
	 * 
	 * @return
	 * 
	 * @param addTime 2017年10月9日
	 * @param author     ChengBo
	 * @throws BusinessException 
	 */
	@RequestMapping(value = "/admin/loan/user/list", method = RequestMethod.GET)
	private String loanUserList(Model model) throws BusinessException{
		//调取一级省份数据
		List<Region> regionList = helpService.getRegionByParentId(0);
		
		//设置省份数据
		model.addAttribute("regionList", regionList);
		
		return "admin/user/loanUserList";
	}
	
	/**
	 * 查询借款用户列表
	 * 
	 * @param param
	 * 
	 * @param addTime 2017年10月9日
	 * @param author     ChengBo
	 * @return 
	 * @throws BusinessException 
	 */
	@RequestMapping(value = "/admin/get/loan/user/list", method = RequestMethod.POST)
	@ResponseBody
	private Object getLoanUserList(@RequestBody String requestBody) throws BusinessException{
		//接收请求参数
		JSONObject jsonObject = parseJsonStrToObj(requestBody);
		
		//实例化查询Map
		Map<String, String> userMap = new HashMap<String, String>();
		
		//设置查询条件
		if(jsonObject.getString("uid") != null && !jsonObject.getString("uid").isEmpty()){
			userMap.put("uid", jsonObject.getString("uid"));
		}
		
		//设置查询手机号
		if(jsonObject.getString("mobile") != null && !jsonObject.getString("mobile").isEmpty()){
			userMap.put("mobile", jsonObject.getString("mobile"));
		}
		
		//设置身份认证
		if(jsonObject.getString("realName") != null && !jsonObject.getString("realName").equals("0")){
			userMap.put("shenfenAuth", 	jsonObject.getString("realName").equals("1") ? "1" : "2");
			userMap.put("huotiAuth", 		jsonObject.getString("realName").equals("1") ? "1" : "2");
		}
		
		//设置完善信息
		if(jsonObject.getString("infoAuth") != null && !jsonObject.getString("infoAuth").equals("0")){
			userMap.put("persionAuth",	jsonObject.getString("infoAuth").equals("1") ? "1" : "2");
			userMap.put("jobAuth",				jsonObject.getString("infoAuth").equals("1") ? "1" : "2");
			userMap.put("contactsAuth",	jsonObject.getString("infoAuth").equals("1") ? "1" : "2");
		}
		
		//设置手机号认证
		if(jsonObject.getString("phoneAuth") != null && !jsonObject.getString("phoneAuth").equals("0")){
			userMap.put("mobileAuth", jsonObject.getString("phoneAuth").equals("1") ? "1" : "2");
		}
		
		//设置绑卡认证
		if(jsonObject.getString("bandcardAuth") != null && !jsonObject.getString("bandcardAuth").equals("0")){
			userMap.put("bandcardAuth", jsonObject.getString("bandcardAuth").equals("1") ? "1" : "2");
		}
		
		//设置注册开始时间
		if(jsonObject.getString("regStartTime") != null && !jsonObject.getString("regStartTime").isEmpty()){
			userMap.put("regStartTime", CommonUtils.getTimeStampByDate(jsonObject.getString("regStartTime")+" 00:00:00"));
		}
		
		//设置注册结束时间
		if(jsonObject.getString("regEndTime") != null && !jsonObject.getString("regEndTime").isEmpty()){
			userMap.put("regEndTime", CommonUtils.getTimeStampByDate(jsonObject.getString("regEndTime")+" 23:59:59"));
		}
		
		//设置所在省份
		if(jsonObject.getString("province") != null && !jsonObject.getString("province").equals("0")){
			userMap.put("province", jsonObject.getString("province"));
		}
		
		//设置所在城市
		if(jsonObject.getString("city") != null && !jsonObject.getString("city").equals("0")){
			userMap.put("city", jsonObject.getString("city"));
		}
		
		//设置所在区县
		if(jsonObject.getString("district") != null && !jsonObject.getString("district").equals("0")){
			userMap.put("district", jsonObject.getString("district"));
		}
		
		//设置查询页数
		userMap.put("page", jsonObject.getString("page") != null ? jsonObject.getString("page") : "1");
		userMap.put("pageSize", jsonObject.getString("pageSize") != null ? jsonObject.getString("pageSize") : "12");
		
		//查询借款用户数据
		List<Map<String, String>> userList = dealGetUserList(userMap);
		
		//查询注册用户总条数
		int userTotalNum = userService.countUserNum(userMap);
		
		//设置返回用户列表数据
		this.set("list", userList);
		
		//设置返回总条数
		this.set("total", userTotalNum);
		
		//返回结果
		return this.getRet();
	}

	/**
	 * 提取处理返回结果
	 * 
	 * @param userListData
	 * @return
	 * 
	 * @param addTime 2017年10月10日
	 * @param author     ChengBo
	 * @throws BusinessException 
	 */
	private List<Map<String, String>>	dealGetUserList(Map<String, String> userParamMap) throws BusinessException{
		//调取用户数据
		List<User> userListData = userService.getUserList(userParamMap);
		
		//实例化返回结果
		List<Map<String, String>> userList = new ArrayList<Map<String, String>>();
		
		//检查是否有数据
		if(userListData.size() == 0){
			return userList;
		}
		
		//设置提取用户uid的List
		List<Long> uidList = new ArrayList<Long>();
		
		//提取数据
		for(User userInfo : userListData){
			//提取用户uid
			uidList.add(userInfo.getUid());
			
			//设置提取Map
			Map<String, String> userMap = new HashMap<String, String>();
			
			//提取用户uid
			userMap.put("uid", userInfo.getUid()+"");
			
			//提取用户手机号
			userMap.put("mobile", userInfo.getMobile());
			
			//提取ip地址
			userMap.put("ip", userInfo.getIp());
			
			//提取是否实名
			userMap.put("realName",  (userInfo.getShenfenAuth() != 0 && userInfo.getHuotiAuth() != 0) ? "1" : "0");

			//提取是否完善信息
			userMap.put("infoAuth",  (userInfo.getJobAuth() != 0 && userInfo.getContactsAuth() != 0 && userInfo.getPersonAuth() != 0) ? "1" : "0");
			
			//提取手机号授信信息
			userMap.put("mobileAuth", userInfo.getMobileAuth() != 0 ? "1":"0");
			
			//设置注册时间
			userMap.put("regTime", CommonUtils.getDateByTimeStamp(userInfo.getCtime()));
			
			//设置借款笔数
			userMap.put("loanNum", "0");
			
			//追加到数组
			userList.add(userMap);
		}
		
		//获取用户的借款笔数
		if(uidList.size() > 0){
			//用户借款数据
			Map<Long, Map<String, String>> userRecordData = loanService.sumRecordNumByUids(uidList);
			
			//设置用户的借款数
			for(int i = 0; i < userList.size(); i++){
				if(userRecordData.containsKey(Long.parseLong(userList.get(i).get("uid")))){
					//提取用户的借款记录数
					String num = userRecordData.get(Long.parseLong(userList.get(i).get("uid"))).get("num");
					
					//设置返回数据中的借款记录数
					userList.get(i).put("loanNum", num);
				}
			}
		}
		
		return userList;
	}

}