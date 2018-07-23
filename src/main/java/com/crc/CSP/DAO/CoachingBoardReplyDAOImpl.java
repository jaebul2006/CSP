package com.crc.CSP.DAO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.crc.CSP.bean.CoachingBoardReplyVO;

@Repository
public class CoachingBoardReplyDAOImpl implements CoachingBoardReplyDAO {

	private static final String namespace = "com.crc.CSP.CoachingBoardReplyMapper";
	
	@Inject
	SqlSession sqlSession;
	
	@Override
	public void create(CoachingBoardReplyVO vo) throws Exception
	{
		sqlSession.insert(namespace + ".rp_insert", vo);
	}
	
	@Override
	public List<CoachingBoardReplyVO> list(int bno, int start, int end) throws Exception
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bno", bno);
		map.put("start", start);
		map.put("end", end);
		return sqlSession.selectList(namespace + ".rp_list", map);
	}
	
	@Override
	public CoachingBoardReplyVO detail(int rno)
	{
		return sqlSession.selectOne(namespace + ".rp_detail", rno);
	}
	
	@Override
	public void update(CoachingBoardReplyVO vo)
	{
		sqlSession.update(namespace + ".rp_update", vo);
	}
	
	@Override
	public void delete(int rno)
	{
		sqlSession.delete(namespace + ".rp_delete", rno);
	}
	
	@Override
	public int count(int bno)
	{
		return sqlSession.selectOne(namespace + ".rp_count", bno);
	}
	
	@Override
	public void insert_eval(HashMap map)
	{
		sqlSession.insert(namespace + ".insert_eval", map);
	}
	
	@Override
	public List<CoachingBoardReplyVO> recent_score_six(String content_type, String user_id) throws Exception
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("content_type", content_type);
		map.put("user_id", user_id);
		return sqlSession.selectList(namespace + ".recent_score_six", map);
	}
	
	@Override
	public void isread(HashMap map) throws Exception
	{
		sqlSession.update(namespace + ".rp_isread", map);
	}
}
