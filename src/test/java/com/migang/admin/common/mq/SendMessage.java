package com.migang.admin.common.mq;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.migang.admin.BaseTest;
import com.migang.admin.common.mq.producer.DirectProducer;
import com.migang.admin.common.mq.producer.FanoutProducer;
import com.migang.admin.common.mq.producer.TopicProducer;

/**
 * 测试发送消息
 *
 */
public class SendMessage extends BaseTest {
	@Autowired
	private FanoutProducer fanoutProducer;
	
	@Autowired
	private DirectProducer directProducer;
	
	@Autowired
	private TopicProducer topicProducer;
	
	/**
	 * 测试发送 发布/订阅消息
	 * 
	 * @param addTime 2017年9月13日
	 * @param author     ChengBo
	 */
	@Test
	public void testQueueProductSender(){
		for(int i=0;i<3;i++){
			long timeStamp = System.currentTimeMillis();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("[city]"+i, "BeiJing" + timeStamp);
			map.put("[year]"+i, "2018" +"-"+timeStamp);
			
			fanoutProducer.sendMqMessage(map);
		}
		
		//发送完后停一下 执行单元测试时　时间短 监听队列没执行完就断开了
		try{
			Thread.sleep(2000);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	/**
	 * 测试发送 发送点对点消息 
	 * 
	 * 	备注：发送多个时　请在Service中新增对应的RoutingKey和发送方法
	 * 
	 * @param addTime 2017年9月13日
	 * @param author     ChengBo
	 */
	@Test
	public void testDirectProductSender(){
		long timeStamp = System.currentTimeMillis();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("city", "BeiJing - "+timeStamp);
		map.put("year", "2016 - "+timeStamp);
		
		directProducer.sendMqMessage(map);
	}
	
	@Test
	public void testTopicProductSender(){
		long timeStamp = System.currentTimeMillis();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("city", "BeiJing - "+timeStamp);
		map.put("year", "2017 - "+timeStamp);
		
		topicProducer.sendMqMessage(map);
	}
}
