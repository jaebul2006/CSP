package com.crc.CSP.service;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.crc.CSP.DAO.LicenseDAO;
import com.crc.CSP.bean.LicenseVO;

@Service
public class LicenseServiceImpl implements LicenseService {

	@Inject
	private LicenseDAO dao;
	
	@Override
	public void insert_license(HashMap license_info) throws Exception
	{
		dao.insert_license(license_info);
	}
	
	@Override
	public List<LicenseVO> check_license(String user_id) throws Exception
	{
		return dao.check_license(user_id);
	}
	
	
}
