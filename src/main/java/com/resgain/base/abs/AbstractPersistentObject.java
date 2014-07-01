package com.resgain.base.abs;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.resgain.base.annotation.Label;

/**
 * 业务实体类父类,一般业务实体类均要继承该类 该类主要定义了一些公共属性：id、创建人、创建日期、是否删除标记
 * @author memphis
 */
@MappedSuperclass
public class AbstractPersistentObject extends AbstractObject
{
	private static final long serialVersionUID = 1L;

	@Column(name = "CREATOR_ID", length = 32, insertable = true, updatable = false, nullable = true)
	@Label(name = "创建人", nullFlag = false)
	protected String creatorId;

	@Column(name = "CREATE_TIME", insertable = true, updatable = false, nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	@Label(name = "创建日期", nullFlag = false, xtype = "datefield", type = "datetime")
	protected Date createTime; // 创建日期

	@Column(name = "EDITOR_ID", length = 32, insertable = false, updatable = true, nullable = true)
	@Label(name = "最后修改人", nullFlag = true)
	protected String editorId;

	@Column(name = "EDIT_TIME", insertable = false, updatable = true, nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	@Label(name = "最后修改日期", nullFlag = true, xtype = "datefield", type = "datetime")
	protected Date editTime; // 修改日期

	public String getCreatorId()
	{
		return creatorId;
	}
	public void setCreatorId(String creatorId)
	{
		this.creatorId = creatorId;
	}

	public Date getCreateTime()
	{
		return createTime;
	}
	public void setCreateTime(Date createTime)
	{
		this.createTime = createTime;
	}

	public String getEditorId()
	{
		return editorId;
	}
	public void setEditorId(String editorId)
	{
		this.editorId = editorId;
	}

	public Date getEditTime()
	{
		return editTime;
	}
	public void setEditTime(Date editTime)
	{
		this.editTime = editTime;
	}
}
