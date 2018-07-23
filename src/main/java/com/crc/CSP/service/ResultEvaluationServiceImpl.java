package com.crc.CSP.service;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.crc.CSP.DAO.ResultEvaluationDAO;
import com.crc.CSP.bean.EvalTSA;

@Service
public class ResultEvaluationServiceImpl implements ResultEvaluationService{

	@Inject
	private ResultEvaluationDAO dao;
	
	@Override 
	public void insert(HashMap map) throws Exception{
		dao.insert(map);
	}
	
	@Override
	public int countOper(String searchOption, String keyword, String content_type, String username) throws Exception
	{
		return dao.countOper(searchOption, keyword, content_type, username);
	}
	
	@Override
	public List<EvalTSA> list(int start, int end, String searchOption, String keyword, String content_type, String username) throws Exception
	{
		return dao.list(start, end, searchOption, keyword, content_type, username);
	}
	
	@Override
	public EvalTSA view(int id) throws Exception
	{
		return dao.view(id);
	}
	
}
