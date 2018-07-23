package com.crc.CSP;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.crc.CSP.bean.AccountVO;
import com.crc.CSP.bean.AuthTokenVO;
import com.crc.CSP.bean.ContentAuthorityVO;
import com.crc.CSP.bean.UserBean;
import com.crc.CSP.service.AccountService;
import com.crc.CSP.service.AuthTokenService;
import com.crc.CSP.service.UserService;

@RestController
public class UserRestController 
{
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	private static final long session_time = 600000;
	
	@Autowired
	private UserService user_service;

	@Autowired
	private AccountService account_service;

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

	
	// 웹브라우저가 아닌 네이티브 클라이언트 로그인용.
	@PostMapping(value = "/restful/csplogin")
	public ResponseEntity<Object> csplogin(HttpSession session, 
			@RequestParam("id")String id,  @RequestParam("pass")String pass) throws Exception 
	{	
		System.out.println("생성된 세션: " + session.getId());
		// 생성된 세션은 값과 시간은 db에 저장하고 인증키로 활용한다.
		
		// 결과 정보 map
		HashMap<String, Object>res_map = new HashMap<String, Object>();
		
		UserBean user_res;
		
		user_res = user_service.user(id);
		
		/*if(user_res != null)
		{
			BCryptPasswordEncoder encode = new BCryptPasswordEncoder();
			boolean res_v = encode.matches(pass, user_res.getPASSWORD());
			if(res_v)
			{
				return new ResponseEntity<Object>(user_res, HttpStatus.OK); // json 키, 값이 소문자로 보내진다.
			}
			return new ResponseEntity<Object>(null, HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<Object>(null, HttpStatus.OK);
		}*/
		
		if(user_res != null)
		{
			if(user_res.getPASSWORD().equals(pass)) 
			{	
				AccountVO account;
				account = account_service.getAccount(user_res.getACCOUNT_ID());
				if(account != null) 
				{
					// lastLogin 이 현재 이시점으로 다시 갱신하고 결과를 클라라인트에 보내줌.
					SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					Calendar cal = Calendar.getInstance();
					String today = null;
					today = date.format(cal.getTime());
					Timestamp ts = Timestamp.valueOf(today);
					String account_id = user_res.getACCOUNT_ID();
					
					String token_v = session.getId();
					long token_create_time = session.getCreationTime();
					
					HashMap<String, Object>map = new HashMap<String, Object>();
					map.put("ID", account_id);
					map.put("LastLogin", ts);
					map.put("AuthToken", token_v);
					account_service.updateLastInfo(map);
					
					account.setLastLogin(ts.toString());
					
					HashMap<String, Object>token_map = new HashMap<String, Object>();
					token_map.put("Token", token_v);
					token_map.put("TimeValid", token_create_time);
					token_map.put("UserId", user_res.getID());
					authtoken_service.updateAuthToken(token_map);
					
					res_map.put("lastLoginTime", today);
					res_map.put("uid", token_v);
					res_map.put("macAddress", account.getMacAddress());
					res_map.put("userLevel", user_res.getUSERTYPE());
					
					return new ResponseEntity<Object>(res_map, HttpStatus.OK); // json 키, 값이 소문자로 보내진다.
				}
			}
			else
			{
				// 패스워드 에러
				res_map.put("Error", "PassError");
				return new ResponseEntity<Object>(res_map, HttpStatus.OK); // json 키, 값이 소문자로 보내진다.
			}
		}
		else
		{
			// 유저가 존재하지않음
			res_map.put("Error", "NotUser");
			return new ResponseEntity<Object>(res_map, HttpStatus.OK); // json 키, 값이 소문자로 보내진다.
		}
		
		return new ResponseEntity<Object>(null, HttpStatus.OK); // json 키, 값이 소문자로 보내진다.
	}
	
	
	// id 가 어드민 계정인지 확인
	@PostMapping(value = "/restful/CheckAdmin")
	public ResponseEntity<Object> CheckAdmin(@RequestParam("authtoken")String authtoken) throws Exception 
	{
		String user_id;
		AuthTokenVO atvo = ValidAuthToken(authtoken);
		
		if(atvo == null) {
			return new ResponseEntity<Object>("session expiration", HttpStatus.OK);
		}
		else {
			user_id = atvo.getUserId();
		}
		
		UserBean user_res;
		user_res = user_service.user(user_id);
		if(user_res != null)
		{
			if(user_res.getAUTHORITY().equals("ROLE_ADMIN")) 
			{	
				return new ResponseEntity<Object>("admin", HttpStatus.OK);
			}
		}
		return new ResponseEntity<Object>("noadmin", HttpStatus.OK);
	}
	
	
	// mac address 변경 
	@PostMapping(value="/restful/ChangeMacAddress")
	public ResponseEntity<Object> ChangeMacAddress(@RequestParam("uid")String uid, 
			@RequestParam("newMacAddress")String newMacAddress) throws Exception
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
		map.put("id", user_id);
		map.put("newMacAddress", newMacAddress);
		
		account_service.ChangeMacAddress(map);
		AccountVO accountvo = account_service.getAccountAdmin(user_id);
		System.out.println(">>>" + accountvo);
		return new ResponseEntity<Object>(accountvo, HttpStatus.OK);
	}
	
	// 유저정보
	@PostMapping(value="/restful/UserInfo")
	public ResponseEntity<Object> UserInfo(@RequestParam("uid")String uid) throws Exception
	{
		String user_id;
		AuthTokenVO atvo = ValidAuthToken(uid);
		
		if(atvo == null) {
			return new ResponseEntity<Object>("session expiration", HttpStatus.OK);
		}
		else {
			user_id = atvo.getUserId();
		}
		
		UserBean user_res = user_service.user(user_id);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("protocall", "user_info");
		map.put("user_res", user_res);
		
		return new ResponseEntity<Object>(map, HttpStatus.OK);
	}
	
	// 유저가 해당 컨텐츠를 이용할 권리가 있는지 확인
	@PostMapping(value="/restful/content_auth")
	public ResponseEntity<Object> content_auth(@RequestParam("uid")String uid) throws Exception
	{
		AuthTokenVO atvo = ValidAuthToken(uid);
		if(atvo == null) {
			return new ResponseEntity<Object>("session expiration", HttpStatus.OK);
		}
		
		HashMap<String, Object> res_map = new HashMap<String, Object>();
		
		AccountVO accountvo = account_service.getAccountByUid(uid);
		
		if(accountvo == null)
		{
			res_map.put("Error", "Invalid uid");
			return new ResponseEntity<Object>(res_map, HttpStatus.OK);
		}
		else 
		{
			String admin_id = accountvo.getAdminID();
			ContentAuthorityVO cav = account_service.getContentAuthority(admin_id);
			if(cav == null) {
				// 권한에 대한 정보가 없다면 디폴트정보를 넣어준다. 디폴트정보는 모든 컨텐츠 이용가능.
				System.out.println("컨텐츠 정보없음. 디폴트 정보로 채움실시");
				account_service.insertContentAuthority(admin_id);
				res_map.put("user_id", admin_id);
				res_map.put("tsa", 1);
				res_map.put("os", 1);
				return new ResponseEntity<Object>(res_map, HttpStatus.OK);
			}
			res_map.put("user_id", admin_id);
			res_map.put("tsa", cav.getTsa());
			res_map.put("os", cav.getOs());
			return new ResponseEntity<Object>(res_map, HttpStatus.OK);
		}
	}
		
	// 혼밥 네이티비앱 로그인
	@PostMapping(value = "/restful/honboblogin")
	public ResponseEntity<Object> honboblogin(HttpSession session, 
			@RequestParam("id")String id,  @RequestParam("pass")String pass) throws Exception 
	{	
		System.out.println("생성된 세션: " + session.getId());
		// 생성된 세션은 값과 시간은 db에 저장하고 인증키로 활용한다.
		
		// 결과 정보 map
		HashMap<String, Object>res_map = new HashMap<String, Object>();
		UserBean user_res;
		user_res = user_service.user(id);
	
		if(user_res != null)
		{
			if(user_res.getPASSWORD().equals(pass)) 
			{	
				String token_v = session.getId();
				long token_create_time = session.getCreationTime();
				
				HashMap<String, Object>token_map = new HashMap<String, Object>();
				token_map.put("Token", token_v);
				token_map.put("TimeValid", token_create_time);
				token_map.put("UserId", user_res.getID());
				authtoken_service.updateAuthToken(token_map);
				res_map.put("uid", token_v);
				
				return new ResponseEntity<Object>(res_map, HttpStatus.OK); // json 키, 값이 소문자로 보내진다.
			}
			else
			{
				// 패스워드 에러
				res_map.put("Error", "PassError");
				return new ResponseEntity<Object>(res_map, HttpStatus.OK); // json 키, 값이 소문자로 보내진다.
			}
		}
		else
		{
			// 유저가 존재하지않음
			res_map.put("Error", "NotUser");
			return new ResponseEntity<Object>(res_map, HttpStatus.OK); // json 키, 값이 소문자로 보내진다.
		}
	}
	
	// honbob app 에서 회원등록 처리 함수
	@Transactional
	@PostMapping(value = "/restful/FreeRegistMember")
	public ResponseEntity<Object> FreeRegistMember(@RequestParam("id")String id, @RequestParam("password")String password,
			@RequestParam("phone_number")String phone_number) throws Exception
	{	
		System.out.println("신규 ID 등록");

		HashMap<String, Object>userinfo = new HashMap<String, Object>();
		userinfo.put("id", id);
		userinfo.put("password", password);
		userinfo.put("enabled", "1");
		userinfo.put("authority", "ROLE_USER");
		userinfo.put("userName", id);
		user_service.user_regist(userinfo);
		
		HashMap<String, Object>param1 = new HashMap<String, Object>();
		param1.put("user_id", id);
		param1.put("phone_number", phone_number);
		user_service.userPhoneNumberRegist(param1);
		
		return new ResponseEntity<Object>("member regist success", HttpStatus.OK);
	}
	
	// push 용 token 업데이트 처리
	@Transactional
	@PostMapping(value = "/restful/PushTokenUpdate")
	public ResponseEntity<Object> PushTokenUpdate(@RequestParam("uid")String uid, @RequestParam("push_token")String push_token) throws Exception
	{
		AuthTokenVO atvo = ValidAuthToken(uid);
		if(atvo == null) {
			return new ResponseEntity<Object>("session expiration", HttpStatus.OK);
		}
		
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("user_id", atvo.getUserId());
		param.put("push_token", push_token);
		user_service.pushTokenUpdate(param);
		
		HashMap<String, Object> res_map = new HashMap<String, Object>();
		res_map.put("protocall", "push_token_update");
		res_map.put("res_msg", "push token updated");
		
		return new ResponseEntity<Object>(res_map, HttpStatus.OK);
	}
	
	
	
}
