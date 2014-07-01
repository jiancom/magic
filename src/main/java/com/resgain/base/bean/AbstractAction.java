package com.resgain.base.bean;

import java.lang.reflect.ParameterizedType;
import java.util.Map;

import com.resgain.base.extjs.GridColumn;

public class AbstractAction<T>
{
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map<String, Object> gconf() {
		Class clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		return GridColumn.getData(clazz);
	}
}
