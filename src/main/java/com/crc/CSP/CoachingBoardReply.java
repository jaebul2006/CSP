package com.crc.CSP;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.crc.CSP.bean.CoachingBoardReplyVO;
import com.crc.CSP.service.CoachingBoardReplyService;
import com.crc.CSP.service.ReplyPager;

@Controller
@RequestMapping("/CoachingBoardReply/*")
public class CoachingBoardReply {

	private static final Logger logger = LoggerFactory.getLogger(CoachingBoardReply.class);
	
	@Inject
	CoachingBoardReplyService replyService;
	
	/*
	@RequestMapping(value="insert", method=RequestMethod.POST)
	public String insert(@ModelAttribute CoachingBoardReplyVO vo, HttpSession session) throws Exception
	{
		logger.info("**************");
		logger.info("평가 score=>{}", vo.getscore());
		logger.info("**************");
		String userId = (String) session.getAttribute("userId");
		vo.setReplyer(userId);
		replyService.create(vo);
		return "redirect:/CoachingBoard/TCoachingBoardList";
	}
	*/
	
	@RequestMapping(value="insertRest", method=RequestMethod.POST)
	public ResponseEntity<String> insertRest(@RequestBody CoachingBoardReplyVO vo, HttpSession session) throws Exception
	{
		ResponseEntity<String> entity = null;
		try {
			String userId = (String) session.getAttribute("userId");
			vo.setReplyer(userId);
			replyService.create(vo);
			entity = new ResponseEntity<String>("success", HttpStatus.OK);
		}
		catch(Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
	
	
	@RequestMapping("list")
	public ModelAndView list(@RequestParam int bno, @RequestParam(defaultValue="1")int curPage, ModelAndView mav, HttpSession session) throws Exception
	{
		// 평가게시물을 진입한 것이므로 isRead는 모두 y 가 되어야 한다.
		int count = replyService.count(bno);
		ReplyPager replyPager = new ReplyPager(count, curPage);
		int start = replyPager.getPageBegin();
		int end = replyPager.getPageEnd();
		List<CoachingBoardReplyVO> list = replyService.list(bno, start, end, session);
		mav.setViewName("ReplyList");
		mav.addObject("list", list);
		mav.addObject("replyPager", replyPager);
		return mav;
	}
	
	@RequestMapping("listJson")
	@ResponseBody
	public List<CoachingBoardReplyVO> listJson(@RequestParam int bno, @RequestParam(defaultValue="1")int curPage, HttpSession session) throws Exception
	{
		int count = replyService.count(bno);
		ReplyPager pager = new ReplyPager(count, curPage);
		int start = pager.getPageBegin();
		int end = pager.getPageEnd();
		List<CoachingBoardReplyVO> list = replyService.list(bno, start, end, session);
		return list;
	}
	
	// 댓글목록
	@RequestMapping(value="/list/{bno}/{curPage}", method=RequestMethod.GET)
	public ModelAndView replyList(@PathVariable("bno") int bno, @PathVariable int curPage, ModelAndView mav, HttpSession session) throws Exception
	{
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("bno", bno);
		map.put("isRead", "y");
		replyService.isread(map);
		
		int count = replyService.count(bno);
		ReplyPager replyPager = new ReplyPager(count, curPage);
		int start = replyPager.getPageBegin();
		int end = replyPager.getPageEnd();
		//logger.info("{} &&&&&", bno);
		List<CoachingBoardReplyVO> list = replyService.list(bno, start, end, session);
		logger.info(">>>>>{}", list.size());
		mav.setViewName("CoachingBoardReplyList");
		mav.addObject("list", list);
		mav.addObject("replyPager", replyPager);
		return mav;
	}
	
	@RequestMapping(value="/detail/{rno}", method=RequestMethod.GET)
	public ModelAndView replyDetail(@PathVariable("rno")int rno, ModelAndView mav) throws Exception
	{
		CoachingBoardReplyVO vo = replyService.detail(rno);
		mav.setViewName("CoachingBoard/replyDetail");
		mav.addObject("vo", vo);
		return mav;
	}
	
	@RequestMapping(value="/update/{rno}", method= {RequestMethod.PUT, RequestMethod.PATCH})
	public ResponseEntity<String> replyUpdate(@PathVariable("rno")int rno, @RequestBody CoachingBoardReplyVO vo)
	{
		ResponseEntity<String> entity = null;
		try {
			vo.setRno(rno);
			replyService.update(vo);
			entity = new ResponseEntity<String>("success", HttpStatus.OK);
		}
		catch(Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
	
	@RequestMapping(value="/delete/{rno}")
	public ResponseEntity<String> replyDelete(@PathVariable("rno")int rno)
	{
		ResponseEntity<String> entity = null;
		try {
			replyService.delete(rno);
			entity = new ResponseEntity<String>("success", HttpStatus.OK);
		}
		catch(Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
	
	
	
	
}
