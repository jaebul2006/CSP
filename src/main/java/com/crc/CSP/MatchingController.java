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

	// ��ħ, ����, ������ �������� ��Ī�� ����Ʈ��
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
	
	// ����� �����. ��Ī ���̺� �ϳ��� ��ϵǰ� ������ ���̺� �ڽ��� ������ ��ϵȴ�. 
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
		// �Ķ���ͷ� max_person �� �޾ƿ��� ������ �����ο��� �ִ��ο��� ������ üũ�Ҷ� Ŭ���̾�Ʈ���� �̹̾˰� �ִ� ������ ���� �� matching_tbl ���� �˻��ؿ��� ���ϸ� ���̱� ���ؼ��̴�.
		AuthTokenVO atvo = ValidAuthToken(uid);
		
		if(atvo == null) {
			return new ResponseEntity<Object>("session expiration", HttpStatus.OK);
		}
		
		// ���ο�û�� GoingAppointment �� �ִ´�
		// goint_appointment �� record�� �߰��Ѵ�.
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
		
		// goint_apoointment ���̺��� �Ķ���ͷ� ����� matching_tbl_id�� ���� ����� ã�´�.
		List<GoingAppointmentVO> list = matching_service.findMatchingIdFromGoingAppointment(matching_tbl_id);
		
		// ���� ��û�� ����� matching_tbl �� max_person �� list�� ������ ���ų� (�� ���� ���� �� ������) list�� ũ�ٸ� ����� �Ϸ�Ȱ����� ����
		if(list.size() >= max_person) {
			// �ش� ��Ī id �� ���� ����鿡�� push msg �� ������
			FCMPushMsg(list);
			
			// ���� ��Ƽ�� �Ϸ�Ǿ���. ������ matching_tbl_id�� ���� matching_tbl�� ��Ȱ��ȭ �Ѵ�.
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("matching_tbl_id", matching_tbl_id);
			map.put("cur_person", max_person);
			matching_service.disableMatchingTbl(map);
			// ���� ��Ƽ�� �Ϸ�Ǿ���. ������ matching_tbl_id�� ���� goint_apointment�� ��Ȱ��ȭ �Ѵ�.
			matching_service.disableGoingAppointment(matching_tbl_id);
		}
		else {
			// matching_tbl cur_person ���� ������Ű�� ������Ʈ�� �����Ѵ�.
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("matching_tbl_id", matching_tbl_id);
			map.put("cur_person", list.size());
			matching_service.matchingAddCurPerson(map);
		}
		
		return new ResponseEntity<Object>(null, HttpStatus.OK);
	}
	
	
	// ������ ����� ��� Ȥ�� �������� ��� ����� �����ϴ� �Լ�
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
	
	// ����غ�Ϸ�� ����� �����ϴ� �Լ�
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
	
	// ����غ� �Ϸ�� ����� ����� ����(��ȭ��ȣ)�� ����
	@PostMapping(value = "/restful/finishAppointmentMember")
	public ResponseEntity<Object> finishAppointmentMember(@RequestParam("uid")String uid, @RequestParam("matching_id")String matching_id) throws Exception
	{
		AuthTokenVO atvo = ValidAuthToken(uid);
		if(atvo == null) {
			return new ResponseEntity<Object>("session expiration", HttpStatus.OK);
		}
		
		List<GoingAppointmentVO> gavo_list = matching_service.finishMatchingMember(matching_id);
		
		// �� ������� user_id �� ������ user_phone_number VO ����Ʈ�� �����´�
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
	
	// �ش� ��Ī id �� ������ ������� push token�� ������
	private void FCMPushMsg(List<GoingAppointmentVO> list) throws Exception
	{
		// �ش� ����� ����Ʈ��������
		//List<GoingAppointmentVO> list = matching_service.findMatchingIdFromGoingAppointment(matching_tbl_id);
		//List<String>user_id_list = new ArrayList<String>();
		//List<String>user_token_list = new ArrayList<String>();
		/*
		for(int i=0; i<list.size(); i++) {
			user_id_list.add(list.get(i).getUser_id());
		}
		*/
		System.out.println("����� ���� ����: " + list.size());
		
		
		// �ش� ������� ��ū��������
		List<String> user_token_list = new ArrayList<String>();
		for(int i=0; i<list.size(); i++) {
			String user_id = list.get(i).getUser_id();
			String user_token = matching_service.getPushToken(user_id);
			//user_token_list.add(user_token);
			System.out.println("���� Ǫ�� ��ū : " + user_token);
			// ��ū�� ���� FCM ������ ��û�ϴ� �κп� ��ū �����ֱ�
			user_token_list.add(user_token);
		}
		
		String json_user_tokens = new Gson().toJson(user_token_list);
		sendPushToken(json_user_tokens);
	}
	
	// fcm ������ Ǫ�� �޽����� ��û�ϴ� �Լ�
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
		
		//String input = "{\"notification\" : {\"title\" : \"��� �Ϸ� \", \"body\" : \"����� �������ϴ�!\"}, \"to\":\"" + user_token + "\"}";
		String input = "{\"notification\" : {\"title\" : \"��� �Ϸ� \", \"body\" : \"����� �������ϴ�!\"}, \"registration_ids\":" + json_user_tokens + "}";
		
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
