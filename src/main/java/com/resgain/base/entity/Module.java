package com.resgain.base.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.resgain.base.abs.AbstractObject;
import com.resgain.base.annotation.Desc;
import com.resgain.base.annotation.Label;

/**
 * 模块信息
 * @author gyl
 */
@Entity
@Table(name = "BASE_MODULE")
@Desc("模块信息")
public class Module extends AbstractObject
{
	private static final long serialVersionUID = 1L;

	@Column(name = "CODE")
	@Label(name = "代码", nullFlag = false)
	private String code;

	@Column(name = "NAME")
	@Label(name = "名称", nullFlag = false)
	private String name;

	@Column(name = "URL")
	@Label(name = "URL", nullFlag = false)
	private String url;

	@Column(name = "AUTH")
	@Label(name = "所需要权限", nullFlag = true)
	private String auth;

	@Column(name = "MODULE_ICON")
	@Label(name = "图标", nullFlag = true)
	private String icon;

	public Module() {
		super();
	}

	public Module(String code, String name, String url, String auth, String icon) {
		super();
		this.code = code;
		this.name = name;
		this.url = url;
		this.auth = auth;
		this.icon = icon;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

}