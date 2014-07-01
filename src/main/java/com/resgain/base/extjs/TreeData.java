package com.resgain.base.extjs;

import java.util.ArrayList;
import java.util.List;

/**
 * Extjs的树数据
 * @author gyl
 */
public class TreeData
{
	private String id;
	private String text;

	private List<TreeData> children = new ArrayList<TreeData>();

	public TreeData(String id, String text) {
		super();
		this.id = id;
		this.text = text;
	}

	public boolean isLeaf() {
		return children == null || children.isEmpty();
	}

	public boolean isExpanded() {
		return !isLeaf();
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

	public List<TreeData> getChildren() {
		return children;
	}
}
