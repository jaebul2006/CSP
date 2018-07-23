package com.crc.CSP.DAO;

import java.util.HashMap;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.crc.CSP.bean.AuthTokenVO;

@Repository
public class AuthTokenDAOImpl implements AuthTokenDAO 
{
	private static final String namespace = "com.crc.CSP.AuthTokenMapper";
	
	@Inject
	private SqlSession sqlSession;
	
	@Override
	public AuthTokenVO getAuthToken(String authtoken) throws Exception
	{
		return sqlSession.selectOne(namespace + ".getAuthToken", authtoken);
	}
	
	@Override
	public void updateAuthToken(HashMap map) throws Exception
	{
		sqlSession.insert(namespace + ".updateAuthToken", map);
	}
	
}
