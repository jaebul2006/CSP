package com.crc.CSP;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.stereotype.Controller;

@Controller
public class SessionListener implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent arg0)
	{
		HttpSession session = arg0.getSession();
		long time = session.getCreationTime();
		String id = session.getId();
		System.out.println(time + "俊 积己等 技记" + id);
	}
	
	@Override
	public void sessionDestroyed(HttpSessionEvent arg0)
	{
		HttpSession session = arg0.getSession();
		long last_time = session.getLastAccessedTime();
		long now_time = System.currentTimeMillis();
		String id = session.getId();
		System.out.println((now_time - last_time) + "ms 父俊 技记 父丰" + id);
		session.invalidate();
	}
	
}
