package com.crc.CSP.DAO;

import java.util.HashMap;
import java.util.List;

import com.crc.CSP.bean.OperInfoVO;

public interface OperInfoDAO {

	public List<OperInfoVO> operinfo(HashMap operinfo) throws Exception;
	public List<OperInfoVO> operinfo_comp(HashMap operinfo) throws Exception;
}
