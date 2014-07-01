package com.resgain.base.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.resgain.base.abs.AbstractPersistentObject;
import com.resgain.base.annotation.Desc;
import com.resgain.base.annotation.Label;

/**
 * 用户角色关联表实体类
 * @author gyl
 */
@Entity
@Table(name = "BASE_USER_ROLE")
@Desc("用户角色关联")
public class UserRole extends AbstractPersistentObject
{
    private static final long serialVersionUID = 1L;

    @Column(name="USER_ID")
	@Label(name = "用户ID", nullFlag = false)
	private String userId;

    @Column(name="ROLE_ID")
	@Label(name = "角色ID", nullFlag = false)
	private String roleId;

	public UserRole() {
		super();
	}

	public UserRole(String userId, String roleId) {
		super();
		this.userId = userId;
		this.roleId = roleId;
	}

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
}