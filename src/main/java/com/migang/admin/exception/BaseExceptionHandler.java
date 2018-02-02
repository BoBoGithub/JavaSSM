package com.migang.admin.exception;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.migang.admin.common.CommonConstant;

/**
 * 请求异常处理基类
 * 
 * 返回 jsp格式或json格式 
 * 
 * ＠param  addTime 2017-08-17
 * @param author    ChengBo
 */
public class BaseExceptionHandler implements HandlerExceptionResolver {

	//设置日志处理变量
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * 自定义处理异常
	 * 	
	 * @Note 包括页面访问异常和Api访问异常
	 * 
	 * ＠param  addTime 2017-08-17
	 * @param author    ChengBo
	 */
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,	 Exception ex) {
		//记录异常日志
		logger.info("BaseExceptionHandler resolveException URI: "+request.getRequestURI()+" exception:"+ex.getMessage());

		//设置错误码
		String errCode = "";
		
		//设置返回数据
		Map<String, Object> map = new HashMap<String, Object>();
		
		//设置返回数据
		if(ex instanceof BusinessException){
			//提取错误码
			errCode = ((BusinessException) ex).getErrCode();
			
			//设置返回数据
			map.put("errno", errCode);
			map.put("errmsg", ex.getMessage());
		}else{
			//设置返回数据
			map.put("errno", CommonConstant.COMMON_SYS_INNER_ERROR);
			map.put("errmsg", "请求处理失败");
		}
		
		//判断是否是接口请求  	|| (request.getHeader("X-Requested-With") != null && request.getHeader("X-Requested-With").indexOf("XMLHttpRequest") > -1)
		if((request.getHeader("Content-Type") != null && request.getHeader("Content-Type").indexOf("application/json") > -1) || (request.getHeader("accept") != null && request.getHeader("accept").indexOf("application/json") > -1)){
			try{				
				//设置返回头
				response.setContentType("application/json;charset=UTF-8");
				
				//实例化打印输出类
				PrintWriter writer = response.getWriter();
				
				//设置返回json数据　bl og.c sdn.net/z itong_ccnu/ar ticle/det ails/47375379
				ObjectMapper mapper = new ObjectMapper();
				writer.write(mapper.writeValueAsString(map));
				
				//刷新缓存 关闭链接
				writer.flush();
				writer.close();
			}catch(IOException e){
				//记录IO异常日志
				logger.info("BaseExceptionHandler resolveException IOException: "+e.getMessage());
			}
		}

		//返回404页面  
		return new ModelAndView( errCode.equals(CommonConstant.COMMON_NO_PERMISSION) ? "forward:/no/permit" : "redirect:/404?"+request.getRequestURI());
	}

}
