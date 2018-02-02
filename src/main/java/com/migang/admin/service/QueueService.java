package com.migang.admin.service;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

/**
 * 处理消息队列的服务
 *
 *@param addTime 2017-10-27
 *@param author    ChengBo
 */
@Service
public interface QueueService {

	/**
	 * 处理Queue任务
	 * 
	 * @param jsonObject
	 * @return
	 * 
	 * @param addTime 2017年10月27日
	 * @param author     ChengBo
	 */
	Boolean dealQueueTask(JSONObject jsonObject);
	
	/**
	 * 处理Topic任务
	 * 
	 * @param jsonObject
	 * @return
	 * 
	 * @param addTime 2017年10月27日
	 * @param author     ChengBo
	 */
	Boolean dealTopicTask(JSONObject jsonObject);
}
