package com.crc.CSP.DAO;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.crc.CSP.bean.OperInfoVO;

@Repository
public class OperInfoDAOImpl implements OperInfoDAO{

	private static final String namespace = "com.crc.CSP.OperInfoMapper";
	
	@Inject
	SqlSession sqlSession;
	
	@Override
	public List<OperInfoVO> operinfo(HashMap operinfo) throws Exception
	{
		return sqlSession.selectList(namespace + ".operinfo", operinfo);
	}
	
	@Override
	public List<OperInfoVO> operinfo_comp(HashMap operinfo) throws Exception
	{
		return sqlSession.selectList(namespace + ".operinfo_comp", operinfo);
	}
}
