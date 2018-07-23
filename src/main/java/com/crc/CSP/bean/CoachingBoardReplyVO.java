package com.crc.CSP.bean;

import java.util.Date;

public class CoachingBoardReplyVO {

	private int rno;
	private int bno;
	private int score;
	private String replyText;
	private String replyer;
	private Date regdate;
	private String writer;
	private String fileName;
	private String isRead;
	private String content_type;
	
	public int getRno()
	{
		return rno;
	}
	
	public void setRno(int rno) 
	{
		this.rno = rno;
	}
	
	public int getBno()
	{
		return bno;
	}
	
	public void setBno(int bno) 
	{
		this.bno = bno;
	}
	
	public int getscore()
	{
		return score;
	}
	
	public void setscore(int score)
	{
		this.score = score;
	}
	
	public String getReplytext()
	{
		return replyText;
	}
	
	public void setReplytext(String replyText)
	{
		this.replyText = replyText;
	}
	
	public String getReplyer()
	{
		return replyer;
	}
	
	public void setReplyer(String replyer)
	{
		this.replyer = replyer;
	}
	
	public Date getRegdate()
	{
		return regdate;
	}
	
	public void setRegdate(Date regdate)
	{
		this.regdate = regdate;
	}
	
	public String getWriter()
	{
		return writer;
	}
	
	public void setWriter(String writer)
	{
		this.writer = writer;
	}
	
	public String getFileName()
	{
		return fileName;
	}
	
	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}
	
	public String getIsRead()
	{
		return isRead;
	}
	
	public void setIsRead(String isRead)
	{
		this.isRead = isRead;
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
