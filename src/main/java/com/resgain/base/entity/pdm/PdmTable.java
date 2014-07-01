package com.resgain.base.entity.pdm;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.resgain.base.abs.AbstractPersistentObject;
import com.resgain.base.annotation.Desc;
import com.resgain.base.annotation.Label;

@Entity
@javax.persistence.Table(name = "PDM_TABLE")
@Desc("表信息")
public class PdmTable extends AbstractPersistentObject
{
	private static final long serialVersionUID = 1L;

	@Column(name = "CODE")
	@Label(name = "表名称", nullFlag = false)
    private String code;  //表代码

	@Column(name = "NAME")
	@Label(name = "表含义", nullFlag = false)
    private String name;  //表含义

	@Column(name = "CATEGORY_ID")
	@Label(name = "所属分类ID", nullFlag = true)
	private String categoryId;

	public PdmTable() {
		super();
	}

	public PdmTable(String code, String name, String categoryId) {
		super();
		this.code = code;
		this.name = name;
		this.categoryId = categoryId;
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

	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
}