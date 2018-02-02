package com.migang.admin.web.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.migang.admin.common.utils.CommonUtils;
import com.migang.admin.entity.HelpFeedBack;
import com.migang.admin.entity.Region;
import com.migang.admin.exception.BusinessException;
import com.migang.admin.service.HelpService;

/**
 * 后台 借款订单控制器
 *
 *@param addTime 2017-10-11
 *@param author     ChengBo
 */
@Controller
public class AdminHelpController extends AdminBaseController {

	@Autowired
	private HelpService helpService;
	
	/**
	 * 用户反馈列表页
	 * 
	 * @return
	 * 
	 * @param addTime 2017年10月27日
	 * @param author     ChengBo
	 */
	@RequestMapping(value = "/admin/help/feedback/list", method = RequestMethod.GET)
	private String helpFeedBackList(){
		return "admin/help/feedBackList";
	}
	
	/**
	 * 查询问题反馈列表
	 * 
	 * @param requestBody
	 * @return
	 * 
	 * @param addTime 2017年10月27日
	 * @param author     ChengBo
	 * @throws BusinessException 
	 */
	@RequestMapping(value = "/admin/help/get/feedback/list", method = RequestMethod.POST)
	@ResponseBody
	private Object getFeedBackList(@RequestBody String requestBody) throws BusinessException{
		//接收请求参数
		JSONObject jsonObject = parseJsonStrToObj(requestBody);
		
		//设置查询Map
		Map<String, String> feedMap = new HashMap<String, String>();
		
		//接收查询参数
		feedMap.put("uid",				jsonObject.getString("uid"));
		feedMap.put("udid",			jsonObject.getString("udid"));
		feedMap.put("startTime", 	jsonObject.getString("startTime"));
		feedMap.put("endTime", 	jsonObject.getString("endTime"));
		feedMap.put("page", 			jsonObject.getString("page"));
		feedMap.put("pageSize", 	jsonObject.getString("pageSize"));
		
		//调取反馈列表数据
		List<Map<String, String>> feedList = dealFeedListData(feedMap);
		
		//汇总反馈数据总条数
		int total	= helpService.countFeedBack(feedMap);
		
		//返回数据
		this.set("list", feedList);
		this.set("total", total);
		
		//返回结果
		return this.getRet();
	}
	
	private List<Map<String, String>> dealFeedListData(Map<String, String> feedMap){
		//实例化处理结果变量
		List<Map<String, String>> feedData = new ArrayList<Map<String, String>>();
		
		//调取反馈数据
		List<HelpFeedBack> feedList = helpService.getFeedBackList(feedMap);
		if(feedList.size() <= 0){
			return feedData;
		}
		
		//处理返回结果
		for(HelpFeedBack feed : feedList){
			//实例化存储Map
			Map<String, String> tmpMap = new HashMap<String, String>();
			
			//设置返回结果
			tmpMap.put("feedBackId", feed.getFeedBackId()+"");
			tmpMap.put("uid", feed.getUid()+"");
			tmpMap.put("udid", feed.getUdid()+"");
			tmpMap.put("shorCon", CommonUtils.subString(feed.getContent(), 30)+"...");
			tmpMap.put("content", feed.getContent());
			tmpMap.put("addTime", CommonUtils.getDateByTimeStamp(feed.getAddTime()));
			tmpMap.put("reply", feed.getReply());
			
			//追加返回结果
			feedData.add(tmpMap);
		}
		
		return feedData;
	}
	
	/**
	 * 获取父级地区下的数据
	 * 
	 * @param requestBody
	 * @return
	 * @throws BusinessException
	 * 
	 * @param addTime 2017年10月30日
	 * @param author     ChengBo
	 */
	@RequestMapping(value = "/admin/help/get/region/list", method = RequestMethod.POST)
	@ResponseBody
	private Object getRegionByPid(@RequestBody String requestBody) throws BusinessException{
		//接收请求参数
		JSONObject jsonObject = parseJsonStrToObj(requestBody);
		
		//提取参数
		int pid = jsonObject.getInteger("pid");
		
		//调取一级省份数据
		List<Region> regionList = helpService.getRegionByParentId(pid);
		
		//设置省份数据
		this.set("regionList", regionList);
		
		return this.getRet();
	}
}