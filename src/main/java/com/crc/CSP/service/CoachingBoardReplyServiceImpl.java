package com.crc.CSP.service;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.crc.CSP.DAO.CoachingBoardReplyDAO;
import com.crc.CSP.bean.CoachingBoardReplyVO;

@Service
public class CoachingBoardReplyServiceImpl implements CoachingBoardReplyService {
	
	@Inject
	CoachingBoardReplyDAO dao;
	
	@Override
	public void create(CoachingBoardReplyVO vo) throws Exception
	{
		dao.create(vo);
	}
	
	@Override
	public List<CoachingBoardReplyVO> list(int bno, int start, int end, HttpSession session) throws Exception
	{
		List<CoachingBoardReplyVO> items = dao.list(bno, start, end);
		//String userId = session.getAttribute("userId").toString();
		/*for(CoachingBoardReplyVO vo : items) {
			String writer = vo.getWriter();
			String replyer = vo.getReplyer();
		}*/
		return items;
	}
	
	@Override
	public CoachingBoardReplyVO detail(int rno) throws Exception
	{
		return dao.detail(rno);
	}
	
	@Override
	public void update(CoachingBoardReplyVO vo) throws Exception
	{
		dao.update(vo);
	}
	
	@Override
	public void delete(int rno) throws Exception
	{
		dao.delete(rno);
	}
	
	@Override
	public int count(int bno) throws Exception
	{
		return dao.count(bno);
	}
	
	@Override
	public void insert_eval(HashMap map) throws Exception
	{
		dao.insert_eval(map);
	}
	
	@Override
	public List<CoachingBoardReplyVO> recent_score_six(String content_type, String user_id) throws Exception
	{
		return dao.recent_score_six(content_type, user_id);
	}
	
	@Override
	public void isread(HashMap map) throws Exception
	{
		dao.isread(map);
	}
	
}
