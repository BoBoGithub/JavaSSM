package com.migang.admin.common.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 数据序列化工具
 * 
 * @param addTime 2017年8月31日
 * @param author     ChengBo
 */
public class SerializeUtils {
	
	//日志处理类
	private static Logger logger = LoggerFactory.getLogger(SerializeUtils.class);
	
	/**
	 * 序列化对象
	 * 
	 * @param object
	 * @return
	 * 
	 * @param addTime 2017年8月31日
	 * @param author     ChengBo
	 */
	public static byte[] serialize(Object object){
		//设置返回值
		byte[] result = null;
		
		//检查是否时空对象
		if(object == null){
			return new byte[0];
		}

		//处理序列化操作
		try {
			ByteArrayOutputStream byteStream = new ByteArrayOutputStream(128);
				try {
					if (!(object instanceof Serializable)) {
						throw new IllegalArgumentException(SerializeUtils.class.getSimpleName() + " requires a Serializable payload but received an object of type [" + object.getClass().getName() + "]");
					}
					ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteStream);
					objectOutputStream.writeObject(object);
					objectOutputStream.flush();
					result = byteStream.toByteArray();
				} catch (Throwable ex) {
					throw new Exception("SerializeUtils serialize Failed to serialize", ex);
				}
			} catch (Exception ex) {
				logger.error("SerializeUtils serialize Failed to serialize", ex);
			}
			return result;
	}
	
	/**
	 * 反序列化
	 * 
	 * @param bytes
	 * @return
	 * 
	 * @param addTime 2017年8月31日
	 * @param author     ChengBo
	 */
	public static Object unserialize(byte[] bytes){
		Object result = null;

		//检查是否为空
		if (isEmpty(bytes)) {
			return null;
		}
		
		try {
			ByteArrayInputStream byteStream = new ByteArrayInputStream(bytes);
			try {
				ObjectInputStream objectInputStream = new ObjectInputStream(byteStream);
				try {
					result = objectInputStream.readObject();
				} catch (ClassNotFoundException ex) {
					throw new Exception("SerializeUtils unserialize Failed to deserialize object type", ex);
				}
			} catch (Throwable ex) {
				throw new Exception("SerializeUtils unserialize Failed to deserialize", ex);
			}
		} catch (Exception e) {
			logger.error("SerializeUtils unserialize Failed to deserialize", e);
		}
		return result;
	}
	
	/**
	 * 检查是否为空
	 * 
	 * @param data
	 * @return
	 * 
	 * @param addTime 2017年8月31日
	 * @param author     ChengBo
	 */
	private static boolean isEmpty(byte[] data) {
		return (data == null || data.length == 0);
	}
	
}
