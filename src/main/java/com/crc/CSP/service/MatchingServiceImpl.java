package com.crc.CSP.service;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.crc.CSP.DAO.MatchingDAO;
import com.crc.CSP.bean.GoingAppointmentVO;
import com.crc.CSP.bean.MatchingVO;

@Service
public class MatchingServiceImpl implements MatchingService{

	@Inject
	private MatchingDAO dao;
	
	@Override
	public List<MatchingVO> getMatching(HashMap map) throws Exception
	{
		return dao.getMatching(map);
	}
	
	@Override
	public void regMatching(HashMap map) throws Exception
	{
		dao.regMatching(map);
	}
	
	@Override
	public void joinMatching(HashMap map) throws Exception
	{
		dao.joinMatching(map);
	}
	
	@Override
	public int isMaxPerson(String id) throws Exception
	{
		return dao.isMaxPerson(id);
	}
	
	@Override
	public void endMatching(String id) throws Exception
	{
		dao.endMatching(id);
	}
	
	@Override
	public void regGoingApp(HashMap map) throws Exception
	{
		dao.regGoingApp(map);
	}
	
	@Override
	public List<GoingAppointmentVO> findMatchingIdFromGoingAppointment(String matching_tbl_id) throws Exception
	{
		return dao.findMatchingIdFromGoingAppointment(matching_tbl_id);
	}
	
	@Override
	public void disableMatchingTbl(HashMap map) throws Exception
	{
		dao.disableMatchingTbl(map);
	}
	
	@Override
	public void disableGoingAppointment(String matching_tbl_id) throws Exception
	{
		dao.disableGoingAppointment(matching_tbl_id);
	}
	
	@Override
	public void matchingAddCurPerson(HashMap map) throws Exception
	{
		dao.matchingAddCurPerson(map);
	}
	
	@Override
	public void addGoingAppointment(HashMap map) throws Exception
	{
		dao.addGoingAppointment(map);
	}
	
	@Override
	public List<GoingAppointmentVO> allGoingAppointment(String user_id) throws Exception
	{
		return dao.allGoingAppointment(user_id);
	}
	
	@Override
	public List<GoingAppointmentVO> finishAppointment(String user_id) throws Exception
	{
		return dao.finishAppointment(user_id);
	}
	
	@Override
	public List<GoingAppointmentVO> finishMatchingMember(String matching_id) throws Exception
	{
		return dao.finishMatchingMember(matching_id);
	}
	
	@Override
	public String getPhoneNumber(String user_id) throws Exception
	{
		return dao.getPhoneNumber(user_id);
	}
	
	@Override
	public String getPushToken(String user_id) throws Exception
	{
		return dao.getPushToken(user_id);
	}
	
	@Override
	public void regLocation(HashMap map) throws Exception
	{
		dao.regLocation(map);
	}
	
}
