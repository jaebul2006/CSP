package com.crc.CSP.DAO;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.crc.CSP.bean.LicenseVO;

@Repository
public class LicenseDAOImpl implements LicenseDAO{

	private static final String namespace = "com.crc.CSP.LicenseMapper";
	
	@Inject
	SqlSession sqlSession;
	
	public void insert_license(HashMap license_info) throws Exception
	{
		sqlSession.insert(namespace + ".insert_license", license_info);
	}
	
	public List<LicenseVO> check_license(String user_id) throws Exception
	{
		return sqlSession.selectList(namespace + ".check_license", user_id);
	}
}
