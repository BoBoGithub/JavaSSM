package com.migang.admin.entity;

/**
 * 用户基本信息实体类
 *
 *@param addTime 2017-09-25
 *@param author    ChengBo
 */
public class UserBase {

	//用户uid
	private long uid;
	
	//用户学历
	private int degree;
	
	//用户结婚状态
	private int marriage;
	
	//子女个数
	private int child;
	
	//所在身份
	private int province;
	
	//所在城市
	private int city;
	
	//所在区县
	private int district;
	
	//具体地址
	private String address;
	
	//居住时长
	private int liveTime;
	
	//微信号
	private String wechat;
	
	//用户邮箱
	private String email;
	
	//真实新姓名
	private String realname;
	
	//性别
	private int sex;
	
	//身份证号
	private String cardNum;
	
	//身份证住址
	private String cardAddress;
	
	//身份证民族
	private String cardNation;
	
	//身份证办证机构
	private String cardOffice;
	
	//身份证过期时间
	private String cardExpire;
	
	//证件号图片路径
	private String cardPic;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCardNum() {
		return cardNum;
	}

	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}

	public String getCardAddress() {
		return cardAddress;
	}

	public void setCardAddress(String cardAddress) {
		this.cardAddress = cardAddress;
	}

	public String getCardNation() {
		return cardNation;
	}

	public void setCardNation(String cardNation) {
		this.cardNation = cardNation;
	}

	public String getCardOffice() {
		return cardOffice;
	}

	public void setCardOffice(String cardOffice) {
		this.cardOffice = cardOffice;
	}

	public String getCardExpire() {
		return cardExpire;
	}

	public void setCardExpire(String cardExpire) {
		this.cardExpire = cardExpire;
	}

	public String getCardPic() {
		return cardPic;
	}

	public void setCardPic(String cardPic) {
		this.cardPic = cardPic;
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}
	
	public int getDegree() {
		return degree;
	}

	public void setDegree(int degree) {
		this.degree = degree;
	}

	public int getMarriage() {
		return marriage;
	}

	public void setMarriage(int marriage) {
		this.marriage = marriage;
	}

	public int getChild() {
		return child;
	}

	public void setChild(int child) {
		this.child = child;
	}

	public int getProvince() {
		return province;
	}

	public void setProvince(int province) {
		this.province = province;
	}

	public int getCity() {
		return city;
	}

	public void setCity(int city) {
		this.city = city;
	}

	public int getDistrict() {
		return district;
	}

	public void setDistrict(int district) {
		this.district = district;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getLiveTime() {
		return liveTime;
	}

	public void setLiveTime(int liveTime) {
		this.liveTime = liveTime;
	}

	public String getWechat() {
		return wechat;
	}

	public void setWechat(String wechat) {
		this.wechat = wechat;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	@Override
	public String toString() {
		return "userBaseInfo: "+uid+" "+realname+" "+ wechat;
	}
	
}
