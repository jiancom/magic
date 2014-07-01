package com.resgain.base.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.resgain.base.abs.AbstractPersistentObject;
import com.resgain.base.annotation.Desc;
import com.resgain.base.annotation.Label;

/**
 * 职位权限关联表实体类
 * @author gyl
 */
@Entity
@Table(name = "BASE_ROLE_AUTH")
@Desc("角色权限关联表")
public class RoleAuth extends AbstractPersistentObject
{
    private static final long serialVersionUID = 1L;

	@Column(name = "ROLE_ID")
	@Label(name = "角色ID", nullFlag = false)
	private String roleId;

    @Column(name="AUTH_ID")
	@Label(name = "权限ID", nullFlag = false)
	private String authId;

	public RoleAuth() {
		super();
	}

	public RoleAuth(String roleId, String authId) {
		super();
		this.roleId = roleId;
		this.authId = authId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getAuthId() {
		return authId;
	}

	public void setAuthId(String authId) {
		this.authId = authId;
	}
}