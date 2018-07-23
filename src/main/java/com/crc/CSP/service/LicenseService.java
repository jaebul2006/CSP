package com.crc.CSP.service;

import java.util.HashMap;
import java.util.List;

import com.crc.CSP.bean.LicenseVO;

public interface LicenseService {

	public void insert_license(HashMap license_info) throws Exception;
	public List<LicenseVO> check_license(String user_id) throws Exception;
	
}
