package com.resgain.base.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.resgain.base.abs.AbstractPersistentObject;
import com.resgain.base.annotation.Desc;
import com.resgain.base.annotation.Label;
import com.resgain.dragon.annotation.HtmlParameter;
import com.resgain.dragon.annotation.HtmlParameter.LEVEL;

/**
 * 角色信息实体类
 * @author gyl
 */
@Entity
@Table(name = "BASE_ROLE")
@Desc("角色信息")
public class Role extends AbstractPersistentObject
{
    private static final long serialVersionUID = 1L;

	@Column(name = "NAME")
	@Label(name = "角色名称", nullFlag = false, sortable = true)
	private String name;

	@Column(name = "REMARK")
	@Label(name = "备注", nullFlag = true, xtype = "memo")
	@HtmlParameter(LEVEL.Normal)
	private String remark;

	public Role() {
		super();
	}

	public Role(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}