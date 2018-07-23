package com.crc.CSP.DAO;

import java.util.HashMap;
import java.util.List;

import com.crc.CSP.bean.LicenseVO;

public interface LicenseDAO {

	public void insert_license(HashMap license_info) throws Exception;
	public List<LicenseVO> check_license(String user_id) throws Exception;
	
}
