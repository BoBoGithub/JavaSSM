package com.migang.admin.common.session;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.migang.admin.common.cache.MemCacheManager;

/**
 * 自定义实现sessionDAO
 * 
 * @param addTime 2017-09-13
 * @param author     ChengBo
 */
public class MemCacheSessionDAO extends AbstractSessionDAO {

	//引入日志操作
	private static final Logger logger = LoggerFactory.getLogger(MemCacheSessionDAO.class);
	
	//设置Session缓存前缀
	private static final String SESSION_KEY_PREFIX = "shiro_memcache_session:";
	
	//设置Session过期时间 毫秒
	private static final Integer SESSION_EXPIER_TIME = 86400000;
	
	@Autowired
	private MemCacheManager memCache;
	
	@Override
	public void update(Session session) throws UnknownSessionException {
		this.saveSession(session);
	}

	/**
	 * 获取缓存key
	 * 
	 * @param sessionId
	 * @return
	 * 
	 * @param addTime 2017年8月31日
	 * @param author     ChengBo
	 */
	private String getSessionKey(Serializable sessionId){		
		return MemCacheSessionDAO.SESSION_KEY_PREFIX + sessionId;
	}
	
	/**
	 * 保存或更新Session
	 * 
	 * @param session
	 * @throws UnknownSessionException
	 * 
	 * @param addTime 2017年8月31日
	 * @param author     ChengBo
	 */
	private void saveSession(Session session) throws UnknownSessionException {
		//检查是否存在session
		if (session == null || session.getId() == null) {
			logger.error("MemCacheSessionDAO saveSession error: session or session id is null");
			return;
		}

		//设置缓存生存时间
		session.setTimeout(MemCacheSessionDAO.SESSION_EXPIER_TIME);
		
		//获取缓存key
		String sessionkey = getSessionKey(session.getId());
		
		//设置缓存
		this.memCache.set(sessionkey, session, MemCacheSessionDAO.SESSION_EXPIER_TIME);
	}
	
	@Override
	public void delete(Session session) {
		//检查是否存在session
		if(session == null || session.getId() == null){  
            logger.error("RedisSessionDAO delete error: session or session id is null");  
            return;  
        }  
		
		//获取缓存key
		String sessionKey = getSessionKey(session.getId());
		
		//删除缓存
		memCache.delete(sessionKey);
	}

	@Override
	public Collection<Session> getActiveSessions() {
		Set<Session> sessions =  new HashSet<Session>();
		
		//获取当前活跃的sessionKey
		ArrayList<String> keys = memCache.getActiveKeys(MemCacheSessionDAO.SESSION_KEY_PREFIX);
		if(keys.size() > 0){
			for(int i = 0; i < keys.size(); i++){
				//获取缓存的Session数据
				Session s = (Session) memCache.get(keys.get(i));
				
				//添加到返回变量中
				sessions.add(s);
			}
		}
		
		return sessions;
	}

	@Override
	protected Serializable doCreate(Session session) {
		Serializable sessionId = this.generateSessionId(session);    
        this.assignSessionId(session, sessionId);  
        this.saveSession(session);  
        return sessionId;  
	}

	@Override
	protected Session doReadSession(Serializable sessionId) {
		//检查sessionid是否正确
		if(sessionId == null){
			logger.error("RedisSessionDAO doReadSession error: session id is null");
			return null;
		}
		
		//设置SessionKey
		String sessionKey = getSessionKey(sessionId);
		
		//获取Session数据
		return (Session) memCache.get(sessionKey);
	}

}
