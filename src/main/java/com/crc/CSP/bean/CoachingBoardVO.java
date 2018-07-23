package com.crc.CSP.bean;

import java.util.Date;

public class CoachingBoardVO {

	private int rn;
	private int bno;
	private String title;
	private String content;
	private String writer;
	private String userName;
	private Date regdate;
	private int viewcnt;
	private int recnt;
	private String isshow;
	private String fileName;
	private String content_type;
	
	public int getRn()
	{
		return rn;
	}
	
	public void setRn(int rn)
	{
		this.rn = rn;
	}
	
	public int getBno()
	{
		return bno;
	}
	
	public void setBno(int bno)
	{
		this.bno = bno;
	}
	
	public String getTitle()
	{
		return title;
	}
	
	public void setTitle(String title)
	{
		this.title = title;
	}
	
	public String getContent()
	{
		return content;
	}
	
	public void setContent(String content)
	{
		this.content = content;
	}
	
	public String getWriter()
	{
		return writer;
	}
	
	public void setWriter(String writer)
	{
		this.writer = writer;
	}
	
	public String getUserName()
	{
		return userName;
	}
	
	public void setUserName(String userName)
	{
		this.userName = userName;
	}
	
	public Date getRegdate()
	{
		return regdate;
	}
	
	public void setRegdate(Date regdate)
	{
		this.regdate = regdate;
	}
	
	public int getViewcnt()
	{
		return viewcnt;
	}
	
	public void setViewcnt(int viewcnt)
	{
		this.viewcnt = viewcnt;
	}
	
	public int getRecnt()
	{
		return recnt;
	}
	
	public void setRecnt(int recnt)
	{
		this.recnt = recnt;
	}
	
	public String getIsshow()
	{
		return isshow;
	}
	
	public void setIsshow(String show)
	{
		this.isshow = show;
	}
	
	public String getFileName()
	{
		return fileName;
	}
	
	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}
	
	public String getContent_type()
	{
		return content_type;
	}
	
	public void setContent_type(String content_type)
	{
		this.content_type = content_type;
	}
	
}
