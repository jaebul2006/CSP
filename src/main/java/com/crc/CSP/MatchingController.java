package com.crc.CSP;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.crc.CSP.bean.AuthTokenVO;
import com.crc.CSP.bean.GoingAppointmentVO;
import com.crc.CSP.bean.MatchingVO;
import com.crc.CSP.service.AuthTokenService;
import com.crc.CSP.service.MatchingService;
import com.crc.CSP.service.UserService;
import com.google.gson.Gson;

@RestController
public class MatchingController {

	private static final long session_time = 600000;
	
	@Autowired
	private AuthTokenService authtoken_service;
	@Autowired
	private MatchingService matching_service;
	@Autowired
	private UserService user_service;
	
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

	// 아침, 점심, 저녁의 영역으로 매칭을 리스트업
	@PostMapping(value = "/restful/getMatching")
	public ResponseEntity<Object> getMatching(@RequestParam("uid")String uid, @RequestParam("app_date")String app_date,
			@RequestParam("time_region")String time_region) throws Exception
	{
		AuthTokenVO atvo = ValidAuthToken(uid);
		
		if(atvo == null) {
			return new ResponseEntity<Object>("session expiration", HttpStatus.OK);
		}
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("app_date", app_date);
		String enc_time_region = new String(time_region.getBytes("8859_1"), "UTF-8");
		map.put("time_region", enc_time_region);
		List<MatchingVO> list_matching = matching_service.getMatching(map);
		
		if(list_matching.isEmpty()) {
			return new ResponseEntity<Object>(null, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<Object>(list_matching, HttpStatus.OK);
		}
	}
	
	// 약속을 등록함. 매칭 테이블에 하나가 등록되고 진행약속 테이블에 자신의 정보가 등록된다. 
	@Transactional
	@PostMapping(value = "/restful/regMatching")
	public ResponseEntity<Object> regMatching(@RequestParam("uid")String uid, @RequestParam("max_person")String max_person, 
			@RequestParam("app_date")String app_date,
			@RequestParam("time_want")String time_want,
			@RequestParam("time_region")String time_region,
			@RequestParam("latitude")String latitude, @RequestParam("longitude")String longitude,
			@RequestParam("loc_address")String loc_address) throws Exception
	{
		AuthTokenVO atvo = ValidAuthToken(uid);
		
		if(atvo == null) {
			return new ResponseEntity<Object>("session expiration", HttpStatus.OK);
		}
		
		String enc_time_region = new String(time_region.getBytes("8859_1"), "UTF-8");
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("id", UUID.randomUUID().toString());
		map.put("max_person", max_person);
		map.put("cur_person", 1);
		map.put("enable", 1);
		map.put("app_date", app_date);
		map.put("time_want", time_want);
		map.put("time_region", enc_time_region);
		map.put("user_id", atvo.getUserId());
		map.put("is_host", 1);
		matching_service.regMatching(map);
		
		HashMap<String, Object> param1 = new HashMap<String, Object>();
		param1.put("id", UUID.randomUUID().toString());
		param1.put("matching_tbl_id", map.get("id"));
		param1.put("user_id", atvo.getUserId());
		param1.put("finish", 0);
		param1.put("app_date", app_date);
		param1.put("time_want", time_want);
		param1.put("time_region", enc_time_region);
		matching_service.regGoingApp(param1);
		
		HashMap<String, Object> loc_map = new HashMap<String, Object>();
		loc_map.put("matching_tbl_id", map.get("id"));
		loc_map.put("latitude", latitude);
		loc_map.put("longitude", longitude);
		String enc_address = new String(loc_address.getBytes("8859_1"), "UTF-8");
		loc_map.put("loc_address", enc_address);
		matching_service.regLocation(loc_map);
		
		return new ResponseEntity<Object>("regist complete", HttpStatus.OK);
	}
	
	
	@Transactional
	@PostMapping(value = "/restful/joinMatching")
	public ResponseEntity<Object> joinMatching(@RequestParam("uid")String uid, @RequestParam("matching_tbl_id")String matching_tbl_id,
			@RequestParam("max_person")int max_person, 
			@RequestParam("app_date")String app_date, 
			@RequestParam("time_want")String time_want,
			@RequestParam("time_region")String time_region) throws Exception
	{
		// 파라미터로 max_person 을 받아오는 이유는 참여인원관 최대인원이 같은지 체크할때 클라이언트에서 이미알고 있는 내용을 구지 또 matching_tbl 에서 검색해오는 부하를 줄이기 위해서이다.
		AuthTokenVO atvo = ValidAuthToken(uid);
		
		if(atvo == null) {
			return new ResponseEntity<Object>("session expiration", HttpStatus.OK);
		}
		
		// 조인요청을 GoingAppointment 에 넣는다
		// goint_appointment 에 record를 추가한다.
		HashMap<String, Object> param1 = new HashMap<String, Object>();
		param1.put("id", UUID.randomUUID().toString());
		param1.put("matching_tbl_id", matching_tbl_id);
		param1.put("user_id", atvo.getUserId());
		param1.put("finish", 0);
		param1.put("app_date", app_date);
		param1.put("time_want", time_want);
		String enc_time_region = new String(time_region.getBytes("8859_1"), "UTF-8");
		param1.put("time_region", enc_time_region);
		matching_service.addGoingAppointment(param1);
		
		// goint_apoointment 테이블에서 파라미터로 날라온 matching_tbl_id을 가진 목록을 찾는다.
		List<GoingAppointmentVO> list = matching_service.findMatchingIdFromGoingAppointment(matching_tbl_id);
		
		// 조인 요청한 약속의 matching_tbl 의 max_person 과 list의 갯수가 같거나 (이 경우는 나올 수 없지만) list가 크다면 약속이 완료된것으로 판정
		if(list.size() >= max_person) {
			// 해당 매칭 id 를 가진 멤버들에게 push msg 를 날려줌
			FCMPushMsg(list);
			
			// 참여 파티가 완료되었다. 동일한 matching_tbl_id를 가진 matching_tbl을 비활성화 한다.
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("matching_tbl_id", matching_tbl_id);
			map.put("cur_person", max_person);
			matching_service.disableMatchingTbl(map);
			// 참여 파티가 완료되었다. 동일한 matching_tbl_id를 가진 goint_apointment를 비활성화 한다.
			matching_service.disableGoingAppointment(matching_tbl_id);
		}
		else {
			// matching_tbl cur_person 수를 증가시키는 업데이트를 실행한다.
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("matching_tbl_id", matching_tbl_id);
			map.put("cur_person", list.size());
			matching_service.matchingAddCurPerson(map);
		}
		
		return new ResponseEntity<Object>(null, HttpStatus.OK);
	}
	
	
	// 유저가 등록한 약속 혹은 참여중인 약속 목록을 리턴하는 함수
	@PostMapping(value = "/restful/goingAppointment")
	public ResponseEntity<Object> goingAppointment(@RequestParam("uid")String uid) throws Exception
	{
		AuthTokenVO atvo = ValidAuthToken(uid);
		if(atvo == null) {
			return new ResponseEntity<Object>("session expiration", HttpStatus.OK);
		}
		String user_id = atvo.getUserId();
		List<GoingAppointmentVO> list = matching_service.allGoingAppointment(user_id);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("protocall", "going_appointment");
		map.put("list", list);
		return new ResponseEntity<Object>(map, HttpStatus.OK);
	}
	
	// 약속준비완료된 목록을 리턴하는 함수
	@PostMapping(value = "/restful/finishAppointment")
	public ResponseEntity<Object> finishAppointment(@RequestParam("uid")String uid) throws Exception
	{
		AuthTokenVO atvo = ValidAuthToken(uid);
		if(atvo == null) {
			return new ResponseEntity<Object>("session expiration", HttpStatus.OK);
		}
		
		String user_id = atvo.getUserId();
		List<GoingAppointmentVO> list = matching_service.finishAppointment(user_id);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("protocall", "finish_appointment");
		map.put("list", list);
		return new ResponseEntity<Object>(map, HttpStatus.OK);
	}
	
	// 약속준비 완료된 약속의 멤버들 정보(전화번호)를 리턴
	@PostMapping(value = "/restful/finishAppointmentMember")
	public ResponseEntity<Object> finishAppointmentMember(@RequestParam("uid")String uid, @RequestParam("matching_id")String matching_id) throws Exception
	{
		AuthTokenVO atvo = ValidAuthToken(uid);
		if(atvo == null) {
			return new ResponseEntity<Object>("session expiration", HttpStatus.OK);
		}
		
		List<GoingAppointmentVO> gavo_list = matching_service.finishMatchingMember(matching_id);
		
		// 이 멤버들의 user_id 를 가지고 user_phone_number VO 리스트를 가져온다
		List<Object> list = new ArrayList<Object>();
		
		for(int i=0; i<gavo_list.size(); i++) {
			String user_id = gavo_list.get(i).getUser_id();
			String phone_number = matching_service.getPhoneNumber(user_id);
			HashMap<String, Object> d = new HashMap<String, Object>();
			d.put("user_id", user_id);
			d.put("phone_number", phone_number);
			list.add(d);
		}
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("protocall", "finish_appointment_member");
		map.put("list", list);
		return new ResponseEntity<Object>(map, HttpStatus.OK);
	}
	
	// 해당 매칭 id 에 참여한 멤버들의 push token을 가져옴
	private void FCMPushMsg(List<GoingAppointmentVO> list) throws Exception
	{
		// 해당 멤버들 리스트가져오기
		//List<GoingAppointmentVO> list = matching_service.findMatchingIdFromGoingAppointment(matching_tbl_id);
		//List<String>user_id_list = new ArrayList<String>();
		//List<String>user_token_list = new ArrayList<String>();
		/*
		for(int i=0; i<list.size(); i++) {
			user_id_list.add(list.get(i).getUser_id());
		}
		*/
		System.out.println("약속쿠 유저 갯수: " + list.size());
		
		
		// 해당 멤버들의 토큰가져오기
		List<String> user_token_list = new ArrayList<String>();
		for(int i=0; i<list.size(); i++) {
			String user_id = list.get(i).getUser_id();
			String user_token = matching_service.getPushToken(user_id);
			//user_token_list.add(user_token);
			System.out.println("유저 푸시 토큰 : " + user_token);
			// 토큰을 실제 FCM 서버에 요청하는 부분에 토큰 날려주기
			user_token_list.add(user_token);
		}
		
		String json_user_tokens = new Gson().toJson(user_token_list);
		sendPushToken(json_user_tokens);
	}
	
	// fcm 서버에 푸시 메시지를 요청하는 함수
	private void sendPushToken(String json_user_tokens) throws Exception
	{
		final String api_key = "AAAAVkXFyxg:APA91bEB_sgKYUug_Er8gIC7Wli8g9oUR_xSvKa3pA89gVw-A5IJh4JzEkxThtHW6i_2eLuAaJskzYiZHBd2F4el985"
				+ "VDzC3vdmFy6Y9Mm0MjffMTphNZg_Y1daBqcnu0EIawcM5wVohcOgtFmIIxGc2upRFDmLiFg";
		
		URL url = new URL("https://fcm.googleapis.com/fcm/send");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/json");
		conn.setRequestProperty("Authorization", "key=" + api_key);
		conn.setDoOutput(true);
		
		//String input = "{\"notification\" : {\"title\" : \"약속 완료 \", \"body\" : \"약속이 잡혔습니다!\"}, \"to\":\"" + user_token + "\"}";
		String input = "{\"notification\" : {\"title\" : \"약속 완료 \", \"body\" : \"약속이 잡혔습니다!\"}, \"registration_ids\":" + json_user_tokens + "}";
		
		java.io.OutputStream os = conn.getOutputStream();
		os.write(input.getBytes("UTF-8"));
		os.flush();
		os.close();
		
		int res_code = conn.getResponseCode();
		System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + input);
        System.out.println("Response Code : " + res_code);
        
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String input_line;
        StringBuffer response = new StringBuffer();

        while ((input_line = in.readLine()) != null) {
            response.append(input_line);
        }
        in.close();
        System.out.println(response.toString());
	}
	
	
}
