package com.resgain.base.entity.pdm;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.resgain.base.abs.AbstractPersistentObject;
import com.resgain.base.annotation.Desc;
import com.resgain.base.annotation.Label;

@Entity
@javax.persistence.Table(name = "PDM_CATEGORY")
@Desc("分类信息")
public class PdmCategory extends AbstractPersistentObject
{
	private static final long serialVersionUID = 1L;

	@Column(name = "CODE")
	@Label(name = "分类代码", nullFlag = false)
	private String code;

	@Column(name = "NAME")
	@Label(name = "分类名称", nullFlag = false)
	private String name;

	public PdmCategory() {
		super();
	}

	public PdmCategory(String code, String name) {
		super();
		this.code = code;
		this.name = name;
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
}
