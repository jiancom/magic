package com.resgain.base.abs.bean;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public final class HqlParam
{
	private StringBuffer hql;
	private List<Object> params = new ArrayList<Object>();

	private HqlParam(){}

	public static HqlParam getInstance(String hql, Object[] param)
	{
		HqlParam ret = new HqlParam();
		ret.hql = new StringBuffer();
		if (hql != null)
			ret.hql.append(hql);
		if(param!=null)
		{
			for (int i = 0; i < param.length; i++) {
				ret.params.add(param[i]);
			}
		}
		return ret;
	}

	public static HqlParam getInstance() {
		return getInstance(null, null);
	}

	public static HqlParam getInstance(String hql)
	{
		return getInstance(hql, null);
	}

	public HqlParam addCond(String cond, Object param)
	{
		if (param != null && !StringUtils.isBlank(param.toString()))
		{
			hql.append(" ").append(cond);
			params.add(param);
		}
		return this;
	}

	public HqlParam addLikeCond(String cond, Object param) {
		if (param != null && !StringUtils.isBlank(param.toString())) {
			hql.append(" ").append(cond);
			params.add("%" + param + "%");
		}
		return this;
	}

	public HqlParam addCond(String cond, Object[] param)
	{
		if(param!=null)
		{
			hql.append(" ").append(cond);
			for (int i = 0; i < param.length; i++) {
				params.add(param[i]);
			}
		}
		return this;
	}

	public HqlParam addCond(boolean flag, String cond)
	{
		if(flag)
			hql.append(" ").append(cond);
		return this;
	}

	public HqlParam addCond(String cond)
	{
		hql.append(" ").append(cond);
		return this;
	}

	public String getHql()
	{
		return hql.toString();
	}
	public List<Object> getParams()
	{
		return params;
	}
}