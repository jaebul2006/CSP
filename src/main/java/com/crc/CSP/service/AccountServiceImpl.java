package com.crc.CSP.service;

import java.util.HashMap;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.crc.CSP.DAO.AccountDAO;
import com.crc.CSP.bean.AccountVO;
import com.crc.CSP.bean.ContentAuthorityVO;


@Service
public class AccountServiceImpl implements AccountService
{
	
	@Inject
	AccountDAO dao;

	@Override
	public void regist(AccountVO vo) throws Exception
	{
		dao.regist(vo);
	}
	
	@Override
	public AccountVO getAccount(String id) throws Exception
	{
		return dao.getAccount(id);
	}
	
	@Override
	public AccountVO getAccountAdmin(String adminid) throws Exception
	{
		return dao.getAccountAdmin(adminid);
	}
	
	@Override
	public void updateLastInfo(HashMap map)throws Exception
	{
		dao.updateLastInfo(map);
	}
	
	@Override
	public int countAdminID(String aminid) throws Exception
	{
		return dao.countAdminID(aminid);
	}
	
	@Override
	public void ChangeMacAddress(HashMap map) throws Exception
	{
		dao.ChangeMacAddress(map);
	}
	
	@Override
	public AccountVO getAccountByUid(String uid) throws Exception
	{
		return dao.getAccountByUid(uid);
	}
	
	@Override
	public ContentAuthorityVO getContentAuthority(String admin_id) throws Exception
	{
		return dao.getContentAuthority(admin_id);
	}
	
	@Override
	public void insertContentAuthority(String admin_id) throws Exception
	{
		dao.insertContentAuthority(admin_id);
	}
	
}
