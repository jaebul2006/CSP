package com.crc.CSP.service;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.crc.CSP.DAO.RawDataDAO;
import com.crc.CSP.bean.RawOSVO;
import com.crc.CSP.bean.RawTSAVO;

@Service
public class RawDataServiceImpl implements RawDataService {

	@Inject
	private RawDataDAO dao;
	
	public void insertTSA(HashMap map) throws Exception
	{
		dao.insertTSA(map);
	}
	
	public List<RawTSAVO> getTSA(String user_id) throws Exception
	{
		return dao.getTSA(user_id);
	}
	
	public String getTSAFilename(String raw_id) throws Exception
	{
		return dao.getTSAFilename(raw_id);
	}
	
	public void insertOS(HashMap map) throws Exception
	{
		dao.insertOS(map);
	}
	
	public List<RawOSVO> getOS(String user_id) throws Exception
	{
		return dao.getOS(user_id);
	}
	
	public String getOSFilename(String raw_id) throws Exception
	{
		return dao.getOSFilename(raw_id);
	}
	
}
