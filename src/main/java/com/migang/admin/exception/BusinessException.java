package com.migang.admin.exception;

import com.migang.admin.common.CommonConstant;

/**
 * 处理运行时异常类
 *
 * @param addTime 2017-08-28
 * @param author    ChengBo
 */
public class BusinessException extends Exception {
    //序列化id
	private static final long serialVersionUID = 1L;

	//错误码
    private String errCode;
    
    //错误信息
    private String errMsg;
    
    /**
     * 设置错误码和错误信息
     * 
     * @param errCode
     * @param errMsg
     */
    public BusinessException(String errCode, String errMsg){  
        super(errMsg);  
        this.errCode = errCode;  
        this.errMsg =errMsg;  
    }  
  
    /**
     * 设置错误信息
     * 
     * @param errMsg
     */
    public BusinessException(String errMsg){
        super(errMsg);
        this.errCode = CommonConstant.COMMON_PARAM_ERROR;
        this.errMsg = errMsg;
    }

	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}  

}
