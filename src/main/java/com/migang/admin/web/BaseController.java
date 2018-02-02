package com.migang.admin.web;

import java.util.HashMap;
import java.util.Map;

import com.migang.admin.dto.ResultData;

/**
 * Controller的基类
 *
 *＠param addTime 2017-08-28
 *＠param author     ChengBo
 */
public class BaseController {	
	//初始化Map
	protected static final Map<String, Object> baseMap = new HashMap<String, Object>();
	
	/**
	 * 设置返回数据
	 */
	protected void set(String key, Object obj){
		baseMap.put(key, obj);
	}
	
	/**
	 * 设置总返回结果
	 */
	protected ResultData<Map<String, Object>> getRet(){
		return new ResultData<Map<String, Object>>(BaseController.baseMap);
	}
	
}