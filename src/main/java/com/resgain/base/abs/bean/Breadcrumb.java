package com.resgain.base.abs.bean;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.resgain.dragon.filter.ActionContext;

/**
 * 默认的访问路径处理
 * @author gyl
 */
public class Breadcrumb
{
    private final static String BREADCRUMB="Breadcrumb";

    private static Breadcrumb instance = new Breadcrumb();

    private Breadcrumb(){}

    public static Breadcrumb getInstance(){
    	return instance;
    }

    public void creatCrumb(String title)
    {
		HttpServletRequest request = ActionContext.getHttpServletRequest();
        LinkedHashMap<String, Crumb> tmp = new LinkedHashMap<String, Crumb>();
        Crumb current = initCrumb(title, null);
        tmp.put(current.getId(), current);
        request.getSession().setAttribute(BREADCRUMB, tmp);
    }

    public void addCrumb(String title){
    	addCrumb(title, null);
    }

    @SuppressWarnings("unchecked")
    public void addCrumb(String title, String param)
    {
		if (ActionContext.getHttpServletRequest().getSession().getAttribute(BREADCRUMB) == null)
            creatCrumb(title);
        else{
			LinkedHashMap<String, Crumb> tmp = (LinkedHashMap<String, Crumb>) ActionContext.getHttpServletRequest().getSession().getAttribute(BREADCRUMB);
            Crumb current = initCrumb(title, param);
            tmp.put(current.getId(), current);
            Iterator<Entry<String, Crumb>> it = tmp.entrySet().iterator();
            boolean flag=false;
            while(it.hasNext())
            {
                Entry<String, Crumb> entry = it.next();
                if(flag) it.remove();
                if(entry.getKey().equals(current.getId()))
                    flag=true;
            }
        }
    }

    @SuppressWarnings("unchecked")
    public LinkedHashMap<String, Crumb> getBreadcrumb()
    {
		return (LinkedHashMap<String, Crumb>) ActionContext.getHttpServletRequest().getSession().getAttribute(BREADCRUMB);
    }

    public Crumb getLastParent(){
    	LinkedHashMap<String, Crumb> tmp = getBreadcrumb();
    	int c = tmp.size() - 2, i=0;
    	for (Entry<String, Crumb> entry : tmp.entrySet()) {
    		Crumb crumb = entry.getValue();
    		if(i==c)
    			return crumb;
    		i++;
		}
    	return null;
    }

    private Crumb initCrumb(String title, String param)
    {
		HttpServletRequest request = ActionContext.getHttpServletRequest();
		String id = ActionContext.getHttpServletRequest().getRequestURI() + ((param == null) ? "" : ("?flag=" + param));
        String uri = request.getRequestURI() + (StringUtils.isBlank(request.getQueryString()) ? "" : ("?" + request.getQueryString()));
        return new Crumb(id, uri, title);
    }
}

