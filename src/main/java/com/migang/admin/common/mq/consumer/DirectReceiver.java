package com.migang.admin.common.mq.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;

/**
 * 监听接收处理 Topic订阅消息
 *
 * @param addTime 2017年9月7日
 * @param author     ChengBo
 */
@Component("rabbitＭqDirectService")
public class DirectReceiver implements ChannelAwareMessageListener {
	//引入日志操作类
	private Logger logger = LoggerFactory.getLogger(DirectReceiver.class);
	
	/**
	 * 接收消息
	 * 
	 * @param addTime 2017年9月7日
	 * @param author     ChengBo
	 */
	@Override
	public void onMessage(Message message, Channel channel) throws Exception {
		//记录接收队列参数
		logger.debug("DirectQueue消息消费者＝{}", message.toString());
		
		try {
			//每次只处理一个
//			channel.basicQos(1);
			
			//接收队列参数转化为字符串
			String bodyMsg = new String(message.getBody(), "UTF-8");
			
			//解析请求Body中的JSON数据
			JSONObject jsonObject = JSONObject.parseObject(bodyMsg);
			
			//字符串转换成JSON串
			logger.debug("DirectQueue消息消费者接收JSON：{}", jsonObject);
			
			//处理消息成功｛消息的标识；false只确认当前一个消息收到，true确认所有consumer获得的消息 ｝
			channel.basicAck(message.getMessageProperties().getDeliveryTag(), false); 
		} catch (Exception e) {
			//处理失败记录日志
			logger.debug("DirectQueueReceiver onMessage exception: "+e.getMessage());
			
			//处理消息失败 ack返回false，此条mq并重新回到队列 {deliveryTag:该消息的index, multiple：是否批量.true:将一次性拒绝所有小于deliveryTag的消息, requeue：被拒绝的是否重新入队列}
			//	NOTE：若要重把当前的消息重新返回队列，需要考虑再次处理失败 陷入死循环问题[考虑缓存]
			channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);  
		}
	}
}
