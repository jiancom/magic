package com.resgain.base.entity.pdm;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.resgain.base.abs.AbstractPersistentObject;
import com.resgain.base.annotation.Label;

@Entity
@javax.persistence.Table(name = "PDM_DOMAIN")
public class PdmDomain extends AbstractPersistentObject
{
	private static final long serialVersionUID = 1L;

	@Column(name = "CODE")
	@Label(name = "代码", nullFlag = false)
	private String code;

	@Column(name = "NAME")
	@Label(name = "名称", nullFlag = false)
	private String name;

	@Column(name = "TYPE")
	@Label(name = "数据类型", nullFlag = false)
	private String type;

	@Column(name = "COMMENT")
	@Label(name = "注释", nullFlag = true)
	private String comment;


	public PdmDomain() {
		super();
	}


	public PdmDomain(String code, String name, String type, String comment) {
		super();
		this.code = code;
		this.name = name;
		this.type = type;
		this.comment = comment;
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

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
}