package com.crc.CSP.Utility;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.crc.CSP.HomeController;

public class FeedSuccessHandler implements AuthenticationSuccessHandler{

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) 
			throws IOException, ServletException{
		
		String id = request.getParameter("id");
		logger.info("onAuthenticationSuccess=>{}", id);
		response.sendRedirect("/CSP/login_success");
		
	}
	
}