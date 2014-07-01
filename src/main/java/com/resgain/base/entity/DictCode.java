package com.resgain.base.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.resgain.base.abs.AbstractPersistentObject;
import com.resgain.base.abs.IDictObject;
import com.resgain.base.annotation.Desc;
import com.resgain.base.annotation.Label;

/**
 * 字典信息实体类
 * @author gyl
 */
@Entity
@Table(name = "BASE_DICT")
@Desc("字典信息")
public class DictCode extends AbstractPersistentObject implements IDictObject
{
	private static final long serialVersionUID = 1L;

	@Column(name = "CATEGORY", insertable = true, updatable = false)
	@Label(name = "字典类别", nullFlag = false)
    private String category;

    @Column(name="CODE")
    @Label(name="代码", nullFlag=false)
    private String code;

    @Column(name="PCODE")
    @Label(name="父代码", nullFlag=true)
    private String pcode;

    @Column(name="NAME")
	@Label(name = "名称", nullFlag = false)
    private String name;

    @Column(name="SORT")
	@Label(name = "排序", nullFlag = false)
    private long sort;

	public DictCode() {
		super();
	}

	public DictCode(String category, String code, String pcode, String name) {
		super();
		this.category = category;
		this.code = code;
		this.pcode = pcode;
		this.name = name;
		this.sort = System.currentTimeMillis();
	}

	@Override
	public String getOtherString_() {
		return " and category='" + category + "'";
	}

	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
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

	public long getSort() {
		return sort;
	}
	public void setSort(long sort) {
		this.sort = sort;
	}
}