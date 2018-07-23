package com.crc.CSP.service;

import java.util.HashMap;
import java.util.List;

import com.crc.CSP.bean.OperInfoVO;

public interface OperInfoService {

	public List<OperInfoVO> operinfo(HashMap operinfo) throws Exception;
	public List<OperInfoVO> operinfo_comp(HashMap operinfo) throws Exception;
}
