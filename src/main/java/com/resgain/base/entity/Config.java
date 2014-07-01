package com.resgain.base.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.resgain.base.abs.AbstractPersistentObject;
import com.resgain.base.annotation.Desc;
import com.resgain.base.annotation.Label;

@Entity
@Table(name = "BASE_CONFIG")
@Desc("配置信息")
public class Config extends AbstractPersistentObject
{
	private static final long serialVersionUID = 1L;

	@Column(name = "CATEGORY")
	@Label(name = "类别", nullFlag = false)
	private String category;

	@Column(name = "KEY_")
	@Label(name = "键", nullFlag = false)
	private String key;

	@Column(name = "VALUE")
	@Label(name = "值", nullFlag = false)
	//@Type(type="encryptedString")
	private String value;

	@Column(name = "REMARK")
	@Label(name = "备注说明")
	private String remark;

	public Config() {
		super();
	}

	public Config(String category, String key, String value, String remark) {
		super();
		this.category = category;
		this.key = key;
		this.value = value;
		this.remark = remark;
	}

	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}

	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}

	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
