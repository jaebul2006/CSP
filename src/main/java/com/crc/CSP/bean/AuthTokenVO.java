package com.crc.CSP.bean;

public class AuthTokenVO {

	private String ID;
	private String Token;
	private String TimeValid;
	private String UserId;
	
	public String getID() {
		return ID;
	}
	
	public void setID(String ID) {
		this.ID = ID;
	}
	
	public String getToken() {
		return Token;
	}
	
	public void setToken(String Token) {
		this.Token = Token;
	}
	
	public String getTimeValid() {
		return TimeValid;
	}
	
	public void setTimeValid(String TimeValid) {
		this.TimeValid = TimeValid;
	}
	
	public String getUserId() {
		return UserId;
	}
	
	public void setUserId(String UserId) {
		this.UserId = UserId;
	}
	
}
