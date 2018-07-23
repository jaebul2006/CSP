package com.crc.CSP.DAO;

import java.util.HashMap;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.crc.CSP.bean.AccountVO;
import com.crc.CSP.bean.ContentAuthorityVO;

@Repository
public class AccountDAOImpl implements AccountDAO
{
	private static final String namespace = "com.crc.CSP.AccountMapper";
	
	@Inject
	private SqlSession sqlSession;
	
	@Override
	public void regist(AccountVO vo) throws Exception
	{
		sqlSession.insert(namespace + ".account_regist", vo);
	}
	
	@Override
	public AccountVO getAccount(String id) throws Exception
	{
		return sqlSession.selectOne(namespace + ".getAccount", id);
	}
	
	@Override
	public AccountVO getAccountAdmin(String adminid) throws Exception
	{
		return sqlSession.selectOne(namespace + ".getAccountAdmin", adminid);
	}
	
	@Override
	public void updateLastInfo(HashMap map) throws Exception
	{
		sqlSession.update(namespace + ".updateLastInfo", map);
	}
	
	@Override
	public int countAdminID(String aminid) throws Exception
	{
		return sqlSession.selectOne(namespace + ".countAdminID", aminid);
	}
	
	@Override
	public void ChangeMacAddress(HashMap map) throws Exception
	{
		sqlSession.selectOne(namespace + ".ChangeMacAddress", map);
	}
	
	@Override
	public AccountVO getAccountByUid(String uid) throws Exception
	{
		return sqlSession.selectOne(namespace + ".getAccountByUid", uid);
	}
	
	@Override
	public ContentAuthorityVO getContentAuthority(String admin_id) throws Exception
	{
		return sqlSession.selectOne(namespace + ".getContentAuthority", admin_id);
	}
	
	@Override
	public void insertContentAuthority(String admin_id) throws Exception
	{
		sqlSession.insert(namespace + ".insertContentAuthority", admin_id);
	}
}
