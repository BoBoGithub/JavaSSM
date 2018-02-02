package com.migang.admin.common.enums;

/**
 * 借款订单状态Enum
 *
 *@param addTime 2017-10-12
 *@param author     ChengBo
 */
public enum LoanOrderStatusEnum {
	//订单状态配置
	//-3:审核失败[机器] -2:审核失败[信审员] -1:取消订单 0:待审核 1:待人工信审 2:待确认银行卡 3:待签约 4:待放款 5:放款失败(原因更新备注) 6:待还款 7:逾期 8:逾期后还款 9:已还款 10:坏账[催收无果]
	AUDIT_MECHINE_FAILURE(					"审核失败[机器]", 			-3),
	AUDIT_PERSON_FAILURE(					"审核失败[信审员]", 		-2),
	AUDIT_FAILURE(											"取消订单",						-1),
	PENDING_AUDIT(										"待审核",					0),
	PENDING_PERSION_CONFIRM(		"待人工信审",			1),
	PENDING_CONFIRM_BANKCARD(	"待确认银行卡",		2),
	PENDING_SIGN(										"待签约",					3),
	PENDING_SEND_LOAN(							"待放款",					4),
	SEND_LOAN_FAILURE(							"放款失败",				5),
	PENDING_REPAYMENT(							"待还款",					6),
	LOAN_OVERDUE(										"逾期",						7),
	LOAN_OVERDUE_REPAY(						"逾期后还款",			8),
//	REPAYMENTING(										"还款中",					7),
	REPAYMENTED(											"已还款",					9),
	BAD_DEBT(													"坏账",						10);
	
	private String value;
	
	private int index;
	
	private LoanOrderStatusEnum(String value, int index){
		this.value = value;
		this.index = index;
	}
	
	//按值查找
	public static LoanOrderStatusEnum get(String value){
		for(LoanOrderStatusEnum p : LoanOrderStatusEnum.values()){
			if(p.getValue().equals(value)){
				return p;
			}
		}
		
		return null;
	}
	
	//按索引查找
	public static LoanOrderStatusEnum get(int index){
		for(LoanOrderStatusEnum p : LoanOrderStatusEnum.values()){
			if(p.getIndex() == index){
				return p;
			}
		}
		
		return null;
	}
	
	//检查索引是否存在
	public static Boolean checkIndexIsExists(int index){
		for(LoanOrderStatusEnum p : LoanOrderStatusEnum.values()){
			if(p.getIndex() == index){
				return true;
			}
		}
		
		return false;
	}
	
	//查找索引对应的值
	public static String getValueByIndex(int index){
		for(LoanOrderStatusEnum p : LoanOrderStatusEnum.values()){
			if(p.getIndex() == index){
				return p.getValue();
			}
		}
		
		return "";
	}
	
	public String getValue(){
		return value;
	}
	
	public void setValue(String value){
		this.value = value;
	}
	
	public int getIndex(){
		return index;
	}
	
	public void setIndex(int index){
		this.index = index;
	}
}
