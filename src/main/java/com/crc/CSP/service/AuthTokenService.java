package com.crc.CSP.service;

import java.util.HashMap;

import com.crc.CSP.bean.AuthTokenVO;

public interface AuthTokenService 
{

	public void updateAuthToken(HashMap map) throws Exception;
	public AuthTokenVO getAuthToken(String autotoken) throws Exception;
	
}
