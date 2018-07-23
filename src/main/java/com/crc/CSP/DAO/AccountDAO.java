package com.crc.CSP.DAO;

import java.util.HashMap;

import com.crc.CSP.bean.AccountVO;
import com.crc.CSP.bean.ContentAuthorityVO;

public interface AccountDAO 
{
	public void regist(AccountVO vo) throws Exception;
	public AccountVO getAccount(String id) throws Exception;
	public AccountVO getAccountAdmin(String adminid) throws Exception;
	public void updateLastInfo(HashMap map) throws Exception;
	public int countAdminID(String aminid) throws Exception;
	public void ChangeMacAddress(HashMap map) throws Exception;
	public AccountVO getAccountByUid(String uid) throws Exception;
	public ContentAuthorityVO getContentAuthority(String admin_id) throws Exception;
	public void insertContentAuthority(String admin_id) throws Exception;
}
