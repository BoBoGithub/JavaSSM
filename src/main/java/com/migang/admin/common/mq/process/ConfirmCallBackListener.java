package com.migang.admin.common.mq.process;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ConfirmCallback;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.stereotype.Component;

/**
 * 消息到交换机的状态节点 回调
 * 
 *	@param addTime 2017-11-22
 * @param author    ChengBo
 */
@Component("confirmCallBackListener")
public class ConfirmCallBackListener implements ConfirmCallback {
	//引入日志操作
	private static final Logger logger = LoggerFactory.getLogger(ConfirmCallBackListener.class);
	
	/**
	 * 消息到交换机的状态节点
	 * 
	 * 	如果消息没有到exchange,则confirm回调,ack=false	
	 *		如果消息到达exchange,则confirm回调,ack=true
	 *
	 *	@param addTime 2017-11-22
	 * @param author    ChengBo
	 */
	@Override
	public void confirm(CorrelationData correlationData, boolean ack, String cause) {
		//记录消息到达交换机的日志
		logger.info("ConfirmCallBackListener Confirm correlationData: "+correlationData+",  ack: "+ack+", cause: "+ cause);
	}

}
