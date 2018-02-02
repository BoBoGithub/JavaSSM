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
 * 发送点对点消息
 *
 * @param addTime 2017年9月7日
 * @param author     ChengBo
 */
@Component
public class DirectProducer {
	//引入日志操作类
	private Logger logger = LoggerFactory.getLogger(DirectProducer.class);
	
	//设置队列操作类
	@Resource(name = "amqpTemplateDirect")
	private AmqpTemplate amqpTemplateDirect;
    
	//设置交换机
	@Value("${rabbitMq.exchange.direct:migang.cash.exchange.direct}")
	private String exchangeName;
	
	//设置发布/订阅路由关键词
	@Value("${rabbitMq.queue.direct.key:migang.cache.direct.routing.key}")
	private String routeTopicKey;	
	
    /**
     * 发送点对点消息
     * 
     * @param message
     * 
     * @param addTime 2017年9月7日
     * @param author     ChengBo
     */
    public void sendMqMessage(Map<String, Object> map){
		try {
	    	//检查参数
	    	if(map.isEmpty()){
	    		return ;
	    	}
	    	
	    	//设置发送数据Map->JSON
	    	ObjectMapper mapper = new ObjectMapper();
			String message = mapper.writeValueAsString(map);
			
			//发送队列数据
			logger.debug(" sendMqMessage message：{}, {}, {}, {}", exchangeName, routeTopicKey, map, message);
			
	    	//发送消息
			amqpTemplateDirect.convertAndSend(exchangeName, routeTopicKey, message);
		} catch (Exception e) {
	    	//记录日志
	    	logger.debug(" sendMqMessage Exception：{}", e.getMessage());
		}
    }
}
