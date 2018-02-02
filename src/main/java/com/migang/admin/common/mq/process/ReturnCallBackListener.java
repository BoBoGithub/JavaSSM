package com.migang.admin.common.mq.process;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ReturnCallback;
import org.springframework.stereotype.Component;

/**
 * 消息到达队列时的状态 回调
 *
 * @param addTime 2017-11-22
 * @param author    ChengBo
 */
@Component("returnCallBackListener")
public class ReturnCallBackListener implements ReturnCallback {
	//引入日志操作
	private static final Logger logger = LoggerFactory.getLogger(ReturnCallBackListener.class);
	
	/**
	 * 消息到达队列时的状态
	 * 
	 * 	exchange到queue成功,则不回调return
	 * 	exchange到queue失败,则回调return(需设置mandatory=true,否则不回回调,消息就丢了)
	 * 
	 * @param addTime 2017-11-22
	 * @param author    ChengBo
	 */
	@Override
	public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
		//记录消息到达队列时的处理状态
		logger.info("ReturnCallBackListener Return message: "+ new String(message.getBody()) + ", replyCode: "+replyCode+", replyText: "+replyText+", exchange: "+exchange+", routingKey: "+routingKey);
	}

}
