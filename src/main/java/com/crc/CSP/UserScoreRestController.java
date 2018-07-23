package com.crc.CSP;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.crc.CSP.bean.UserScoreBean;
import com.crc.CSP.service.UserScoreService;

@RestController
public class UserScoreRestController 
{
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private UserScoreService userscore_service;
	
	// 해당 유저의 해당 콘텐츠 를 db에 저장. 
	@PostMapping(value = "/update_userscore")
	public String update_userscore(@RequestParam("id")String id, @RequestParam("content")String content,
			@RequestParam("score")String score) throws Exception
	{
		String user_name = "";
		user_name = id;
		String content_name = "";
		content_name = content;
		String score_point = "";
		score_point = score;
		
		logger.info("user_name=>{}", user_name);
		logger.info("content_name=>{}", content_name);
		logger.info("score_point=>{}", score_point);
		
		HashMap<String, String>user_info = new HashMap<String, String>();
		user_info.put("id", id);
		user_info.put("content", content);
		Calendar cd = Calendar.getInstance();
		int i_year = cd.get(Calendar.YEAR);
		int i_month = cd.get(Calendar.MONTH) + 1; // 월은 0부터 시작해서 1더해줌.
		int i_day = cd.get(Calendar.DAY_OF_MONTH);
		String date = i_year + "-" + i_month + "-" + i_day;
		user_info.put("date", date);
		int i_hour = cd.get(Calendar.HOUR_OF_DAY);
		int i_min = cd.get(Calendar.MINUTE);
		int i_sec = cd.get(Calendar.SECOND);
		String time = i_hour + ":" + i_min + ":" + i_sec;
		user_info.put("time", time);
		user_info.put("score", score);
		
		logger.info("user_info{}", user_info);
		
		userscore_service.update_userscore(user_info);
		
		return HttpStatus.OK.toString();
	}
	
	@PostMapping(value = "/userscore")
	public ResponseEntity<Object> userscore(@RequestParam("id")String id, @RequestParam("content")String content, 
			@RequestParam("sd")String sd, @RequestParam("ed")String ed) throws Exception
	{
		HashMap<String, String>user_info = new HashMap<String, String>();
		user_info.put("id", id);
		user_info.put("content", content);
		// 시작날짜, 끝날짜 변수get
		String start_date = sd;
		String end_date = ed;
		user_info.put("FROM_DT", start_date);
		user_info.put("TO_DT", end_date);
		
		List<UserScoreBean> usb_list;
		usb_list = userscore_service.userscore(user_info);
		
		logger.info("usblist=>{}", usb_list);
		
		return new ResponseEntity<Object>(usb_list, HttpStatus.OK);
	}


}
