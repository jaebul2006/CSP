package com.crc.CSP.service;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.crc.CSP.DAO.UserScoreDAO;
import com.crc.CSP.bean.UserScoreBean;

@Service
public class UserScoreServiceImpl implements UserScoreService{

	@Inject
	private UserScoreDAO dao;
	
	@Override 
	public void update_userscore(HashMap userinfo) throws Exception{
		dao.update_userscore(userinfo);
	}
	
	@Override
	public List<UserScoreBean> userscore(HashMap userinfo) throws Exception{
		return dao.userscore(userinfo);
	}
	
	
	
}
