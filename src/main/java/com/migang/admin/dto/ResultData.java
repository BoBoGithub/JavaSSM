package com.migang.admin.dto;

import com.migang.admin.common.CommonConstant;

/**
 * 封装返回结果
 *
 * @param <T>
 * 
 * ＠param addTime 2017-08-28
 * @param author   ChengBo
 */
public class ResultData<T> {	
	//错误码
	private String errno;
	
	//错误描述
	private String errmsg;
	
	//成功返回的数据
	private T data;

	public String getErrno() {
		return errno;
	}

	/**
	 * 默认构造方法
	 */
	public ResultData(){}
	
	/**
	 * 处理成功时返回数据
	 * 
	 * @param errno
	 * 
	 * @param addTime 2017年8月28日
	 * @param author     ChengBo
	 */
	public ResultData(T data){
		this.errno = CommonConstant.SUCCESS;
		this.errmsg = "success";
		this.data = data;
	}
	
	/**
	 * 处理失败时返回数据
	 * 
	 * @param errno
	 * 
	 * @param addTime 2017年8月28日
	 * @param author     ChengBo
	 */
	public ResultData(String errno, String errMsg){
		this.errno = errno;
		this.errmsg = errMsg;
		//this.data = (T) "";
	}
	
	public void setErrno(String errno) {
		this.errno = errno;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "errno="+errno+", errmsg="+errmsg+" data="+data;
	}
	
}
