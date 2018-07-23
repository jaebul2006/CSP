package com.crc.CSP;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.crc.CSP.bean.AuthTokenVO;
import com.crc.CSP.service.AuthTokenService;
import com.crc.CSP.service.ResultEvaluationService;

@RestController
public class ResultEvaluationRestController {

	private static final long session_time = 600000;
	
	@Autowired
	private AuthTokenService authtoken_service;
	
	// ����� ����Ű�� �ִ���, �ð��� ����Ǿ����� üũ
	private AuthTokenVO ValidAuthToken(String auth_token) throws Exception
	{
		AuthTokenVO atvo;
		
		if(auth_token == null || auth_token == "") {
			atvo = null;
			return atvo;
		}
		
		// db �� ����� ���� �ִ��� Ȯ��
		atvo = authtoken_service.getAuthToken(auth_token);
		
		if(atvo != null) {
			// ������ū�� ���� �ð��� Ư�� �� ���� �����ٸ� return false
			// ����� ��ū�� ����
			System.out.println("����ð�: " + System.currentTimeMillis());
			System.out.println("������ �ð�: " + atvo.getTimeValid());
			// if ����ð� - ������ �ð� > �����ð�: ��ū ����
			//atvo = null;
			long cur_time = System.currentTimeMillis();
			long prev_time = Long.parseLong(atvo.getTimeValid());
			long elapsed_time = cur_time - prev_time;
			System.out.println("�귯�� �ð�: " + elapsed_time);
			
			// ���Ǹ���
			if(elapsed_time > session_time) {
				atvo = null;
			}
		}
		return atvo;
	}
	
	@Autowired
	private ResultEvaluationService result_evaluation_service;
	
	// �ѹ��� ��������� ����
	@PostMapping(value="/restful/upload_result_evaluation")
	public ResponseEntity<Object> upload_result_evaluation(@RequestParam("uid")String uid, @RequestParam("oper_type")String oper_type,
			@RequestParam("total_score")String total_score, @RequestParam("middle_score1")String middle_score1, 
			@RequestParam("middle_score2")String middle_score2, @RequestParam("middle_score3")String middle_score3) throws Exception
	{
		String user_id;
		AuthTokenVO atvo = ValidAuthToken(uid);
		if(atvo == null) {
			return new ResponseEntity<Object>("session expiration", HttpStatus.OK);
		}
		else {
			user_id = atvo.getUserId();
		}
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Calendar cal = Calendar.getInstance();
		String today = null;
		today = date.format(cal.getTime());
		Timestamp ts = Timestamp.valueOf(today);
		map.put("upload_date", ts);
		map.put("content_type", oper_type);
		map.put("total_score", total_score);
		map.put("middle_score1", middle_score1);
		map.put("middle_score2", middle_score2);
		map.put("middle_score3", middle_score3);
		map.put("username", user_id);
		result_evaluation_service.insert(map);
		
		return new ResponseEntity<Object>("finish", HttpStatus.OK);
	}
	
}
