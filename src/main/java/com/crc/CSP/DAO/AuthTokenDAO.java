package com.crc.CSP.DAO;

import java.util.HashMap;

import com.crc.CSP.bean.AuthTokenVO;

public interface AuthTokenDAO {

	public AuthTokenVO getAuthToken(String authtoken) throws Exception;
	public void updateAuthToken(HashMap map) throws Exception;
	
}
