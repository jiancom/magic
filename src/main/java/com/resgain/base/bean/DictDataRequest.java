package com.resgain.base.bean;

import org.apache.commons.lang.StringUtils;


public class DictDataRequest
{
	private String beanId;
	private String category;
	private String pcode;
	private String keywords;

	public String getBeanId()
	{
		return beanId;
	}
	public void setBeanId(String beanId)
	{
		this.beanId = beanId;
	}

	public String getCategory()
	{
		return category;
	}
	public void setCategory(String category)
	{
		this.category = category;
	}

	public String getPcode()
	{
		return pcode;
	}
	public void setPcode(String pcode)
	{
		this.pcode = pcode;
	}

	public String getKeywords()
	{
		return keywords;
	}
	public void setKeywords(String keywords)
	{
		this.keywords = keywords;
	}

	public String getKey(){
		StringBuffer sb = new StringBuffer();
		sb.append(StringUtils.isBlank(beanId)?"null":beanId).append(":");
		sb.append(StringUtils.isBlank(category)?"null":category).append(":");
		sb.append(StringUtils.isBlank(pcode)?"null":pcode).append(":");
		sb.append(StringUtils.isBlank(keywords)?"null":keywords);
		return sb.toString();
	}
}
