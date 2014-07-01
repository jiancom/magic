package com.resgain.base.util;

import java.net.URLEncoder;
import java.util.Date;
import java.util.Enumeration;

import com.resgain.base.security.bean.ResgainUser;
import com.resgain.dragon.filter.ActionContext;
import com.resgain.dragon.util.ConfigUtil;
import com.resgain.dragon.util.ResgainUtil;

/**
 * View层格式化数据显示方法
 * @author memphis.guo
 */
public class ViewUtil
{
	private static ViewUtil instance = new ViewUtil();

	private ViewUtil(){}

	public static ViewUtil getInstance()
	{
		return instance;
	}

	//格式化日期时间
	public String fdt(Date date)
	{
		return ResgainUtil.getDateTimeString(date);
	}

	//格式化日期
	public String fd(Date date)
	{
		return ResgainUtil.getDateString(date);
	}

	//格式化时间
	public String ft(Date date)
	{
		return ResgainUtil.getTimeString(date);
	}

	public Date now() {
		return new Date();
	}

	//格式化金额
	public String fm(Number n)
	{
		return ResgainUtil.foraNumber(n, ConfigUtil.getValue("money-format", "##,###,###,###.00"));
	}

	//格式化数字数据
	public String fn(Number n)
	{
		return ResgainUtil.foraNumber(n, ConfigUtil.getValue("number-format", "###.##"));
	}

	public String getAction(int page) throws Exception {
		Enumeration<String> keys = ActionContext.getHttpServletRequest().getParameterNames();
		StringBuffer sb = new StringBuffer();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			if ("qp.page".equalsIgnoreCase(key))
				continue;
			sb.append("&").append(key).append("=").append(URLEncoder.encode(ActionContext.getHttpServletRequest().getParameter(key), "UTF-8"));
		}
		return ActionContext.getHttpServletRequest().getRequestURI() + "?qp.page=" + page + sb.toString();
	}

	public boolean isLogin(){
		return getLoginUser()!=null;
	}

	public String getLoginUserId() {
		return SessionUtil.getLoginUserId();
	}

	public ResgainUser getLoginUser() {
		return SessionUtil.getLoginUser();
	}

	public boolean hasAuth(String auth){
		return SessionUtil.hasAuth(auth);
	}
}
