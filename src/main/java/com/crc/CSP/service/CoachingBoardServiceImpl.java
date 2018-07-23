package com.crc.CSP.service;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crc.CSP.HomeController;
import com.crc.CSP.DAO.CoachingBoardDAO;
import com.crc.CSP.bean.CoachingBoardVO;

@Service
public class CoachingBoardServiceImpl implements CoachingBoardService{
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Inject
	CoachingBoardDAO boardDao;

	final long SESSION_LIMIT_TIME = 5000;
	
	@Transactional
	@Override
	public void create(CoachingBoardVO vo) throws Exception
	{
		String title = vo.getTitle();
		String content = vo.getContent();
		String writer = vo.getWriter();
		String fileName = vo.getFileName();
		String content_type = vo.getContent_type();
		//logger.info("fileName is {}", fileName);
		
		/*title = title.replace("<", "&lt;");
		title = title.replace(">", "&gt;");
		writer = writer.replace("<", "&lt;");
		writer = writer.replace(">", "&gt;");
		title = title.replace(" ", "&nbsp;&npsp;");
		writer = writer.replace(" ", "&nbsp;&nbsp;");
		content = content.replace("\n", "<br>"); */
		
		vo.setTitle(title);
		vo.setContent(content);
		vo.setWriter(writer);
		vo.setContent_type(content_type);
		
		boardDao.create(vo);
	}
	
	@Override
	public CoachingBoardVO read(int bno) throws Exception
	{
		return boardDao.read(bno); 
	}
	
	@Transactional
	@Override
	public void update(CoachingBoardVO vo) throws Exception
	{
		boardDao.update(vo);
		boardDao.updateAttach(vo.getFileName(), vo.getBno());
	}
	
	@Override
	public void delete(int bno) throws Exception
	{
		boardDao.delete(bno);
	}
	
	@Override
	public List<CoachingBoardVO> listAll(int start, int end, String searchOption, String keyword, String user_id, String content_type) throws Exception
	{
		return boardDao.listAll(start, end, searchOption, keyword, user_id, content_type);
	}
	
	@Override
	public void increaseViewcnt(int bno, HttpSession session) throws Exception
	{
		long update_time = 0;
		if(session.getAttribute("update_time_" + bno) != null) {
			String temp = session.getAttribute("update_time_" + bno).toString();
			update_time = Long.parseLong(temp);
		}
		
		long current_time = System.currentTimeMillis();
		if(current_time - update_time > SESSION_LIMIT_TIME) {
			boardDao.increaseViewcnt(bno);
			session.setAttribute("update_time_" + bno, current_time);
		}
	}
	
	@Override
	public int countArticle(String searchOption, String keyword, String user_id, String content_type) throws Exception
	{
		return boardDao.countArticle(searchOption, keyword, user_id, content_type);
	}
	
	@Override
	public List<String> getAttach(int bno) throws Exception
	{
		return boardDao.getAttach(bno);
	}
	
	@Override
	public void deleteFile(String fullname) throws Exception
	{
		boardDao.deleteFile(fullname);
	}
	
	@Override
	public List<CoachingBoardVO> TlistAll(int start, int end, String searchOption, String keyword, String content_type) throws Exception
	{
		return boardDao.TlistAll(start, end, searchOption, keyword, content_type);
	}
	
	@Override
	public int TcountArticle(String searchOption, String keyword, String content_type) throws Exception
	{
		return boardDao.TcountArticle(searchOption, keyword, content_type);
	}
	
}
