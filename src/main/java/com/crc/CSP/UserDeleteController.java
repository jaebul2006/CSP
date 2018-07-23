package com.crc.CSP;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.crc.CSP.bean.UserBean;
import com.crc.CSP.service.UserService;

@Controller
public class UserDeleteController 
{

	@Autowired
	UserService user_service;
	
	@RequestMapping(value = "/user_delete", method=RequestMethod.GET)
	public void user_delete(HttpServletRequest req, Model model) throws Exception
	{
		String user_name = req.getRemoteUser();
		
		UserBean user_bean;
		user_bean = user_service.user(user_name);
		model.addAttribute("user", user_bean);
	}
	
}
