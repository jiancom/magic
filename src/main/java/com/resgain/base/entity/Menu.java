package com.resgain.base.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.resgain.base.abs.AbstractPersistentObject;
import com.resgain.base.annotation.Desc;
import com.resgain.base.annotation.Label;

/**
 * 菜单信息
 * @author gyl
 */
@Entity
@Table(name = "BASE_MENU")
@Desc("菜单信息")
public class Menu extends AbstractPersistentObject
{
	private static final long serialVersionUID = 1L;

	@Column(name = "PID")
	@Label(name = "PID", nullFlag = true)
	private String pid;

	@Column(name = "NAME")
	@Label(name = "名称", nullFlag = false)
	private String name;

	@Column(name = "TYPE")
	@Label(name = "类型", nullFlag = false)
	private int type; // 0--分类标识，1--实际功能

	@Column(name = "MODULE_CODE")
	@Label(name = "模块", nullFlag = true)
	private String moduleCode;

	@Column(name = "SORT", insertable = true, updatable = false)
	@Label(name = "顺序", nullFlag = false)
	private long sort;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "MODULE_CODE", referencedColumnName = "CODE", insertable = false, updatable = false)
	private Module module;

	@Transient
	private List<Menu> subList = new ArrayList<Menu>();

	public Menu() {
		super();
	}

	public Menu(String pid, String name) {
		super();
		this.pid = pid;
		this.name = name;
		this.type = 0;
		this.sort = System.currentTimeMillis();
	}

	public Menu(String pid, String name, String moduleCode) {
		super();
		this.pid = pid;
		this.name = name;
		this.type = 1;
		this.moduleCode = moduleCode;
		this.sort = System.currentTimeMillis();
	}

	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}

	public String getModuleCode() {
		return moduleCode;
	}
	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}

	public List<Menu> getSubList() {
		return subList;
	}

	public Module getModule() {
		return module;
	}
	public void setModule(Module module) {
		this.module = module;
	}

	public long getSort() {
		return sort;
	}
	public void setSort(long sort) {
		this.sort = sort;
	}
}