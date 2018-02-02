package com.migang.admin;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 配置spring和junit整合，junit启动时加载springIOC容器 spring-test,junit
 */
@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit spring配置文件
@ContextConfiguration({
		"classpath:spring/spring-dao.xml", "classpath:spring/spring-service.xml",
		"classpath:applicationcontext/applicationContext-memcache.xml", 
		"classpath:applicationcontext/applicationContext-RabbitMQ.xml"
})
//	"classpath:applicationcontext/applicationContext-redis.xml", 
// "classpath:applicationcontext/applicationContext-activemq.xml"
public class BaseTest {

	//引入日志操作类
	protected static final Logger logger = LoggerFactory.getLogger(BaseTest.class);
	
	@Test
	public void testBase(){
		System.out.println("This is the base test.");
	}
}
