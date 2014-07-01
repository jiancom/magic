package com.resgain.base.extjs;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections.map.LinkedMap;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.resgain.base.abs.AbstractObject;
import com.resgain.base.annotation.Desc;
import com.resgain.base.annotation.Dict;
import com.resgain.base.annotation.Extprop;
import com.resgain.base.annotation.Label;
import com.resgain.base.annotation.TreeDict;
import com.resgain.base.entity.DictCode;
import com.resgain.dragon.util.ClassUtil;
import com.resgain.dragon.util.ResgainUtil;

public class GridColumn
{
	private static Logger logger = LoggerFactory.getLogger(GridColumn.class);

	public static <T extends AbstractObject> Map<String, Object> getData(Class<T> clazz) {
		Map<String, Object> ret = new HashMap<String, Object>();
		Map<String, Object> meta = new HashMap<String, Object>();

		meta.put("fields", getMetaField(clazz));
		meta.put("dicts", getFieldDictDatas(clazz));
		meta.put("treeDatas", getFieldTreeDatas(clazz));

		if (clazz.isAnnotationPresent(Desc.class)) {
			Desc desc = clazz.getAnnotation(Desc.class);
			ret.put("desc", desc.value());
		} else {
			ret.put("desc", "");
		}
		ret.put("metaData", meta);
		ret.put("gridColumns", getShowField(clazz));
		ret.put("formColumns", getFormField(clazz));
		return ret;
	}

	public static <T extends AbstractObject> Map<String, Object> getFormData(Class<T> clazz) {
		Map<String, Object> ret = new HashMap<String, Object>();
		Map<String, Object> meta = new HashMap<String, Object>();
		meta.put("dicts", getFieldDictDatas(clazz));
		meta.put("treeDatas", getFieldTreeDatas(clazz));
		if (clazz.isAnnotationPresent(Desc.class)) {
			Desc desc = clazz.getAnnotation(Desc.class);
			ret.put("desc", desc.value());
		}
		ret.put("metaData", meta);
		ret.put("formColumns", getFormField(clazz));
		return ret;
	}

	private static List<Map<String, String>> getMetaField(Class<?> clazz) {
		List<Map<String, String>> ret = new ArrayList<Map<String, String>>();
		for (Entry<String, Field> entry : ClassUtil.getFields(clazz, false).entrySet()) {
			if (entry.getValue().isAnnotationPresent(Extprop.class)) // 扩展属性不处理
				continue;
			if ("serialVersionUID".equals(entry.getValue().getName()))
				continue;
			ret.add(getMetaFiled(entry.getValue().getName(), entry.getValue().getType().getSimpleName()));
		}
		return ret;
	}

	private static Map<String, Object> getShowField(Class<?> clazz) {
		Map<String, Object> ret = new HashMap<String, Object>();
		Map<String, Field> fields = ClassUtil.getFields(clazz, false);
		for (Entry<String, Field> entry : fields.entrySet()) {
			Field field = entry.getValue();
			if (field.isAnnotationPresent(Extprop.class)) // 扩展属性不处理
				continue;
			if ("serialVersionUID".equals(field.getName()))
				continue;
			Label label = field.isAnnotationPresent(Label.class) ? field.getAnnotation(Label.class) : null;
			Map<String, Object> t = (label == null)?getShowColumn(entry.getKey(), entry.getKey(), false, null):getShowColumn(label.name(), entry.getKey(), label.sortable(), label.type());
			if (field.isAnnotationPresent(Dict.class))
				t.put("dict_category", field.getAnnotation(Dict.class).category());
			if (field.isAnnotationPresent(TreeDict.class))
				t.put("tree_category", field.getAnnotation(TreeDict.class).category());
			if (label != null){
				if("yes_no".equals(label.xtype()))
					t.put("yes_no", true);
				if ("percentfield".equals(label.xtype()))
					t.put("percentfield", true);
				if (label.cshow().length() > 0)
					t.put("cshow", label.cshow());
				if (label.g_options().length() > 1) {
						for (String o : label.g_options().split(";")) {
							if (o.indexOf('=') > 1) {
								String ops[] = o.split("=");
								if(ops.length==2)
									t.put(ops[0].trim(), getValue(ops[1].trim()));
							}

						}
				}
			}
			ret.put(field.getName(), t);
		}
		return ret;
	}

	private static Map<String, Object> getFormField(Class<?> clazz) {
		Map<String, Object> ret = new HashMap<String, Object>();
		Map<String, Field> fields = ClassUtil.getFields(clazz, false);
		for (Entry<String, Field> entry : fields.entrySet()) {
			Field field = entry.getValue();
			if (field.isAnnotationPresent(Extprop.class)) // 扩展属性不处理
				continue;
			if ("serialVersionUID".equals(field.getName()))
				continue;
			if (!field.isAnnotationPresent(Label.class)) {
				logger.debug("{}类的{}没有Label注解。", clazz.getName(), field.getName());
				ret.put(field.getName(), null);
				continue;
			}
			Label label = field.getAnnotation(Label.class);
			ret.put(field.getName(), getFormColumn(field, label));
		}
		return ret;
	}

	private static Map<String, Map<String, DictCode>> getFieldDictDatas(Class<?> clazz) {
		Map<String, Map<String, DictCode>> ret = new HashMap<String, Map<String, DictCode>>();
		Map<String, Field> fields = ClassUtil.getFields(clazz, false);
		for (Entry<String, Field> entry : fields.entrySet()) {
			Field field = entry.getValue();
			if (field.isAnnotationPresent(Dict.class)) {
				Dict dict = field.getAnnotation(Dict.class);
				if (ret.containsKey(dict.category()))
					continue;
				List<DictCode> dicts = ResgainUtil.getBean(dict.impl()).getDataList(dict.category(), null, null);
				@SuppressWarnings("unchecked")
				Map<String, DictCode> data = new LinkedMap();
				for (DictCode dc : dicts) {
					data.put(dc.getCode(), dc);
				}
				ret.put(dict.category(), data);
			}
		}
		return ret;
	}

	private static Map<String, List<TreeData>> getFieldTreeDatas(Class<?> clazz) {
		Map<String, List<TreeData>> ret = new HashMap<String, List<TreeData>>();
		Map<String, Field> fields = ClassUtil.getFields(clazz, false);
		for (Entry<String, Field> entry : fields.entrySet()) {
			Field field = entry.getValue();
			if (field.isAnnotationPresent(TreeDict.class)) {
				TreeDict td = field.getAnnotation(TreeDict.class);
				if (ret.containsKey(td.category()))
					continue;
				List<TreeData> data = ResgainUtil.getBean(td.impl()).getTreeDataList(td.category());
				ret.put(td.category(), data);
			}
		}
		return ret;
	}

	private static Map<String, String> getMetaFiled(String name, String type) {
		Map<String, String> ret = new HashMap<String, String>();
		ret.put("name", name);
		if (!"date".equals(type.toLowerCase()))
			ret.put("type", type.toLowerCase());
		return ret;
	}

	private static Map<String, Object> getShowColumn(String name, String dataIndex, boolean sortable, String type) {
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("header", name);
		ret.put("dataIndex", dataIndex);
		ret.put("sortable", sortable);
		if (!StringUtils.isBlank(type))
			ret.put("type_", type);
		return ret;
	}

	private static Map<String, Object> getFormColumn(Field field, Label label) {
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("fieldLabel", label.name());
		ret.put("name", field.getName());
		ret.put("allowBlank", label.nullFlag());
		ret.put("xtype", label.xtype());
		if (field.isAnnotationPresent(Dict.class))
			ret.put("dict_category", field.getAnnotation(Dict.class).category());
		if (field.isAnnotationPresent(TreeDict.class))
			ret.put("tree_category", field.getAnnotation(TreeDict.class).category());
		if (label.value().length() > 0) {
			ret.put("value", label.value());
		}
		if (label.minLength() != 0)
			ret.put("minLength", label.minLength());
		if (label.maxLength() != 0)
			ret.put("maxLength", label.maxLength());
		if (!StringUtils.isBlank(label.vtype()))
			ret.put("vtype", label.vtype());
		if ("datetime".equals(label.type()))
			ret.put("format", "Y-m-d H:i:s");
		else if ("date".equals(label.type()))
			ret.put("format", "Y-m-d");
		else if ("time".equals(label.type()))
			ret.put("format", "H:i:s");
		if ("yes_no".equals(label.xtype())) {
			ret.put("xtype", "radiogroup");
			ret.put("columns", 2);
			ret.put("name", field.getName() + Math.round(Math.random() * 1000));
			ret.put("items", new RadioGroupItem[] { new RadioGroupItem("是", field.getName(), "true", true), new RadioGroupItem("否", field.getName(), "false", false) });
		}
		if ("memo".equals(label.xtype())) {
			ret.put("xtype", "textarea");
			ret.put("anchor", "100% 100%");
		}
		if (label.f_options().length() > 1) {
			for (String o : label.f_options().split(";")) {
				if (o.indexOf('=') > 1) {
					String ops[] = o.split("=");
					if (ops.length == 2)
						ret.put(ops[0].trim(), getValue(ops[1].trim()));
				}

			}
		}
		return ret;
	}

	private static Object getValue(String s) {
		if (StringUtils.isNumeric(s))
			return new Long(s);
		if ("true".equals(s) || "false".equals(s))
			return new Boolean(s);
		return s;
	}
}

class RadioGroupItem
{
	private String boxLabel;
	private String name;
	private Object inputValue;
	private boolean checked;

	public RadioGroupItem(String boxLabel, String name, Object inputValue, boolean checked) {
		super();
		this.boxLabel = boxLabel;
		this.name = name;
		this.inputValue = inputValue;
		this.checked = checked;
	}

	public String getBoxLabel() {
		return boxLabel;
	}

	public void setBoxLabel(String boxLabel) {
		this.boxLabel = boxLabel;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Object getInputValue() {
		return inputValue;
	}

	public void setInputValue(Object inputValue) {
		this.inputValue = inputValue;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}
}
