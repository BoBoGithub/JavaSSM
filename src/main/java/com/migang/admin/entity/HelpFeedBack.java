package com.migang.admin.entity;

/**
 * 帮助中心－问题返回实体类
 *
 *@param addTime 2017-10-27
 *@param author     ChengBo
 */
public class HelpFeedBack {

	//自增id
	private int feedBackId;
	
	//用户uid
	private int uid;
	
	//设备唯一标识
	private String udid;
	
	//反馈内容
	private String content;
	
	//入库时间
	private int addTime;
	
	//回复内容
	private String reply;

	public int getFeedBackId() {
		return feedBackId;
	}

	public void setFeedBackId(int feedBackId) {
		this.feedBackId = feedBackId;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getUdid() {
		return udid;
	}

	public void setUdid(String udid) {
		this.udid = udid;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getAddTime() {
		return addTime;
	}

	public void setAddTime(int addTime) {
		this.addTime = addTime;
	}

	public String getReply() {
		return reply;
	}

	public void setReply(String reply) {
		this.reply = reply;
	}

	@Override
	public String toString() {
		return "HelpFeedBack: "+feedBackId+" "+uid+" "+udid+" "+content+" "+reply;
	}
}
