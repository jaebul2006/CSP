package com.crc.CSP;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.crc.CSP.bean.LicenseVO;
import com.crc.CSP.service.LicenseService;

@RestController
public class LicenseBuyRestController 
{
	private static final Logger logger = LoggerFactory.getLogger(LicenseBuyRestController.class);
	
	@Autowired
	private LicenseService license_service;
	
	@PostMapping(value = "/registLicense")
	public String registLicense(@RequestParam("user_id")String user_id, @RequestParam("content")String content,
			@RequestParam("imp_uid")String imp_uid, @RequestParam("merchant_uid")String merchant_uid, 
			@RequestParam("paid_amount")String paid_amount, @RequestParam("apply_num")String apply_num) throws Exception
	{
		HashMap<String, String>license_map = new HashMap<String, String>();
		license_map.put("user_id", user_id);
		license_map.put("content", content);
		license_map.put("imp_uid", imp_uid);
		license_map.put("merchant_uid", merchant_uid);
		license_map.put("paid_amount", paid_amount);
		license_map.put("apply_num", apply_num);
		license_service.insert_license(license_map);
		return HttpStatus.OK.toString();
	}
	
	@GetMapping(value = "/checkLicense")
	public ResponseEntity<Object> checkLicense(@RequestParam("user_id")String user_id) throws Exception
	{
		List<LicenseVO> license_list;
		license_list = license_service.check_license(user_id);
		return new ResponseEntity<Object>(license_list, HttpStatus.OK);
	}
	
}

