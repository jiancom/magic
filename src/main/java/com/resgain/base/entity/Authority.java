package com.resgain.base.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.resgain.base.abs.AbstractObject;
import com.resgain.base.abs.IDictObject;
import com.resgain.base.annotation.Desc;
import com.resgain.base.annotation.Label;

/**
 * BASE_权限信息实体类
 * @author gyl
 */
@Entity
@Table(name = "BASE_AUTHORITY")
@Desc("权限信息")
public class Authority extends AbstractObject implements IDictObject
{
    private static final long serialVersionUID = 1L;

	@Label(name = "权限名称", nullFlag = false)
	private String name;

	@Label(name = "权限组ID", nullFlag = false)
	private String agId;

	public Authority() {
		super();
	}

	public Authority(String id, String name, String agId) {
		super();
		this.id = id;
		this.name = name;
		this.agId = agId;
	}

	 @Column(name="NAME")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	 @Column(name="AG_ID")
	public String getAgId() {
		return agId;
	}
	public void setAgId(String agId) {
		this.agId = agId;
	}

	@Id
	@Column(name = "ID", length = 36, nullable = false)
	@GeneratedValue(generator = "user-assigned")
	@GenericGenerator(name = "user-assigned", strategy = "assigned")
	@Override
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Transient
	public String getCode() {
		return id;
	}
}