package com.resgain.base.interceptor;

import java.io.Serializable;

import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;

import com.resgain.base.abs.AbstractObject;
import com.resgain.base.abs.AbstractPersistentObject;
import com.resgain.base.util.SessionUtil;
import com.resgain.dragon.util.ResgainUtil;


/**
 * Hibernate拦截器，主要是自动设置一些常用字段值，不用每个程序自己处理
 * @author memphis.guo
 */
public class HibernateInterceptor extends EmptyInterceptor {

	private static final long serialVersionUID = 1L;

	@Override
	public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState, String[] propertyNames, Type[] types) {
		if (entity instanceof AbstractObject) {
			for (int i = 0; i < propertyNames.length; i++) {
				if ("editTime".equals(propertyNames[i])) {
					currentState[i] = ResgainUtil.getToday();
				} else if ("editorId".equals(propertyNames[i])) {
					currentState[i] = SessionUtil.getLoginUserId();
				}
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
		if (entity instanceof AbstractPersistentObject) {
			for (int i = 0; i < propertyNames.length; i++) {
				if ("createTime".equals(propertyNames[i])) {
					state[i] = ResgainUtil.getToday();
				} else if ("creatorId".equals(propertyNames[i])) {
					state[i] = SessionUtil.getLoginUserId();
				}
			}
			return true;
		}
		return false;
	}
}
