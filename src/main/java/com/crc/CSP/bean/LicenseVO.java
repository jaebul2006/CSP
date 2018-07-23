package com.crc.CSP.bean;

public class LicenseVO {

	int no;
	String user_id;
	String content;
	String imp_uid;
	String merchant_uid;
	String paid_amount;
	String apply_num;
	
	public int getno()
	{
		return no;
	}
	
	public void setno(int no)
	{
		this.no = no;
	}
	
	public String getuser_id()
	{
		return user_id;
	}
	
	public void setuser_id(String user_id)
	{
		this.user_id = user_id;
	}
	
	public String getcontent()
	{
		return content;
	}
	
	public void setcontent(String content)
	{
		this.content = content;
	}
	
	public String getimp_uid()
	{
		return imp_uid;
	}
	
	public void setimp_uid(String imp_uid)
	{
		this.imp_uid = imp_uid;
	}
	
	public String getmerchant_uid()
	{
		return merchant_uid;
	}
	
	public void setmerchant_uid(String merchant_uid)
	{
		this.merchant_uid = merchant_uid;
	}
	
	public String getpaid_amount()
	{
		return paid_amount;
	}
	
	public void setpaid_amount(String paid_amount)
	{
		this.paid_amount = paid_amount;
	}
	
	public String getapply_num()
	{
		return apply_num;
	}
	
	public void setapply_num(String apply_num)
	{
		this.apply_num = apply_num;
	}
	
}
