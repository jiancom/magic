package com.resgain.base.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.resgain.base.abs.AbstractPersistentObject;
import com.resgain.base.annotation.Desc;
import com.resgain.base.annotation.Dict;
import com.resgain.base.annotation.Label;
import com.resgain.dragon.annotation.HtmlParameter;
import com.resgain.dragon.annotation.HtmlParameter.LEVEL;

/**
 * 部门信息实体类
 * @author gyl
 */
@Entity
@Table(name = "BASE_DEPT")
@Desc("部门信息")
public class Dept extends AbstractPersistentObject
{
    private static final long serialVersionUID = 1L;

	@Column(name = "PID", insertable = true, updatable = false)
	@Label(name = "父ID", nullFlag = true, xtype = "hidden")
	private String pid;

	@Column(name = "TYPE", insertable = true, updatable = false)
	@Label(name = "类型", nullFlag = false)
	@Dict(category = "DEPT_TYPE")
	private int type;

    @Column(name="NAME")
	@Label(name = "名称", nullFlag = false, maxLength=32)
	private String name;

    @Column(name="REMARK")
	@Label(name = "备注", nullFlag = true, maxLength=1024)
	@HtmlParameter(LEVEL.Normal)
	private String remark;

	@Transient
	private List<Dept> children = new ArrayList<Dept>();

	public Dept() {
		super();
	}

	public Dept(String pid, String name) {
		super();
		this.pid = pid;
		this.name = name;
	}

	public boolean isLeaf() {
		return children == null || children.isEmpty();
	}

	public boolean isExpanded() {
		return !isLeaf();
	}

	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}

	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
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

	public List<Dept> getChildren() {
		return children;
	}
}