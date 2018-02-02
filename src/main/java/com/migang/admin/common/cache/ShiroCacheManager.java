package com.migang.admin.common.cache;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 自定义ShiroRedis缓存管理器
 * 
 * @param addTime 2017-09-01
 * @param author    ChengBo
 */
public class ShiroCacheManager implements CacheManager {

	//引入操作日志类
	private static final Logger logger = LoggerFactory.getLogger(ShiroCacheManager.class);
	
	//设置缓存管理器
	private ShiroMemCache<?, ?> shiroMemCache; 
	
    /**
     * Retains all Cache objects maintained by this cache manager.
     */
    @SuppressWarnings("rawtypes")
	private final ConcurrentMap<String, Cache> caches;

    /**
     * Default no-arg constructor that instantiates an internal name-to-cache {@code ConcurrentMap}.
     */
    @SuppressWarnings("rawtypes")
	public ShiroCacheManager() {
        this.caches = new ConcurrentHashMap<String, Cache>();
    }
	
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public <K, V> Cache<K, V> getCache(String name) throws CacheException {
		//记录调用日志
		logger.debug("=== ShiroCacheManager getCache name: "+name);
		
		//获取缓存实例
		Cache cache = caches.get(name);
		if(cache == null){
			//设置缓存到caches里面 
			Cache existing = caches.putIfAbsent(name, shiroMemCache);
            if (existing != null) {
                cache = existing;
            }
		}
		
		return cache;
	}
	
    public ShiroMemCache<?, ?> getShiroMemCache() {  
        return shiroMemCache;  
    }  
  
    public void setShiroMemCache(ShiroMemCache<?, ?> memCacheManager) {  
        this.shiroMemCache = memCacheManager;  
    } 
}
