package com.crc.CSP.DAO;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.crc.CSP.bean.GoingAppointmentVO;
import com.crc.CSP.bean.MatchingVO;

@Repository
public class MatchingDAOImpl implements MatchingDAO{

	private static final String namespace = "com.crc.CSP.MatchingMapper";
	
	@Inject
	private SqlSession sqlSession;
	
	@Override
	public List<MatchingVO> getMatching(HashMap map) throws Exception
	{
		return sqlSession.selectList(namespace + ".getMatching", map);
	}
	
	@Override
	public void regMatching(HashMap map) throws Exception
	{
		sqlSession.insert(namespace + ".regMatching", map);
	}
	
	@Override
	public void joinMatching(HashMap map) throws Exception
	{
		sqlSession.insert(namespace + ".joinMatching", map);
	}
	
	@Override
	public int isMaxPerson(String id) throws Exception
	{
		return sqlSession.selectOne(namespace + ".isMaxPerson", id);
	}
	
	@Override
	public void endMatching(String id) throws Exception
	{
		sqlSession.update(namespace + ".endMatching", id);
	}
	
	@Override
	public void regGoingApp(HashMap map) throws Exception
	{
		sqlSession.insert(namespace + ".regGoingApp", map);
	}
	
	@Override
	public List<GoingAppointmentVO> findMatchingIdFromGoingAppointment(String matching_tbl_id) throws Exception
	{
		return sqlSession.selectList(namespace + ".findMatchingIdFromGoingAppointment", matching_tbl_id);
	}
	
	@Override
	public void disableMatchingTbl(HashMap map) throws Exception
	{
		sqlSession.update(namespace + ".disableMatchingTbl", map);
	}
	
	@Override
	public void disableGoingAppointment(String matching_tbl_id) throws Exception
	{
		sqlSession.update(namespace + ".disableGoingAppointment", matching_tbl_id);
	}
	
	@Override
	public void matchingAddCurPerson(HashMap map) throws Exception
	{
		sqlSession.update(namespace + ".matchingAddCurPerson", map);
	}
	
	@Override
	public void addGoingAppointment(HashMap map) throws Exception
	{
		sqlSession.insert(namespace + ".addGoingAppointment", map);
	}
	
	@Override
	public List<GoingAppointmentVO> allGoingAppointment(String user_id) throws Exception
	{
		return sqlSession.selectList(namespace + ".allGoingAppointment", user_id);
	}
	
	@Override
	public List<GoingAppointmentVO> finishAppointment(String user_id) throws Exception
	{
		return sqlSession.selectList(namespace + ".finishAppointment", user_id);
	}
	
	@Override
	public List<GoingAppointmentVO> finishMatchingMember(String matching_id) throws Exception
	{
		return sqlSession.selectList(namespace + ".finishMatchingMember", matching_id);
	}
	
	@Override
	public String getPhoneNumber(String user_id) throws Exception
	{
		return sqlSession.selectOne(namespace + ".getPhoneNumber", user_id);
	}
	
	@Override
	public String getPushToken(String user_id) throws Exception
	{
		return sqlSession.selectOne(namespace + ".getPushToken", user_id);
	}
	
	@Override
	public void regLocation(HashMap map) throws Exception
	{
		sqlSession.insert(namespace + ".regLocation", map);
	}
	
}
