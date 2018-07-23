package com.crc.CSP;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.crc.CSP.bean.UserBean;
import com.crc.CSP.service.UserService;

@Controller
public class DashBoard1 {

	private static final Logger logger = LoggerFactory.getLogger(DashBoard1.class);
	
	@Inject
	UserService userService;

	
	@RequestMapping(value="/DashBoard1", method=RequestMethod.GET)
	public String DashBoard1(@RequestParam("id")String user_id) throws Exception
	{
		logger.info("À¯Àúid=>{}", user_id);
		UserBean user_bean;
		user_bean = userService.user(user_id);
		
		if(user_bean.getUSERTYPE().equals("STUDENT")) {
			return "DashBoard1";
		}
		else {
			return "redirect:CoachingBoard/TCoachingBoardListHome";
		}
	}

	
}
