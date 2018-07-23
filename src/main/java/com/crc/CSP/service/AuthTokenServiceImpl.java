package com.crc.CSP.service;

import java.util.HashMap;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.crc.CSP.DAO.AuthTokenDAO;
import com.crc.CSP.bean.AuthTokenVO;

@Service
public class AuthTokenServiceImpl implements AuthTokenService
{

	@Inject
	AuthTokenDAO dao;

	@Override
	public AuthTokenVO getAuthToken(String authtoken) throws Exception
	{
		return dao.getAuthToken(authtoken);
	}

	@Override
	public void updateAuthToken(HashMap map) throws Exception
	{
		dao.updateAuthToken(map);
	}
	
}
