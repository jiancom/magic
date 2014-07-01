package com.resgain.base.abs;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.resgain.base.annotation.Label;

/**
 * 业务实体类父类,一般业务实体类均要继承该类 该类主要定义了一些公共属性：id、创建人、创建日期、是否删除标记
 * @author memphis
 */
@MappedSuperclass
public abstract class AbstractObject implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID", length = 36, nullable = false)
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@GeneratedValue(generator = "system-uuid")
	@Label(name = "ID", nullFlag = false, xtype = "hidden")
	protected String id; // ID

	@Label(name = "删除标记", nullFlag = false, xtype = "hidden")
	@Column(name = "DEL_FLAG", length = 1, nullable = false)
	protected boolean delFlag = false; // 是否删除标记

	@Transient
	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id = id;
	}

	@Transient
	public boolean isDelFlag()
	{
		return delFlag;
	}
	public void setDelFlag(boolean delFlag)
	{
		this.delFlag = delFlag;
	}

	@Transient
	public String getOtherString_() { // 用于某些方法统一处理，子类可以继承复写
		return null;
	}
}
