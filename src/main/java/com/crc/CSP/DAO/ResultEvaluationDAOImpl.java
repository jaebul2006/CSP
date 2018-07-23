package com.crc.CSP.DAO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.crc.CSP.bean.EvalTSA;

@Repository
public class ResultEvaluationDAOImpl implements ResultEvaluationDAO{

private static final String namespace = "com.crc.CSP.ResultEvaluationMapper";
	
	@Inject
	private SqlSession sqlSession;
	
	
	@Override
	public void insert(HashMap map) throws Exception
	{	
		sqlSession.insert(namespace + ".insert", map);
	}
	
	@Override
	public int countOper(String searchOption, String keyword, String content_type, String username) throws Exception
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("searchOption", searchOption);
		map.put("keyword", keyword);
		map.put("content_type", content_type);
		map.put("username", username);
		return sqlSession.selectOne(namespace + ".countOper", map);
	}
	
	@Override
	public List<EvalTSA> list(int start, int end, String searchOption, String keyword, String content_type, String username) throws Exception
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("searchOption", searchOption);
		map.put("keyword", keyword);
		map.put("start", start);
		map.put("end", end);
		map.put("content_type", content_type);
		map.put("username", username);
		return sqlSession.selectList(namespace + ".list", map);
	}
	
	@Override
	public EvalTSA view(int id) throws Exception
	{
		return sqlSession.selectOne(namespace + ".view", id);
	}
	
}
