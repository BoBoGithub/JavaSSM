package com.migang.admin.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.migang.admin.service.QueueService;

/**
 * 处理消息队列的服务
 *
 *@param addTime 2017-10-27
 *@param author    ChengBo
 */
@Service
public class QueueServiceImpl implements QueueService {
	//引入日志操作类
	private Logger logger = LoggerFactory.getLogger(QueueServiceImpl.class);

	/**
	 * 处理Queue任务
	 * 
	 * @param jsonObject
	 * @return
	 * 
	 * @param addTime 2017年10月27日
	 * @param author     ChengBo
	 */
	@Override
	public Boolean dealQueueTask(JSONObject jsonObject) {
		//记录处理参数
		logger.debug(" dealQueueTask param: {}", jsonObject);
		
		//处理相关逻辑
		
		return true;
	}

	/**
	 * 处理Topic任务
	 * 
	 * @param jsonObject
	 * @return
	 * 
	 * @param addTime 2017年10月27日
	 * @param author     ChengBo
	 */
	@Override
	public Boolean dealTopicTask(JSONObject jsonObject) {
		//记录处理参数
		logger.debug(" dealTopicTask param: {}", jsonObject);
		
		//处理相关逻辑
		
		return true;
	}

}
