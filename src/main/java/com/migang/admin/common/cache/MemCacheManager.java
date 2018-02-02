package com.migang.admin.common.cache;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.whalin.MemCached.MemCachedClient;

/**
 * Memcache缓存处理类
 *
 * @param addTime 2017-09-14
 * @param author    ChengBo
 */
@Component
public class MemCacheManager {
	
	//引入日志操作
	private static Logger logger = LoggerFactory.getLogger(MemCacheManager.class);
	
	private static MemCachedClient cachedClient;
	static {
        if (cachedClient == null)
        	cachedClient = new MemCachedClient();
    }
	
	//实例化
	private MemCacheManager() {}
	
	/**
	 * 向缓存添加新的键值对。如果键已经存在，则之前的值将被替换。
	 * 
	 * @param key
	 * @param obj
	 * @param expire
	 * @return
	 * 
	 * @param addTime 2017年9月14日
	 * @param author     ChengBo
	 */
	public boolean set(String key, Object obj, Integer expire){
		//设置缓存
		return setExp(key, obj, expire);
	}
	
	/**
	 * 向缓存添加新的键值对。如果键已经存在，则之前的值将被替换。
	 * 
	 * @param key
	 * @param value
	 * @param expire 过期时间 New Date(1000*10)：十秒后过期
	 * @return
	 * 
	 * @param addTime 2017年9月14日
	 * @param author     ChengBo
	 */
	private static boolean setExp(String key, Object value, Integer expire){
		try{
			//设置缓存
			return cachedClient.set(key, value, new Date(1000*expire));
		}catch(Exception e){
			//记录异常日志
			logger.warn("MemCacheManager setExp key:"+key+" exception: "+e.getMessage());
		}
		
		return false;
	}
	
	/**
	 * 根据键值获取缓存数据
	 * 
	 * @param key
	 * @return
	 * 
	 * @param addTime 2017年9月14日
	 * @param author     ChengBo
	 */
	public Object get(String key){
		try{
			//获取缓存数据
			return cachedClient.get(key);
		}catch(Exception e){
			//记录异常日志
			logger.warn("MemCacheManager get key: "+key+" exception: "+e.getMessage());
		}

		return null;
	}

	/**
	 * 检查缓存是否存在
	 * 
	 * @param key
	 * @return
	 * 
	 * @param addTime 2017年9月14日
	 * @param author     ChengBo
	 */
	public boolean exists(String key){
		try{
			//检查key是否存在
			return cachedClient.keyExists(key);
		}catch(Exception e){
			//记录异常日志
			logger.warn("MemCacheManager exists key: "+key+" exception: "+e.getMessage());
		}

		return false;
	}
	
	/**
	 * 删除制定键值的缓存
	 * 
	 * @param key
	 * @return
	 * 
	 * @param addTime 2017年9月14日
	 * @param author     ChengBo
	 */
	public boolean delete(String key){
		try{
			//删除缓存
			return cachedClient.delete(key);
		}catch(Exception e){
			//记录异常日志
			logger.warn("MemCacheManager delete key:"+key+" exception: "+e.getMessage());
		}
		
		return false;
	}

	/**
	 * 获取当前活跃的keys
	 * 
	 * @param preKey
	 * @return
	 * 
	 * @param addTime 2017年9月14日
	 * @param author     ChengBo
	 */
	@SuppressWarnings({ "rawtypes" })
	public ArrayList<String> getActiveKeys(final String preKey){
		//定义返回变量
		ArrayList<String> cacheKeys = new ArrayList<String>();
		
		//获取活跃的状态数据
		Map slabs =  cachedClient.statsItems();
		Iterator itemsItr = slabs.keySet().iterator();
		
		  //以server IP key值去循环查找
		  while (itemsItr.hasNext()) {
			  //获取当前的服务器信息
			  String serverInfo = itemsItr.next().toString();
			  
			   //取得当前server的各种 status [itemname:number:field=value]
			   Map itemNames = (Map) slabs.get(serverInfo);
			   Iterator itemNameItr = itemNames.keySet().iterator();
			   
			   //以status key值去循环查找
			   while (itemNameItr.hasNext()) {
				   //获取项目名称
				   String itemName = itemNameItr.next().toString();
				   
				   //拆解status
				   String[] itemAtt = itemName.split(":");
				   
				   	//要取得field是number的CacheDump参数
				    if (itemAtt[2].equals("number")) {
				    	//以status取到的參數,取得cachedDump Map
				        Map chcheDump = cachedClient.statsCacheDump(Integer.parseInt(itemAtt[1]), 0);
				        Iterator itr = chcheDump.keySet().iterator();
				        
				        //以server IP key值去循环
				        while (itr.hasNext()) {
				        	 //key=ip:port
				            String serverInfos = itr.next().toString();
				            
				            //取得Cached Key Map...
				            Map items = (Map) chcheDump.get(serverInfos);
				            Iterator keyItr = items.keySet().iterator();
				            
				            //以Cached Key 去循环
				            while (keyItr.hasNext()) {
				            	 String key = keyItr.next().toString();
				            	 if(key.startsWith(preKey)){
					            	 cacheKeys.add(key);
				            	 }
				            }
				        }
				    }
			   }
		  }
		  
		return cacheKeys;
	}
	
	/**
	 * 刷新缓存
	 * 
	 * @return
	 * 
	 * @param addTime 2017年9月14日
	 * @param author     ChengBo
	 */
	public boolean flushDb(){
		try{
			//刷新缓存
			return cachedClient.flushAll();
		}catch(Exception e){
			//记录异常日志
			logger.warn("MemCacheManager flushDb exception: "+e.getMessage());
		}
		
		return false;
	}
}
