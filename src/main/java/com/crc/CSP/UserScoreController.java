package com.crc.CSP;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.crc.CSP.bean.UserBean;
import com.crc.CSP.service.UserScoreService;
import com.crc.CSP.service.UserService;

@Controller
public class UserScoreController 
{
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	UserService user_service;
	
	@Autowired
	UserScoreService userscore_service;
	
	
	@RequestMapping(value = "/mypage", method=RequestMethod.GET)
	public String my_page(HttpServletRequest req, Model model) throws Exception
	{
		String user_name = req.getRemoteUser();
		logger.info("유저네임=> {}", user_name);
		if(user_name == null) {
			return "/logoutDo";
		}
		
		UserBean user_bean;
		user_bean = user_service.user(user_name);
		model.addAttribute("user", user_bean);
		
		return "mypage";
	}

}
