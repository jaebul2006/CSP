package com.crc.CSP.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.crc.CSP.bean.CoachingBoardVO;

public interface CoachingBoardService {

	public void create(CoachingBoardVO vo) throws Exception;
	public CoachingBoardVO read(int bno) throws Exception;
	public void update(CoachingBoardVO vo) throws Exception;
	public void delete(int bno) throws Exception;
	public List<CoachingBoardVO> listAll(int start, int end, String searchOption, String keyword, String user_id, String content_type) throws Exception;
	public void increaseViewcnt(int bno, HttpSession session) throws Exception;
	public int countArticle(String searchOption, String keyword, String user_id, String content_type) throws Exception;
	public List<String> getAttach(int bno) throws Exception;
	public void deleteFile(String fullname) throws Exception;
	
	public int TcountArticle(String searchOption, String keyword, String content_type) throws Exception;
	public List<CoachingBoardVO> TlistAll(int start, int end, String searchOption, String keyword, String content_type) throws Exception;
	
}
