package com.migang.admin.web.app;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.migang.admin.exception.BusinessException;
import com.migang.admin.service.HelpService;

/**
 * App的帮助Controller
 *
 *@param addTime 2017-10-09
 *@param author    ChengBo
 */
@Controller
public class AppHelpController extends AppBaseController {

	@Autowired
	private HelpService helpService;
	
	/**
	 * 帮助中心　问题反馈
	 * 
	 * @param request
	 * @param requestBody
	 * @throws BusinessException
	 * 
	 * @param addTime 2017年10月9日
	 * @param author     ChengBo
	 * @return 
	 */
	@RequestMapping(value = "/app/help/feedback", method = RequestMethod.POST)
	@ResponseBody
	private Object feedback(HttpServletRequest request, @RequestBody String requestBody) throws BusinessException{
		//解析json字符串
		JSONObject jsonObject	= this.parseRequestBody(request, requestBody);
		
		//设置反馈Map
		Map<String, String> feedMap = new HashMap<String, String>();
		
		//提取返回内容
		feedMap.put("content", jsonObject.getString("content"));
		feedMap.put("uid", this.getUid()+"");
		feedMap.put("udid", "XXXX21f1f19edff198e2a2356bf4XXXX");
		
		//返回数据入库
		Boolean ret = helpService.addFeedback(feedMap);
		
		//设置返回结果
		this.set("feedbackRet", (ret?1:0));
		
		//返回数据
		return this.getAppRet();
	}
	
	/**
	 * 帮助中心首页
	 * 
	 * @param request
	 * @param model
	 * @return
	 * 
	 * @param addTime 2017年10月9日
	 * @param author     ChengBo
	 */
	@RequestMapping(value = "/app/help/index", method = RequestMethod.GET)
	private String helpIndex(HttpServletRequest request, Model model){
		//页面赋值
		model.addAttribute("page", "/app/helpIndex");
		
		return "app/helpIndex";
	}
}
