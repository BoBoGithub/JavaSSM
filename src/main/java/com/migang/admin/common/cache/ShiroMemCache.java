package com.migang.admin.common.cache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 自定义Shiro的缓存管理 
 * 
 * 备注：Shiro有一个默认的缓存管理，为了统一缓存存放到MemCache，因此这个重新了
 * 
 *
 * @param <K>
 * @param <V>
 */
public class ShiroMemCache<K, V> implements Cache<K, V> {

	//引入之日操作
	private static final Logger logger = LoggerFactory.getLogger(ShiroMemCache.class);
	
	//设置缓存key
	private String keyPrefix = "shiro_mem_cache:";
	
	//设置过期时间
	private static final Integer SHIRO_CACHE_EXPIER_TIME = 86400000;
	
	@Autowired
	private MemCacheManager memCacheManager;
	
	/**
	 * 加工缓存key
	 * 
	 * @param key
	 * @return
	 * 
	 * @param addTime 2017年9月1日
	 * @param author     ChengBo
	 */
	private String getCacheKey(K key){
		String preKey = this.keyPrefix + key;
		return preKey;
	}
	
	@Override
	public V get(K key) throws CacheException {
		//记录调用日志
		logger.debug("== ShiroMemCache get cache key:"+key);
		
		//检查key
		if(key == null){
			return null;
		}
		
		try{
			//获取缓存数据
			@SuppressWarnings("unchecked")
			 V cacheData =   (V)memCacheManager.get(getCacheKey(key));
			
			//返回缓存结果
			return cacheData;
		}catch(Throwable t){
			//记录异常日志
			logger.debug("ShiroMemCache get cache Exception: "+t.getMessage());
		}
		
		return null;
	}

	@Override
	public V put(K key, V value) throws CacheException {
		try{
			memCacheManager.set(getCacheKey(key), value, SHIRO_CACHE_EXPIER_TIME);
			return value;
		}catch(Throwable t){
			//记录异常日志
			logger.debug("ShiroMemCache put cache Exception: "+t.getMessage());
		}
		
		return null;
	}

	@Override
	public V remove(K key) throws CacheException {
		//获取缓存数据
		V cacheData = get(key);
		
		//删除缓存
		memCacheManager.delete(getCacheKey(key));
		
		return cacheData;
	}

	@Override
	public void clear() throws CacheException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<K> keys() {
		//获取缓存数据
		ArrayList<String> keyList = memCacheManager.getActiveKeys(this.keyPrefix);
		if(keyList.size() == 0){
			return Collections.emptySet();
		}
		
		//设置提取存放变量
		Set<K> newKeys = new HashSet<K>();
		
		//提取缓存数据
		for(int i = 0; i < keyList.size(); i++){
			newKeys.add((K)keyList.get(i));
		}
		
		return newKeys;
	}

	@Override
	public Collection<V> values() {
		//获取缓存数据
		ArrayList<String> keyList = memCacheManager.getActiveKeys(this.keyPrefix);
		if(keyList.size() == 0){
			return Collections.emptyList();
		}
		
		//设置返回结果变量
		List<V> values = new ArrayList<V>(keyList.size());
		
		//提取数据
		for(int i = 0; i < keyList.size(); i++){
			//获取缓存数据
			 Object cacheByteData =   memCacheManager.get(keyList.get(i));
			 
			 //类型转换
			 @SuppressWarnings("unchecked")
			 V value = (V) cacheByteData;
			if(value != null){
				values.add(value);
			}
		}
		
		return Collections.unmodifiableCollection(values);
	}

}
