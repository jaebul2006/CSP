package com.crc.CSP.DAO;

import java.util.HashMap;
import java.util.List;

import com.crc.CSP.bean.UserScoreBean;

public interface UserScoreDAO {

	public void update_userscore(HashMap userinfo) throws Exception;
	public List<UserScoreBean> userscore(HashMap userinfo) throws Exception;
	
}
