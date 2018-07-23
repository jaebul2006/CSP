package com.crc.CSP.DAO;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.crc.CSP.bean.UserScoreBean;

@Repository
public class UserScoreDAOImpl implements UserScoreDAO{

	private static final String namespace = "com.crc.CSP.UserScoreMapper";
	
	@Inject
	private SqlSession sqlSession;
	
	@Override
	public void update_userscore(HashMap userinfo) throws Exception{
		sqlSession.insert(namespace + ".update_userscore", userinfo);
	}
	
	@Override
	public List<UserScoreBean> userscore(HashMap userinfo) throws Exception{
		return sqlSession.selectList(namespace + ".userscore", userinfo);
	}
	
}
