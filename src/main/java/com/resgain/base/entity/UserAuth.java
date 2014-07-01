package com.resgain.base.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.resgain.base.abs.AbstractPersistentObject;
import com.resgain.base.annotation.Desc;
import com.resgain.base.annotation.Label;

/**
 * 用户权限关联表实体类
 * @author gyl
 */
@Entity
@Table(name = "BASE_USER_AUTH")
@Desc("用户权限关联表")
public class UserAuth extends AbstractPersistentObject
{
    private static final long serialVersionUID = 1L;

    @Column(name="USER_ID")
	@Label(name = "用户ID", nullFlag = false)
	private String userId;

    @Column(name="AUTH_ID")
	@Label(name = "权限ID", nullFlag = false)
	private String authId;

	public UserAuth() {
		super();
	}

	public UserAuth(String userId, String authId) {
		super();
		this.userId = userId;
		this.authId = authId;
	}

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAuthId() {
		return authId;
	}
	public void setAuthId(String authId) {
		this.authId = authId;
	}
}