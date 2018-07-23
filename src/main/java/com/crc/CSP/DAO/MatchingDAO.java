package com.crc.CSP.DAO;

import java.util.HashMap;
import java.util.List;

import com.crc.CSP.bean.GoingAppointmentVO;
import com.crc.CSP.bean.MatchingVO;

public interface MatchingDAO {

	public List<MatchingVO> getMatching(HashMap map) throws Exception;
	public void regMatching(HashMap map) throws Exception;
	public void joinMatching(HashMap map) throws Exception;
	public int isMaxPerson(String id) throws Exception;
	public void endMatching(String id) throws Exception;
	public void regGoingApp(HashMap map) throws Exception;
	public List<GoingAppointmentVO> findMatchingIdFromGoingAppointment(String matching_tbl_id) throws Exception;
	public void disableMatchingTbl(HashMap map) throws Exception;
	public void disableGoingAppointment(String matching_tbl_id) throws Exception;
	public void matchingAddCurPerson(HashMap map) throws Exception;
	public void addGoingAppointment(HashMap map) throws Exception;
	public List<GoingAppointmentVO> allGoingAppointment(String user_id) throws Exception;
	public List<GoingAppointmentVO> finishAppointment(String user_id) throws Exception;
	public List<GoingAppointmentVO> finishMatchingMember(String matching_id) throws Exception;
	public String getPhoneNumber(String user_id) throws Exception;
	public String getPushToken(String user_id) throws Exception;
	
	public void regLocation(HashMap map) throws Exception;
}
