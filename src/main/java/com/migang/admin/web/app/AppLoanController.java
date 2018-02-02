package com.migang.admin.web.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.migang.admin.common.enums.LoanOrderStatusEnum;
import com.migang.admin.common.utils.CommonUtils;
import com.migang.admin.entity.BorrowRecord;
import com.migang.admin.exception.BusinessException;
import com.migang.admin.service.LoanService;

/**
 * App的借款Controller
 *
 *@param addTime 2017-09-27
 *@param author    ChengBo
 */
@Controller
public class AppLoanController extends AppBaseController {

	@Autowired
	private LoanService loanService;
	
	/**
	 * 获取申请贷款项目
	 * 
	 * @param request
	 * @param requestBody
	 * 
	 * @param addTime 2017年9月27日
	 * @param author     ChengBo
	 * @return 
	 * @throws BusinessException 
	 */
	@RequestMapping(value = "/app/get/loan/item", method = RequestMethod.POST)
	@ResponseBody
	private Object getApplyLoanItem(HttpServletRequest request, @RequestBody String requestBody) throws BusinessException{
		//解析json字符串
		this.parseRequestBody(request, requestBody);
		
		//获取贷款项目配置
		Map<String, Object> loanItem = loanService.getLoanItem();
		
		//设置贷款项目
		this.set("loanItem", loanItem);
		
		//返回结果
		return this.getAppRet();
	}
	
	/**
	 * 贷款申请提交
	 * 
	 * @param request
	 * @param requestBody
	 * @throws BusinessException
	 * 
	 * @param addTime 2017年9月28日
	 * @param author     ChengBo
	 * @return 
	 */
	@RequestMapping(value = "/app/submit/loan/apply", method = RequestMethod.POST)
	@ResponseBody
	private Object submitApplyLoan(HttpServletRequest request, @RequestBody String requestBody) throws BusinessException{
		//解析json字符串
		JSONObject jsonObject = this.parseRequestBody(request, requestBody);
		
		//提取贷款申请参数
		String itemLimit				= jsonObject.getString("itemLimit");
		String borrowMoney	= jsonObject.getString("borrowMoney");
		
		//提交贷款申请
		int ret = loanService.addBorrowRecord(this.getUid(), itemLimit, borrowMoney);
		
		//设置申请结果
		this.set("insertRet", ret > 0?1:0);

		//返回数据
		return this.getAppRet();
	}
	
	/**
	 * 获取用户最近的一条申请借款记录
	 * 
	 * @param request
	 * @param requestBody
	 * 
	 * @param addTime 2017年9月29日
	 * @param author     ChengBo
	 * @return 
	 * @throws BusinessException 
	 */
	@RequestMapping(value = "/app/get/curr/borrow/record", method = RequestMethod.POST)
	@ResponseBody
	private Object getLatelyBorrowRecordByUid(HttpServletRequest request, @RequestBody String requestBody) throws BusinessException{
		//解析json字符串
		this.parseRequestBody(request, requestBody);
		
		//调取借款记录数据
		BorrowRecord borrowRecord = loanService.getLatelyBorrowRecordByUid(this.getUid());
		
		//设置返回值
		this.set("borrowRecord", borrowRecord);
		
		//返回结果
		return this.getAppRet();
	}
	
	/**
	 * 查询最近的借款记录列表
	 * 
	 * @param request
	 * @param requestBody
	 * @throws BusinessException
	 * 
	 * @param addTime 2017年9月29日
	 * @param author     ChengBo
	 * @return 
	 */
	@RequestMapping(value = "/app/get/lately/brecord/list", method = RequestMethod.POST)
	@ResponseBody
	private Object getLatelyBorrowRecordList(HttpServletRequest request, @RequestBody String requestBody) throws BusinessException{
		//解析json字符串
		JSONObject jsonObject = this.parseRequestBody(request, requestBody);
		
		//设置查询条件
		int limit = jsonObject.getInteger("limit");
		
		//查询数据
		List<BorrowRecord> borrowRecordList = loanService.getLatelyBorrowRecordList(limit);
		
		//设置返回值
		this.set("brList", borrowRecordList);
		
		//返回结果　
		return this.getAppRet();
	}
	
	/**
	 * 用户主动还款操作
	 * 
	 * @param request
	 * @param requestBody
	 * @return
	 * @throws BusinessException
	 * 
	 * @param addTime 2017年11月16日
	 * @param author     ChengBo
	 */
	@RequestMapping(value = "/app/loan/repay", method = RequestMethod.POST)
	@ResponseBody
	private Object userRepayBorrow(HttpServletRequest request, @RequestBody String requestBody) throws BusinessException{
		//解析json字符串
		JSONObject jsonObject = this.parseRequestBody(request, requestBody);
		
		//提取要还款的订单id
		int orderId = jsonObject.getInteger("orderId");
		
		//处理还款
		Boolean repayRet = loanService.repayLoan(this.getUid(), orderId);
		
		//返回处理结果
		this.set("repayRet", (repayRet ? 1 : 0));
		
		//返回结果
		return this.getAppRet();
	}
	
	/**
	 * 调取用户的借款记录列表
	 * 
	 * @param request
	 * @param requestBody
	 * @return
	 * @throws BusinessException
	 * 
	 * @param addTime 2017年11月16日
	 * @param author     ChengBo
	 */
	@RequestMapping(value = "/app/user/loan/list", method = RequestMethod.POST)
	@ResponseBody
	private Object getBorrowRecordList(HttpServletRequest request, @RequestBody String requestBody) throws BusinessException{
		//解析json字符串
		JSONObject jsonObject = this.parseRequestBody(request, requestBody);
		
		//设置查询参数变量
		Map<String, String> recordMap = new HashMap<String, String>();
		
		//接收请求参数
		recordMap.put("uid", this.getUid()+"");
		recordMap.put("page", jsonObject.getString("page"));
		recordMap.put("pageSize", jsonObject.getString("pageSize"));
		
		//调取当前用户的借款记录
		List<Map<String, Object>> recordList = dealGetBorrowRecordList(recordMap);
		
		//设置返回结果
		this.set("recordList", recordList);
		
		//返回结果
		return this.getAppRet();
	}
	
	/**
	 * 处理借款记录返回结果
	 * 
	 * @param recordMap
	 * @return
	 * 
	 * @param addTime 2017年11月16日
	 * @param author     ChengBo
	 * @throws BusinessException 
	 */
	private List<Map<String, Object>> dealGetBorrowRecordList(Map<String, String> recordMap) throws BusinessException{
		//返回结果变量
		List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
		
		//调取数据
		List<BorrowRecord> recordList = loanService.getBorrowRecordList(recordMap);
		if(recordList.size() <= 0){
			return returnList;
		}
	
		//提取返回结果
		for(int i = 0 ; i<recordList.size(); i++){
			//定义提取变量
			Map<String, Object> tmpMap = new HashMap<String, Object>();
			
			//设置提取值
			tmpMap.put("orderId", recordList.get(i).getBrecordId());
			tmpMap.put("borrowMoney", recordList.get(i).getBorrowMoney());
			tmpMap.put("status", LoanOrderStatusEnum.getValueByIndex(recordList.get(i).getStatus()));
			tmpMap.put("applyTime", CommonUtils.getDateByTimeStamp(recordList.get(i).getApplyTime()));
			
			//设置借款期限
			int borrowLimit = recordList.get(i).getBorrowLimit();
			tmpMap.put("borrowLimit", (borrowLimit == 1 ? "7天" : (borrowLimit == 2 ? "15天" : (borrowLimit == 3 ? "30天" : "-"))));

			//追加数组
			returnList.add(tmpMap);
		}
		
		return returnList;
	}
}