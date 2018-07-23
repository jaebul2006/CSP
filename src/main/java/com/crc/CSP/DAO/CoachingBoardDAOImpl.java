package com.crc.CSP.DAO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.crc.CSP.HomeController;
import com.crc.CSP.bean.CoachingBoardVO;

@Repository
public class CoachingBoardDAOImpl implements CoachingBoardDAO {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	private static final String namespace = "com.crc.CSP.CoachingBoardMapper";
	
	@Inject
	SqlSession sqlSession;
	
	@Override
	public void create(CoachingBoardVO vo) throws Exception
	{
		logger.info("#####{}", vo);
		sqlSession.insert(namespace + ".board_insert", vo);
	}
	
	@Override
	public CoachingBoardVO read(int bno) throws Exception
	{
		return sqlSession.selectOne(namespace + ".board_view", bno);
	}
	
	@Override
	public void update(CoachingBoardVO vo) throws Exception
	{
		sqlSession.update(namespace + ".board_updateArticle", vo);
	}
	
	@Override
	public void delete(int bno) throws Exception
	{
		sqlSession.delete(namespace + ".board_deleteArticle", bno); 
	}
	
	@Override
	public List<CoachingBoardVO> listAll(int start, int end, String searchOption, String keyword, String user_id, String content_type) throws Exception
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("searchOption", searchOption);
		map.put("keyword", keyword);
		map.put("start", start);
		map.put("end", end);
		map.put("user_id", user_id);
		map.put("content_type", content_type);
		return sqlSession.selectList(namespace + ".board_listAll", map);
	}
	
	@Override
	public void increaseViewcnt(int bno) throws Exception
	{
		sqlSession.update(namespace + ".board_increaseViewcnt", bno);
	}
	
	@Override
	public int countArticle(String searchOption, String keyword, String user_id, String content_type) throws Exception
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("searchOption", searchOption);
		map.put("keyword", keyword);
		map.put("user_id", user_id);
		map.put("content_type", content_type);
		return sqlSession.selectOne(namespace + ".board_countArticle", map);
	}
	
	@Override
	public void addAttach(String fullName) throws Exception
	{
		sqlSession.insert(namespace + ".board_attach", fullName);
	}
	
	@Override
	public List<String> getAttach(int bno) throws Exception
	{
		return sqlSession.selectList(namespace + ".board_getAttach", bno);
	}
	
	@Override
	public void deleteFile(String fullname) throws Exception
	{
		sqlSession.delete(namespace + ".board_deleteAttach", fullname);
	}
	
	@Override
	public void updateAttach(String fullName, int bno) throws Exception
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("fullName",  fullName);
		map.put("bno", bno);
		sqlSession.insert(namespace + ".board_updateAttach", map);
	}
	
	@Override
	public List<CoachingBoardVO> TlistAll(int start, int end, String searchOption, String keyword, String content_type) throws Exception
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("searchOption", searchOption);
		map.put("keyword", keyword);
		map.put("start", start);
		map.put("end", end);
		map.put("content_type", content_type);
		return sqlSession.selectList(namespace + ".board_TlistAll", map);
	}
	
	@Override
	public int TcountArticle(String searchOption, String keyword, String content_type) throws Exception
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("searchOption", searchOption);
		map.put("keyword", keyword);
		map.put("content_type", content_type);
		return sqlSession.selectOne(namespace + ".board_TcountArticle", map);
	}
	
}
