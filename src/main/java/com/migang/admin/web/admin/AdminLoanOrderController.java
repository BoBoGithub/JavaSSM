package com.migang.admin.web.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.migang.admin.common.enums.LoanOrderStatusEnum;
import com.migang.admin.common.utils.CommonUtils;
import com.migang.admin.common.utils.ValidateUtils;
import com.migang.admin.entity.BorrowRecord;
import com.migang.admin.entity.User;
import com.migang.admin.entity.UserBank;
import com.migang.admin.entity.UserBase;
import com.migang.admin.entity.UserJob;
import com.migang.admin.entity.UserLinkMan;
import com.migang.admin.exception.BusinessException;
import com.migang.admin.service.HelpService;
import com.migang.admin.service.LoanService;
import com.migang.admin.service.UserService;

/**
 * 后台 借款订单控制器
 *
 *@param addTime 2017-10-11
 *@param author     ChengBo
 */
@Controller
public class AdminLoanOrderController extends AdminBaseController {

	@Autowired
	private LoanService loanService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private HelpService helpService;
	
	/**
	 * 借款订单列表页
	 * 
	 * @return
	 * 
	 * @param addTime 2017年10月11日
	 * @param author     ChengBo
	 */
	@RequestMapping(value = "/admin/loan/order/list", method = RequestMethod.GET)
	private String loanOrderList(){
		return "admin/loan/loanOrderList";
	}
	
	/**
	 * 查询借款订单列表
	 * 
	 * @param param
	 * 
	 * @param addTime 2017年10月11日
	 * @param author     ChengBo
	 * @return 
	 * @throws Exception 
	 */
	@RequestMapping(value = "/admin/get/loan/order/list", method = RequestMethod.POST)
	@ResponseBody
	private Object getLoanOrderList(@RequestBody String requestBody) throws BusinessException{
		//接收请求参数
		JSONObject jsonObject = parseJsonStrToObj(requestBody);
		
		//实例化查询Map
		Map<String, String> orderMap = new HashMap<String, String>();
		
		//设置订单ID条件
		if(jsonObject.getString("orderId") != null && !jsonObject.getString("orderId").isEmpty()){
			orderMap.put("orderId", jsonObject.getString("orderId"));
		}
		
		//设置用户uid
		if(jsonObject.getString("uid") != null && !jsonObject.getString("uid").isEmpty()){
			orderMap.put("uid", jsonObject.getString("uid"));
		}

		//设置用户手机号
		if(jsonObject.getString("mobile") != null && !jsonObject.getString("mobile").isEmpty()){
			//通过手机号查询用户uid
			User userInfo = userService.getUserByUserMobile(jsonObject.getString("mobile"));

			//设置用户uid
			if(userInfo == null){
				orderMap.put("uid", "-1000");
			}else{
				if(orderMap.containsKey("uid")){
					if(!jsonObject.getString("uid").equals(userInfo.getUid()+"")){
						orderMap.put("uid", "-1000");
					}
				}else{
					orderMap.put("uid", userInfo.getUid()+"");
				}
			}
		}
		
		//设置借款额度
		if(jsonObject.getString("borrowMoney") != null && !jsonObject.getString("borrowMoney").isEmpty()){
			orderMap.put("borrowMoney", jsonObject.getString("borrowMoney"));
		}
		
		//设置借款期限
		if(jsonObject.getString("borrowLimit") != null && !jsonObject.getString("borrowLimit").isEmpty()){
			orderMap.put("borrowLimit", jsonObject.getString("borrowLimit"));
		}
		
		//设置订单状态
		if(jsonObject.getString("orderStatus") != null && !jsonObject.getString("orderStatus").isEmpty()){
			orderMap.put("orderStatus", jsonObject.getString("orderStatus"));
		}
		
		//设置审核类型
		if(jsonObject.getString("checkType") != null && !jsonObject.getString("checkType").isEmpty()){
			orderMap.put("checkType", jsonObject.getString("checkType"));
		}

		//设置申请开始时间
		if(jsonObject.getString("applyStartTime") != null && !jsonObject.getString("applyStartTime").isEmpty()){
			orderMap.put("applyStartTime", CommonUtils.getTimeStampByDate(jsonObject.getString("applyStartTime")+" 00:00:00"));
		}
		
		//设置申请结束时间
		if(jsonObject.getString("applyEndTime") != null && !jsonObject.getString("applyEndTime").isEmpty()){
			orderMap.put("applyEndTime", CommonUtils.getTimeStampByDate(jsonObject.getString("applyEndTime")+" 23:59:59"));
		}
		
		//设置操作人条件
		if(jsonObject.getString("operater") != null && !jsonObject.getString("operater").isEmpty()){
			orderMap.put("operater", jsonObject.getString("operater"));
		}
		
		//设置最低数据评分
		if(jsonObject.getString("scoreStart") != null && !jsonObject.getString("scoreStart").isEmpty()){
			orderMap.put("scoreStart", jsonObject.getString("scoreStart"));
		}
		
		//设置最高数据评分
		if(jsonObject.getString("scoreEnd") != null && !jsonObject.getString("scoreEnd").isEmpty()){
			orderMap.put("scoreEnd", jsonObject.getString("scoreEnd"));
		}
		
		//设置查询页数
		orderMap.put("page", jsonObject.getString("page") != null ? jsonObject.getString("page") : "1");
		orderMap.put("pageSize", jsonObject.getString("pageSize") != null ? jsonObject.getString("pageSize") : "12");
		
		//查询借款用户数据
		List<Map<String, Object>> recordList = dealGetBorrowRecordList(orderMap);
		
		//查询注册用户总条数
		int orderTotalNum = loanService.countUserNum(orderMap);
		
		//设置返回用户列表数据
		this.set("list", recordList);
		
		//设置返回总条数
		this.set("total", orderTotalNum);
		
		//返回结果
		return this.getRet();
	}
	
	/**
	 * 处理返回的借款记录
	 * 
	 * @param orderMap
	 * @return
	 * 
	 * @param addTime 2017年10月11日
	 * @param author     ChengBo
	 * @throws BusinessException 
	 */
	private List<Map<String, Object>> dealGetBorrowRecordList(Map<String, String> orderMap) throws BusinessException{
		//调取用户数据
		List<BorrowRecord> recordList = loanService.getBorrowRecordList(orderMap);
		
		//实例化返回结果
		List<Map<String, Object>> orderList = new ArrayList<Map<String, Object>>();
		
		//检查是否有数据
		if(recordList.size() == 0){
			return orderList;
		}
		
		//设置提取用户uid
		List<Long> uidList = new ArrayList<Long>();
		
		//提取数据
		for(BorrowRecord record : recordList){
			//提取用户uid
			if(!uidList.contains(record.getUid())){
				uidList.add(record.getUid());
			}
			
			//设置提取Map
			Map<String, Object> recordMap = new HashMap<String, Object>();
			
			//提取订单号
			recordMap.put("brecordId", record.getBrecordId());
			
			//提取借款金额
			recordMap.put("borrowMoney", record.getBorrowMoney());
			
			//提取借款期限
			recordMap.put("borrowLimit", record.getBorrowLimit());
			
			//设置借款状态
			recordMap.put("status", LoanOrderStatusEnum.getValueByIndex(record.getStatus()));
			
			//设置风控分数
			recordMap.put("score", record.getScore());
			
			//设置审核人
			recordMap.put("operater", record.getOperater());
			
			//设置申请时间
			recordMap.put("applyTime", CommonUtils.getDateByTimeStamp(record.getApplyTime()));

			//设置放款时间
			recordMap.put("loanTime", record.getLoanTime() != 0 ? CommonUtils.getDateByTimeStamp(record.getLoanTime()):"");
			
			//设置借款人uid
			recordMap.put("uid", record.getUid());
			
			//追加数据
			orderList.add(recordMap);
		}
		
		//查询注册用户手机号数据
		Map<Long, User> userInfos = userService.getUserDataByUids(uidList);
		
		//查询用户真实姓名
		Map<Long, UserBase> userBaseInfos = userService.getUserBaseDataByUids(uidList);
		
		//查询用户银行卡信息
		Map<Long, UserBank> userBankInfos = userService.getUserBankByUids(uidList);
		
		//设置用户信息
		for(int i = 0 ; i < orderList.size(); i++){
			//提取用户uid
			String uid = orderList.get(i).get("uid").toString();
			
			//设置手机号
			orderList.get(i).put("mobile", ( userInfos.containsKey(Long.parseLong(uid)) ? CommonUtils.getShortMobile(userInfos.get(Long.parseLong(uid)).getMobile()) : ""));

			//设置真实姓名
			orderList.get(i).put("realName", ( userBaseInfos.containsKey(Long.parseLong(uid)) ? userBaseInfos.get(Long.parseLong(uid)).getRealname() : ""));
			
			//设置银行信息
			orderList.get(i).put("bankName", ( userBankInfos.containsKey(Long.parseLong(uid)) ? userBankInfos.get(Long.parseLong(uid)).getBankName() : ""));
			
			//设置银行卡号
			String bankNum = userBankInfos.containsKey(Long.parseLong(uid)) ? userBankInfos.get(Long.parseLong(uid)).getBankNum() : "";
			orderList.get(i).put("bankNum", (bankNum.isEmpty() ? "" : bankNum.substring(0, 4)+"*****"+bankNum.substring(bankNum.length()-4)));
		}
		
		return orderList;
	}
	
	/**
	 * 订单明细
	 * 
	 * @param model
	 * @return
	 * 
	 * @param addTime 2017年11月3日
	 * @param author     ChengBo
	 * @throws BusinessException 
	 * @throws NumberFormatException 
	 */
	@RequestMapping(value = "/admin/loan/order/detail", method = RequestMethod.GET)
	private String orderDetail(HttpServletRequest request, Model model) throws BusinessException{
	 	//接收订单id
	   	Integer orderId = Integer.parseInt(request.getParameter("orderId"));
	   	
		//调取订单数据
	   	Map<String, Object> borrowDetail = getBorrowRecordById(orderId);
	   
	   	//调取用户信息
	   	Map<String, Object> userInfo = getBorrowUserInfo(Integer.parseInt(borrowDetail.get("uid").toString()));
	   	
	   	//页面赋值
	   	model.addAttribute("borrowRecord", borrowDetail);
	   	model.addAttribute("userInfo", userInfo);
		
		return "admin/loan/loanOrderDetail";
	}
	
	//调取订单的用户相关数据
	private Map<String, Object> getBorrowUserInfo(int uid) throws BusinessException{
		//设置返回结果变量
		Map<String, Object> retUserInfo = new HashMap<String, Object>();
		
		//调取用户基本信息
		User userInfo = userService.getUserInfoByUid(uid);
		
		//调取身份信息
		UserBase userBaseInfo = userService.getUserBaseInfoByUid(uid);
		
		//调取用户银行卡信息
		UserBank userBankInfo = userService.getUserBankInfoByUid(uid);
		
		//设置返回结果
		retUserInfo.put("uid", uid);
		retUserInfo.put("realName", userBaseInfo.getRealname());
		retUserInfo.put("mobile", userInfo.getMobile());
		retUserInfo.put("sex", userBaseInfo.getSex());
		retUserInfo.put("bankName", userBankInfo.getBankName());;
		
		return retUserInfo;
	}
	
	/**
	 * 处理订单信息的返回值
	 * 
	 * @param orderId
	 * @return
	 * 
	 * @param addTime 2017年11月3日
	 * @param author     ChengBo
	 */
	private Map<String, Object> getBorrowRecordById(Integer orderId){
		//调取订单数据
		BorrowRecord brecord = loanService.getBorrowRecordById(orderId);
		
		//实例化返回结果
		Map<String, Object> recordMap = new HashMap<String, Object>();
		
		//设置返回结果
		recordMap.put("brecordId", brecord.getBrecordId());
		recordMap.put("uid", brecord.getUid());
		recordMap.put("orderStatus", LoanOrderStatusEnum.getValueByIndex(brecord.getStatus()));
		recordMap.put("borrowMoney", brecord.getBorrowMoney());
		recordMap.put("borrowInterest", brecord.getBorrowInterest());
		recordMap.put("serviceCharge", brecord.getServiceCharge());
		recordMap.put("overdueCharge", brecord.getOverdueCharge());
		recordMap.put("applyTime", CommonUtils.getDateByTimeStamp(brecord.getApplyTime()));
		recordMap.put("checkMoney", "100.00{待定数据}");
		
		//设置借款期限
		int borrowLimit = brecord.getBorrowLimit();
		recordMap.put("borrowLimit", (borrowLimit == 1 ? "7" : (borrowLimit == 2 ? "15" : (borrowLimit == 3 ? "30" : "-"))));
		
		//应还总金额＝借款金额＋借款利息＋服务费＋逾期金额
		String totalMoney = CommonUtils.doubleAdd(brecord.getBorrowMoney().toString(), brecord.getBorrowInterest().toString());
		totalMoney = CommonUtils.doubleAdd(totalMoney, brecord.getServiceCharge().toString());
		totalMoney = CommonUtils.doubleAdd(totalMoney, brecord.getOverdueCharge().toString());		
		recordMap.put("totalMoney", totalMoney);
		
		//设置逾期天数
		int overdueDay = brecord.getRealRepayTime() > 0 ? (brecord.getRealRepayTime()-CommonUtils.getCurrTimeStamp())/86400 : 0;
		recordMap.put("overdueDay", overdueDay);
		
		//实际还款时间
		recordMap.put("repayTime", CommonUtils.getDateByTimeStamp(brecord.getRepayTime()));
		//正常还款时间
		recordMap.put("realRepayTime", CommonUtils.getDateByTimeStamp(brecord.getRealRepayTime()));
		
		return recordMap;
	}
	
	/**
	 * 借款单状态列表
	 * 
	 * @param request
	 * @param model
	 * @return
	 * 
	 * @param addTime 2017年11月3日
	 * @param author     ChengBo
	 */
	@RequestMapping(value = "/admin/loan/order/status/list", method = RequestMethod.GET)
	private String getOrderStatusList(HttpServletRequest request, Model model){
		//接收参数
	   	Integer orderId = Integer.parseInt(request.getParameter("orderId"));
	   	
	   	//申明订单状态变量
	   	List<Map<String, Object>> orderStatusList = new ArrayList<Map<String, Object>>();
	   	
	   	//调取订单的信息
	   	BorrowRecord recordData = loanService.getBorrowRecordById(orderId);
	   	
	   	//设置订单状态申请状态
	   	Map<String, Object> applyMap = new HashMap<String, Object>();
	   	applyMap.put("title", "申请借款");
	   	applyMap.put("name", recordData.getUid());
	   	applyMap.put("time", CommonUtils.getDateByTimeStamp(recordData.getApplyTime()));
	   	orderStatusList.add(applyMap);
	   	
	   	//设置审核状态
//	   	if(recordData.getCheckTime() > 0){
		   	Map<String, Object> checkMap = new HashMap<String, Object>();
		   	checkMap.put("title", "审核通过");
		   	checkMap.put("name", recordData.getCheckType());
		   	checkMap.put("time", CommonUtils.getDateByTimeStamp(recordData.getCheckTime()));
		   	orderStatusList.add(checkMap);
//	   	}
	   	
	   	//设置签约数据
		Map<String, Object> signMap = new HashMap<String, Object>();
		signMap.put("title", "用户已签约");
		signMap.put("name", recordData.getUid());
		signMap.put("time", CommonUtils.getDateByTimeStamp(recordData.getLoanTime()));
	   	orderStatusList.add(signMap);
	   	
	   	//已放款数据
		Map<String, Object> moneyMap = new HashMap<String, Object>();
		moneyMap.put("title", "已放款");
		moneyMap.put("name", recordData.getUid());
		moneyMap.put("time", CommonUtils.getDateByTimeStamp(recordData.getLoanTime()));
	   	orderStatusList.add(moneyMap);
	   	
	   	//页面赋值
	   	model.addAttribute("orderStatusList", orderStatusList);
		
		return "admin/loan/loanOrderStatusList";
	}
	
	/**
	 * 审核借款订单列表
	 * 
	 * @return
	 * 
	 * @param addTime 2017年11月6日
	 * @param author     ChengBo
	 */
	@RequestMapping(value = "/admin/loan/check/list", method = RequestMethod.GET)
	private String checkLoanOrderList(){
		return "admin/loan/loanCheckList";
	}
	
	/**
	 * 审核订单明细页面
	 * 
	 * @param request
	 * @param model
	 * @return
	 * 
	 * @param addTime 2017年11月6日
	 * @param author     ChengBo
	 * @throws BusinessException 
	 * @throws NumberFormatException 
	 */
	@RequestMapping(value = "/admin/loan/order/check", method = RequestMethod.GET)
	private String checkLoanOrder(HttpServletRequest request, Model model) throws BusinessException{
	 	//接收订单id
	   	int orderId = Integer.parseInt(request.getParameter("orderId"));
		
	   	//查询订单数据
	   	BorrowRecord brecord = loanService.getBorrowRecordById(orderId);
	   	
	   	//提取用户uid
	   	int uid = ValidateUtils.checkIsNumber(brecord.getUid()) ? Integer.parseInt(brecord.getUid()+"") : 0;
	   	
	   	//调取用户基本信息
	   	Map<String, Object> userBaseInfo = getUserBaseInfo(uid);
	   	
	   	//调取用户职业信息
	   	Map<String, Object> userJobInfo = getUerJobInfo(uid);
	   	
	   	//调取银行卡信息
	   	Map<String, Object> userBankInfo = getUserBankInfo(uid);
	   	
	   	//调取紧急联系人
	   	List<Map<String, Object>> userLinkManInfo = getUserLinkMan(uid);
	   	
	   	//页面赋值
	   	model.addAttribute("orderId", orderId);
	   	model.addAttribute("userBaseInfo", userBaseInfo);
	   	model.addAttribute("userJobInfo", userJobInfo);
	   	model.addAttribute("userBankInfo", userBankInfo);
	   	model.addAttribute("userLinkManInfo", userLinkManInfo);
	   	
		return "admin/loan/checkLoanOrder";
	}
	
	/**
	 * 调取用户联系人信息
	 * 
	 * @param uid
	 * @return
	 * @throws BusinessException
	 * 
	 * @param addTime 2017年11月7日
	 * @param author     ChengBo
	 */
	private List<Map<String, Object>> getUserLinkMan(int uid) throws BusinessException{
		//实例化返回结果
		List<Map<String, Object>> userLinkManList = new ArrayList<Map<String, Object>>();

		//调取联系人信息
		List<UserLinkMan> userLinkManData = userService.getUserLinkManInfoByUid(uid);
		if(userLinkManData == null){
			return userLinkManList;
		}
		
		//设置返回值
		for(int i = 0; i<userLinkManData.size(); i++){
			//设置临时map
			Map<String, Object> tmpLinkManMap = new HashMap<String, Object>();
			
			//提取当前实例
			UserLinkMan tmpLinkMan = userLinkManData.get(i);
			
			//设置返回参数
			tmpLinkManMap.put("relation", tmpLinkMan.getRelation());
			tmpLinkManMap.put("name", tmpLinkMan.getName());
			
			//设置手机号
			String phone = tmpLinkMan.getPhone().substring(0,  3)+"****"+tmpLinkMan.getPhone().substring(tmpLinkMan.getPhone().length()-4);
			tmpLinkManMap.put("phone", phone);
			
			//追加到返回结果
			userLinkManList.add(tmpLinkManMap);
		}
		
		return userLinkManList;
	}
	
	/**
	 * 调取用户银行卡信息
	 * 
	 * @param uid
	 * @return
	 * @throws BusinessException
	 * 
	 * @param addTime 2017年11月7日
	 * @param author     ChengBo
	 */
	private Map<String, Object> getUserBankInfo(int uid) throws BusinessException{
		//实例化返回结果
		Map<String, Object> userBankInfo = new HashMap<String, Object>();
		
		//调取职业信息
	   	UserBank userBankInfoData = userService.getUserBankInfoByUid(uid);
	   	if(userBankInfoData == null){
	   		return userBankInfo;
	   	}
	   	
	   	//设置银行信息
	   	userBankInfo.put("bankName", userBankInfoData.getBankName());
	   	userBankInfo.put("mobile", userBankInfoData.getMobile());
	   	userBankInfo.put("bankNum", CommonUtils.getBankNumBySplit(userBankInfoData.getBankNum()));
		
	   	//设置开户行省市
	   	List<Integer> regionIds = new ArrayList<Integer>();
	   	regionIds.add(userBankInfoData.getBankProvince());
	   	regionIds.add(userBankInfoData.getBankCity());
	   	
	   	//调取用户所在地
	   	Map<Integer, String> regionMap = helpService.getRegionByIds(regionIds);
	   	
	   	//设置省份/城市/区县
	   	String province	= regionMap.containsKey(userBankInfoData.getBankProvince()) ? regionMap.get(userBankInfoData.getBankProvince()) : "";
	    String city				= regionMap.containsKey(userBankInfoData.getBankCity()) ? regionMap.get(userBankInfoData.getBankCity()) : "";
	   
	   	//拼接用户住处所在地
	   	String address = province+" "+city;
	   	
	   	//设置公司地址
	   	userBankInfo.put("address", address);
	   	
		return userBankInfo;
	}
	
	/**
	 * 调取用户职业信息
	 * 
	 * @param uid
	 * @return
	 * @throws BusinessException
	 * 
	 * @param addTime 2017年11月7日
	 * @param author     ChengBo
	 */
	private Map<String, Object> getUerJobInfo(int uid) throws BusinessException{
		//实例化返回结果
		Map<String, Object> userJobInfo = new HashMap<String, Object>();
		
		//调取职业信息
	   	UserJob userJobInfoData = userService.getUserJobInfoByUid(uid);
	   	if(userJobInfoData == null){
	   		return userJobInfo;
	   	}
	   	
	   	//设置返回值
	   	userJobInfo.put("jobName", userJobInfoData.getJobName());
	   	userJobInfo.put("income", userJobInfoData.getIncome());
	   	userJobInfo.put("company", userJobInfoData.getCompany());
	   	userJobInfo.put("phone", userJobInfoData.getPhone());
	   	
	   	//设置用户所在地数据
	   	List<Integer> regionIds = new ArrayList<Integer>();
	   	regionIds.add(userJobInfoData.getProvince());
	   	regionIds.add(userJobInfoData.getCity());
	   	regionIds.add(userJobInfoData.getDistrict());
	   	
	   	//调取用户所在地
	   	Map<Integer, String> regionMap = helpService.getRegionByIds(regionIds);
	   	
	   	//设置省份/城市/区县
	   	String province	= regionMap.containsKey(userJobInfoData.getProvince()) ? regionMap.get(userJobInfoData.getProvince()) : "";
	    String city				= regionMap.containsKey(userJobInfoData.getCity()) ? regionMap.get(userJobInfoData.getCity()) : "";
	    String district		= regionMap.containsKey(userJobInfoData.getDistrict()) ? regionMap.get(userJobInfoData.getDistrict()) : "";
	   
	   	//拼接用户住处所在地
	   	String address = province+" "+city+" "+district+" "+userJobInfoData.getAddress();
	   	
	   	//设置公司地址
	   	userJobInfo.put("address", address);
		
		return userJobInfo;
	}
	
	/**
	 * 调取用户的个人信息
	 * 
	 * @param uid
	 * @return
	 * @throws BusinessException
	 * 
	 * @param addTime 2017年11月7日
	 * @param author     ChengBo
	 */
	private Map<String, Object> getUserBaseInfo(int uid) throws BusinessException{
		//实例化返回结果
		Map<String, Object> userBaseInfo = new HashMap<String, Object>();
		
	   	//调取用户基本信息
	   	UserBase userBaseInfoData = userService.getUserBaseInfoByUid(uid);
	   	
	   	//设置用户所在地数据
	   	List<Integer> regionIds = new ArrayList<Integer>();
	   	regionIds.add(userBaseInfoData.getProvince());
	   	regionIds.add(userBaseInfoData.getCity());
	   	regionIds.add(userBaseInfoData.getDistrict());
	   	
	   	//调取用户所在地
	   	Map<Integer, String> regionMap = helpService.getRegionByIds(regionIds);
	   	
	   	//设置省份/城市/区县
	   	String province	= regionMap.containsKey(userBaseInfoData.getProvince()) ? regionMap.get(userBaseInfoData.getProvince()) : "";
	    String city				= regionMap.containsKey(userBaseInfoData.getCity()) ? regionMap.get(userBaseInfoData.getCity()) : "";
	    String district		= regionMap.containsKey(userBaseInfoData.getDistrict()) ? regionMap.get(userBaseInfoData.getDistrict()) : "";
	   
	   	//拼接用户住处所在地
	   	String address = province+" "+city+" "+district+" "+userBaseInfoData.getAddress();
	   	userBaseInfo.put("address", address);

	   	//设置返回值
	   	userBaseInfo.put("realname", userBaseInfoData.getRealname());
	   	userBaseInfo.put("sex", userBaseInfoData.getSex());
	   	userBaseInfo.put("cardnum", userBaseInfoData.getCardNum());
	   	
	   	//设置出生年月日
	   	userBaseInfo.put("birthday", CommonUtils.getBirthDayByCardNum(userBaseInfoData.getCardNum()));
	   	
	   	//设置学历
	   	userBaseInfo.put("degree", userBaseInfoData.getDegree());
	   	
	   	//设置婚姻状况
	   	userBaseInfo.put("marriage", userBaseInfoData.getMarriage());
	   	
	   	//子女个数
	   	userBaseInfo.put("child", userBaseInfoData.getChild());
	   	
	   	//居住时长
	   	userBaseInfo.put("liveTime", userBaseInfoData.getLiveTime());
	   	
	   	//设置微信号
	   	userBaseInfo.put("wechat", userBaseInfoData.getWechat());
	   	
	   	//设置用户户口所在地
	   	userBaseInfo.put("cardAddress", userBaseInfoData.getCardAddress());
	   	
	   	//设置民族
	   	userBaseInfo.put("userNation", userBaseInfoData.getCardNation());
	   	
	   	//设置公安局
	   	userBaseInfo.put("cardOffice", userBaseInfoData.getCardOffice());
	   	
	   	//有效期
	   	userBaseInfo.put("cardExpire", userBaseInfoData.getCardExpire());
		
	   	//设置邮箱
	   	userBaseInfo.put("email", userBaseInfoData.getEmail());
	   	
		return userBaseInfo;
	}

	/**
	 * 处理订单审核状态
	 * 
	 * @param requestBody
	 * @return
	 * @throws BusinessException
	 * 
	 * @param addTime 2017年11月7日
	 * @param author     ChengBo
	 */
	@RequestMapping(value = "/admin/loan/check/order", method = RequestMethod.POST)
	@ResponseBody
	private Object doCheckLoanOrder(HttpServletRequest request, @RequestBody String requestBody) throws BusinessException{
		//接收请求参数
		JSONObject jsonObject = parseJsonStrToObj(requestBody);
		
		//设置更新参数变量
		Map<String, String> updMap = new HashMap<String, String>();
		
		//提取订单id
		updMap.put("orderId", jsonObject.getString("orderId"));
		
		//接收审核状态
		updMap.put("type", jsonObject.getString("type"));
		
		//设置当前的登陆用户id
		updMap.put("adminUid", this.getAdminUser(request).getUid()+"");
		
		//设置拒绝原因
		updMap.put("remark", jsonObject.getString("reason"));
		
		//更新人工审核的订单状态
		Boolean updStatus = loanService.updPersonCheckOrderStatus(updMap);
		
		//设置返回值
		this.set("status", (updStatus ? 1 : 0));
	
		//返回结果
		return this.getRet();
	}
	
	/**
	 * 机器决策页面
	 * 
	 * @return
	 * 
	 * @param addTime 2017年11月8日
	 * @param author     ChengBo
	 */
	@RequestMapping(value = "/admin/loan/mechine/list", method = RequestMethod.GET)
	private String mechineOrderList(){
		return "admin/loan/loanMechineList";
	}
	
	/**
	 * 订单放款记录表
	 * 
	 * @return
	 * 
	 * @param addTime 2017年11月8日
	 * @param author     ChengBo
	 */
	@RequestMapping(value = "/admin/loan/pay/list", method = RequestMethod.GET)
	private String payOrderList(){
		return "admin/loan/loanPayList";
	}
	
	/**
	 * 查询指定订单的还款明细
	 * 
	 * @param requestBody
	 * @return
	 * 
	 * @param addTime 2017年11月8日
	 * @param author     ChengBo
	 * @throws BusinessException 
	 */
	@RequestMapping(value = "/admin/get/loan/repay/detail/byid", method = RequestMethod.POST)
	@ResponseBody
	private Object getLoanRepayDetailByOrderId(@RequestBody String requestBody) throws BusinessException{
		//接收请求参数
		JSONObject jsonObject = parseJsonStrToObj(requestBody);
		
		//提取订单id
		int orderId = jsonObject.getInteger("orderId");
		
		//设置返回订单明细数据变量
		Map<String, Object> repayDetail = new HashMap<String, Object>();
		
		//调取订单数据
		BorrowRecord orderRecord = loanService.getBorrowRecordById(orderId);
		if(orderRecord != null){
			//设置返回结果
			repayDetail.put("orderId", orderRecord.getBrecordId());
			repayDetail.put("borrowMoney", orderRecord.getBorrowMoney());
			repayDetail.put("borrowInterest", orderRecord.getBorrowInterest());
			repayDetail.put("serviceCharge", orderRecord.getServiceCharge());
			repayDetail.put("repayTime", CommonUtils.getDateByTimeStamp(orderRecord.getRepayTime()));
			repayDetail.put("realRepayTime", CommonUtils.getDateByTimeStamp(orderRecord.getRealRepayTime()));
			repayDetail.put("status", LoanOrderStatusEnum.getValueByIndex(orderRecord.getStatus()));
			repayDetail.put("overdueCharge", orderRecord.getOverdueCharge());
			
			//设置借款期限
			int borrowLimit = orderRecord.getBorrowLimit();
			repayDetail.put("borrowLimit", (borrowLimit == 1 ? "7天" : (borrowLimit == 2 ? "15天" : (borrowLimit == 3 ? "30天" : "-"))));
			
			//应还总金额＝借款金额＋借款利息＋服务费＋逾期金额
			String totalMoney = CommonUtils.doubleAdd(orderRecord.getBorrowMoney().toString(), orderRecord.getBorrowInterest().toString());
			totalMoney = CommonUtils.doubleAdd(totalMoney, orderRecord.getServiceCharge().toString());
			totalMoney = CommonUtils.doubleAdd(totalMoney, orderRecord.getOverdueCharge().toString());		
			repayDetail.put("totalMoney", totalMoney);
			repayDetail.put("realTotalMoney", totalMoney);
			
			//设置逾期天数
			int overdueDay = orderRecord.getRealRepayTime() > 0 ? (orderRecord.getRealRepayTime()-CommonUtils.getCurrTimeStamp())/86400 : 0;
			repayDetail.put("overdueDay", overdueDay);
		}
		
		//设置返回结果
		this.set("repayDetail", repayDetail);
		
		//返回结果
		return this.getRet();
	}
}
