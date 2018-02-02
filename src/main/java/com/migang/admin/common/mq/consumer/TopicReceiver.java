package com.migang.admin.common.mq.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

/**
 * 监听接收处理 Topic匹配消息
 *
 * @param addTime 2017年9月7日
 * @param author     ChengBo
 */
@Component("rabbitMqTopicService")
public class TopicReceiver implements MessageListener {
	//引入日志操作类
	private Logger logger = LoggerFactory.getLogger(TopicReceiver.class);
	
	/**
	 * 接收消息
	 * 
	 * @param addTime 2017年9月7日
	 * @param author     ChengBo
	 */
	@Override
	public void onMessage(Message message) {
		//记录接收队列参数
		logger.debug("TopicQueue消息消费者＝{}", message.toString());
		
		try {
//			throw new Exception("dddddddddddd");
			
			//接收队列参数转化为字符串
			String bodyMsg = new String(message.getBody(), "UTF-8");
			
			//解析请求Body中的JSON数据
			JSONObject jsonObject = JSONObject.parseObject(bodyMsg);
			
			//字符串转换成JSON串
			logger.debug("TopicQueue消息消费者接收JSON：{}", jsonObject);
		} catch (Exception e) {
			//处理失败记录日志
			logger.debug("TopicQueueReceiver onMessage exception: "+e.getMessage());
		}
	}
}
