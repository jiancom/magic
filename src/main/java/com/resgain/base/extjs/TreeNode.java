package com.resgain.base.extjs;


/**
 * Extjs的树属性定义类
 * @author gyl
 */
public class TreeNode
{
	private String id;
	private String text;
	private String cls;
	private boolean draggable;
	private boolean allowDrop;
	private boolean leaf;

	public TreeNode(String id, String text) {
		super();
		this.id = id;
		this.text = text;
		this.cls = "folder";
	}

	public TreeNode(String id, String text, String cls, boolean draggable, boolean allowDrop, boolean leaf) {
		super();
		this.id = id;
		this.text = text;
		this.cls = cls;
		this.draggable = draggable;
		this.allowDrop = allowDrop;
		this.leaf = leaf;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getCls() {
		return cls;
	}
	public void setCls(String cls) {
		this.cls = cls;
	}
	public boolean isDraggable() {
		return draggable;
	}
	public void setDraggable(boolean draggable) {
		this.draggable = draggable;
	}
	public boolean isAllowDrop() {
		return allowDrop;
	}
	public void setAllowDrop(boolean allowDrop) {
		this.allowDrop = allowDrop;
	}
	public boolean isLeaf() {
		return leaf;
	}
	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}
}
