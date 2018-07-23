package com.crc.CSP;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.crc.CSP.bean.AuthTokenVO;
import com.crc.CSP.service.AuthTokenService;

@RestController
public class EvalResult 
{
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	private static final long session_time = 600000;
	
	@Autowired
	private AuthTokenService authtoken_service;
	
	// 저장된 인증키가 있는지, 시간은 만료되었는지 체크
	private AuthTokenVO ValidAuthToken(String auth_token) throws Exception
	{
		AuthTokenVO atvo;
		
		if(auth_token == null || auth_token == "") {
			atvo = null;
			return atvo;
		}
		
		// db 에 저장된 값이 있는지 확인
		atvo = authtoken_service.getAuthToken(auth_token);
		
		if(atvo != null) {
			// 인증토큰의 갱신 시간이 특정 분 보다 지났다면 return false
			// 만료된 토큰은 삭제
			System.out.println("현재시간: " + System.currentTimeMillis());
			System.out.println("생성된 시간: " + atvo.getTimeValid());
			// if 현재시간 - 설정된 시간 > 설정시간: 토큰 삭제
			//atvo = null;
			long cur_time = System.currentTimeMillis();
			long prev_time = Long.parseLong(atvo.getTimeValid());
			long elapsed_time = cur_time - prev_time;
			System.out.println("흘러간 시간: " + elapsed_time);
			
			// 세션만료
			if(elapsed_time > session_time) {
				atvo = null;
			}
		}
		return atvo;
	}
	
	// id 가 어드민 계정인지 확인
	@PostMapping(value = "/restful/EvalResult")
	public ResponseEntity<Object> EvalResult(@RequestParam("uid")String uid, @RequestParam("eval_res_data")String eval_res_data) throws Exception 
	{
		AuthTokenVO atvo = ValidAuthToken(uid);
		
		if(atvo == null) {
			return new ResponseEntity<Object>("session expiration", HttpStatus.OK);
		}
		
		return new ResponseEntity<Object>(null, HttpStatus.OK);
	}
	
	
}
