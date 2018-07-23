package com.crc.CSP.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import com.crc.CSP.bean.UserScoreBean;

@Service
public interface UserScoreService {

	public void update_userscore(HashMap userinfo) throws Exception;
	public List<UserScoreBean> userscore(HashMap userinfo) throws Exception;
	
	
}
