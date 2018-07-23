package com.crc.CSP;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.crc.CSP.bean.EvalTSA;
import com.crc.CSP.service.BoardPager;
import com.crc.CSP.service.ResultEvaluationService;

@Controller
public class ResultEvaluationController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Inject
	ResultEvaluationService evalBoardService;
	
	@RequestMapping("/ResultEvaluationHome")
	public String ResultEvaluationHome(@RequestParam(defaultValue="title")String searchOption, @RequestParam(defaultValue="")String keyword,
						@RequestParam(defaultValue="1")int curPage) throws Exception
	{
		return "ResultEvaluationHome";
	}
	
	@RequestMapping("/ResultEvaluationTSA")
	public ModelAndView ResultEvaluationTSA(@RequestParam(defaultValue="title")String searchOption, @RequestParam(defaultValue="")String keyword,
						@RequestParam(defaultValue="1")int curPage, @RequestParam("content_type")String content_type, @RequestParam("id")String username) throws Exception
	{
		logger.info("username=> {}", username);
		logger.info("content_type=> {}", content_type);
		
		int count = evalBoardService.countOper(searchOption, keyword, content_type, username);
		BoardPager boardPager = new BoardPager(count, curPage);
		int start = boardPager.getPageBegin();
		int end = boardPager.getPageEnd();
		
		List<EvalTSA> list = evalBoardService.list(start, end, searchOption, keyword, content_type, username);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		map.put("count", count);
		map.put("searchOption", searchOption);
		map.put("keyword", keyword);
		map.put("boardPager", boardPager);
		map.put("content_type", content_type);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("map", map);
		mav.setViewName("ResultEvaluationTSA");
		return mav;
	}
	
	@RequestMapping(value="/ResultEvaluationTSAView", method=RequestMethod.GET)
	public ModelAndView view(@RequestParam int id, HttpSession session) throws Exception
	{
		EvalTSA etsa = evalBoardService.view(id);
		
		Map<String, Object> map = new HashMap<String, Object>();
		int total_score = etsa.getTotal_score();
		int total_score_percent = (total_score / 3) - 10;
		// 웹 jsp 에서 프로그래스바의 넓이는 전체의 90프로 이므로 10프정도를 깍아줘야 마커위치가 얼추 맞는다 -ㅛ-;;
		map.put("total_score_percent", total_score_percent);
		if(etsa.getTotal_score() >= 240) {
			map.put("total_score_status", "success");
		}
		else if(etsa.getTotal_score() < 240 && etsa.getTotal_score() > 180) {
			map.put("total_score_status", "warning");
		}
		else {
			map.put("total_score_status", "fail");
		}
		map.put("etsa", etsa);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("map", map);
		mav.setViewName("ResultEvaluationTSAView");
		return mav;
	}
	
	
	
}
