package com.resgain.base.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.resgain.base.abs.AbstractObject;
import com.resgain.base.annotation.Desc;
import com.resgain.base.annotation.Label;

/**
 * 权限分组信息实体类
 * @author gyl
 */
@Entity
@Table(name = "BASE_AUTH_GROUP")
@Desc("权限分组信息")
public class AuthGroup extends AbstractObject
{
    private static final long serialVersionUID = 1L;

    @Column(name="PID")
	@Label(name = "父ID", nullFlag = true)
	private String pid;

    @Column(name="NAME")
	@Label(name = "分组名称", nullFlag = false)
	private String name;

	public AuthGroup() {
		super();
	}

	public AuthGroup(String pid, String name) {
		super();
		this.pid = pid;
		this.name = name;
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

}