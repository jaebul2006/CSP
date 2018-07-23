package com.crc.CSP.service;

import java.util.HashMap;
import java.util.List;

import com.crc.CSP.bean.RawOSVO;
import com.crc.CSP.bean.RawTSAVO;

public interface RawDataService {

	public void insertTSA(HashMap map) throws Exception;
	public List<RawTSAVO> getTSA(String user_id) throws Exception;
	public String getTSAFilename(String raw_id) throws Exception;

	public void insertOS(HashMap map) throws Exception;
	public List<RawOSVO> getOS(String user_id) throws Exception;
	public String getOSFilename(String raw_id) throws Exception;
	
}
