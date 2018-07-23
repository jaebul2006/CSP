package com.crc.CSP;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.crc.CSP.bean.UserBean;
import com.crc.CSP.service.UserService;

@RestController
public class UserDeleteRestController 
{
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private UserService user_service;
	
	@PostMapping(value = "/group_delete_process")
	public boolean group_delete_process(HttpServletRequest request, @RequestParam("id")String id, @RequestParam("pass")String pass) throws Exception 
	{	
		UserBean user_res;
		user_res = user_service.user(id);
		
		if(user_res != null)
		{
			BCryptPasswordEncoder encode = new BCryptPasswordEncoder();
			boolean res_v = encode.matches(pass, user_res.getPASSWORD());
			if(res_v)
			{
				int v1 = user_service.user_delete(id);
				int v2 = user_service.user_role_delete(id);
				if(v1==1 && v2==1) {
					// 技记昏力
					HttpSession session = request.getSession(false);
					session = request.getSession(false);
					if(session != null) {
						logger.info("start 技记昏力 is {}.", session);
						session.invalidate();
						logger.info("end 技记昏力 is {}.", session);
					}
					for(Cookie cookie: request.getCookies()) {
						cookie.setMaxAge(0);
					}
					return true;
				}
			}
		}
		return false;
	}
}

/*HttpSession session = request.getSession(false);
session = request.getSession(false);
if(session != null) {
	logger.info("start 技记昏力 is {}.", session);
	session.invalidate();
	logger.info("end 技记昏力 is {}.", session);
}
for(Cookie cookie: request.getCookies()) {
	cookie.setMaxAge(0);
}*/
