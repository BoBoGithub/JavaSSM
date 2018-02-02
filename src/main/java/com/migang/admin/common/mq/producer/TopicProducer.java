package com.migang.admin.common.mq.producer;

import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 发送Topic匹配消息
 *
 * @param addTime 2017年9月7日
 * @param author     ChengBo
 */
@Component
public class TopicProducer {
	//引入日志操作类
	private Logger logger = LoggerFactory.getLogger(TopicProducer.class);
	
	@Resource(name = "amqpTemplateTopic")
	private AmqpTemplate amqpTemplateTopic;
	
	//设置交换机
	@Value("${rabbitMq.exchange.topic:migang.cash.exchange.topic}")
	private String exchangeName;
	
	//设置过滤关键词
	private String routeQueueKey = "admin.user.login";	
	
	/**
	 * 发送队列消息
	 * 
	 * @param messageRecord
	 * 
	 * @param addTime 2017年9月7日
	 * @param author     ChengBo
	 */
	public void sendMqMessage(Map<String, Object> map) {
		try {
	    	//检查参数
	    	if(map.isEmpty()){
	    		return ;
	    	}
	    	
	    	//设置发送数据Map->JSON
	    	ObjectMapper mapper = new ObjectMapper();
			String message = mapper.writeValueAsString(map);
			
			//发送队列数据
			logger.debug("TopicProducer sendMqMessage message：{}, {}, {}, {}", exchangeName, routeQueueKey, map, message);
			
	    	//发送消息
			amqpTemplateTopic.convertAndSend(exchangeName, routeQueueKey, message);
		} catch (Exception e) {
	    	//记录日志
	    	logger.debug("TopicProducer sendMqMessage Exception：{}", e.getMessage());
		}
    } 
}
