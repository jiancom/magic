package com.resgain.base.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.resgain.base.abs.AbstractObject;
import com.resgain.base.annotation.Desc;
import com.resgain.base.annotation.Label;

/**
 * 字典分类表
 * @author gyl
 */
@Entity
@Table(name = "BASE_DICT_CATEGORY")
@Desc("字典分类")
public class DictCategory extends AbstractObject
{
	private static final long serialVersionUID = 1L;

	@Column(name = "CODE")
    @Label(name="代码", nullFlag=false)
    private String code;

	@Column(name = "PCODE")
    @Label(name="父代码", nullFlag=true)
    private String pcode;

	@Column(name = "NAME")
	@Label(name = "名称", nullFlag = false, maxLength=32)
	private String name;

	@Column(name = "FLAG")
	@Label(name = "标记", nullFlag = false, desc = "1-隐藏 2-开放 3-不能增加")
	private int flag;

	public DictCategory() {
		super();
	}

	public DictCategory(String code, String pcode, String name, int flag) {
		super();
		this.code = code;
		this.pcode = pcode;
		this.name = name;
		this.flag = flag;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPcode() {
		return pcode;
	}

	public void setPcode(String pcode) {
		this.pcode = pcode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}
}