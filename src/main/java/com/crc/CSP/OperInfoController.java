package com.crc.CSP;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.crc.CSP.bean.OperInfoVO;
import com.crc.CSP.service.OperInfoService;

@RestController
public class OperInfoController {
	
	private static final Logger logger = LoggerFactory.getLogger(OperInfoController.class);
	
	@Autowired
	private OperInfoService operinfo_service;
	
	// 디폴트로 일주일간의 데이터 -> 최근 10개의 수술결과로 변경. 수술연습의 주기는 불규칙적이므로 항상 그래프로 먼가를 그려주어야하는게 낫다는 판단.
	@RequestMapping(value = "/operinfo")
	public ResponseEntity<Object> operinfo(@RequestParam("id")String id, @RequestParam("content")String content) throws Exception
	{
		HashMap<String, String>oper_info = new HashMap<String, String>();
		oper_info.put("id", id);
		oper_info.put("content", content);
		
		/*
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -7);
		Date before_seven_day = cal.getTime();
		cal.add(Calendar.DAY_OF_MONTH, 7);
		Date after_seven_day = cal.getTime();
		
		String nbefore_seven_day = new SimpleDateFormat("yyyy-MM-dd").format(before_seven_day);
		String nafter_seven_day = new SimpleDateFormat("yyyy-MM-dd").format(after_seven_day);
		
		logger.info("{}", nbefore_seven_day);
		logger.info("{}", nafter_seven_day);
		
		
		// ex) 2018-01-01 의 형태의 포멧이어야한다.
		
		// 시작날짜, 끝날짜 변수get
		String start_date = nbefore_seven_day;
		String end_date = nafter_seven_day;
		oper_info.put("FROM_DT", start_date);
		oper_info.put("TO_DT", end_date);
		*/
		
		List<OperInfoVO> oper_info_list, oper_info_list20;
		oper_info_list = operinfo_service.operinfo(oper_info);
		oper_info_list20 = operinfo_service.operinfo_comp(oper_info);
		
		HashMap<String, String>res_info = new HashMap<String, String>();
		res_info = CalcOperInfo(oper_info_list, oper_info_list20);
		
		return new ResponseEntity<Object>(res_info, HttpStatus.OK);
	}

	
	// 레코드 갯수만큼의 수술 점수, 시간, 성공횟수등의 평균치를 계산.
	private HashMap<String, String> CalcOperInfo(List<OperInfoVO> opervo, List<OperInfoVO> opervo20)
	{
		
		HashMap<String, String>res_info = new HashMap<String, String>();
		
		int total_score = 0;
		int total_time = 0;
		int succ_cnt = 0;
		int notbad_more_cnt = 0;
		
		int len = opervo.size();
		if(len <= 0) {
			return null;
		}
		
		for(int i=0; i<len; i++) {
			total_score += opervo.get(i).getOper_score();
			total_time += opervo.get(i).getOper_time();
			
			if(opervo.get(i).getOper_success().equals("y")) {
				succ_cnt++;
			}
			
			// 70점이상이면 보통.
			if(opervo.get(i).getOper_score() > 70) {
				notbad_more_cnt++;
			}
		}
		
		int aver_score = total_score / len;
		int aver_time = total_time / len;
		aver_time /= 60;
		float aver_succ = (float)succ_cnt / (float)len * 100F;
		int iaver_succ = (int)aver_succ;
		
		
		res_info.put("aver_score", Integer.toString(aver_score));
		res_info.put("aver_time", Integer.toString(aver_time));
		res_info.put("aver_succ", Integer.toString(iaver_succ));
		res_info.put("notbad_more_cnt", Integer.toString(notbad_more_cnt));
		
		int total_score20 = 0;
		int total_time20 = 0;
		int succ_cnt20 = 0;
		int notbad_more_cnt20 = 0;
		
		int len20 = opervo20.size();
		if(len20 <= 0) {
			return null;
		}
		
		for(int i=0; i<len20; i++) {
			total_score20 += opervo20.get(i).getOper_score();
			total_time20 += opervo20.get(i).getOper_time();
			
			if(opervo20.get(i).getOper_success().equals("y")) {
				succ_cnt20++;
			}
			
			if(opervo20.get(i).getOper_score() > 70) {
				notbad_more_cnt20++;
			}
		}
		
		int aver_score20 = total_score20 / len20;
		int aver_time20 = total_time20 / len20;
		aver_time20 /= 60;
		float aver_succ20 = (float)succ_cnt20 / (float)len20 * 100F;
		int iaver_succ20 = (int)aver_succ20;
		
		res_info.put("aver_score20", Integer.toString(aver_score20));
		res_info.put("aver_time20", Integer.toString(aver_time20));
		res_info.put("aver_succ20", Integer.toString(iaver_succ20));
		res_info.put("notbad_more_cnt20", Integer.toString(notbad_more_cnt20));
		
		return res_info;
	}
	
	@RequestMapping(value = "/RecentSurgeryGraph")
	public ResponseEntity<Object> RecentSurgeryGraph(@RequestParam("id")String id, @RequestParam("content")String content) throws Exception
	{
		HashMap<String, String>oper_info = new HashMap<String, String>();
		oper_info.put("id", id);
		oper_info.put("content", content);
				
		List<OperInfoVO> oper_info_list;
		oper_info_list = operinfo_service.operinfo(oper_info);
		
		logger.info("oper_info_list=>{}", oper_info_list);
		
		return new ResponseEntity<Object>(oper_info_list, HttpStatus.OK);
	}
	
}
