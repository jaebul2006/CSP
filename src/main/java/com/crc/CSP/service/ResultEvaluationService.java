package com.crc.CSP.service;

import java.util.HashMap;
import java.util.List;

import com.crc.CSP.bean.EvalTSA;

public interface ResultEvaluationService {

	void insert(HashMap result) throws Exception;
	public int countOper(String searchOption, String keyword, String content_type, String username) throws Exception;
	public List<EvalTSA> list(int start, int end, String searchOption, String keyword, String content_type, String username) throws Exception;
	public EvalTSA view(int id) throws Exception;
	
}
