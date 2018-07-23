package com.crc.CSP.bean;

public class OperInfoVO {

	private int no; // row ³Ñ¹ö
	private String id;
	private String content_type;
	private int oper_score;
	private int oper_time;
	private String oper_success;
	private String DATE;
	
	public int getNo() {
		return no;
	}
	
	public void setNo(int no) {
		this.no = no;
	}
	
	public String getContent_type() {
		return content_type;
	}
	
	public void setContent_type(String content_type) {
		this.content_type = content_type;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public int getOper_score(){
		return oper_score;
	}
	
	public void setOper_score(int oper_score) {
		this.oper_score = oper_score;
	}
	
	public int getOper_time() {
		return oper_time;
	}
	
	public void setOper_time(int oper_time) {
		this.oper_time = oper_time;
	}
	
	public String getOper_success() {
		return oper_success;
	}
	
	public void setOper_success(String oper_success) {
		this.oper_success = oper_success;
	}
	
	public String getDATE() {
		return DATE;
	}
	
	public void setDATE(String DATE) {
		this.DATE = DATE;
	}
	
}
