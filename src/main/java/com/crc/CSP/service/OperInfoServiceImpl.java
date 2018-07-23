package com.crc.CSP.service;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.crc.CSP.DAO.OperInfoDAO;
import com.crc.CSP.bean.OperInfoVO;

@Service
public class OperInfoServiceImpl implements OperInfoService {

	@Inject
	private OperInfoDAO dao;
	
	@Override
	public List<OperInfoVO> operinfo(HashMap operinfo) throws Exception
	{
		return dao.operinfo(operinfo);
	}
	
	@Override
	public List<OperInfoVO> operinfo_comp(HashMap operinfo) throws Exception
	{
		return dao.operinfo_comp(operinfo);
	}
	
}
