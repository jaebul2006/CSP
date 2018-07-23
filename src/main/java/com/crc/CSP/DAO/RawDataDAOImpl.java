package com.crc.CSP.DAO;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.crc.CSP.bean.RawOSVO;
import com.crc.CSP.bean.RawTSAVO;

@Repository
public class RawDataDAOImpl implements RawDataDAO {

	private static final String namespace = "com.crc.CSP.RawDataMapper";
	
	@Inject
	private SqlSession sqlSession;
	
	
	public void insertTSA(HashMap map) throws Exception
	{
		sqlSession.insert(namespace + ".insertTSA", map);
	}
	
	public List<RawTSAVO> getTSA(String user_id) throws Exception
	{
		return sqlSession.selectList(namespace + ".getTSA", user_id);
	}
	
	public String getTSAFilename(String raw_id) throws Exception
	{
		return sqlSession.selectOne(namespace + ".getTSAFilename", raw_id);
	}

	public void insertOS(HashMap map) throws Exception
	{
		sqlSession.insert(namespace + "insertOS", map);
	}
	
	public List<RawOSVO> getOS(String user_id) throws Exception
	{
		return sqlSession.selectList(namespace + ".getOS", user_id);
	}
	
	public String getOSFilename(String raw_id) throws Exception
	{
		return sqlSession.selectOne(namespace + ".getOSFilename", raw_id);
	}
	
}
