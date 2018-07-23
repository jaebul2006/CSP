package com.crc.CSP;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLConnection;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.crc.CSP.bean.AuthTokenVO;
import com.crc.CSP.bean.RawOSVO;
import com.crc.CSP.bean.RawTSAVO;
import com.crc.CSP.service.AuthTokenService;
import com.crc.CSP.service.RawDataService;

@RestController
public class RawDataController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	@Resource(name="uploadPath")
	String uploadPath;
	
	private static final long session_time = 600000;
	
	@Autowired
	private AuthTokenService authtoken_service;
	
	@Autowired
	private RawDataService rawdata_service;
	
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
	
	
	@RequestMapping(value="/restful/upload_tsa_raw", method=RequestMethod.POST, produces="text/plain;charset=utf-8")
	public ResponseEntity<Object> upload_tsa_raw(@RequestParam("uid")String uid, MultipartFile file) throws Exception
	{
		AuthTokenVO atvo = ValidAuthToken(uid);
		if(atvo == null) {
			return new ResponseEntity<Object>("session expiration", HttpStatus.OK);
		}
		
		System.out.println(file);
		
		logger.info("originalName : " + file.getOriginalFilename());
		logger.info("size : " + file.getSize());
		logger.info("contentType : " + file.getContentType());
		
		// db 에 로우데이터 정보 저장
		String user_id = atvo.getUserId();
		
		String file_name = UploadFileUtils.uploadFile(uploadPath, file.getOriginalFilename(), file.getBytes());
		HashMap<String, Object>map = new HashMap<String, Object>();
		map.put("user_id", user_id);
		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Calendar cal = Calendar.getInstance();
		String today = null;
		today = date.format(cal.getTime());
		Timestamp ts = Timestamp.valueOf(today);
		map.put("reg_date", ts);
		map.put("file_name", file_name);
		
		rawdata_service.insertTSA(map);
		
		return new ResponseEntity<Object>(map, HttpStatus.OK);
	}
	
	
	// tsa 목록을 가져옴
	@PostMapping(value = "/restful/tsa_raw_list")
	public ResponseEntity<Object> tsa_raw_list(@RequestParam("uid")String uid) throws Exception
	{
		AuthTokenVO atvo = ValidAuthToken(uid);
		if(atvo == null) {
			return new ResponseEntity<Object>("session expiration", HttpStatus.OK);
		}
		
		List<RawTSAVO>tsa_list = rawdata_service.getTSA(atvo.getUserId());
		if(tsa_list.isEmpty()) {
			return new ResponseEntity<Object>("no data", HttpStatus.OK);
		}
		return new ResponseEntity<Object>(tsa_list, HttpStatus.OK);
	}
	
	
	@PostMapping(value = "/restful/tsa_raw_download")
	public ResponseEntity<Object> tsa_raw_download(HttpServletResponse response, @RequestParam("uid")String uid, @RequestParam("tsa_raw_id")String tsa_raw_id) throws Exception
	{
		String file_name = rawdata_service.getTSAFilename(tsa_raw_id);
		
		String full_filepath = "";
		if(file_name != null) {
			full_filepath = uploadPath + file_name;
		}
		
		File file = null;
		file = new File(full_filepath);
		
		if(!file.exists()) {
			System.out.println("파일이 존재하지 않음");
		}
		
		String mime_type = URLConnection.guessContentTypeFromName(file.getName());
		if(mime_type == null) {
			System.out.println("mimetype is not detectable, will take default");
			mime_type = "application/octet-stream";
		}
		
		System.out.println("mimetype : " + mime_type);
		
		response.setContentType(mime_type);
		response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() + "\""));
		response.setContentLength((int)file.length());
		InputStream input_stream = new BufferedInputStream(new FileInputStream(file));
		FileCopyUtils.copy(input_stream, response.getOutputStream());
		
		return new ResponseEntity<Object>(null, HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/restful/upload_os_raw", method=RequestMethod.POST, produces="text/plain;charset=utf-8")
	public ResponseEntity<Object> upload_os_raw(@RequestParam("uid")String uid, MultipartFile file) throws Exception
	{
		AuthTokenVO atvo = ValidAuthToken(uid);
		if(atvo == null) {
			return new ResponseEntity<Object>("session expiration", HttpStatus.OK);
		}
		
		System.out.println(file);
		
		logger.info("originalName : " + file.getOriginalFilename());
		logger.info("size : " + file.getSize());
		logger.info("contentType : " + file.getContentType());
		
		// db 에 로우데이터 정보 저장
		String user_id = atvo.getUserId();
		
		String file_name = UploadFileUtils.uploadFile(uploadPath, file.getOriginalFilename(), file.getBytes());
		HashMap<String, Object>map = new HashMap<String, Object>();
		map.put("user_id", user_id);
		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Calendar cal = Calendar.getInstance();
		String today = null;
		today = date.format(cal.getTime());
		Timestamp ts = Timestamp.valueOf(today);
		map.put("reg_date", ts);
		map.put("file_name", file_name);
		
		rawdata_service.insertOS(map);
		
		return new ResponseEntity<Object>(map, HttpStatus.OK);
	}
	
	
	// os 목록을 가져옴
	@PostMapping(value = "/restful/os_raw_list")
	public ResponseEntity<Object> os_raw_list(@RequestParam("uid")String uid) throws Exception
	{
		AuthTokenVO atvo = ValidAuthToken(uid);
		if(atvo == null) {
			return new ResponseEntity<Object>("session expiration", HttpStatus.OK);
		}
		
		List<RawOSVO>os_list = rawdata_service.getOS(atvo.getUserId());
		if(os_list.isEmpty()) {
			return new ResponseEntity<Object>("no data", HttpStatus.OK);
		}
		return new ResponseEntity<Object>(os_list, HttpStatus.OK);
	}
	
	
	@PostMapping(value = "/restful/os_raw_download")
	public ResponseEntity<Object> os_raw_download(HttpServletResponse response, @RequestParam("uid")String uid, @RequestParam("os_raw_id")String os_raw_id) throws Exception
	{
		String file_name = rawdata_service.getTSAFilename(os_raw_id);
		
		String full_filepath = "";
		if(file_name != null) {
			full_filepath = uploadPath + file_name;
		}
		
		File file = null;
		file = new File(full_filepath);
		
		if(!file.exists()) {
			System.out.println("파일이 존재하지 않음");
		}
		
		String mime_type = URLConnection.guessContentTypeFromName(file.getName());
		if(mime_type == null) {
			System.out.println("mimetype is not detectable, will take default");
			mime_type = "application/octet-stream";
		}
		
		System.out.println("mimetype : " + mime_type);
		
		response.setContentType(mime_type);
		response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() + "\""));
		response.setContentLength((int)file.length());
		InputStream input_stream = new BufferedInputStream(new FileInputStream(file));
		FileCopyUtils.copy(input_stream, response.getOutputStream());
		
		return new ResponseEntity<Object>(null, HttpStatus.OK);
	}
	
}
