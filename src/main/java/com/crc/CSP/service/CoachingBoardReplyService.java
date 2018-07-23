package com.crc.CSP.service;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.crc.CSP.bean.CoachingBoardReplyVO;

public interface CoachingBoardReplyService {

	public void create(CoachingBoardReplyVO vo) throws Exception;
	public List<CoachingBoardReplyVO> list(int bno, int start, int end, HttpSession session) throws Exception;
	public CoachingBoardReplyVO detail(int rno) throws Exception;
	public void update(CoachingBoardReplyVO vo) throws Exception;
	public void delete(int rno) throws Exception;
	public int count(int bno) throws Exception;
	
	public void insert_eval(HashMap map) throws Exception;
	public List<CoachingBoardReplyVO> recent_score_six(String content_type, String user_id) throws Exception;
	public void isread(HashMap map) throws Exception;
}
