package com.resgain.base.abs;

import java.util.List;

import com.resgain.base.extjs.TreeData;


/**
 * 数据字典数据服务提供接口
 * @author gyl
 */
public interface ITreeDictService
{
    /**
     * 获取指定分类和键值的数据字典列表
     * @param 字典分类
     * @return
     */
	public abstract List<TreeData> getTreeDataList(String category);
}
