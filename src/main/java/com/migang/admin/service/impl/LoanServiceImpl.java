package com.migang.admin.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.migang.admin.common.CommonConstant;
import com.migang.admin.common.enums.LoanOrderStatusEnum;
import com.migang.admin.common.mq.producer.DirectProducer;
import com.migang.admin.common.utils.CommonUtils;
import com.migang.admin.common.utils.ValidateUtils;
import com.migang.admin.dao.BorrowRecordDao;
import com.migang.admin.dao.RepayRecordDao;
import com.migang.admin.entity.BorrowRecord;
import com.migang.admin.entity.RepayRecord;
import com.migang.admin.exception.BusinessException;
import com.migang.admin.service.LoanService;

/**
 * 贷款的业务处理类
 *
 *＠param addTime 2017-08-27
 *@param author  ChengBo
 */
@Service
public class LoanServiceImpl implements LoanService {

	//引入日志操作
	private Logger logger = LoggerFactory.getLogger(LoanServiceImpl.class);
	
	@Autowired
	private BorrowRecordDao borrowRecordDao;
	
	@Autowired
	private DirectProducer directProducer;
	
	@Autowired
	private RepayRecordDao repayRecordDao;
	
	/**
	 * 获取贷款项目配置
	 * 
	 * @return
	 * 
	 * @param addTime 2017年9月27日
	 * @param author     ChengBo
	 */
	@SuppressWarnings("serial")
	public Map<String, Object> getLoanItem(){
		//设置返回项目结果　目前做成配置文件 后期可能会动态
		Map<String, Object> loanItem			= new HashMap<String, Object>();
		ArrayList<Object> loanItem7			= new ArrayList<Object>();
		ArrayList<Object> loanItem15			= new ArrayList<Object>();
		ArrayList<Object> loanItem30			= new ArrayList<Object>();
		
		//设置 7天 500
		Map<String, Object> loanItem7D500 = new HashMap<String, Object>(){{
			put("loanMoney", 500);
			put("limitType", 1);
			put("repayMoney", 527);
			put("borrowInterest", 2);
			put("serviceCharge", 25);
		}};
		
		//设置 7天 1000
		Map<String, Object> loanItem7D1000 = new HashMap<String, Object>(){{  
			put("loanMoney", 1000);
			put("limitType", 1);
			put("repayMoney", 1051.5);
			put("borrowInterest", 4);
			put("serviceCharge", 47.5);
		}};
		
		//设置7天的配置
		loanItem7.add(loanItem7D500);
		loanItem7.add(loanItem7D1000);
		
		//设置 15天 500
		Map<String, Object> loanItem15D500 = new HashMap<String, Object>(){{  
			put("loanMoney", 500);
			put("limitType", 2);
			put("repayMoney", 530);
			put("borrowInterest", 5);
			put("serviceCharge", 25);
		}};
		
		//设置 15天 1000
		Map<String, Object> loanItem15D1000 = new HashMap<String, Object>(){{  
			put("loanMoney", 1000);
			put("limitType", 2);
			put("repayMoney", 1049);
			put("borrowInterest", 9);
			put("serviceCharge", 47.5);
		}};
		
		//设置 15天 2000
		Map<String, Object> loanItem15D2000 = new HashMap<String, Object>(){{  
			put("loanMoney", 2000);
			put("limitType", 2);
			put("repayMoney", 2110.5);
			put("borrowInterest", 18);
			put("serviceCharge", 92.5);
		}};
		
		//设置15天的配置
		loanItem15.add(loanItem15D500);
		loanItem15.add(loanItem15D1000);
		loanItem15.add(loanItem15D2000);

		//设置 30天 1000
		Map<String, Object> loanItem30D1000 = new HashMap<String, Object>(){{
			put("loanMoney", 1000);
			put("limitType", 3);
			put("repayMoney", 1065.5);
			put("borrowInterest", 18);
			put("serviceCharge", 47.5);
		}};
		
		//设置 30天 2000
		Map<String, Object> loanItem30D2000 = new HashMap<String, Object>(){{  
			put("loanMoney", 2000);
			put("limitType", 3);
			put("repayMoney", 2128.5);
			put("borrowInterest", 36);
			put("serviceCharge", 92.5);
		}};
		
		//设置15天的配置
		loanItem30.add(loanItem30D1000);
		loanItem30.add(loanItem30D2000);
		
		//设置借款项目
		loanItem.put("7天", loanItem7);
		loanItem.put("15天", loanItem15);
		loanItem.put("30天", loanItem30);
		
		//返回项目配置
		return loanItem;
	}

	/**
	 * 新增借款记录
	 * 
	 * @param uid
	 * @param itemLimit
	 * @param borrowMoney
	 * @return
	 * @throws BusinessException
	 * 
	 * @param addTime 2017年9月28日
	 * @param author     ChengBo
	 */
	public int addBorrowRecord(long uid, String itemLimit, String borrowMoney) throws BusinessException{
		//过滤提交参数
		BorrowRecord bRecord = checkBorrowRecordParam(uid, itemLimit, borrowMoney);
		
		try{
			//借款记录入库
			Boolean ret = borrowRecordDao.insertBorrowRecord(bRecord);
			
			//处理其他逻辑
			if(ret){
				//设置发送内容
				Map<String, Object> loanMap = new HashMap<String, Object>();
				loanMap.put("queueType", CommonConstant.QUEUE_MESSAGE_TYPE_APPLY_LOAN);
				loanMap.put("uid", uid);
				loanMap.put("brid", bRecord.getBrecordId());
				
				//申请成功后入消息队列 做后续处理
				directProducer.sendMqMessage(loanMap);
			}
			
			//返回新增id
			return bRecord.getBrecordId();
		}catch(Exception ex){
			//记录日志
			logger.error("LoanServiceImpl addBorrowRecord uid:"+uid+" itemLimit:"+itemLimit+" borrowMoney:"+borrowMoney+"  Exception: "+ ex.getMessage());
			
			//抛出异常
			throw new BusinessException(CommonConstant.BORROW_RECORD_INSRET_FAILURE, "借款申请失败");
		}
	}
	
	/**
	 * 检查借款记录入库参数
	 * 
	 * @param borrowRecordMap
	 * @return
	 * 
	 * @param addTime 2017年9月28日
	 * @param author     ChengBo
	 * @throws BusinessException 
	 */
	@SuppressWarnings("unchecked")
	private BorrowRecord checkBorrowRecordParam(long uid, String itemLimit, String borrowMoney) throws BusinessException{
		//检查借款用户
		if(uid <= 0){
			throw new BusinessException(CommonConstant.USER_NOT_EXIST, "用户不存在");
		}

		//获取借款项目
		Map<String, Object> loanItem = getLoanItem();
		
		//检查是否存在当前期限项目
		if(!loanItem.containsKey(itemLimit) || loanItem.get(itemLimit) == null){
			throw new BusinessException(CommonConstant.BORROW_RECORD_LIMIT_NOT_EXISTS, "借款项目类型不存在");
		}
		
		//提取借款期限下的借款项目
		ArrayList<Object> itemList = (ArrayList<Object>) loanItem.get(itemLimit);

		//提取当前借款项目
		Boolean itemMoneyStatus					= false;
		Map<String, Object> currItemMap	= null;
		for(int i = 0; i<itemList.size();i++){
			//获取当前项目
			currItemMap = (Map<String, Object>) itemList.get(i);
			if(currItemMap.get("loanMoney").toString().equals(borrowMoney)){
				itemMoneyStatus = true;
				break;
			}
		}
		
		//检查借款项目是否存在
		if(!itemMoneyStatus){
			throw new BusinessException(CommonConstant.BORROW_RECORD_ITEM_NOT_EXISTS, "借款项目不存在");
		}
		
		//实例化借款记录Bean
		BorrowRecord borrowRecord = new BorrowRecord();
		
		//设置用户uid
		borrowRecord.setUid(uid);
		
		//设置借款金额
		borrowRecord.setBorrowMoney(new BigDecimal(currItemMap.get("loanMoney").toString()));
		
		//设置期限类型
		borrowRecord.setBorrowLimit(Integer.parseInt(currItemMap.get("limitType").toString()));
		
		//设置借款利息
		borrowRecord.setBorrowInterest(new BigDecimal(currItemMap.get("borrowInterest").toString()));
		
		//设置服务费
		borrowRecord.setServiceCharge(new BigDecimal(currItemMap.get("serviceCharge").toString()));
		
		//设置申请时间
		borrowRecord.setApplyTime(CommonUtils.getCurrTimeStamp());
		
		//设置备注信息
		borrowRecord.setRemark("申请借款入库");
		
		//返回借款记录
		return borrowRecord;
	}
	
	/**
	 * 获取用户最近的一条申请借款记录
	 * 
	 * @param uid
	 * @return
	 * @throws BusinessException
	 * 
	 * @param addTime 2017年9月29日
	 * @param author     ChengBo
	 */
	public BorrowRecord getLatelyBorrowRecordByUid(long uid) throws BusinessException{
		//检查参数
		if(uid <= 0){
			throw new BusinessException(CommonConstant.USER_NOT_EXIST, "用户不存在");
		}
		
		//调取借款记录数据
		BorrowRecord borrowRecord = borrowRecordDao.getLatelyBorrowRecordByUid(uid);
		
		return borrowRecord;
	}
	
	/**
	 * 获取最近的借款记录
	 * 
	 * @param limit
	 * @return
	 * 
	 * @param addTime 2017年9月29日
	 * @param author     ChengBo
	 * @throws BusinessException 
	 */
	public List<BorrowRecord> getLatelyBorrowRecordList(int limit) throws BusinessException{
		//检查参数
		if(!ValidateUtils.checkIsNumber(limit) || limit < 1){
			throw new BusinessException(CommonConstant.USER_NOT_EXIST, "用户不存在");
		}
		
		//检查最大条数
		limit = limit > 20 ? 20 : limit;
		
		//查询借款记录数据
		List<BorrowRecord> borrowRecordList = borrowRecordDao.getLatelyBorrowList(limit);
		
		//返回查询列表
		return borrowRecordList;
	}
	
	/**
	 * 按条件查询借款记录数据
	 * 
	 * @param bRecord
	 * @return
	 * @throws BusinessException
	 * 
	 * @param addTime 2017年10月11日
	 * @param author     ChengBo
	 */
	public List<BorrowRecord> getBorrowRecordList(Map<String, String> recordMap) throws BusinessException{
		//检查查询条件
		Map<String, Object> paramMap = checkGetBorrowRecordListParam(recordMap);
		
		try{
			//调取查询数据
			List<BorrowRecord> recordListData = borrowRecordDao.getBorrowList(paramMap);
			
			return recordListData;
		}catch(Exception ex){
			//记录日志
			logger.error("LoanServiceImpl getBorrowRecordList recordMap:"+recordMap+"  Exception: "+ ex.getMessage());
			
			//抛出异常
			throw new BusinessException(CommonConstant.BORROW_RECORD_SELECT_FAILURE, "借款记录查询失败");
		}
	}
	
	/**
	 * 检查查询借款记录参数
	 * 
	 * @param recordMap
	 * @return
	 * 
	 * @param addTime 2017年10月11日
	 * @param author     ChengBo
	 * @throws BusinessException 
	 */
	private Map<String, Object> checkGetBorrowRecordListParam(Map<String, String> recordMap) throws BusinessException{
		//实例化查询条件Map
		Map<String, Object> paramMap = new HashMap<String, Object>();

		//检查订单号条件
		if(recordMap.containsKey("orderId")){
			if(ValidateUtils.checkIsNumber(recordMap.get("orderId"))){
				paramMap.put("orderId", Integer.parseInt(recordMap.get("orderId")));		
			}else{
				throw new BusinessException(CommonConstant.BORROW_RECORD_ORDER_ID_ERROR, "订单ID参数错误");
			}
		}
		
		//检查用户uid
		if(recordMap.containsKey("uid")){
			if(ValidateUtils.checkIsNumber(recordMap.get("uid"))){
				paramMap.put("uid", Integer.parseInt(recordMap.get("uid")));		
			}else{
				throw new BusinessException(CommonConstant.USER_NOT_EXIST, "用户UID参数错误");
			}
		}
		
		//检查借款金额
		if(recordMap.containsKey("borrowMoney")){
			if(ValidateUtils.checkIsNumber(recordMap.get("borrowMoney"))  || ValidateUtils.checkIsDouble(recordMap.get("borrowMoney"))){
				paramMap.put("borrowMoney", recordMap.get("borrowMoney"));		
			}else{
				throw new BusinessException(CommonConstant.BORROW_RECORD_MONEY_ERROR, "借款金额参数错误");
			}
		}
		
		//检查借款期限
		if(recordMap.containsKey("borrowLimit")){
			if(Arrays.asList(new String[]{"1", "2", "3"}).contains(recordMap.get("borrowLimit"))){
				paramMap.put("borrowLimit", recordMap.get("borrowLimit"));		
			}else{
				throw new BusinessException(CommonConstant.BORROW_RECORD_LIMIT_NOT_EXISTS, "借款期限参数错误");
			}
		}
		
		//设置订单状态
		if(recordMap.containsKey("orderStatus")){
			if(LoanOrderStatusEnum.checkIndexIsExists(Integer.parseInt(recordMap.get("orderStatus")))){
				paramMap.put("orderStatus", recordMap.get("orderStatus"));
			}else{
				throw new BusinessException(CommonConstant.BORROW_RECORD_STATUS_ERROR, "订单状态错误");
			}
		}
		
		//设置审核类型
		if(recordMap.containsKey("checkType")){
			if(Arrays.asList(new String[]{"0", "1", "2"}).contains(recordMap.get("checkType"))){
				paramMap.put("checkType", recordMap.get("checkType"));		
			}else{
				throw new BusinessException(CommonConstant.BORROW_RECORD_CHECK_TYPE_ERROR, "审核类型参数错误");
			}
		}
		
		//设置申请开始时间
		if(recordMap.containsKey("applyStartTime")){
			if(ValidateUtils.checkIsNumber(recordMap.get("applyStartTime"))){
				paramMap.put("applyStartTime", recordMap.get("applyStartTime"));
			}else{
				throw new BusinessException(CommonConstant.BORROW_RECORD_APPLY_TIME_ERROR, "申请开始时间错误");
			}
		}
		
		//设置申请结束时间
		if(recordMap.containsKey("applyEndTime")){
			if(ValidateUtils.checkIsNumber(recordMap.get("applyEndTime"))){
				paramMap.put("applyEndTime", recordMap.get("applyEndTime"));
			}else{
				throw new BusinessException(CommonConstant.BORROW_RECORD_APPLY_TIME_ERROR, "申请结束时间错误");
			}
		}
		
		//检查操作人条件
		if(recordMap.containsKey("operater")){
			if(ValidateUtils.checkIsNumber(recordMap.get("operater"))){
				paramMap.put("operater", recordMap.get("operater"));
			}else{
				throw new BusinessException(CommonConstant.BORROW_RECORD_OPERATER_ERROR, "操作人参数错误");
			}
		}
		
		//检查最小数据评分
		if(recordMap.containsKey("scoreStart")){
			if(ValidateUtils.checkIsDouble(recordMap.get("scoreStart"))){
				paramMap.put("scoreStart", recordMap.get("scoreStart"));
			}else{
				throw new BusinessException(CommonConstant.BORROW_RECORD_SCORE_ERROR, "最小数据评分参数错误");
			}
		}
		
		//检查最大数据评分
		if(recordMap.containsKey("scoreEnd")){
			if(ValidateUtils.checkIsDouble(recordMap.get("scoreEnd"))){
				paramMap.put("scoreEnd", recordMap.get("scoreEnd"));
			}else{
				throw new BusinessException(CommonConstant.BORROW_RECORD_SCORE_ERROR, "最大数据评分参数错误");
			}
		}
		
		 //提取每页条数
		 int pageSize	= recordMap.containsKey("pageSize") && ValidateUtils.checkIsNumber(Integer.parseInt(recordMap.get("pageSize"))) ? Integer.parseInt(recordMap.get("pageSize")) : 13;

		 //提取当前页数
		 int page			= recordMap.containsKey("page") && ValidateUtils.checkIsNumber(Integer.parseInt(recordMap.get("page"))) && Integer.parseInt(recordMap.get("page")) > 0 ? Integer.parseInt(recordMap.get("page")) : 1;
		 
		 //设置查询偏移数
		 paramMap.put("offset", (page-1)*pageSize);
		 
		 //设置每页条数
		 paramMap.put("pageSize", pageSize);

		return paramMap;
	}

	/**
	 * 汇总订单总数
	 * 
	 * @param recordMap
	 * @return
	 * 
	 * @param addTime 2017年10月11日
	 * @param author     ChengBo
	 * @throws BusinessException 
	 */
	public int countUserNum(Map<String, String> recordMap) throws BusinessException{
		//检查并设置查询条件
		Map<String, Object> paramMap = checkGetBorrowRecordListParam(recordMap);
		
		try{
			//查询借款记录总条数
			return borrowRecordDao.countBorrowRecord(paramMap);
		}catch(Exception ex){
			//记录日志
			logger.error("LoanServiceImpl countUserNum recordMap:"+recordMap+"  Exception: "+ ex.getMessage());
			
			//抛出异常
			throw new BusinessException(CommonConstant.BORROW_RECORD_SELECT_FAILURE, "借款记录统计失败");
		}
	}
	
	/**
	 * 汇总指定用户的借款记录数
	 * 
	 * @return
	 * 
	 * @param addTime 2017年10月13日
	 * @param author     ChengBo
	 */
	public Map<Long, Map<String, String>> sumRecordNumByUids(List<Long> uidList){
		//检查参数
		if(uidList == null || uidList.size() == 0){
			return null;
		}
		
		//设置返回结果变量
		Map<Long, Map<String, String>> recordNumMap = new HashMap<Long, Map<String, String>>();
		
		//实例化查询条件
		Map<String, Object> recordMap = new HashMap<String, Object>();
		
		//设置查询条件
		recordMap.put("uids", uidList);

		//统计数据
		List<Map<String, String>> sumData = borrowRecordDao.sumRecordNumByUids(recordMap);
		if(sumData.size() > 0){
			for(Map<String, String> sumMap : sumData){
				//设置返回结果
				recordNumMap.put(Long.parseLong(sumMap.get("uid")), sumMap);
			}
		}
		
		return recordNumMap;
	} 
	
	/**
	 * 获取指定订单ID的订单数据
	 * 
	 * @param orderId
	 * @return
	 * 
	 * @param addTime 2017年11月3日
	 * @param author     ChengBo
	 */
	public BorrowRecord getBorrowRecordById(Integer orderId){
		//检查参数
		
		//调取订单数据
		BorrowRecord brecord = borrowRecordDao.getBorrowRecordById(orderId);
		
		//返回订单详情
		return brecord;
	}
	
	/**
	 * 查询还款记录数据
	 * 
	 * @param orderId
	 * @return
	 * 
	 * @param addTime 2017年11月3日
	 * @param author     ChengBo
	 */
	public RepayRecord getRepayRecordByBid(int orderId){
		//调取还款记录数据
		RepayRecord repayRecordData = repayRecordDao.getRepayRecordByBid(orderId);;
		
		return repayRecordData;
	}
	
	/**
	 * 人工审核订单提交操作
	 * 
	 * @param updOrderParam
	 * @return
	 * @throws BusinessException
	 * 
	 * @param addTime 2017年11月7日
	 * @param author     ChengBo
	 */
	public Boolean updPersonCheckOrderStatus(Map<String, String> updOrderParam) throws BusinessException{
		//检查参数
		if(!updOrderParam.containsKey("orderId") || !ValidateUtils.checkIsNumber(updOrderParam.get("orderId"))){
			throw new BusinessException(CommonConstant.BORROW_RECORD_ORDER_ID_ERROR, "借款订单ID错误");
		}
		
		//检查审核类型
		if(!updOrderParam.containsKey("type") || !Arrays.asList(new String[]{"-2","1", "2"}).contains(updOrderParam.get("type"))){
			throw new BusinessException(CommonConstant.BORROW_RECORD_CHECK_TYPE_ERROR, "借款订单审核类型错误");
		}
		
		//检查操作人
		if(!updOrderParam.containsKey("adminUid") || Integer.parseInt(updOrderParam.get("adminUid")) <= 0){
			throw new BusinessException(CommonConstant.BORROW_RECORD_OPERATER_ERROR, "借款订单审核操作人错误");
		}
		
		//检查当前订单状态
		BorrowRecord borrowRecordData = borrowRecordDao.getBorrowRecordById(Integer.parseInt(updOrderParam.get("orderId")));
		if(borrowRecordData != null && borrowRecordData.getStatus() != 1){
			//只有待人工确认的订单才能被审核
			throw new BusinessException(CommonConstant.BORROW_RECORD_STATUS_ERROR, "当前借款订单状态不能被人工审核");
		}
		
		//设置更新Map
		Map<String, Object> updMap = new HashMap<String, Object>();
		
		//设置更新条件
		updMap.put("orderId", updOrderParam.get("orderId"));
		updMap.put("status", (updOrderParam.get("type").equals("1") ? 2 : -2));
		updMap.put("operater", updOrderParam.get("adminUid"));
		updMap.put("checkTime", CommonUtils.getCurrTimeStamp());
		if(updOrderParam.get("type").equals("2") && updOrderParam.get("remark") != null){
			updMap.put("remark", borrowRecordData.getRemark()+" !@##@! "+updOrderParam.get("remark"));
		}
		
		//更新为审核通过
		return borrowRecordDao.updBorrowRecordCheckStatus(updMap);
	}

	/**
	 * 还款操作
	 * 
	 * @param orderId
	 * @return
	 * 
	 * @param addTime 2017年11月16日
	 * @param author     ChengBo
	 * @throws BusinessException 
	 */
	public Boolean repayLoan(long uid, int orderId) throws BusinessException{
		//查询借款记录数据
		BorrowRecord recordData = borrowRecordDao.getBorrowRecordById(orderId);
		if(recordData == null || !Arrays.asList(new String[]{"6", "7"}).contains(recordData.getStatus()+"")){
			throw new BusinessException(CommonConstant.BORROW_RECORD_STATUS_ERROR, "要还款的借款记录不能存在或还款状态错误");
		}
		
		//检查还款用户和订单是否匹配
		if(uid != recordData.getUid()){
			throw new BusinessException(CommonConstant.USER_UID_ERROR, "借款记录和还款用户不匹配");
		}
		
		//设置更新条件变量
		Map<String, Object> updOrderParam = new HashMap<String, Object>();
		
		//设置更新条件
		updOrderParam.put("orderId", orderId);
		updOrderParam.put("realRepayTime", CommonUtils.getCurrTimeStamp());
		updOrderParam.put("status", (recordData.getStatus() == 7 ? 8 : 9));
		
		//更新还款状态
		Boolean ret =  borrowRecordDao.updBorrowRecordCheckStatus(updOrderParam);
		
		return ret;
	}
}