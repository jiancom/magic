package com.resgain.base.entity.pdm;

import javax.persistence.Entity;

import com.resgain.base.abs.AbstractPersistentObject;
import com.resgain.base.annotation.Desc;
import com.resgain.base.annotation.Label;
import com.resgain.sparrow.pdm.bean.Column;

@Entity
@javax.persistence.Table(name = "PDM_COLUMN")
@Desc("表字段信息")
public class PdmColumn extends AbstractPersistentObject
{
	private static final long serialVersionUID = 1L;

	@javax.persistence.Column(name = "TABLE_ID")
	@Label(name = "TABLE_ID", nullFlag = false)
	private String tableId;

	@javax.persistence.Column(name = "CODE")
	@Label(name = "代码", nullFlag = false)
	private String code;

	@javax.persistence.Column(name = "NAME")
	@Label(name = "含义", nullFlag = false)
    private String name;

	@javax.persistence.Column(name = "DOMAIN_ID")
	@Label(name = "DOMAIN_ID", nullFlag = true)
    private String domainId; //定义的数据域ID

	@javax.persistence.Column(name = "TYPE")
	@Label(name = "类型", nullFlag = false)
    private String type;

	@javax.persistence.Column(name = "LENGTH")
	@Label(name = "长度", nullFlag = true)
    private int length;

	@javax.persistence.Column(name = "PK_FLAG")
	@Label(name = "是否为主键", nullFlag = false)
    private boolean pkFlag; //

	@javax.persistence.Column(name = "NULL_FLAG")
	@Label(name = "是否可以为空", nullFlag = false)
    private boolean nullFlag; //是否可以为空

	@javax.persistence.Column(name = "UNIQUE_FLAG")
	@Label(name = "是否唯一", nullFlag = false)
    private boolean uniqueFlag;

	@javax.persistence.Column(name = "REF_TABLE_ID")
	@Label(name = "关联表ID", nullFlag = true)
    private String refTableId;

	@javax.persistence.Column(name = "REF_COLUMN_ID")
	@Label(name = "关联表字段ID", nullFlag = true)
    private String refColumnId;

	@javax.persistence.Column(name = "COMMENT")
	@Label(name = "注释", nullFlag = true)
    private String comment;

	public PdmColumn() {
		super();
	}

	public PdmColumn(String tableId, Column column) {
		super();
		this.tableId = tableId;
		this.code = column.getCode();
		this.name = column.getName();
		this.domainId = column.getDomainId();
		this.type = column.getType().replaceAll("\\([0-9\\.]+\\)", "");
		this.length = column.getLength();
		this.pkFlag = column.isPkFlag();
		this.nullFlag = column.isNullFlag();
		this.uniqueFlag = column.isUniqueFlag();
		this.refTableId = column.getRef()==null?null:column.getRef().getId();
		this.refColumnId = column.getRefId();
		this.comment = column.getComment();
	}

	public String getTableId() {
		return tableId;
	}

	public void setTableId(String tableId) {
		this.tableId = tableId;
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

	public String getDomainId() {
		return domainId;
	}

	public void setDomainId(String domainId) {
		this.domainId = domainId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public boolean isPkFlag() {
		return pkFlag;
	}

	public void setPkFlag(boolean pkFlag) {
		this.pkFlag = pkFlag;
	}

	public boolean isNullFlag() {
		return nullFlag;
	}

	public void setNullFlag(boolean nullFlag) {
		this.nullFlag = nullFlag;
	}

	public boolean isUniqueFlag() {
		return uniqueFlag;
	}

	public void setUniqueFlag(boolean uniqueFlag) {
		this.uniqueFlag = uniqueFlag;
	}

	public String getRefTableId() {
		return refTableId;
	}

	public void setRefTableId(String refTableId) {
		this.refTableId = refTableId;
	}

	public String getRefColumnId() {
		return refColumnId;
	}

	public void setRefColumnId(String refColumnId) {
		this.refColumnId = refColumnId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}
